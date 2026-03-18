package com.lg.theex.domain.coffee.entity;

import com.lg.theex.domain.coffee.entity.enumtype.CapsuleBrands;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "coffee_capsule")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoffeeCapsuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "capsule_id", nullable = false)
    private Long capsuleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "capsule_brand", nullable = false)
    private CapsuleBrands capsuleBrand;

    @Column(name = "capsule_name")
    private String capsuleName;

    @Builder
    public CoffeeCapsuleEntity(
            Long capsuleId,
            CapsuleBrands capsuleBrand,
            String capsuleName
    ) {
        this.capsuleId = capsuleId;
        this.capsuleBrand = capsuleBrand;
        this.capsuleName = capsuleName;
    }
}