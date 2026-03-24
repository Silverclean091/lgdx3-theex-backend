package com.lg.theex.domain.mood.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "current_mood_state")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurrentMoodStateEntity {

    @Id
    @Column(name = "state_id", nullable = false)
    private Long stateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mood_id", nullable = false)
    private MoodCustomEntity mood;

    @Column(name = "light_r", nullable = false)
    private Integer lightR;

    @Column(name = "light_g", nullable = false)
    private Integer lightG;

    @Column(name = "light_b", nullable = false)
    private Integer lightB;

    @Builder
    public CurrentMoodStateEntity(Long stateId, MoodCustomEntity mood,
                                  Integer lightR, Integer lightG, Integer lightB) {
        this.stateId = stateId;
        this.mood = mood;
        this.lightR = lightR;
        this.lightG = lightG;
        this.lightB = lightB;
    }
}
