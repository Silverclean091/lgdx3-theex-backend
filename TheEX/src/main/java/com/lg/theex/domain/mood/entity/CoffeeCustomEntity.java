package com.lg.theex.domain.mood.entity;

import com.lg.theex.domain.coffee.entity.CoffeeRecipeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "coffee_custom")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoffeeCustomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coffee_custom_id", nullable = false)
    private Long coffeeCustomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private CoffeeRecipeEntity recipe;

    @Column(name = "product_code")
    private String productCode;

    @Builder
    public CoffeeCustomEntity(
            CoffeeRecipeEntity recipe,
            String productCode
    ) {
        this.recipe = recipe;
        this.productCode = productCode;
    }
}
