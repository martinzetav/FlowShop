package com.microservice.gateway.config.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.microservice.gateway.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtTokenValidator extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        System.out.println("🛡️ Ejecutando JwtTokenValidator...");
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(jwtToken != null && jwtToken.startsWith("Bearer ")){
            jwtToken = jwtToken.substring(7);
            try{
                DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);

                String email = jwtUtils.extractEmail(decodedJWT);
                String roleClaim = jwtUtils.getSpecificClaim(decodedJWT, "role").asString();

                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+roleClaim);

                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, Collections.singleton(authority));

                SecurityContext context = SecurityContextHolder.getContext();
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
            }catch(JWTVerificationException e){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalid, not Authorized");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
