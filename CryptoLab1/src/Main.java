import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Character> alphabet = getRussianAlphabet();
        String[] cesarKey = readFile("src/afinCesarKey.txt").split(" ");
        String[] magicKey = readFile("src/magicSquareKey.txt").split(" ");

        if (cesarKey.length != 2){
            throw new RuntimeException("cesar key not valid");
        }
        System.out.println(Arrays.toString(readFile("src/magicSquareKey.txt").split(" ")));
        AfinCesarEncryptor afinCesarEncryptor = new AfinCesarEncryptor(alphabet, Integer.parseInt(cesarKey[0]), Integer.parseInt(cesarKey[1]));
        int n = Integer.parseInt(magicKey[0]);
        ArrayList<Integer> key = new ArrayList<>();
        if (n*n != magicKey.length - 1){
            throw new RuntimeException("magic key not valid");
        }
        for(int i = 1; i < magicKey.length; i++){
            key.add(Integer.parseInt(magicKey[i]));
        }
        MagicSquareEncryptor magicSquareEncryptor = new MagicSquareEncryptor(n, key);
        String encrypted = magicSquareEncryptor.encrypt(magicSquareEncryptor.encrypt("МАГИЧЕСКАЯ СИЛА"));
        String decrypted = magicSquareEncryptor.decrypt(magicSquareEncryptor.decrypt(encrypted));
        System.out.println(decrypted);
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