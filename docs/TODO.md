1. 인증/인가 구현 (HIGH)

```txt
- Spring Security + JWT 도입
  └── SecurityConfig: 기본 보안 설정
  └── JwtAuthenticationFilter: 토큰 검증
  └── JwtTokenProvider: 토큰 생성/관리
- 회원/비회원 접근 권한 분리
  └── @PreAuthorize 어노테이션 활용
  └── 상품 조회 API: 비회원 허용
  └── 구매/판매 API: 회원만 허용
```

2. 동시성 처리 (HIGH)

```txt
- 상품 구매시 동시성 제어
    └── @Version 활용한 Optimistic Lock
    └── Pessimistic Lock으로 재고 관리
    └── 트랜잭션 격리 수준 설정
```

```java

@Transactional(isolation = Isolation.SERIALIZABLE)
public TransactionResponse purchaseProduct(/*...*/) {
    try {
        Product product = productRepository.findByIdWithPessimisticLock(productId);
        // 구매 로직
    } catch (OptimisticLockingFailureException e) {
        throw new CustomException(ErrorCode.CONCURRENT_UPDATE_ERROR);
    }
}
```

3. 예외 처리 고도화 (MEDIUM)

```txt
- 도메인별 상세 예외 클래스 구현
    └── ProductException
        ├── OutOfStockException
        └── InvalidPriceException
    └── TransactionException
        ├── InvalidStatusException
        └── DuplicatePurchaseException
    └── UserException
        ├── UserNotFoundException
        └── DuplicateEmailException

- 트랜잭션 롤백 처리
@Transactional(rollbackFor = Exception.class)

```

4. 테스트 코드 작성 (MEDIUM)

```txt
- 단위 테스트
    └── 각 Service 클래스별 테스트
    └── Repository 쿼리 테스트
- 통합 테스트
    └── API 엔드포인트 테스트
    └── 동시성 시나리오 테스트
- 시나리오 테스트
    └── 구매 프로세스 E2E 테스트
```

5. 비즈니스 검증 강화 (LOW)

```txt
- Validation 추가
    └── 상품 가격/수량 유효성 검증
    └── 거래 상태 전이 규칙 검증
- 엣지 케이스 처리
    └── 동시 구매 요청 처리
    └── 재고 소진시 처리
```
