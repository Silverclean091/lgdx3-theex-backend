package com.lg.theex.domain.sensor.repository;

import com.lg.theex.domain.sensor.entity.SensorLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorLogRepository extends JpaRepository<SensorLogEntity, Long> {

    Optional<SensorLogEntity> findTopByOrderByRecordedAtDesc();
}
