package com.wanted.market.domain.product.controller;

import com.wanted.market.common.dto.ResponseDto;
import com.wanted.market.domain.product.dto.ProductCreateRequest;
import com.wanted.market.domain.product.dto.ProductResponse;
import com.wanted.market.domain.product.dto.ProductUpdateRequest;
import com.wanted.market.domain.product.service.ProductService;
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
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 상품 목록 조회 (비회원 가능)
     * GET /api/products
     */
    @GetMapping
    public ResponseEntity<ResponseDto<Page<ProductResponse>>> getProducts(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ProductResponse> products = productService.getAvailableProducts(pageable);
        return ResponseEntity.ok(ResponseDto.success(products));
    }

    /**
     * 상품 상세 조회 (비회원 가능)
     * GET /api/products/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ProductResponse>> getProduct(
            @PathVariable Long id
    ) {
        ProductResponse product = productService.getProduct(id);
        return ResponseEntity.ok(ResponseDto.success(product));
    }

    /**
     * 상품 등록 (회원만)
     * POST /api/products
     */
    @PostMapping
    public ResponseEntity<ResponseDto<ProductResponse>> createProduct(
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid ProductCreateRequest request
    ) {
        ProductResponse product = productService.createProduct(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(product));
    }

    /**
     * 상품 수정 (판매자만)
     * PUT /api/products/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<ProductResponse>> updateProduct(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long id,
            @RequestBody @Valid ProductUpdateRequest request
    ) {
        ProductResponse product = productService.updateProduct(userId, id, request);
        return ResponseEntity.ok(ResponseDto.success(product));
    }
}