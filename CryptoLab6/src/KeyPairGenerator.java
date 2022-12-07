import java.math.BigInteger;
import java.util.Random;

public class KeyPairGenerator {

    BigInteger q;
    BigInteger p;
    BigInteger g;

    public KeyPairGenerator() {
        q = BigInteger.probablePrime(SHAUtils.HASH_BIT_LENGTH, new Random());
        p = BigInteger.probablePrime(511, new Random());
        while (!p.mod(q).equals(BigInteger.ZERO)) {
            BigInteger mod = p.mod(q);
            p = BigInteger.probablePrime(511, new Random());
        }
        BigInteger[] bigIntegers = p.divideAndRemainder(q);
        System.out.println("GOOD");
    }

    public KeyPair generateKeyPair() {
        return null;
    }
}
