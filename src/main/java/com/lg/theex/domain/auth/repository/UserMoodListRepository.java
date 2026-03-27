package com.lg.theex.domain.auth.repository;

import com.lg.theex.domain.auth.entity.UserMoodListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserMoodListRepository extends JpaRepository<UserMoodListEntity, Long> {
    List<UserMoodListEntity> findAllByUserUserId(Long userId);
    boolean existsByUserUserIdAndMoodMoodId(Long userId, Long moodId);
    void deleteByUserUserIdAndMoodMoodId(Long userId, Long moodId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from UserMoodListEntity uml where uml.mood.moodId = :moodId")
    void deleteAllByMoodId(@Param("moodId") Long moodId);
}
