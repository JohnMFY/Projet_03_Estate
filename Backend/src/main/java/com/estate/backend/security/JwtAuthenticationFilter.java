package com.estate.backend.security;

// User et BDD
import com.estate.backend.entity.User;
import com.estate.backend.repository.UserRepository;

// Gestion JWT
import io.jsonwebtoken.JwtException;

// Gestion requête / réponse
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Auth Spring
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

// Filtre Spring
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

// Utils
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(
        JwtService jwtService,
        UserRepository userRepository
    ) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            String email = jwtService.extractEmail(token);

            User user = userRepository
                .findByEmail(email)
                .orElse(null);

            if (user != null) {
                UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        Collections.emptyList()
                    );

                SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);
            }

        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}