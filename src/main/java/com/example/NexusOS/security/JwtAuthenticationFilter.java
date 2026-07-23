package com.example.NexusOS.security;

import com.mongodb.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Service responsible for JWT parsing and validation.
    private final JwtService jwtService;

    // Loads user details from the database.
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Intercepts every incoming HTTP request once and authenticates
     * the user if a valid JWT is present in the Authorization header.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Read the Authorization header from the incoming request.
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Skip authentication if the request does not contain a Bearer token.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Remove the "Bearer " prefix to obtain the raw JWT.
        String jwtToken = authHeader.substring(7);

        // Extract the user's unique identifier (email) from the token.
        String username = jwtService.extractUsername(jwtToken);

        /*
         * Authenticate only if:
         * 1. A username was successfully extracted.
         * 2. No authentication has already been established for this request.
         */
        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load the latest user information from the database.
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            // Validate the token before trusting its contents.
            if (jwtService.isTokenValid(jwtToken, userDetails)) {

                /*
                 * Create an authenticated Security object containing
                 * the user and granted authorities.
                 */
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // Attach request-specific information (IP, session, etc.).
                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                /*
                 * Store the authenticated user in the Security Context.
                 * Spring Security will treat this request as authenticated
                 * for the remainder of its lifecycle.
                 */
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            }
        }

        // Continue processing the remaining filters in the chain.
        filterChain.doFilter(request, response);
    }
}