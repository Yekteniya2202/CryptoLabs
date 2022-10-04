import java.util.*;

public class AfinCesarEncryptor implements Encryptor {

    ArrayList<Character> alphabet;
    ArrayList<Character> encryptedAlphabet;

    int a, k, m;

    public AfinCesarEncryptor(ArrayList<Character> alphabetSource, int a, int k) throws Exception {
        if (!checkParams(a, k, alphabetSource.size())) {
            throw new Exception("Invalid params for input keys");
        }
        this.a = a;
        this.k = k;
        this.m = alphabetSource.size();
        alphabet = (ArrayList<Character>) alphabetSource.clone();
        encryptedAlphabet = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            encryptedAlphabet.add(alphabet.get(mainFormula(i)));
        }
        System.out.println(alphabet);
        System.out.println(encryptedAlphabet);
    }

    private boolean checkParams(int a, int k, int m) {
        if (a <= m - 1 && a >= 0 && k <= m - 1 && nod(a, m) == 1) {
            return true;
        }
        return false;
    }

    private int mainFormula(int j) {
        return (a * j + k) % m;
    }

    private int nod(int a, int b) {
        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }

    @Override
    public String encrypt(String s) {
        char[] chars = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            sb.append(encryptedAlphabet.get(alphabet.indexOf(c)));
        }
        return sb.toString();
    }


    @Override
    public String decrypt(String s) {
        char[] chars = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            sb.append(alphabet.get(encryptedAlphabet.indexOf(c)));
        }
        return sb.toString();
    }
}
