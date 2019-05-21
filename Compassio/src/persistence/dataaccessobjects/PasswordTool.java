package persistence.dataaccessobjects;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Handles salting and passwords in relation to the users.
 * 
 * @author L530
 * @author Morten Kargo Lyngesen
 */
public class PasswordTool {

    protected static byte[] generateSalt() {
        SecureRandom secRan = new SecureRandom();
        byte[] salt = new byte[128];
        secRan.nextBytes(salt);
        return salt;
    }

    protected static byte[] hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return factory.generateSecret(spec).getEncoded();
    }
    
}
