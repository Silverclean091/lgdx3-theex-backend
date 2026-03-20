package com.lg.theex.domain.product.entity;

import com.lg.theex.domain.auth.entity.UsersInfoEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_group")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersInfoEntity user;

    @Builder
    public ProductGroupEntity(
            Long groupId,
            String groupName,
            UsersInfoEntity user
    ) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.user = user;
    }
}