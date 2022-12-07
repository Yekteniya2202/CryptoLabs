import java.math.BigInteger;

public class KeyPair {
    BigInteger secretKey;
    BigInteger publicKey;

    public void setSecretKey(BigInteger secretKey) {
        this.secretKey = secretKey;
    }

    public void setPublicKey(BigInteger publicKey) {
        this.publicKey = publicKey;
    }

    public BigInteger getSecretKey() {
        return secretKey;
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }
}
