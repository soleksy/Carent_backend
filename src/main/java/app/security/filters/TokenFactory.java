package app.security.filters;

import app.entity.UserEntity;
import app.exception.ServerErrorCode;
import app.exception.ServerException;
import app.service.UserService;
import app.utils.TimeUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

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
        Map<String, Object> payload = new HashMap<>();
        UserEntity user = userService.getUserByUsername(credentials[0]);
        payload.put("userId", user.getId());
        payload.put("firstName", user.getFirstName());
        payload.put("lastName", user.getLastName());
        return generateToken(payload, credentials[0]);
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
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

}
