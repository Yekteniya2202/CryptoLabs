import java.util.*;

public class PolybiusSquare {

    char[][] table;
    int rows;
    int cols;

    public PolybiusSquare(ArrayList<Character> alphabetSource) {
        ArrayList<Character> alphabet = (ArrayList<Character>) alphabetSource.clone();
        int size = alphabet.size();
        ArrayList<ArrayList<Integer>> twoFactors = twoFactors(size);
        rows = twoFactors.get(twoFactors.size() / 2).get(0);
        cols = twoFactors.get(twoFactors.size() / 2).get(1);
        table = new char[rows][cols];

        Random rng = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int index = rng.nextInt(alphabet.size());
                table[i][j] = alphabet.get(index);
                alphabet.remove(index);
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
    }

    private ArrayList<ArrayList<Integer>> twoFactors(int number) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            if (number % i == 0) {
                ArrayList<Integer> twoFactors = new ArrayList<>();
                twoFactors.add(i);
                twoFactors.add(number / i);
                res.add(twoFactors);
            }
        }
        return res;
    }

    public String encrypt(String s) {
        char[] chars = s.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            ArrayList<Integer> charAtTable = charAtTable(chars[i]);
            stringBuilder.append(table[(charAtTable.get(0) + 1) % rows][charAtTable.get(1)]);
        }
        return stringBuilder.toString();
    }

    private ArrayList<Integer> charAtTable(char aChar) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (table[i][j] == aChar) {
                    return new ArrayList<>(List.of(i, j));
                }
            }
        }
        return new ArrayList<>();
    }

    public String decrypt(String s) {
        char[] chars = s.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            ArrayList<Integer> charAtTable = charAtTable(chars[i]);
            int pseudoRow = charAtTable.get(0) - 1;
            int rowInd = pseudoRow < 0 ? rows - pseudoRow * -1 : pseudoRow;
            stringBuilder.append(table[rowInd][charAtTable.get(1)]);
        }
        return stringBuilder.toString();
    }
}
