import java.util.Arrays;
import java.util.List;

public class BinaryStringUtils {
    public static String convertToEightBinary(String nonBinaryString) {
        char[] chars = nonBinaryString.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            StringBuilder binariesForChar = new StringBuilder(Integer.toBinaryString((int) chars[i]));
            while (binariesForChar.length() < 8) {
                binariesForChar.insert(0, "0");
            }
            sb.append(binariesForChar);
        }
        return sb.toString();
    }

    public static String xorTwoStrings(String first, String second) {
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

    public static String fromBinary(String binaryString){
        List<String> split = Arrays.stream(binaryString.split("(?<=\\G.{8})")).toList();
        StringBuilder stringBuilder = new StringBuilder();
        for (String binaryLetter : split) {
            char encodedSymbol = (char) Integer.parseInt(binaryLetter, 2);
            stringBuilder.append(encodedSymbol);
        }
        return stringBuilder.toString();
    }
}
