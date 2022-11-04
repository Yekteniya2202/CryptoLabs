public class DESKeyGenerator {

    private final int[] boxC = {
            57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36
    };

    private final int[] boxD = {
            63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4
    };

    private final int[] boxP = {
            14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4,
            26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55, 30, 40,
            51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32
    };

    public String[] generateRoundKeys(String binary56bit) {
        String[] roundKeys = new String[16];
        //System.out.println("KEY GENERATION\n============================");
        //System.out.println("input 56 bit key = " + binary56bit);
        String binary64Key = generate64Key(binary56bit);
        //System.out.println("64 bit key = " + binary64Key);
        String binary28C = permutationC(binary64Key);
        String binary28D = permutationD(binary64Key);

        //System.out.println("C 28 bit block = " + binary28C);
        //System.out.println("D 28 bit block = " + binary28D);

        for (int i = 0; i < 16; i++) {

            //System.out.println("Shifting left blocks");
            binary28C = shiftLeft(binary28C, i + 1);
            binary28D = shiftLeft(binary28C, i + 1);
            //System.out.println("C" + (i + 1) + " = " + binary28C);
            //System.out.println("D" + (i + 1) + " = " + binary28D);
            String binary56CD = binary28C + binary28D;
            //System.out.println("CD" + (i + 1) + " = " + binary56CD);
            roundKeys[i] = permutationP(binary56CD);
            //System.out.println("k" + (i + 1) + " = " + roundKeys[i]);
        }
        return roundKeys;
    }

    private String shiftLeft(String binary28, int round) {
        char[] a = binary28.toCharArray();
        int length = binary28.length();
        char[] b = new char[length];
        int shift = 0;
        switch (round) {
            case 1:
            case 2:
            case 9:
            case 16:
                shift = 1;
                break;
            default:
                if (round > 16) throw new RuntimeException("Round number invalid: " + round);
                shift = 2;
        }
        // шаг 1
        System.arraycopy(a, shift, b, 0, length - shift);
        // шаг 2
        System.arraycopy(a, 0, b, length - shift, shift);
        return new String(b);
    }

    private String permutationP(String binary56Key) {
        char[] pOutput = new char[48];
        for (int i = 0; i < 48; i++) {
            pOutput[i] = binary56Key.charAt(boxP[i] - 1);
        }
        return new String(pOutput);
    }
    private String permutationC(String binary64Key) {
        char[] cOutput = new char[28];
        for (int i = 0; i < 28; i++) {
            cOutput[i] = binary64Key.charAt(boxC[i] - 1);
        }
        return new String(cOutput);
    }

    private String permutationD(String binary64Key) {
        char[] dOutput = new char[28];
        for (int i = 0; i < 28; i++) {
            dOutput[i] = binary64Key.charAt(boxD[i] - 1);
        }
        return new String(dOutput);
    }

    private String generate64Key(String binary56bit) {
        StringBuilder binary64Key = new StringBuilder();
        for (int i = 7; i <= 56; i += 7) {
            String block = binary56bit.substring(i - 7, i);
            long count = block.chars().filter(bit -> bit == '1').count();
            if (count % 2 == 0) {
                block += '1';
            } else {
                block += '0';
            }
            binary64Key.append(block);
        }
        return binary64Key.toString();
    }
}
