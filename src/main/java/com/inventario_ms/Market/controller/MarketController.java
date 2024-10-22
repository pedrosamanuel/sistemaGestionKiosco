package com.inventario_ms.Market.controller;


import com.inventario_ms.Generic.NonDependent.NonDependentGenericController;
import com.inventario_ms.Market.domain.Market;
import com.inventario_ms.Market.domain.MarketProduct;
import com.inventario_ms.Market.dto.MarketDTO;
import com.inventario_ms.Market.repository.MarketRepository;
import com.inventario_ms.Market.request.MarketProductRequest;
import com.inventario_ms.Market.service.MarketService;
import com.inventario_ms.Security.jwt.JwtUtils;
import com.inventario_ms.Security.service.UserDetailsImpl;
import com.inventario_ms.Security.service.UserDetailsServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/market")
public class MarketController extends NonDependentGenericController<Market, MarketDTO,
        MarketService, MarketRepository, Long> {

    private final MarketService marketService;
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    public MarketController(MarketService marketService, JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsServiceImpl) {
        super(marketService);
        this.marketService = marketService;
        this.jwtUtils = jwtUtils;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }
    @PostMapping("/withUser")
    public ResponseEntity<String> create(@RequestBody Market market,
                                         @CookieValue(name = "jwtToken") String cookie) {
        String username = jwtUtils.getUserNameFromJwtToken(cookie);
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userDetailsServiceImpl.loadUserByUsername(username);
        marketService.save(market, userDetailsImpl.getId());
        return ResponseEntity.ok("Entidad Creada");
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MarketDTO>> getAllByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(marketService.findAllByUserId(userId));
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<String> selectMarket(
            @RequestParam Long marketId,
            @PathVariable Long userId,
            HttpServletResponse response) {


        if (!marketService.marketBelongsToUser(marketId, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado");
        }

        Cookie marketCookie = new Cookie("marketId", marketId.toString());
        marketCookie.setHttpOnly(true);
        marketCookie.setSecure(false);
        marketCookie.setPath("/");
        marketCookie.setMaxAge(24 * 60 * 60);

        response.addCookie(marketCookie);

        return ResponseEntity.ok("Market seleccionado correctamente");
    }


}
