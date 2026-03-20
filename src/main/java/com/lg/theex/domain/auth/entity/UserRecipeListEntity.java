package com.lg.theex.domain.auth.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_recipe_list")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRecipeListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_recipe_id", nullable = false)
    private Long userRecipeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersInfoEntity user;

    @Column(name = "recipe_id", nullable = false)
    private String recipeId;

    @Column(name = "is_coffee", nullable = false)
    private Boolean isCoffee;

    @Builder
    private UserRecipeListEntity(
            Long userRecipeId,
            UsersInfoEntity user,
            String recipeId,
            Boolean isCoffee
    ) {
        this.userRecipeId = userRecipeId;
        this.user = user;
        this.recipeId = recipeId;
        this.isCoffee = isCoffee;
    }
}