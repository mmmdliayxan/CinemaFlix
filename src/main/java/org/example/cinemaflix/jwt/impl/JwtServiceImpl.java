package org.example.cinemaflix.jwt.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.cinemaflix.model.dto.constant.Const;
import org.example.cinemaflix.dao.entity.Authority;
import org.example.cinemaflix.dao.entity.User;
import org.example.cinemaflix.jwt.JwtService;
import org.example.cinemaflix.model.dto.exceptions.NotFoundException;
import org.example.cinemaflix.dao.entity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@PropertySource("classpath:application.yml")
@Slf4j
public class JwtServiceImpl implements JwtService {

    private final UserRepository userRepository;

    public JwtServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Value("${secretKey}")
    private String issueKey;

    @PostConstruct
    public Key initializeKey(){
        byte[] keyBytes;
        keyBytes = Decoders.BASE64.decode(issueKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    @Override
    public String issueToken(User user) {
        user = userRepository.findByUsername(user.getUsername()).orElseThrow(()-> new NotFoundException("User is not found"));
        List<String> authorities = user.getAuthorities()
                        .stream()
                        .map(Authority::getAuthority).toList();

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("roles",authorities);
        claimsMap.put("username",user.getUsername());
        claimsMap.put("user_id",user.getId());
        final JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(Duration.ofHours(1L))))
                .addClaims(claimsMap)
                .setHeader(Map.of("type","JWT"))
                .signWith(initializeKey(), SignatureAlgorithm.HS256);

        return jwtBuilder.compact();
    }
    @Override
    public Claims parseToken(String token) {
        log.info("Parsing token: {}", token);
        return Jwts.parserBuilder()
                .setSigningKey(initializeKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public Claims resolveClaims(HttpServletRequest request){
        String token = resolveToken(request);
        if(token!=null){
            return parseToken(token);
        }
        return null;
    }
    public String resolveToken(HttpServletRequest request){

        String bearerToken = request.getHeader(Const.AUTH_HEADER);
        if(bearerToken!=null && bearerToken.startsWith(Const.BEARER)){
            return bearerToken.substring(Const.BEARER.length());
        }
        return null;
    }
    public boolean validateClaims(Claims claims){
        return claims.getExpiration().after(new Date());
    }
    public List<GrantedAuthority> getGrantedAuthorities(Claims claims){
        List<?> roles = claims.get(Const.ROLE_CLAIM,List.class);
        return roles.stream()
                .map(auth->new SimpleGrantedAuthority(auth.toString()))
                .collect(Collectors.toList());
    }
}
