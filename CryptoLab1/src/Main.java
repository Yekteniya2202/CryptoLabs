import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Character> alphabet = getRussianAlphabet();
        AfinCesarEncryptor afinCesarEncryptor = new AfinCesarEncryptor(alphabet, 3, 3);
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String toEnc = scanner.nextLine();
            String encrypted = afinCesarEncryptor.encrypt(toEnc);
            System.out.println(encrypted);
            System.out.println(afinCesarEncryptor.decrypt(encrypted));
        }
    }

    private static String readFile(String path) {
        try(FileReader reader = new FileReader(path)){
            int c;
            StringBuilder stringBuilder = new StringBuilder();
            while((c=reader.read())!=-1){
                stringBuilder.append((char)c);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeFile(String path, String data) {
        try(FileWriter writer = new FileWriter(path)){
            writer.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static ArrayList<Character> getRussianAlphabet() {
        ArrayList<Character> alphabet = new ArrayList<>();
        for (char c = 'А'; c <= 'я'; c++) {
            alphabet.add(c);
        }
        alphabet.add(' ');
        alphabet.add(',');
        alphabet.add('.');
        return alphabet;
    }
}