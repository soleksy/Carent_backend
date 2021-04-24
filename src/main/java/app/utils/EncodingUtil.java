package app.utils;

import app.security.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EncodingUtil {

    @Autowired
    private PasswordEncryptor encryptor;

    public String encode(String password) {
        return encryptor.passwordEncoder().encode(password);
    }

    public boolean verify(String dbPassword, String rqPassword) {
        return encryptor.passwordEncoder().matches(rqPassword, dbPassword);
    }

}
