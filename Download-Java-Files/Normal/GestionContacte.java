import java.io.File;
import java.util.*;
public class GestionContacte {
public boolean found;
	public GestionContacte() {
		Scanner option = new Scanner (System.in);
		System.out.print("Veuillez choisire une option : \n"
				+ "1. Modifi� un contacte existant \n"
				+ "2. Cr�er un nouveau contacte \n");
		int option_1 = option.nextInt ();
		switch (option_1) {
		case 1 :
			Modify ();
			break;
		case 2 :
			
			break;
		}
	}
	public String Modify () {
		Scanner info = new Scanner (System.in);
		File contacte;
		String info_1;
		view affichage = new view ();
		do {
		System.out.print("\n Veuillez entr�e le pr�nom et le nom du contacte tout attach� :");
		info_1 = info.nextLine ();
		contacte = new File ("C:/Users/Robi/git/gcontact/Contacte/"+info_1+".txt");
		}while (!contacte.exists());
		rw modif = new rw (("C:/Users/Robi/git/gcontact/Contacte/"+info_1+(".txt")),false);
		modif.lire(0, 0,false);
		return ("Les modification ont bien �t� appliqu�.");
	}
}
