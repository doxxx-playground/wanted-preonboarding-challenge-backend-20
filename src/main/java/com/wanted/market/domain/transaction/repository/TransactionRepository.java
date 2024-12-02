package com.wanted.market.domain.transaction.repository;

import com.wanted.market.domain.transaction.Transaction;
import com.wanted.market.domain.transaction.TransactionStatus;
import com.wanted.market.domain.user.User;
import com.wanted.market.domain.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * 구매자의 거래 내역을 조회합니다.
     */
    Page<Transaction> findByBuyer(User buyer, Pageable pageable);

    /**
     * 판매자의 거래 내역을 조회합니다.
     */
    Page<Transaction> findBySeller(User seller, Pageable pageable);

    /**
     * 구매자와 거래 상태로 거래 내역을 조회합니다.
     */
    Page<Transaction> findByBuyerAndStatus(User buyer, TransactionStatus status, Pageable pageable);

    /**
     * 판매자와 거래 상태로 거래 내역을 조회합니다.
     */
    Page<Transaction> findBySellerAndStatus(User seller, TransactionStatus status, Pageable pageable);

    /**
     * 상품에 대한 거래 내역을 조회합니다.
     */
    Page<Transaction> findByProduct(Product product, Pageable pageable);

    /**
     * 특정 상품의 완료된 거래 개수를 조회합니다.
     */
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.product = :product AND t.status = :status")
    long countCompletedTransactionsByProduct(@Param("product") Product product, @Param("status") TransactionStatus status);

    /**
     * 거래 ID와 구매자로 거래를 조회합니다.
     * 구매자 검증에 사용됩니다.
     */
    Optional<Transaction> findByIdAndBuyer(Long id, User buyer);

    /**
     * 거래 ID와 판매자로 거래를 조회합니다.
     * 판매자 검증에 사용됩니다.
     */
    Optional<Transaction> findByIdAndSeller(Long id, User seller);

    /**
     * 특정 상품의 진행 중인 거래가 있는지 확인합니다.
     * 상품 상태 변경 시 사용됩니다.
     */
    @Query("SELECT COUNT(t) > 0 FROM Transaction t WHERE t.product = :product AND t.status IN (:statuses)")
    boolean existsOngoingTransactionForProduct(@Param("product") Product product, @Param("statuses") List<TransactionStatus> statuses);

    /**
     * 특정 사용자(구매자 또는 판매자)의 최근 거래 내역을 조회합니다.
     */
    @Query("SELECT t FROM Transaction t WHERE t.buyer = :user OR t.seller = :user ORDER BY t.createdAt DESC")
    Page<Transaction> findRecentTransactionsByUser(@Param("user") User user, Pageable pageable);

    /**
     * 특정 상태의 오래된 거래들을 조회합니다.
     * 거래 자동 취소 등에 사용됩니다.
     */
    @Query("SELECT t FROM Transaction t WHERE t.status = :status AND t.createdAt < :beforeTime")
    List<Transaction> findOldTransactionsByStatus(@Param("status") TransactionStatus status, @Param("beforeTime") java.time.LocalDateTime beforeTime);
}