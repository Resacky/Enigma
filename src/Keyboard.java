
public class Keyboard {

	public static int[] keys = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86,
			87, 88, 89, 90 };

	// A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z

	// 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25

	public static void printKeyboard() {
		
		for (int i = 0; i < keys.length; i++) {
			
			System.out.print((char) (Keyboard.keys[i]) + "\t");

		}
		System.out.println();
		
	}
	
	
}
