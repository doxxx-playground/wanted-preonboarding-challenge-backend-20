package com.wanted.market.domain.product;

import com.wanted.market.domain.base.BaseEntity;
import com.wanted.market.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    private Integer quantity;

    @Builder
    private Product(String name, BigDecimal price, User seller, Integer quantity) {
        this.name = name;
        this.price = price;
        this.seller = seller;
        this.status = ProductStatus.ON_SALE;
        this.quantity = quantity;
    }

    public void updateStatus(ProductStatus status) {
        this.status = status;
    }

    public void updateQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public boolean isSellerMatch(User user) {
        return this.seller.getId().equals(user.getId());
    }
}