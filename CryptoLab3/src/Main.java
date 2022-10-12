import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        String[] cesarKey = readFile("src/afinCesarKey.txt").split(" ");
        ArrayList<Character> alphabet = getRussianAlphabet();
        AfinCesarEncryptor afinCesarEncryptor = new AfinCesarEncryptor(alphabet, Integer.parseInt(cesarKey[0]), Integer.parseInt(cesarKey[1]));

        String toEncrypt = readFile("src/encrypt.txt");
        String encrypted = afinCesarEncryptor.encrypt(toEncrypt);

        AfinCesarCryptoAnalysis afinCesarCryptoAnalysis = new AfinCesarCryptoAnalysis(toEncrypt);
        afinCesarCryptoAnalysis.setA(Integer.parseInt(cesarKey[0]));

        int k = afinCesarCryptoAnalysis.analyse(encrypted);
        afinCesarEncryptor = new AfinCesarEncryptor(alphabet, Integer.parseInt(cesarKey[0]), k);

        String decrypted = afinCesarEncryptor.decrypt(encrypted);
        System.out.println(decrypted);
        System.out.println("Ключ, полученный в рез-те криптоанализа - " + k);
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

}