import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        GammaEncryptor gammaEncryptor = new GammaEncryptor();
        gammaEncryptor.init("src/lcg-keys.txt", "src/lfsr-connectionvector.txt");
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        String encrypt = gammaEncryptor.encrypt(text);
        String decrypt = gammaEncryptor.encrypt(encrypt);

        System.out.println("MAIN RESULT");
        System.out.println(encrypt);
        System.out.println(decrypt);
    }

    public static int pow(int value, int powValue) {
        return (int) Math.pow(value, powValue);
    }

}