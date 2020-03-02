package com.springboot.usuarios.app.service.impl;

import com.springboot.usuarios.app.model.User;
import com.springboot.usuarios.app.service.IJwtTokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtTokenServiceImpl implements IJwtTokenService {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Override
    public String generateToken(User user) {

        Map<String, Object> infoToken = new HashMap<>();
        infoToken.put("name", user.getName());
        infoToken.put("email", user.getEmail());

        return doGenerateToken(infoToken, user);

    }


    private String doGenerateToken(Map<String, Object> infoToken, User user) {

        return Jwts.builder().setClaims(infoToken).setSubject(user.getEmail()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, "yisusxp90-secret").compact();

    }


}
