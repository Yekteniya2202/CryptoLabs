import java.util.Arrays;
import java.util.List;

public class DESRounds {

    private String binaryL;
    private String binaryR;

    private String binaryK1 = "000001101010011100111000111110010000000010101010";

    private final int[] ip = {
            58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7
    };

    private final int[] e = {
            32, 1, 2, 3, 4, 5,
            4, 5, 6, 7, 8, 9,
            8, 9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32, 1
    };

    private final int[] p = {
            16, 7, 20, 21, 29, 12, 28, 17,
            1, 15, 23, 26, 5, 18, 31, 10,
            2, 8, 24, 14, 32, 27, 3, 9,
            19, 13, 30, 6, 22, 11, 4, 25
    };

    private final int[][][] sBoxes = {
            {
                    {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                    {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                    {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                    {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
            },
            {
                    {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                    {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                    {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                    {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
            },

            {
                    {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                    {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                    {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                    {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
            },

            {
                    {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                    {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                    {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                    {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
            },

            {
                    {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                    {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                    {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                    {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
            },

            {
                    {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                    {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                    {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                    {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
            },

            {
                    {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                    {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                    {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                    {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
            },

            {
                    {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                    {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                    {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                    {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
            }
    };

    //принимает 64 бита
    public DESRounds(String input) {
        String binaryInput = convertToEightBinary(input);
        if (binaryInput.length() < 64) {
            throw new RuntimeException("input binaries length < 64, binaries = " + binaryInput + "\ninput = " + input);
        }
        String ipBinaryInput = IP(binaryInput);
        binaryL = ipBinaryInput.substring(0, 32);
        binaryR = ipBinaryInput.substring(32, 64);
        System.out.println("Binary input = " + binaryInput);
        System.out.println("IpBinary input = " + ipBinaryInput);
        System.out.println("L binary = " + binaryL);
        System.out.println("R binary = " + binaryR);
    }

    public String makeRound() {
        if (binaryR.length() > 32) throw new RuntimeException("binaryR length > 32, binaryR = " + binaryR);
        if (binaryL.length() > 32) throw new RuntimeException("binaryL length > 32, binaryL = " + binaryL);

        String tmp = binaryR;
        String fResult = f(binaryK1, binaryR);
        System.out.println("F result = " + fResult);
        binaryR = xorTwoStrings(fResult, binaryL);
        System.out.println("F      " + fResult);
        System.out.println("XOR    ");
        System.out.println("L      " + binaryL);
        System.out.println("equals " + binaryR);
        binaryL = tmp;
        System.out.println("L binary = " + binaryL);
        System.out.println("R binary = " + binaryR);

        return binaryL + binaryR;
    }

    public String f(String keyPart, String binaryString) {

        System.out.println("32 bits = " + binaryString);
        String extendedBinaryString = E(binaryString);
        System.out.println("extended to 48 bits = " + extendedBinaryString);
        System.out.println("key = " + keyPart);
        String xorBinaryStringWithKeyPart = xorTwoStrings(extendedBinaryString, keyPart);
        System.out.println("xor = " + xorBinaryStringWithKeyPart);

        StringBuilder sb4bits = new StringBuilder();

        System.out.println("S boxes starts");
        int boxNumber = 0;
        for (int i = 0; i < 48; i += 6) {
            String binary6Bits = xorBinaryStringWithKeyPart.substring(i, i + 6);
            System.out.println("Taken 6 bits = " + binary6Bits);
            String outer2Bits = Character.toString(binary6Bits.charAt(0)) + Character.toString(binary6Bits.charAt(5));
            System.out.println("Taken 2 outer bits = " + outer2Bits);
            String inner4Bits = binary6Bits.substring(1, 5);
            System.out.println("Taken 4 inner bits = " + inner4Bits);
            int row = (int) Long.parseLong(outer2Bits, 2);
            int column = (int) Long.parseLong(inner4Bits, 2);

            System.out.println("boxNumber = " + boxNumber);
            System.out.println("row = " + row);
            System.out.println("column = " + column);

            String sboxResult = Integer.toBinaryString(sBoxes[boxNumber][row][column]);
            while (sboxResult.length() < 4) {
                sboxResult = "0" + sboxResult;
            }
            System.out.println("sBoxResult = " + sboxResult);
            sb4bits.append(sboxResult);

            boxNumber++;
        }
        System.out.println("S boxes ends");
        System.out.println("Result for Sbox = " + sb4bits);
        String pPermuted = P(sb4bits.toString());
        System.out.println("Permuted = " + pPermuted);
        return pPermuted;
    }

    private String E(String binaryString) {
        char[] eOutput = new char[48];
        for (int i = 0; i < 48; i++) {
            eOutput[i] = binaryString.charAt(e[i] - 1);
        }
        return new String(eOutput);
    }

    private String P(String binaryString) {
        char[] pOutput = new char[32];
        for (int i = 0; i < 32; i++) {
            pOutput[i] = binaryString.charAt(p[i] - 1);
        }
        return new String(pOutput);
    }

    public String IP(String binaryString) {
        char[] ipOutput = new char[64];
        for (int i = 0; i < ip.length; i++) {
            ipOutput[i] = binaryString.charAt(ip[i] - 1);
        }
        return new String(ipOutput);
    }

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
