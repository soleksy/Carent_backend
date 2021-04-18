package app.security.filters;

import app.entity.UserEntity;
import app.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private UserService userService;

    private String generateToken(Map<String, Object> payload, String subject) {
        return Jwts.builder().addClaims(payload).setSubject(subject).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String getAccessToken(String authString) {
        String[] credentials = getUserCredentials(authString);
        UserEntity user = userService.getUserByUsername(credentials[0]);
        //TODO add password encryption and validate passwords accordingly
        if (user == null || !credentials[1].equals(user.getPassword())) {
            throw new RuntimeException("Wrong login or password");
        }
        Map<String, Object> payload = new HashMap<>();
        // TODO add data to payload
        return generateToken(payload, user.getFirstName());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    private String[] getUserCredentials(String authString) {
        if (authString == null || !authString.startsWith("Basic")) {
            throw new RuntimeException("Invalid auth header");
        }
        String basic = authString.substring(6);
        byte[] decodedBasicBytes = Base64.getDecoder().decode(basic);
        String decodedBasic = new String(decodedBasicBytes, StandardCharsets.UTF_8);
        return decodedBasic.split(":", -1);
    }

}
