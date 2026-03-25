package com.lg.theex.domain.sensor.controller;

import com.lg.theex.domain.sensor.dto.response.SensorLogResponseDTO;
import com.lg.theex.domain.sensor.service.SensorLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sensor")
@RequiredArgsConstructor
public class SensorLogController {

    private final SensorLogService sensorLogService;

    @GetMapping("/latest")
    public SensorLogResponseDTO getLatest() {
        return sensorLogService.getLatest();
    }
}
