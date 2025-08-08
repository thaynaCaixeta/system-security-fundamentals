package salt;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class HashWithSaltDemo {

    public HashWithSaltDemo() {}

    public String getHashedValue(String originalPsd) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");

        long start = System.currentTimeMillis();
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(originalPsd.toCharArray(), salt, 100_000, 256);
        byte[] hashedPassword = factory.generateSecret(spec).getEncoded();
        long end = System.currentTimeMillis();

        Base64.Encoder encoder = Base64.getEncoder();
        String hashed = encoder.encodeToString(salt) + ":" + encoder.encodeToString(hashedPassword);
        System.out.println(hashed);

        System.out.printf("Time to hash %s%n", end - start);

        return hashed;
    }
}
