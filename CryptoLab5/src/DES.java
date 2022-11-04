import java.util.Arrays;

public class DES {
    private DESKeyGenerator desKeyGenerator = new DESKeyGenerator();
    private String[] roundKeys;
    private String IC;

    public DES(String key, String IC) {
        if (key.length() != 7) throw new RuntimeException("Key length must be 7, input key = " + key);
        if (IC.length() != 8) throw new RuntimeException("Initial cipher length must be 8, input cipher = " + IC);
        String binary56Key = BinaryStringUtils.convertToEightBinary(key);
        this.IC = IC;
        System.out.println("Key = " + key + " binary key = " + binary56Key);
        roundKeys = desKeyGenerator.generateRoundKeys(binary56Key);
        System.out.println("Generated round keys = " + Arrays.toString(roundKeys));
        System.out.println("IC = " + IC);
    }

    public String encrypt(String text) {
        System.out.println("ENCRYPTION\n================================");
        StringBuilder encryptedStringBuilder = new StringBuilder();
        String C = IC;
        while(text.length() > 0) {
            int symbolsCountToTCopy = Math.min(text.length(), 8);
            String substringToEnc = text.substring(0, symbolsCountToTCopy); // взяли 64 бита/ 8 символов
            text = text.substring(symbolsCountToTCopy); // отрезали начало
            while(substringToEnc.length() < 8){
                substringToEnc += '\0';
            }

            System.out.println("C = " + C);
            System.out.println("block = " + substringToEnc);
            String xorCwithBlock = BinaryStringUtils.fromBinary(BinaryStringUtils.xorTwoStrings(BinaryStringUtils.convertToEightBinary(C), BinaryStringUtils.convertToEightBinary(substringToEnc)));
            System.out.println("xor C with block = " + xorCwithBlock);
            DESRounds desRounds = new DESRounds(xorCwithBlock);

            for (int i = 0; i < 16; i++) {
                desRounds.makeInverseRound(i, roundKeys);
            }

            String encrypted = desRounds.commit();
            C = encrypted;
            encryptedStringBuilder.append(encrypted);
        }
        System.out.println("================================");
        return encryptedStringBuilder.toString();
    }

    public String decrypt(String text) {
        System.out.println("DECRYPTION\n================================");
        StringBuilder decryptedStringBuilder = new StringBuilder();
        String C = IC;
        while(text.length() > 0) {
            int symbolsCountToTCopy = Math.min(text.length(), 8);
            String substringToEnc = text.substring(0, symbolsCountToTCopy); // взяли 64 бита/ 8 символов
            text = text.substring(symbolsCountToTCopy); // отрезали начало
            while(substringToEnc.length() < 8){
                substringToEnc += '\0';
            }

            DESRounds desRounds = new DESRounds(substringToEnc);

            for (int i = 15; i >= 0; i--) {
                desRounds.makeReverseRound(i, roundKeys);
            }

            String decrypted = desRounds.commit();
            System.out.println("C = " + C);
            System.out.println("block = " + decrypted);
            String xorCwithBlock = BinaryStringUtils.fromBinary(BinaryStringUtils.xorTwoStrings(BinaryStringUtils.convertToEightBinary(C), BinaryStringUtils.convertToEightBinary(decrypted)));
            System.out.println("xor C with block = " + xorCwithBlock);
            C = substringToEnc;

            decryptedStringBuilder.append(xorCwithBlock);
        }
        System.out.println("================================");
        return decryptedStringBuilder.toString();
    }
}

