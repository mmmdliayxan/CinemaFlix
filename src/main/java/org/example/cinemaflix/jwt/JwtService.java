package org.example.cinemaflix.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.example.cinemaflix.dao.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface JwtService {

    String issueToken(User user);

    Claims parseToken(String token);

    Claims resolveClaims(HttpServletRequest request);

    String resolveToken(HttpServletRequest request);

    boolean validateClaims(Claims claims);

    List<GrantedAuthority> getGrantedAuthorities(Claims claims);
}
