import javax.swing.*;
import java.util.Scanner;

public class DESApp extends JFrame {
    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter key with length = 7: ");
        String key = scanner.nextLine();
        System.out.print("Enter initial vector with length = 8: ");
        String IV = scanner.nextLine();
        String text = scanner.nextLine();
        DES des = new DES(key, IV);
        String encrypted = des.encrypt(text);
        String decrypted = des.decrypt(encrypted);
        System.out.println("MAIN");
        System.out.println("text = " + text);
        System.out.println("encrypted = " + encrypted);
        System.out.println("decrypted = " + decrypted);

    }
}