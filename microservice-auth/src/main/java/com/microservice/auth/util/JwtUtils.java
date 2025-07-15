package com.microservice.auth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.microservice.auth.model.UserEntity;
import com.microservice.auth.security.UserSecurityDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;


    public String createToken(Authentication authentication){
        // Definimos el algoritmo de encriptacion + clave privada
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        // Obtenemos el usuario completo
        UserEntity user = ((UserSecurityDetails) authentication.getPrincipal()).getUserEntity();

        //Generamos el token
        String jwtToken = JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(user.getEmail())
                .withClaim("role", user.getRol().getRoleName().name())
                .withExpiresAt(this.generateExpirationDate())
                .sign(algorithm);

        return jwtToken;
    }

    public DecodedJWT validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT;

        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Token invalid, not authorized");
        }
    }

    // extraemos el usuario que viene dentro del token
    public String extractEmail(DecodedJWT decodedJWT){
        return decodedJWT.getSubject().toString();
    }

    // Obtenemos un claim en especifico
    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName){
        return decodedJWT.getClaim(claimName);
    }

    // Obtenemos todos los claims del JWT
    public Map<String, Claim> returnAllClaims(DecodedJWT decodedJWT){
        return decodedJWT.getClaims();
    }

    private Instant generateExpirationDate() {
        return LocalDateTime
                .now()
                .plusMinutes(30)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
