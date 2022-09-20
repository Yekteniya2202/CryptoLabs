import java.util.ArrayList;
import java.util.Arrays;

public class MagicSquareEncryptor implements Encryptor {

    ArrayList<Integer> magicSquare = new ArrayList<>();
    int n;

    public MagicSquareEncryptor(int n, ArrayList<Integer> key) {
        this.n = n;
        magicSquare = copyByN(n, key);
        System.out.println(magicSquare);
    }

    private <T> ArrayList<T> copyByN(int n, ArrayList<T> key) {
        ArrayList<T> table = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                table.add(key.get(n * i + j));
            }
        }
        return table;
    }


    @Override
    public String encrypt(String string) {
        char[] chars = string.toCharArray();
        StringBuilder sb = new StringBuilder();

        int intBlocks = chars.length / (n * n);
        int remainingChars = chars.length - intBlocks * n * n;

        for (int i = 0; i < intBlocks; i++) {
            char[] charSquare = new char[n * n];
            for(int j = 0; j < n*n; j++){
                charSquare[magicSquare.get(j) - 1] = chars[i * n + j];
            }
            sb.append(charSquare);
        }

        char[] charSquare = new char[n * n];
        for(int j = 0; j < remainingChars; j++){
            charSquare[magicSquare.get(j) - 1]
                    = chars[intBlocks * n * n + j];
        }
        for(int i = 0; i < n * n; i++) {
            if (charSquare[i] == 0)
                charSquare[i] = ' ';
        }

        sb.append(charSquare);
        return sb.toString();
    }

    @Override
    public String decrypt(String string) {
        char[] chars = string.toCharArray();
        StringBuilder sb = new StringBuilder();

        int intBlocks = chars.length / (n * n);
        int remainingChars = chars.length - intBlocks * n * n;

        for (int i = 0; i < intBlocks; i++) {
            char[] charSquare = new char[n * n];
            for(int j = 0; j < n*n; j++){
                charSquare[magicSquare.get(j) - 1] = chars[i * n + j];
            }
            sb.append(charSquare);
        }

        char[] charSquare = new char[n * n];
        for(int j = 0; j < remainingChars; j++){
            charSquare[magicSquare.get(j) - 1] = chars[intBlocks * n * n + j];
        }

        for(int i = 0; i < n * n; i++) {
            if (charSquare[i] == 0)
                charSquare[i] = ' ';
        }

        sb.append(charSquare);
        return sb.toString();
    }
}
