package com.wanted.market.domain.transaction.controller;

import com.wanted.market.common.dto.ResponseDto;
import com.wanted.market.domain.transaction.dto.TransactionCreateRequest;
import com.wanted.market.domain.transaction.dto.TransactionResponse;
import com.wanted.market.domain.transaction.dto.TransactionStatusUpdateRequest;
import com.wanted.market.domain.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController implements TransactionControllerSpec {

    private final TransactionService transactionService;

    /**
     * 거래 생성 (구매하기)
     * POST /api/transactions
     */
    @PostMapping
    public ResponseEntity<ResponseDto<TransactionResponse>> createTransaction(
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid TransactionCreateRequest request
    ) {
        TransactionResponse transaction = transactionService.createTransaction(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(transaction));
    }

    /**
     * 거래 상태 변경 (승인/확정)
     * PUT /api/transactions/{id}/status
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<ResponseDto<TransactionResponse>> updateTransactionStatus(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long id,
            @RequestBody @Valid TransactionStatusUpdateRequest request
    ) {
        TransactionResponse transaction = transactionService.updateTransactionStatus(userId, id, request);
        return ResponseEntity.ok(ResponseDto.success(transaction));
    }

    /**
     * 내 구매 목록
     * GET /api/transactions/my/purchases
     */
    @GetMapping("/my/purchases")
    public ResponseEntity<ResponseDto<Page<TransactionResponse>>> getPurchasedTransactions(
            @AuthenticationPrincipal Long userId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<TransactionResponse> transactions = transactionService.getPurchasedTransactions(userId, pageable);
        return ResponseEntity.ok(ResponseDto.success(transactions));
    }

    /**
     * 내 진행중 거래 목록
     * GET /api/transactions/my/ongoing
     */
    @GetMapping("/my/ongoing")
    public ResponseEntity<ResponseDto<Page<TransactionResponse>>> getOngoingTransactions(
            @AuthenticationPrincipal Long userId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<TransactionResponse> transactions = transactionService.getOngoingTransactions(userId, pageable);
        return ResponseEntity.ok(ResponseDto.success(transactions));
    }
}
