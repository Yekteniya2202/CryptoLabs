import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AfinCesarCryptoAnalysis {

    private AfinCesarEncryptor currentEncryptor;

    private Map<Character, Double> russianAlphabetProbability = new HashMap<>();

    {
        russianAlphabetProbability.put(' ', 0.175);
        russianAlphabetProbability.put('О', 0.090);
        russianAlphabetProbability.put('Е', 0.072);

        russianAlphabetProbability.put('Ё', 0.072);
        russianAlphabetProbability.put('А', 0.062);
        russianAlphabetProbability.put('И', 0.062);
        russianAlphabetProbability.put('Н', 0.053);
        russianAlphabetProbability.put('Т', 0.053);
        russianAlphabetProbability.put('С', 0.045);
        russianAlphabetProbability.put('Р', 0.040);
        russianAlphabetProbability.put('В', 0.038);
        russianAlphabetProbability.put('Л', 0.035);

        russianAlphabetProbability.put('К', 0.028);
        russianAlphabetProbability.put('М', 0.026);
        russianAlphabetProbability.put('Д', 0.025);
        russianAlphabetProbability.put('П', 0.023);
        russianAlphabetProbability.put('У', 0.021);
        russianAlphabetProbability.put('Я', 0.018);
        russianAlphabetProbability.put('Ы', 0.016);
        russianAlphabetProbability.put('З', 0.016);
        russianAlphabetProbability.put('Ъ', 0.014);
        russianAlphabetProbability.put('Ь', 0.014);
        russianAlphabetProbability.put('Б', 0.014);
        russianAlphabetProbability.put('Г', 0.013);

        russianAlphabetProbability.put('Ч', 0.012);
        russianAlphabetProbability.put('Й', 0.010);
        russianAlphabetProbability.put('Х', 0.009);
        russianAlphabetProbability.put('Ж', 0.007);
        russianAlphabetProbability.put('Ю', 0.006);
        russianAlphabetProbability.put('Ш', 0.006);
        russianAlphabetProbability.put('Ц', 0.004);
        russianAlphabetProbability.put('Щ', 0.003);
        russianAlphabetProbability.put('Э', 0.003);
        russianAlphabetProbability.put('Ф', 0.002);

    }

    String sourceText;

    private int a = 7;
    private int k = 0;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public AfinCesarCryptoAnalysis(String sourceText) throws Exception {
        currentEncryptor = new AfinCesarEncryptor(getRussianAlphabet(), a, k);
    }

    public int analyse(String encrypted) {
        int length = encrypted.length();

        double wMin = 1;
        int kBest = 0;
        while (k < currentEncryptor.alphabet.size()) {
            try {
                currentEncryptor = new AfinCesarEncryptor(getRussianAlphabet(), a, k);
            } catch (Exception e) {
                k++;
                continue;
            }

            Map<Character, Integer> charCount = new HashMap<>();
            for (char c = 'А'; c <= 'Я'; c++) {
                charCount.put(c, 0);
            }
            charCount.put('Ё', 0);
            charCount.put(' ', 0);

            char[] decrypted = currentEncryptor.decrypt(encrypted).toCharArray();
            for (char c : decrypted) {
                if (c >= 'А' && c <= 'Я' || c == 'Ё' || c == ' ') {
                    charCount.put(c, charCount.get(c) + 1);
                }

            }

            double w = 0;
            for (Map.Entry<Character, Integer> entry : charCount.entrySet()) {
                w += Math.pow(entry.getValue() / (length * 1.0) - russianAlphabetProbability.get(entry.getKey()), 2);
            }
            if (w < wMin){
                wMin = w;
                kBest = k;
            }

            k++;
        }
        return kBest;
    }


    private static ArrayList<Character> getRussianAlphabet() {
        ArrayList<Character> alphabet = new ArrayList<>();
        for (char c = 'А'; c <= 'Я'; c++) {
            alphabet.add(c);
        }
        alphabet.add(' ');
        alphabet.add('-');
        alphabet.add('.');
        alphabet.add('Ё');
        return alphabet;
    }

}
