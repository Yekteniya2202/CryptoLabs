import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAUtils {

    public static final int HASH_BIT_LENGTH = 160;

    public BigInteger hash(String message) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash = md.digest(message.getBytes(StandardCharsets.UTF_8));
        BigInteger bigInteger = new BigInteger(hash);
        return bigInteger.shiftLeft(160 - bigInteger.bitLength());
    }
}
