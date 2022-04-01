import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;

public class Plugboard {

    static int[] plugboard = new int[Keyboard.keys.length];

    public static <or> void plugconfig(String message) {
        System.out.println(message);

        Scanner SwapInput = new Scanner(System.in);
        String userInput;

        for (int i = 0; i < Keyboard.keys.length; i++) {
            plugboard[i] = i;
        }

        // Plug-board config:
        continueSwap:
        while (true) {

            System.out.println("What TWO characters do you want to swap?");
            userInput = SwapInput.nextLine().toUpperCase();

            if (userInput.length() > 2) {
                System.err.println("Error found, please only provide a maximum of TWO characters");
                continue;
            } else if (userInput.length() < 2) {
                System.err.println("Error found, please provide a minimum of TWO characters");
                continue;
            }

            int char1;
            int char2;
            int temp;
            char1 = (userInput.charAt(0) - 65);
            char2 = (userInput.charAt(1) - 65);

            if (isKeySwapped(char1) || isKeySwapped(char2)) {
                System.err.println("Error found, one of these two characters were already swapped");
                continue;
            }

            temp = plugboard[char1];
            plugboard[char1] = plugboard[char2];
            plugboard[char2] = temp;

            continueCheck:
            while (true) {

                System.out.println("Would you like to swap more characters? Y/N");
                userInput = SwapInput.nextLine().toUpperCase();

                switch (userInput) {

                    case "Y":
                        break continueCheck;
                    case "N":
                        return;
                    default:
                        System.err.println("Error found, Please respond with Y/N");
                }
            }
        }
    }

    public static int plug(int message) {

        int result = indexASCII(message);

        result = plugboard[result];

        return result;
    }

    private static int indexASCII(int charASCII) {

        for (int x = 0; x < Keyboard.keys.length; x++) {

            if (charASCII == Keyboard.keys[x]) {
                return x;
            }
        }
        return -1;
    }

    private static boolean isKeySwapped(int key) {
        if (plugboard[key] + 65 == Keyboard.keys[key]) {
            return false;
        }
        return true;
    }

    public static void printPlug() {
        for (int i = 0; i < Keyboard.keys.length; i++) {
            System.out.print((char) (plugboard[i] + 65) + " ");
        }
        System.out.println("\n");
    }
}