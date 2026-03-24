package com.lg.theex.global.serial;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "serial")
public class SerialProperties {

    private boolean enabled = true;

    private boolean failFast = false;

    @NotBlank
    private String port = "COM3";

    @Min(1)
    private int baudRate = 115200;

    @Min(1)
    private int writeTimeoutMs = 2000;
}
