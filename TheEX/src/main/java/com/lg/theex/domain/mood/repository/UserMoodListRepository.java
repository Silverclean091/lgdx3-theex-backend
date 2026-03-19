package com.lg.theex.domain.mood.repository;

import com.lg.theex.domain.auth.entity.UserMoodListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMoodListRepository extends JpaRepository<UserMoodListEntity, Long> {
    boolean existsByUserUserIdAndMoodMoodId(Long userId, Long moodId);
}
