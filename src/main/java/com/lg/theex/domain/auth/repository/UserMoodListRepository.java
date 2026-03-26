package com.lg.theex.domain.auth.repository;

import com.lg.theex.domain.auth.entity.UserMoodListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserMoodListRepository extends JpaRepository<UserMoodListEntity, Long> {
    List<UserMoodListEntity> findAllByUserUserId(Long userId);
    boolean existsByUserUserIdAndMoodMoodId(Long userId, Long moodId);
    void deleteByUserUserIdAndMoodMoodId(Long userId, Long moodId);
    void deleteAllByMoodMoodId(Long moodId);
}
