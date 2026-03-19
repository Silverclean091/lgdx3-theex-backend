package com.lg.theex.domain.mood.repository;

import com.lg.theex.domain.mood.entity.MoodCustomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoodCustomRepository extends JpaRepository<MoodCustomEntity, Long> {
    boolean existsByMoodIdAndUserIdUserId(Long moodId, Long userId);
    List<MoodCustomEntity> findAllByIsSharedTrueOrderByUserIdUserIdAscSaveCountDesc();
}
