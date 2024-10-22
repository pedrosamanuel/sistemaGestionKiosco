package com.inventario_ms.Security.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.inventario_ms.Security.exception.TokenRefreshException;
import com.inventario_ms.Security.jwt.JwtUtils;
import com.inventario_ms.Security.model.*;
import com.inventario_ms.Security.payload.request.LoginRequest;
import com.inventario_ms.Security.payload.request.SignupRequest;
import com.inventario_ms.Security.payload.request.TokenRefreshRequest;
import com.inventario_ms.Security.payload.response.JwtResponse;
import com.inventario_ms.Security.payload.response.MessageResponse;
import com.inventario_ms.Security.payload.response.TokenRefreshResponse;
import com.inventario_ms.Security.repository.RoleRepository;
import com.inventario_ms.Security.repository.UserRepository;
import com.inventario_ms.Security.service.RefreshTokenService;
import com.inventario_ms.Security.service.UserDetailsImpl;
import com.inventario_ms.Security.service.UserDetailsServiceImpl;
import com.inventario_ms.Security.service.UserMarketRoleService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.hibernate.type.descriptor.sql.internal.Scale6IntervalSecondDdlType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UserMarketRoleService userMarketRoleService;
    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        Cookie jwtCookie = new Cookie("jwtToken", jwt);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        refreshTokenService.deleteByUserId(userDetails.getId());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        Cookie newRefreshTokenCookie = new Cookie("refreshToken", refreshToken.getToken());
        newRefreshTokenCookie.setHttpOnly(true);
        newRefreshTokenCookie.setSecure(false);
        newRefreshTokenCookie.setPath("/");
        newRefreshTokenCookie.setMaxAge(30 * 24 * 60 * 60);

        response.addCookie(jwtCookie);
        response.addCookie(newRefreshTokenCookie);

        List<UserMarketRole> userMarketRoles = userMarketRoleService.getUserMarketRoleByUserId(userDetails.getId());

        return ResponseEntity
                .ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), userMarketRoles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }


        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));



        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        UserMarketRole userMarketRole = new UserMarketRole(user, null, userRole);

        Set<UserMarketRole> userMarketRoles = new HashSet<>();
        userMarketRoles.add(userMarketRole);

        user.setUserMarketRoles(userMarketRoles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(HttpServletResponse response,
                                          @CookieValue(name = "jwtToken") String jwtCookie,
                                          @CookieValue(name = "refreshToken") String refreshTokenCookie) {
        String username = jwtUtils.getUserNameFromJwtToken(jwtCookie);

        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenCookie).orElseThrow(() -> new TokenRefreshException(refreshTokenCookie,
                "Refresh token is not in database!"));

        refreshToken = refreshTokenService.verifyExpiration(refreshToken);
        String jwtToken = jwtUtils.generateTokenFromUsername(username);

        Cookie newJwtCookie = new Cookie("jwtToken", jwtToken);
        newJwtCookie.setHttpOnly(true);
        newJwtCookie.setSecure(false);
        newJwtCookie.setPath("/");
        newJwtCookie.setMaxAge(60 * 60);

        response.addCookie(newJwtCookie);

        return ResponseEntity.ok(new TokenRefreshResponse(jwtToken, refreshToken.getToken()));
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())
                        || "marketId".equals(cookie.getName())
                        || "refreshToken".equals(cookie.getName())) {
                    Cookie deleteCookie = new Cookie(cookie.getName(), null);
                    deleteCookie.setPath("/");
                    deleteCookie.setHttpOnly(true);
                    deleteCookie.setSecure(false);
                    deleteCookie.setMaxAge(0);
                    response.addCookie(deleteCookie);
                }
            }
        }

        return ResponseEntity.ok("Logout exitoso. Cookies eliminadas.");
    }
}
