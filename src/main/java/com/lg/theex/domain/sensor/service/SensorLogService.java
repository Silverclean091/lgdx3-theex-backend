package com.lg.theex.domain.sensor.service;

import com.lg.theex.domain.sensor.dto.response.SensorLogResponseDTO;
import com.lg.theex.domain.sensor.entity.SensorLogEntity;
import com.lg.theex.domain.sensor.repository.SensorLogRepository;
import com.lg.theex.global.exception.ErrorCode;
import com.lg.theex.global.exception.exceptionType.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SensorLogService {

    private final SensorLogRepository sensorLogRepository;

    @Transactional
    public void save(Float temperature, Float humidity) {
        sensorLogRepository.save(new SensorLogEntity(temperature, humidity));
    }

    @Transactional(readOnly = true)
    public SensorLogResponseDTO getLatest() {
        SensorLogEntity sensorLog = sensorLogRepository.findTopByOrderByRecordedAtDesc()
                .orElseThrow(() -> new BadRequestException(ErrorCode.DATA_NOT_EXIST, "Sensor log does not exist."));

        return new SensorLogResponseDTO(
                sensorLog.getTemperature(),
                sensorLog.getHumidity(),
                sensorLog.getRecordedAt()
        );
    }
}
