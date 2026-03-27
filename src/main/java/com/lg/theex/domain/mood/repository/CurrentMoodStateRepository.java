package com.lg.theex.domain.mood.repository;

import com.lg.theex.domain.mood.entity.CurrentMoodStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CurrentMoodStateRepository extends JpaRepository<CurrentMoodStateEntity, Long> {
    Optional<CurrentMoodStateEntity> findTopByOrderByStateIdDesc();

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from CurrentMoodStateEntity cms where cms.mood.moodId = :moodId")
    void deleteAllByMoodId(@Param("moodId") Long moodId);
}
