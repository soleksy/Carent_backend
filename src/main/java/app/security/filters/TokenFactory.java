package app.security.filters;

import app.entity.UserEntity;
import app.exception.ServerErrorCode;
import app.exception.ServerException;
import app.service.UserService;
import app.utils.TimeUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenFactory {

    @Value("${spring.security.secret}")
    private String secret;

    @Value("${spring.security.jwt.expiration}")
    private int expirationTime;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionFactory sessionFactory;

    private String generateToken(Map<String, Object> payload, String subject) {
        Date currentDate = new Date();
        return Jwts.builder().addClaims(payload).setSubject(subject).setIssuedAt(currentDate)
                .setExpiration(TimeUtils.addMinutes(currentDate, expirationTime))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String getAccessToken(String authString) throws ServerException {
        String[] credentials = getUserCredentials(authString);
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(credentials[0], credentials[1]));
        } catch (BadCredentialsException e) {
            throw new ServerException(ServerErrorCode.INVALID_LOGIN_OR_PASSWORD);
        }
        return issueToken(credentials[0]);
    }

    public String issueToken(String username) {
        Map<String, Object> payload = new HashMap<>();
        UserEntity user = userService.getUserByUsername(username);
        payload.put("userId", user.getId());
        payload.put("firstName", user.getFirstName());
        payload.put("lastName", user.getLastName());
        payload.put("role", user.getRole().getRole());
        return generateToken(payload, username);
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String getBearer(String header) {
        if (header != null && header.startsWith(AuthPrefix.BEARER)) {
            return header.substring(AuthPrefix.BEARER.length()).trim();
        }
        return null;
    }

    private String[] getUserCredentials(String authString) throws ServerException {
        if (authString == null || !authString.startsWith(AuthPrefix.BASIC)) {
            throw new ServerException(ServerErrorCode.INVALID_LOGIN_OR_PASSWORD);
        }
        String basic = authString.substring(AuthPrefix.BASIC.length()).trim();
        byte[] decodedBasicBytes = Base64.getDecoder().decode(basic);
        String decodedBasic = new String(decodedBasicBytes, StandardCharsets.UTF_8);
        String[] credentials = decodedBasic.split(":", -1);
        if (credentials.length !=2 ) {
            throw new ServerException(ServerErrorCode.INVALID_LOGIN_OR_PASSWORD);
        }
        return credentials;
    }

    @Transactional
    public boolean isTokenValid(String accessToken) {
        Session session = sessionFactory.getCurrentSession();
        String queryStr = "SELECT id FROM invalid_tokens WHERE token = ? LIMIT 1";
        return session.createSQLQuery(queryStr).setParameter(1, accessToken).getResultList().isEmpty();
    }

    @Transactional
    public void invalidateToken(String accessToken) {
        Session session = sessionFactory.getCurrentSession();
        String queryStr = "INSERT INTO invalid_tokens (token) VALUES (?)";
        session.createSQLQuery(queryStr).setParameter(1, accessToken).executeUpdate();
    }

}
