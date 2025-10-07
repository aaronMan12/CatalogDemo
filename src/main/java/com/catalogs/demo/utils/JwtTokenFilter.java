package com.catalogs.demo.utils;

import com.catalogs.demo.utils.JwtTokenProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        logger.info("Processing request to: " + request.getRequestURI()); // ✅ Log para debug

        String token = resolveToken(request);
        System.out.println("Resultado de obtención de token existente: "+token);

        if (token != null) {
            logger.info("Token found: " + token.substring(0, Math.min(20, token.length())) + "..."); // ✅ Log token
            if (jwtTokenProvider.validateToken(token)) {
                String username = jwtTokenProvider.getUserNameFromToken(token);
                logger.info("Authenticated user: " + username); // ✅ Log usuario

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(username, null, null);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                logger.warn("Invalid JWT token"); // ✅ Log token inválido
            }
        } else {
            logger.info("No JWT token found in request"); // ✅ Log sin token
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}