import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        DESRounds desRounds = new DESRounds(input);
        desRounds.makeRound();
    }
}