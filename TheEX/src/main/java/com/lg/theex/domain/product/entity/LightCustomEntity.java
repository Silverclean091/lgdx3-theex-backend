package com.lg.theex.domain.product.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "light_custom")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LightCustomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "light_id", nullable = false)
    private Long lightId;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "light_color")
    private String lightColor;

    @Column(name = "light_bright")
    private Integer lightBright;

    @Builder
    public LightCustomEntity(Long lightId, String productCode, String lightColor, Integer lightBright) {
        this.lightId = lightId;
        this.productCode = productCode;
        this.lightColor = lightColor;
        this.lightBright = lightBright;
    }
}