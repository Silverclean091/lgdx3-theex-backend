package com.lg.theex.domain.mood.repository;

import com.lg.theex.domain.mood.entity.CurrentMoodStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrentMoodStateRepository extends JpaRepository<CurrentMoodStateEntity, Long> {
    Optional<CurrentMoodStateEntity> findTopByOrderByStateIdDesc();
    void deleteAllByMoodMoodId(Long moodId);
}
