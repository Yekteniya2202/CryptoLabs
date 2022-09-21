import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Character> alphabet = getRussianAlphabet();
        try {
            String[] cesarKey = readFile("src/afinCesarKey.txt").split(" ");
            String[] magicKey = readFile("src/magicSquareKey.txt").split(" ");
            String toEncrypt = readFile("src/encrypt.txt");

            if (cesarKey.length != 2) {
                throw new RuntimeException("cesar key not valid");
            }
            System.out.println(Arrays.toString(readFile("src/magicSquareKey.txt").split(" ")));
            AfinCesarEncryptor afinCesarEncryptor = new AfinCesarEncryptor(alphabet, Integer.parseInt(cesarKey[0]), Integer.parseInt(cesarKey[1]));
            int n = Integer.parseInt(magicKey[0]);
            ArrayList<Integer> key = new ArrayList<>();
            if (n * n != magicKey.length - 1) {
                throw new RuntimeException("magic key not valid");
            }
            for (int i = 1; i < magicKey.length; i++) {
                key.add(Integer.parseInt(magicKey[i]));
            }
            MagicSquareEncryptor magicSquareEncryptor = new MagicSquareEncryptor(n, key);

            System.out.println("Encypting");
            String afinEncrypted = afinCesarEncryptor.encrypt(toEncrypt);
            System.out.println("Afin encrypted to \"" + afinEncrypted + "\" (chech to decrypt - \"" + afinCesarEncryptor.decrypt(afinEncrypted) + "\")");
            String magicSquareEncrypted = magicSquareEncryptor.encrypt(afinEncrypted);
            System.out.println("Magic square encrypted to \"" + magicSquareEncrypted + "\" (chech to decrypt - \"" + magicSquareEncryptor.decrypt(magicSquareEncrypted) + "\")");
            System.out.println("=======================");
            System.out.println("Decrypting");
            String magicSquareDecrypted = magicSquareEncryptor.decrypt(magicSquareEncrypted);
            System.out.println("Magic square decrypted to \"" + magicSquareDecrypted + "\" (chech to encrypt - \"" + magicSquareEncryptor.encrypt(magicSquareDecrypted) + "\")");
            String afinDecrypted = afinCesarEncryptor.decrypt(magicSquareDecrypted);
            System.out.println("Afin decrypted to \"" + afinDecrypted + "\" (chech to encrypt - \"" + afinCesarEncryptor.encrypt(afinDecrypted) + "\")");

            writeFile("src/decrypt.txt", afinDecrypted);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
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
        for (char c = 'А'; c <= 'Я'; c++) {
            alphabet.add(c);
        }
        alphabet.add(' ');
        alphabet.add(',');
        alphabet.add('.');
        alphabet.add('Ё');
        return alphabet;
    }
}