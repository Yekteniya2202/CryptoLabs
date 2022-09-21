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

        int intBlocks = (int)Math.ceil(chars.length * 1.0 / (n * n));

        for(int i = 0; i < intBlocks; i++){
            int to = i * n * n + n * n;
            if (i == intBlocks - 1){
                to = chars.length;
            }
            char[] subchars = Arrays.copyOfRange(chars, i * n * n, to);
            char[] charSquare = new char[n * n];
            for(int j = 0; j < subchars.length; j++){
                charSquare[magicSquare.indexOf(j + 1)] = subchars[j];
            }

            sb.append(charSquare);
        }
        return sb.toString();
    }

    @Override
    public String decrypt(String string) {
        char[] chars = string.toCharArray();
        StringBuilder sb = new StringBuilder();

        int intBlocks = (int)Math.ceil(chars.length * 1.0 / (n * n));

        for(int i = 0; i < intBlocks; i++){
            int to = i * n * n + n * n;
            if (i == intBlocks - 1){
                to = chars.length;
            }
            char[] subchars = Arrays.copyOfRange(chars, i * n * n, to);
            char[] charSquare = new char[n * n];
            for(int j = 0; j < subchars.length; j++){
                charSquare[magicSquare.indexOf(j + 1)] = subchars[j];
            }

            int cutFrom = n * n;
            for(int j = 0; j < n * n; j++) {
                if (charSquare[j] == 0) {
                    cutFrom = j;
                    break;
                }
            }
            sb.append(Arrays.copyOfRange(charSquare, 0, cutFrom));
        }
        return sb.toString();
    }
}
