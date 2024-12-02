package ru.kulbaka.effectivemobile.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.kulbaka.effectivemobile.service.JwtService;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Кульбака Никита
 * Сервис для работы с jwt токенами
 */
@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret}")
    private String secret;


    /**
     * Время жизни токена в мс
     * По-дефолту токен действителен 5 минут
     **/
    @Value("${jwt.lifetime}")
    private int lifetime;

    /**
     * Извлекает почту из токена
     *
     * @param token токен аутентификации
     * @return почта из токена
     */
    @Override
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Генерирует jwt токен
     *
     * @param userDetails данные пользователя
     * @return jwt токен
     */
    @Override
    public String generateToken(UserDetailsImpl userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDetails.getId());
        claims.put("email", userDetails.getEmail());
        claims.put("role", userDetails.getUserRole());

        return Jwts.builder().claims(claims).subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + lifetime))
                .signWith(getSigningKey()).compact();
    }

    /**
     * Проверяет истёк ли токен и принадлежит ли он существующему пользователю
     *
     * @param token       токен аутентификации
     * @param userDetails данные существующего пользователя
     * @return true, если токен валидный, и false в противном случае
     */
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Извлекает данные конкретного типа из токена
     *
     * @param token           токен аутентификации
     * @param claimsResolvers функция извлечения данных
     * @param <T>             тип данных
     * @return данные
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Проверяет токен на просроченность
     *
     * @param token jwt токен
     * @return true, если токен просрочен, false в противном случае
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Извлечение даты истечения токена
     *
     * @param token jwt токен
     * @return дата истечения
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Извлекает все данные из токена
     *
     * @param token jwt токен
     * @return данные
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Получает ключ для подписи токена
     *
     * @return ключ
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
