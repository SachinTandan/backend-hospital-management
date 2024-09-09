package com.example.hospitalmanagement.Config;

import com.example.hospitalmanagement.Service.JWTService;
import com.example.hospitalmanagement.Service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        // Check if the Authorization header is present and starts with "Bearer "

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            // Extract the token from the Authorization header
            token = authHeader.substring(7);

            // Extract the username from the token
            username = jwtService.extractUserName(token);
        }

        // Check if the username is not null and the authentication is not set in the security context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // If user is authenticated, set it as the current security context
            logger.info("Authentication is not set. Attempting to authenticate user: "+ username);

            // Load the user details for the given username
            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
            // Validate the token with the user details
            if (jwtService.validateToken(token, userDetails)) {
                // Set the authentication in the context
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Proceed with the next filter in the chain
        filterChain.doFilter(request, response);
    }
}