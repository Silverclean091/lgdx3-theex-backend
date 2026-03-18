package com.lg.theex.domain.mood.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mood_colorset")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoodColorsetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "colorset_id", nullable = false)
    private Long colorsetId;

    @Column(name = "colorset_name", nullable = false)
    private String colorsetName;

    @Column(name = "colorset_main", nullable = false)
    private String colorsetMain;

    @Column(name = "color_opacity1", nullable = false)
    private Integer colorOpacity1;

    @Column(name = "color_opacity2", nullable = false)
    private Integer colorOpacity2;

    @Column(name = "color_opacity3", nullable = false)
    private Integer colorOpacity3;

    @Builder
    public MoodColorsetEntity(Long colorsetId, String colorsetName, String colorsetMain,
                              Integer colorOpacity1, Integer colorOpacity2, Integer colorOpacity3) {
        this.colorsetId = colorsetId;
        this.colorsetName = colorsetName;
        this.colorsetMain = colorsetMain;
        this.colorOpacity1 = colorOpacity1;
        this.colorOpacity2 = colorOpacity2;
        this.colorOpacity3 = colorOpacity3;
    }
}