package com.lg.theex.domain.coffee.service;

import com.lg.theex.domain.coffee.entity.enumtype.CoffeeCategory;
import com.lg.theex.global.exception.CustomException;
import com.lg.theex.global.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Service
public class CoffeeModelProcessService {

    private static final Duration PROCESS_TIMEOUT = Duration.ofSeconds(15);
    private static final String DEFAULT_MODEL_RESOURCE = "models/coffee_model.pkl";
    private static final String DEFAULT_SCRIPT_RESOURCE = "python/predict_coffee_category.py";
    @Value("${coffee.model.python-command:python}")
    private String pythonCommand;

    @Value("${coffee.model.file-path:}")
    private String modelFilePath;

    @Value("${coffee.model.script-path:}")
    private String scriptPath;

    private volatile Path extractedModelPath;
    private volatile Path extractedScriptPath;
    private volatile PredictionCache cachedPrediction;

    public PredictionResult predict(
            Float temperature,
            Float humidity,
            Float temperatureDiff1h,
            LocalDateTime recordedAt
    ) {
        PredictionCache currentCache = cachedPrediction;
        if (currentCache != null && currentCache.matches(temperature, humidity, temperatureDiff1h, recordedAt)) {
            return currentCache.result();
        }

        String resolvedModelPath = resolveModelPath().toString();
        String resolvedScriptPath = resolveScriptPath().toString();

        validateFileExists(Path.of(resolvedModelPath), "Model file does not exist: " + resolvedModelPath);
        validateFileExists(Path.of(resolvedScriptPath), "Model script does not exist: " + resolvedScriptPath);

        List<String> command = new ArrayList<>();
        command.add(pythonCommand);
        command.add(resolvedScriptPath);
        command.add(resolvedModelPath);
        command.add(formatFloat(temperature));
        command.add(formatFloat(humidity));
        command.add(formatFloat(temperatureDiff1h));
        command.add(recordedAt.toString());

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.environment().put("PYTHONIOENCODING", "UTF-8");
        processBuilder.environment().put("PYTHONUTF8", "1");

        try {
            Process process = processBuilder.start();
            String output;
            String errorOutput;
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8)
            );
                 BufferedReader errorReader = new BufferedReader(
                         new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8)
            )) {
                output = reader.lines().reduce((left, right) -> left + System.lineSeparator() + right)
                        .orElse("");
                errorOutput = errorReader.lines().reduce((left, right) -> left + System.lineSeparator() + right)
                        .orElse("");
            }

            boolean finished = process.waitFor(PROCESS_TIMEOUT.toSeconds(), TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                throw new CustomException(ErrorCode.SERVICE_TIMEOUT, "Coffee model process timed out.");
            }

            if (process.exitValue() != 0) {
                throw new CustomException(
                        ErrorCode.INTERNAL_SERVER_ERROR,
                        "Coffee model process failed: " + mergeOutputs(output, errorOutput)
                );
            }

            String label = extractLastNonBlankLine(output);
            if (label.isBlank()) {
                throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "Coffee model returned empty result.");
            }

            PredictionResult result = new PredictionResult(label, mapLabelToCategory(label));
            cachedPrediction = new PredictionCache(temperature, humidity, temperatureDiff1h, recordedAt, result);
            return result;
        } catch (IOException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "Failed to execute coffee model: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "Coffee model execution interrupted.");
        }
    }

    private void validateFileExists(Path path, String message) {
        if (!Files.exists(path)) {
            throw new CustomException(ErrorCode.DATA_NOT_EXIST, message);
        }
    }

    private Path resolveModelPath() {
        if (modelFilePath != null && !modelFilePath.isBlank()) {
            return Path.of(modelFilePath);
        }
        return extractClasspathResource(DEFAULT_MODEL_RESOURCE, ".pkl", true);
    }

    private Path resolveScriptPath() {
        if (scriptPath != null && !scriptPath.isBlank()) {
            return Path.of(scriptPath);
        }
        return extractClasspathResource(DEFAULT_SCRIPT_RESOURCE, ".py", false);
    }

    private Path extractClasspathResource(String resourcePath, String suffix, boolean modelFile) {
        Path cachedPath = modelFile ? extractedModelPath : extractedScriptPath;
        if (cachedPath != null && Files.exists(cachedPath)) {
            return cachedPath;
        }

        synchronized (this) {
            Path currentPath = modelFile ? extractedModelPath : extractedScriptPath;
            if (currentPath != null && Files.exists(currentPath)) {
                return currentPath;
            }

            try {
                ClassPathResource resource = new ClassPathResource(resourcePath);
                if (!resource.exists()) {
                    throw new CustomException(
                            ErrorCode.DATA_NOT_EXIST,
                            "Classpath resource does not exist: " + resourcePath
                    );
                }

                Path tempFile = Files.createTempFile("coffee-model-", suffix);
                try (InputStream inputStream = resource.getInputStream()) {
                    Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
                }
                tempFile.toFile().deleteOnExit();

                if (modelFile) {
                    extractedModelPath = tempFile;
                } else {
                    extractedScriptPath = tempFile;
                }
                return tempFile;
            } catch (IOException e) {
                throw new CustomException(
                        ErrorCode.INTERNAL_SERVER_ERROR,
                        "Failed to extract classpath resource: " + resourcePath
                );
            }
        }
    }

    private String formatFloat(Float value) {
        return String.format(Locale.US, "%.4f", value);
    }

    private String extractLastNonBlankLine(String output) {
        String[] lines = output.split("\\R");
        for (int i = lines.length - 1; i >= 0; i--) {
            String line = lines[i].trim();
            if (!line.isBlank()) {
                return line;
            }
        }
        return "";
    }

    private String mergeOutputs(String output, String errorOutput) {
        String stdout = output == null ? "" : output.trim();
        String stderr = errorOutput == null ? "" : errorOutput.trim();

        if (stdout.isBlank()) {
            return stderr;
        }
        if (stderr.isBlank()) {
            return stdout;
        }
        return stdout + System.lineSeparator() + stderr;
    }

    private CoffeeCategory mapLabelToCategory(String label) {
        return switch (label.trim()) {
            case "BLACK" -> CoffeeCategory.BLACK;
            case "CREAMY" -> CoffeeCategory.CREAMY;
            case "SWEET" -> CoffeeCategory.SWEET;
            default -> throw new CustomException(
                    ErrorCode.INVALID_PARAMETER,
                    "Unsupported coffee model label: " + label
            );
        };
    }

    public record PredictionResult(
            String label,
            CoffeeCategory coffeeCategory
    ) {
    }

    private record PredictionCache(
            Float temperature,
            Float humidity,
            Float temperatureDiff1h,
            LocalDateTime recordedAt,
            PredictionResult result
    ) {
        private boolean matches(
                Float temperature,
                Float humidity,
                Float temperatureDiff1h,
                LocalDateTime recordedAt
        ) {
            return this.temperature.equals(temperature)
                    && this.humidity.equals(humidity)
                    && this.temperatureDiff1h.equals(temperatureDiff1h)
                    && this.recordedAt.equals(recordedAt);
        }
    }
}
