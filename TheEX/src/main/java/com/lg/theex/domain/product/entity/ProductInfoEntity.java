package com.lg.theex.domain.product.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_info_id", nullable = false)
    private Long productInfoId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_code", nullable = false, unique = true)
    private String productCode;

    @Column(name = "product_no", nullable = false)
    private String productNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private ProductGroupEntity group;

    @Builder
    public ProductInfoEntity(
            String productName,
            String productCode,
            String productNo,
            ProductGroupEntity group
    ) {
        this.productName = productName;
        this.productCode = productCode;
        this.productNo = productNo;
        this.group = group;
    }
}
