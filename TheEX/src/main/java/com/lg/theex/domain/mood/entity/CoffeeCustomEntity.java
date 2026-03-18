package com.lg.theex.domain.mood.entity;

import com.lg.theex.domain.coffee.entity.CoffeeRecipeEntity;
import com.lg.theex.domain.product.entity.ProductInfoEntity;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_code", referencedColumnName = "product_code")
    private ProductInfoEntity product;

    @Builder
    public CoffeeCustomEntity(
            CoffeeRecipeEntity recipe,
            ProductInfoEntity product
    ) {
        this.recipe = recipe;
        this.product = product;
    }
}