import java.util.Scanner;

public class UserInput {

	public static String getUserInput(String message) {
		Scanner input = new Scanner(System.in);
		String message_to_encrypt;

		System.out.println(message);
		message_to_encrypt = input.nextLine();

		System.out.println(message_to_encrypt);
		input.close();
		return message_to_encrypt.toUpperCase();
	}
}
