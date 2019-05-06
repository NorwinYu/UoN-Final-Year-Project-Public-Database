import java.util.Scanner;
//finds occurrence of each letter and replaces with code letter
public class Cipher { 
	//	Library for encrypting
	public static String encrypt(String user_mat) {
		user_mat = user_mat.replace("A", "v");
		user_mat = user_mat.replace("B", "u");
		user_mat = user_mat.replace("C", "t");
		user_mat = user_mat.replace("D", "s"); 
		user_mat = user_mat.replace("E", "r");
		user_mat = user_mat.replace("F", "q");
		user_mat = user_mat.replace("G", "p");
		user_mat = user_mat.replace("H", "o");
		user_mat = user_mat.replace("I", "n");
		user_mat = user_mat.replace("J", "m");
		user_mat = user_mat.replace("K", "l");
		user_mat = user_mat.replace("L", "k");
		user_mat = user_mat.replace("M", "j");
		user_mat = user_mat.replace("N", "i");
		user_mat = user_mat.replace("O", "h");
		user_mat = user_mat.replace("P", "g");
		user_mat = user_mat.replace("Q", "f");
		user_mat = user_mat.replace("R", "e");
		user_mat = user_mat.replace("S", "d");
		user_mat = user_mat.replace("T", "c");
		user_mat = user_mat.replace("U", "b");
		user_mat = user_mat.replace("V", "a");
		user_mat = user_mat.replace("W", "z");
		user_mat = user_mat.replace("X", "y");
		user_mat = user_mat.replace("Y", "x");
		user_mat = user_mat.replace("Z", "w");
		user_mat = user_mat.replace(" ", ";");
		return "Encrypted: " + user_mat;
	}
	//finds occurance of each letter and replaces with "real" letter
	public static String decrypt(String user_mat)
	{ //Library for decrypting
		user_mat = user_mat.replace("v","A"); 
		user_mat = user_mat.replace("u","B");
		user_mat = user_mat.replace("t","C");
		user_mat = user_mat.replace("s","D");
		user_mat = user_mat.replace("r","E");
		user_mat = user_mat.replace("q","F");
		user_mat = user_mat.replace("p","G");
		user_mat = user_mat.replace("o","H");
		user_mat = user_mat.replace("n","I");
		user_mat = user_mat.replace("m","J");
		user_mat = user_mat.replace("l","K");
		user_mat = user_mat.replace("k","L");
		user_mat = user_mat.replace("j","M");
		user_mat = user_mat.replace("i","N");
		user_mat = user_mat.replace("h","O");
		user_mat = user_mat.replace("g","P");
		user_mat = user_mat.replace("f","Q");
		user_mat = user_mat.replace("e","R");
		user_mat = user_mat.replace("d","S");
		user_mat = user_mat.replace("c","T");
		user_mat = user_mat.replace("b","U");
		user_mat = user_mat.replace("a","V");
		user_mat=  user_mat.replace("z","W");
		user_mat = user_mat.replace("y","X");
		user_mat = user_mat.replace("x","Y");
		user_mat = user_mat.replace("w","Z");
		user_mat = user_mat.replace(";"," ");
		return "Decrypted: " + user_mat; 
	}
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		boolean run = true;
		while (run) 
		{
			System.out.println("To translate English to code, press [1] \nTo translate code to English, press [2]\nTo exit press [3]");
			System.out.println("Enter your choice: ");
			int response = Integer.parseInt(scan.nextLine());
			//to translate English to code
			if (response == 1) {
				System.out.println("Enter your message: ");
				String message = scan.nextLine().toUpperCase();
				String en_message = encrypt(message);
				System.out.println(en_message);
				//to translate code to English
			} else if (response == 2) {
				System.out.println("Enter the message to decrypt: ");
				String message = scan.nextLine().toLowerCase();
				String de_message = decrypt(message);
				System.out.println(de_message);
				//if no valid option is chosen, run option menu again
			} else if (response == 3) {
				run = false;
				//if no valid option is chosen, run option menu again
			}else {
				System.out.println("Not valid. Enter either a 1, 2, or 3");
			}
		}
		scan.close();
	}
}




