package com.tyranno.ssg.recent.presentation;

import com.tyranno.ssg.global.ResponseEntity;
import com.tyranno.ssg.recent.application.RecentService;
import com.tyranno.ssg.recent.dto.RecentViewedDto;
import com.tyranno.ssg.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "최근 본 상품", description = "최근 본 상품 API")
@RequestMapping("/api/v1/recent")
@Slf4j
public class RecentController {

    private final RecentService recentService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "최근 본 상품 조회", description = "uuid로 DB의 상품 목록 조회")
    @GetMapping("/user")
    public ResponseEntity<?> getRecentByUuid(@RequestHeader(value = "Authorization", required = false) String token) {
        String uuid = jwtTokenProvider.tokenToUuid(token);
        List<RecentViewedDto> recentViewedDto = recentService.getRecentByUser(uuid);
        return new ResponseEntity<>(recentViewedDto);
    }

    @Operation(summary = "최근 본 상품 담기", description = "uuid, productId로 최근 본 상품에 담기")
    @PostMapping("/{product_id}")
    public ResponseEntity<?> addRecent(@PathVariable Long product_id, @RequestHeader("Authorization") String token) {
        String uuid = jwtTokenProvider.tokenToUuid(token);
        String result = recentService.addRecentByProduct(product_id, uuid);
        return new ResponseEntity<>(result);
    }
}