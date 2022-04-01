import java.util.Scanner;

public class Main {

    static String message;

    public static void main(String[] Args) {

        Scanner config = new Scanner(System.in);
        String userInput;

        String printPlug = "";
        String printRotor = "";
        String printReflector = "";
        String printRotorBackwards = "";
        String printResult = "";
        System.out.println(Version.ver());

        // Plug-board config:
        Plugboard.plugconfig("Setting up Plug-Board configuration:");
        Plugboard.printPlug();

        // Rotors config:
        System.out.println("Setting up rotor configuration:");

        Rotor leftRotor = makeRotor("Left Rotor", config);
        Rotor middleRotor = makeRotor("Middle Rotor", config);
        Rotor rightRotor = makeRotor("Right Rotor", config);

        System.out.println("Starting rotations:");
        System.out.println(leftRotor.getPosition());
        System.out.println(middleRotor.getPosition());
        System.out.println(rightRotor.getPosition() + "\n");

        Keyboard.printKeyboard();
        System.out.println("Rotors start from LEFT to RIGHT:" + "\n");
        leftRotor.printRotor();
        middleRotor.printRotor();
        rightRotor.printRotor();

        // Reflectors config:
        System.out.println("Reflector Configuration:");
        Reflector reflector = makeReflector(config);
        reflector.printReflector();

        // Put loop here for user to keep encrypting until they want to stop
        continueEncryption:
        while (true) {

            rightRotor.reset();
            middleRotor.reset();
            leftRotor.reset();

            printPlug = "";
            printRotor = "";
            printReflector = "";
            printRotorBackwards = "";
            printResult = "";

            // User input message:
            message = UserInput.getUserInput("write message to encrypt:");

            //check for error syntax within user message:
            if (checkError()) {
                System.err.println("Error found. Please only type in using English alphabet, Space, and !");
                return;
            }

            // Simplified variables:
            int encryptedMessage;

            // Prime for loop:
            for (int i = 0; i < message.length(); i++) {

                //Plug-board encryption:
                encryptedMessage = message.charAt(i);
                encryptedMessage = Plugboard.plug(encryptedMessage); // using int encryptedMessage and passing it through the plug
                // class while also updating the int encryptedMessage

                printPlug += String.valueOf((char) (encryptedMessage + 65)); // print statement for plug

                //Rotor Encryption:
                //This code compensates for rotation in the rotors:
                rotorRotation(rightRotor, middleRotor, leftRotor);

                encryptedMessage = rightRotor.forward(encryptedMessage);
                encryptedMessage = middleRotor.forward(encryptedMessage);
                encryptedMessage = leftRotor.forward(encryptedMessage);

                printRotor += String.valueOf((char) (encryptedMessage + 65)); // print statement for rotors

                //Reflector Encryption:
                encryptedMessage = reflector.forward(encryptedMessage);

                printReflector += String.valueOf((char) (encryptedMessage + 65)); // print statement for reflectors

                //Backward Rotor Encryption:
                encryptedMessage = leftRotor.backward(encryptedMessage);
                encryptedMessage = middleRotor.backward(encryptedMessage);
                encryptedMessage = rightRotor.backward(encryptedMessage);

                printRotorBackwards += String.valueOf((char) (encryptedMessage + 65)); // print statement for rotors again

                //Second Plug-board encryption:
                encryptedMessage = Plugboard.plug(encryptedMessage + 65); // print statement for plug again

                //Print statement of the final encrypted message:
                printResult += String.valueOf((char) (encryptedMessage + 65)); // print statement for result

            }
            System.out.println("Plug encryption: " + printPlug);
            System.out.println("Rotor encryption: " + printRotor);
            System.out.println("Reflector encryption: " + printReflector);
            System.out.println("Rotor Backwards encryption: " + printRotorBackwards);
            System.err.println("encryption Result: " + printResult + "\n");

            continueCheck:
            while (true) {

                System.out.println("\nWould you like to encrypt another message? Y/N");
                userInput = config.nextLine().toUpperCase();

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

    public static boolean checkError() {
        for (int i = 0; i < message.length(); i++) {

            for (int x = 0; x < Keyboard.keys.length; x++) {
                if (message.charAt(i) == (char) Keyboard.keys[x]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void rotorRotation(Rotor rightRotor, Rotor middleRotor, Rotor leftRotor) {

        if (rightRotor.getPosition() == 25) {

            middleRotor.turnover();

            if (middleRotor.getPosition() == 25) {

                leftRotor.turnover();

            }

        }

        rightRotor.turnover();

    }

    public static Rotor makeRotor(String rotor, Scanner config) {

        String rotorType;
        int rotorPos;
        int ringSetting;

        System.out.println(rotor + " configuration:");
        System.out.println("What kind of rotor? (I -> VIII)");

        rotorType = config.nextLine().toUpperCase();

        System.out.println("Position of rotor: (0 -> 25)");
        rotorPos = config.nextInt();
        config.nextLine();

        System.out.println("Ring setting of rotor: ( 0 -> 25)");
        ringSetting = config.nextInt();
        config.nextLine();

        System.err.println(rotor + " has been configured" + "\n");

        return Rotor.Create(rotorType, rotorPos, ringSetting);
    }

    public static Reflector makeReflector(Scanner config) {

        System.out.println("What kind of reflector do you want to use? (B or C)");

        String reflectorType;
        reflectorType = config.nextLine().toUpperCase();

        System.err.println("Reflector has been configured with " + reflectorType);

        return Reflector.Create(reflectorType);
    }
}

// update goals:
// - update main to where message is turned from ASCII to keysPOS so plug-board only has to worry about position within
//   keys array rather than converting ASCII to keysPOS
// - potentially expand keys array for more characters input for encryption?x
// - GUI interface for this program.
