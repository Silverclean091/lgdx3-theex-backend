package com.lg.theex.domain.mood.repository;

import com.lg.theex.domain.mood.entity.MoodCustomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodCustomRepository extends JpaRepository<MoodCustomEntity, Long> {
}
