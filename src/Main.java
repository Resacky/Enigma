public class Main {

    static String message;

    public static void main(String[] Args) {

        String printPlug = "";
        String printRotor = "";
        String printReflector = "";
        String printRotorBackwards = "";
        String printResult = "";
        System.out.println(Version.ver());

        // Plugboard config:
        Plugboard.plugconfig("Setting up Plug-Board configuration:");
        Plugboard.printPlug();

        // Rotors config:
        System.out.println("Pre-config Rotors:");
        Rotor rightRotor = Rotor.Create("III", 0, 0);
        Rotor middleRotor = Rotor.Create("II", 0, 0);
        Rotor leftRotor = Rotor.Create("I", 0, 0);

        System.out.println("Starting rotations:");
        System.out.println(rightRotor.getPosition());
        System.out.println(middleRotor.getPosition());
        System.out.println(leftRotor.getPosition());
        System.out.println();

        Keyboard.printKeyboard();
        System.out.println("Rotors start from LEFT to RIGHT:" + "\n");
        rightRotor.printRotor();
        System.out.println();
        middleRotor.printRotor();
        System.out.println();
        leftRotor.printRotor();
        System.out.println();

        // Reflectors config:
        System.out.println("pre-config Reflector:");
        Reflector defaultReflector = Reflector.Create("B");
        defaultReflector.printReflector();

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

            //Plugboard encryption:
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
            encryptedMessage = defaultReflector.forward(encryptedMessage);

            printReflector += String.valueOf((char) (encryptedMessage + 65)); // print statement for reflectors

            //Backward Rotor Encryption:
            encryptedMessage = leftRotor.backward(encryptedMessage);
            encryptedMessage = middleRotor.backward(encryptedMessage);
            encryptedMessage = rightRotor.backward(encryptedMessage);

            printRotorBackwards += String.valueOf((char) (encryptedMessage + 65)); // print statement for rotors again

            //Second Plugboard encryption:
            encryptedMessage = Plugboard.plug(encryptedMessage + 65); // print statement for plug again

            //Print statement of the final encrypted message:
            printResult += String.valueOf((char) (encryptedMessage + 65)); // print statement for result

        }
        System.out.println("Plug encryption: " + printPlug);
        System.out.println("Rotor encryption: " + printRotor);
        System.out.println("Reflector encryption: " + printReflector);
        System.out.println("Rotor Backwards encryption: " + printRotorBackwards);
        System.err.println("encryption Result: " + printResult);
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
}

// Update code: The portion of how the rotors -> reflector -> backwards rotors operate with each other and how they communicate with the shift variable in the rotors class
// update goals:
// - update main to where message is turned from ASCII to keysPOS so plugboard only has to worry about position within
//   keys array rather than converting ASCII to keysPOS
// - add UserInput for configuration, before any sort of encryption (for the plug-board, rotors, and reflector)
// - add UserInput for the ring position rotor order, and the ring for the rotors.
// - potentially expand keys array for more characters input for encryption?
