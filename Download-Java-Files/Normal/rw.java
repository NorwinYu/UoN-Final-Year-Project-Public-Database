import java.io.*;
import java.util.*;
public class rw {
private String directory;
private String ins;
private String compte;
private boolean creer;
private boolean ligne;
private File fichier;
private FileReader lecture;
private FileWriter ecriture;
private BufferedReader lecture_1;
private BufferedWriter ecriture_1;
	public rw(String directory_1, boolean creer_1) {
		directory = directory_1;
		creer = creer_1;
		fichier = new File (directory);
		if (!fichier.exists() && creer==false) {
			System.out.print("Aucun fichier trouvé !");
		}
		if (!fichier.exists() && creer==true) {
				try {
					fichier.createNewFile();
					} catch (IOException e) {
					e.printStackTrace();
			}
		}
			try {
				lecture = new FileReader (new File (directory));
				ecriture = new FileWriter (new File (directory),true);
				ecriture_1 = new BufferedWriter (ecriture);
				lecture_1 = new BufferedReader (lecture);
			}catch (IOException e) {
				e.printStackTrace();
			}
		}

	public void ecrire (String inscription) {
		ins = inscription;
		System.out.println (directory);
		System.out.println (ins);
		try {
			ecriture_1.write(ins);
			ecriture_1.close ();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void lire (int ligne_p, int ligne_d, boolean ligne_1) {
		int pp = 0;
		int dp = 0;
		while (pp != ligne_p) {
			try {
				lecture_1.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
			++pp;
		}
		if (ligne_1==true) {
			while (dp != ligne_d){
				try {
					System.out.print(lecture_1.readLine()+"\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
				++dp;
		}
	}
		if (ligne_1==false) {
			compte = "";
			while  (compte != null){
				try {
					compte = lecture_1.readLine();
					System.out.print(compte+"\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
}
}
