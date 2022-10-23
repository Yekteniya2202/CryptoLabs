import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class GammaEncryptor {


    public static int pow(int value, int powValue) {
        return (int) Math.pow(value, powValue);
    }

    private boolean left17Bits = false;

    private long seed;
    private long a;
    private long b;
    private long m;
    private String initConnectionVector;

    public void init(String lcgPath, String lfsrPath) throws IOException {
        String[] lcdKeys = (String[]) Files.readAllLines(Path.of(lcgPath)).toArray(new String[4]);
        String lfsrKey =  Files.readString(Path.of(lfsrPath));
        seed = Long.parseLong(lcdKeys[0]);
        a = Long.parseLong(lcdKeys[1]);
        b = Long.parseLong(lcdKeys[2]);
        m = Long.parseLong(lcdKeys[3]);
        initConnectionVector = lfsrKey;
    }
    public String encrypt(String text) {

        left17Bits = false;
        LinearCongruentGenerator linearCongruentGenerator = new LinearCongruentGenerator(seed, a, b, m);
        LFSRGenerator lfsrGenerator = new LFSRGenerator();

        int iterationNum = 1;
        StringBuilder resultStringBuilder = new StringBuilder();
        StringBuilder totalGamma = new StringBuilder();
        while (text.length() > 0) {

            //скопировать либо целый кусок, либо остаток
            int symbolsCountToTCopy = Math.min(text.length(), 8);
            //скопировали
            String substringToEnc = text.substring(0, symbolsCountToTCopy);
            //убрали нач кусок
            text = text.substring(symbolsCountToTCopy);

            StringBuilder tCopy = new StringBuilder();

            //проходим по буквам
            for (String letter : substringToEnc.split("")) {
                //перевели букву в двоич
                StringBuilder encLetter = new StringBuilder(stringToBinary(letter));
                //если остаток - заполняем нулями
                while (encLetter.length() < 8) {
                    encLetter.insert(0, "0");
                }
                tCopy.append(encLetter);
            }

            //если остаток - заполняем нулями
            while (tCopy.length() < 64) {
                tCopy.append("0");
            }



            long num1 = linearCongruentGenerator.next();
            String binaryStringNum1 = Long.toBinaryString(num1);
            String bits = left17Bits ? binaryStringNum1.substring(0, 17) : binaryStringNum1.substring(binaryStringNum1.length() - 17);
            left17Bits = !left17Bits;
            System.out.println("LinearCongruentGenerator next() = " + num1);
            System.out.println("LinearCongruentGenerator next() = " + Long.toBinaryString(num1));
            String gamma = lfsrGenerator.makeIteration(bits, initConnectionVector);

            totalGamma.append(gamma);

            //xor гаммы и куска
            String resPart = xorTwoStrings(tCopy.toString(), gamma);
            resultStringBuilder.append(resPart);

            System.out.println("\nIteration " + (iterationNum++));
            System.out.println("TEXT :" + tCopy);
            System.out.println("GAMMA:" + gamma);
            System.out.println("XOR  :" + resPart);
        }

        //0110101000011111011001111000001001001100110111011110100011000001
        String result = resultStringBuilder.toString();
        StringBuilder stringBuilder = new StringBuilder();

        List<String> split = Arrays.stream(result.split("(?<=\\G.{8})")).toList();
        for (String binaryLetter : split) {
            char encodedSymbol = (char) Integer.parseInt(binaryLetter, 2);
            stringBuilder.append(encodedSymbol);
        }

        System.out.println("=========RESULT=========");
        System.out.println("RESULT     : " + result);
        System.out.println("TOTAL GAMMA: " + totalGamma);
        System.out.println("ENCRYTED TO: " + stringBuilder);

        return stringBuilder.toString();
    }

    public String decrypt(String encrypted) {
        return encrypted;
    }


    private static String stringToBinary(String s) {
        return s.chars()
                .collect(StringBuilder::new,
                        (sb, c) -> sb.append(Integer.toBinaryString(c)),
                        StringBuilder::append)
                .toString();
    }

    private static long parseLong(String s, int base) {
        return new BigInteger(s, base).longValue();
    }

    private static String xorTwoStrings(String first, String second) {
        if (first.length() != second.length())
            throw new RuntimeException("Different lengths");

        List<String> firstList = Arrays.stream(first.split("")).toList();
        List<String> secondList = Arrays.stream(second.split("")).toList();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < first.length(); i++) {
            sb.append(Integer.parseInt(firstList.get(i)) ^ Integer.parseInt(secondList.get(i)));
        }

        return sb.toString();
    }
}
