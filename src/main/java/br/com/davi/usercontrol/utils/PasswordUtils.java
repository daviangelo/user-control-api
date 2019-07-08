package br.com.davi.usercontrol.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * Bcrypt class util
 *
 * @author Davi Lessa
 * @version 1.0
 * @since 20-06-2019
 */
public class PasswordUtils {
    private static final Logger log = LoggerFactory.getLogger(PasswordUtils.class);

    public PasswordUtils() {
    }

    /**
     * Generate a hash using BCrypt.
     *
     * @param password
     * @return String - password encrypted
     */
    public static String generateBCrypt(String password) {
        if (password != null) {
            log.info("Generating hash with BCrypt.");
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            return bCryptPasswordEncoder.encode(password);
        }
        return null;
    }
}
