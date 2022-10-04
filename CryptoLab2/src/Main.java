import java.util.ArrayList;
import java.util.Scanner;
import java.util.SortedMap;

public class Main {
    public static void main(String[] args) {
        ArrayList<Character> alphabet = getRussianAlphabet();
        Viginer vigener = new Viginer(alphabet);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите ключевое 1 слово: ");
        String keyWord1 = scanner.nextLine();

        System.out.println("Введите ключевое 2 слово: ");
        String keyWord2 = scanner.nextLine();

        System.out.println("Введите текст: ");
        String text = scanner.nextLine();

        String enc = vigener.encrypt(vigener.encrypt(text, keyWord1), keyWord2);

        System.out.println("Зашифрованное сообщение: ");
        System.out.println(enc);

        String dec = vigener.decrypt(vigener.decrypt(enc, keyWord2), keyWord1);

        System.out.println("Расшифрованное сообщение: ");
        System.out.println(dec);
    }

    private static ArrayList<Character> getRussianAlphabet(){
        ArrayList<Character> res = new ArrayList<>();
        for(char c = 'А'; c <= 'я'; c++){
            res.add(c);
        }
        return res;
    }
}