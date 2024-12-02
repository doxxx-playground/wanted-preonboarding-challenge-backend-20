package com.wanted.market.domain.user.controller;

import com.wanted.market.common.dto.ResponseDto;
import com.wanted.market.domain.user.dto.UserCreateRequest;
import com.wanted.market.domain.user.dto.UserResponse;
import com.wanted.market.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원 가입
     * POST /api/users
     */
    @PostMapping
    public ResponseEntity<ResponseDto<UserResponse>> createUser(
            @RequestBody @Valid UserCreateRequest request
    ) {
        UserResponse user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(user));
    }

    /**
     * 내 정보 조회
     * GET /api/users/me
     */
    @GetMapping("/me")
    public ResponseEntity<ResponseDto<UserResponse>> getMyInfo(
            @AuthenticationPrincipal Long userId
    ) {
        UserResponse user = userService.getUserById(userId);
        return ResponseEntity.ok(ResponseDto.success(user));
    }
}