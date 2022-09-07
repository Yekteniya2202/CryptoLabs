import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        ArrayList<Character> alphabet = getRussianAlphabet();
        PolybiusSquare polybiusSquare = new PolybiusSquare(alphabet);
        String encrypted = polybiusSquare.encrypt("Приехал грузин из Москвы домой. Хвастается \"Был в театре, смотрел спектакль\" - Как называется? - \"Малиновая жопа\" - Не может быть! - Вспомнил! \"Вишневый зад\"!");
        System.out.println(encrypted);
        String decrypted = polybiusSquare.decrypt(encrypted);
        System.out.println(decrypted);

    }

    private static ArrayList<Character> getRussianAlphabet() {
        ArrayList<Character> alphabet = new ArrayList<>();
        for (char c = 'А'; c <= 'я'; c++) {
            alphabet.add(c);
        }
        alphabet.add(' ');
        alphabet.add(',');
        alphabet.add('.');
        alphabet.add('!');
        alphabet.add(':');
        alphabet.add('?');
        alphabet.add('\"');
        alphabet.add('-');
        return alphabet;
    }
}