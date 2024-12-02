package ru.kulbaka.effectivemobile.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.kulbaka.effectivemobile.service.JwtService;
import ru.kulbaka.effectivemobile.service.UserService;

import java.io.IOException;

/**
 * @author Кульбака Никита
 * Фильтр для аутентификации пользователя с помощью данных
 * из jwt токена
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";

    private final JwtService jwtService;

    private final UserService userService;

    /**
     * Проверяет, есть ли токен в хедере запроса. Если есть и
     * валиден, то аутентифицирует пользователя
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            // Получаем токен из заголовка
            String authHeader = request.getHeader(HEADER_NAME);
            if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {

                filterChain.doFilter(request, response);
                return;
            }

            // Обрезаем префикс и получаем почту пользователя из токена
            String jwt = authHeader.substring(BEARER_PREFIX.length());
            String email = jwtService.extractEmail(jwt);

            if (!email.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = UserDetailsImpl.build(userService.getByEmail(email));

                // Если токен валиден, то аутентифицируем пользователя
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    SecurityContext context = SecurityContextHolder.createEmptyContext();

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(authToken);
                    SecurityContextHolder.setContext(context);
                }
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token expired");
        }
    }
}
