package com.lg.theex.domain.auth.entity;

import com.lg.theex.domain.mood.entity.MoodCustomEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_mood_list")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserMoodListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_mood_id", nullable = false)
    private Long userMoodId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersInfoEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mood_id", nullable = false)
    private MoodCustomEntity mood;

    @Builder
    private UserMoodListEntity(
            Long userMoodId,
            UsersInfoEntity user,
            MoodCustomEntity mood
    ) {
        this.userMoodId = userMoodId;
        this.user = user;
        this.mood = mood;
    }
}