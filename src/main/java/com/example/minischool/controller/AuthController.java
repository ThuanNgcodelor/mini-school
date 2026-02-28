package com.example.minischool.controller;

import com.example.minischool.config.JwtAuthenticationFilter;
import com.example.minischool.dto.ApiResponse;
import com.example.minischool.request.LoginRequest;
import com.example.minischool.request.RegisterStudentRequest;
import com.example.minischool.request.RegisterTeacherRequest;
import com.example.minischool.service.AuthService;
import com.example.minischool.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    /**
     * POST /api/auth/login
     * Đăng nhập → set JWT cookie (90 ngày)
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, Object>>> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response) {
        try {
            String token = authService.login(request);
            Map<String, Object> userInfo = authService.getCurrentUserInfo(request.getPhone());

            // Set JWT cookie — 90 days
            Cookie cookie = new Cookie(JwtAuthenticationFilter.JWT_COOKIE_NAME, token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge((int) (jwtService.getExpirationMs() / 1000)); // 90 days in seconds
            cookie.setSecure(false); // set true khi deploy HTTPS
            response.addCookie(cookie);

            return ResponseEntity.ok(ApiResponse.ok("Đăng nhập thành công", userInfo));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Sai số điện thoại hoặc mật khẩu"));
        }
    }

    /**
     * POST /api/auth/register/teacher
     * Đăng ký GV (cần invite code)
     */
    @PostMapping("/register/teacher")
    public ResponseEntity<ApiResponse<Void>> registerTeacher(@RequestBody RegisterTeacherRequest request) {
        try {
            authService.registerTeacher(request);
            return ResponseEntity.ok(ApiResponse.ok("Đăng ký giáo viên thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * POST /api/auth/register/student
     * Đăng ký HS (cần mã lớp)
     */
    @PostMapping("/register/student")
    public ResponseEntity<ApiResponse<Void>> registerStudent(@RequestBody RegisterStudentRequest request) {
        try {
            authService.registerStudent(request);
            return ResponseEntity.ok(ApiResponse.ok("Đăng ký học sinh thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * POST /api/auth/logout
     * Xóa JWT cookie
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie(JwtAuthenticationFilter.JWT_COOKIE_NAME, null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok(ApiResponse.ok("Đăng xuất thành công"));
    }

    /**
     * GET /api/auth/me
     * Lấy thông tin user đang đăng nhập
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<Map<String, Object>>> me(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401)
                    .body(ApiResponse.error("Chưa đăng nhập"));
        }
        Map<String, Object> info = authService.getCurrentUserInfo(authentication.getName());
        return ResponseEntity.ok(ApiResponse.ok("OK", info));
    }
}
