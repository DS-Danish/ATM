import java.util.Scanner;

public class Keypad {
    private Scanner scanner; // Scanner to read input

    // Constructor initializes the scanner
    public Keypad() {
        scanner = new Scanner(System.in);
    }

    // Read input as string
    public String getInput() {
        return scanner.nextLine();
    }
}
