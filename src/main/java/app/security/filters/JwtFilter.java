package app.security.filters;

import app.exception.ErrorTemplate;
import app.exception.ServerErrorCode;
import app.utils.MessageUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Qualifier("userSerivceImpl")
    @Autowired
    private UserDetailsService userService;

    @Autowired
    private TokenFactory tokenFactory;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTimeDeserializer dateTimeDeserializer = new LocalDateTimeDeserializer(formatter);
        LocalDateTimeSerializer dateTimeSerializer = new LocalDateTimeSerializer(formatter);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, dateTimeDeserializer);
        javaTimeModule.addSerializer(LocalDateTime.class, dateTimeSerializer);
        OBJECT_MAPPER.registerModule(javaTimeModule);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authString = request.getHeader("Authorization");
        String username;
        if (authString != null && authString.startsWith(AuthPrefix.BEARER)) {
            String bearer = authString.substring(AuthPrefix.BEARER.length()).trim();
            try {
                username = tokenFactory.getUsername(bearer);
            } catch (MalformedJwtException e) {
                setError(response, request, HttpStatus.UNAUTHORIZED, ServerErrorCode.INVALID_ACCESS_TOKEN);
                return;
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(username);
                //TODO validate token claims
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void setError(HttpServletResponse response, HttpServletRequest request, HttpStatus status, ServerErrorCode errorCode) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorTemplate error = new ErrorTemplate();
        error.setError(errorCode.toString());
        error.setStatus(status.value());
        MessageUtils.setCommonErrorFields(error, request, errorCode.getMsg());
        response.getWriter().write(OBJECT_MAPPER.writeValueAsString(error));
    }
}
