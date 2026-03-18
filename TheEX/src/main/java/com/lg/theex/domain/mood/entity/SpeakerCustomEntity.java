package com.lg.theex.domain.mood.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "speaker_custom")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpeakerCustomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "speaker_id", nullable = false)
    private Long speakerId;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "music_link")
    private String musicLink;

    @Column(name = "volume")
    private Integer volume;

    @Builder
    public SpeakerCustomEntity(Long speakerId, String productCode, String musicLink, Integer volume) {
        this.speakerId = speakerId;
        this.productCode = productCode;
        this.musicLink = musicLink;
        this.volume = volume;
    }
}