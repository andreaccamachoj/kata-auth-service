package co.com.bb.kata.cryptpassword;

import co.com.bb.kata.model.exception.TechnicalException;
import co.com.bb.kata.model.exception.message.TechnicalExceptionMessage;
import co.com.bb.kata.model.jwt.JwtGateway;
import co.com.bb.kata.model.login.UserLogged;
import co.com.bb.kata.model.tokensession.TokenSession;
import co.com.bb.kata.model.useraccount.UserAccount;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Service
public class JwtGatewayImpl implements JwtGateway {

    private static final String SECRET_KEY = "clave-secreta-ultra-segura-para-firmar-el-jwt-2025";
    private static final long EXPIRATION_SECONDS = 36000; // 10 hours

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    @Override
    public TokenSession generateToken(UserAccount user) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plusSeconds(EXPIRATION_SECONDS);

        Date issuedAt = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        Date expiresAt = Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant());

        String jwt = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRoleId())
                .claim("name", user.getFirstName() + " " + user.getLastName())
                .setIssuedAt(issuedAt)
                .setExpiration(expiresAt)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenSession.builder()
                .userAccountEntityId(user.getId())
                .token(jwt)
                .createdAt(now)
                .expiresAt(expiration)
                .isActive(true)
                .build();
    }

    @Override
    public UserLogged validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = claimsJws.getBody();

            Date expiration = claims.getExpiration();
            LocalDateTime exp = expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            if (exp.isBefore(LocalDateTime.now())) {
                throw new TechnicalException(TechnicalExceptionMessage.EXPIRED_TOKEN);
            }
            return UserLogged.builder()
                    .token(token)
                    .userName(claims.get("name", String.class))
                    .email(claims.getSubject())
                    .role(String.valueOf(claims.get("role")))
                    .build();

        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            throw new TechnicalException(TechnicalExceptionMessage.INVALID_TOKEN);
        }
    }
}
