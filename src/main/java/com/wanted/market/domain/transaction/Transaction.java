package com.wanted.market.domain.transaction;

import com.wanted.market.domain.base.BaseEntity;
import com.wanted.market.domain.product.Product;
import com.wanted.market.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(nullable = false)
    private BigDecimal purchasePrice;

    @Builder
    private Transaction(Product product, User buyer, User seller, BigDecimal purchasePrice) {
        this.product = product;
        this.buyer = buyer;
        this.seller = seller;
        this.purchasePrice = purchasePrice;
        this.status = TransactionStatus.REQUESTED;
    }

    public void updateStatus(TransactionStatus status) {
        this.status = status;
    }

    public boolean isBuyerMatch(User user) {
        return this.buyer.getId().equals(user.getId());
    }

    public boolean isSellerMatch(User user) {
        return this.seller.getId().equals(user.getId());
    }
}