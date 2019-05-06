package hhu.ausleihservice.validators;

class Messages {


	static String notEmpty = "Benötigtes Feld";

	static String usernameSize = "Der Benutzername muss zwischen 6 und 32 Zeichen lang sein.";
	static String duplicateUsername = "Benutzername bereits vergeben.";
	static String passwordSize = "Passwort muss zwischen 3 und 100 Zeichen lang sein.";
	static String invalidRole = "Diese Rolle existiert nicht.";
	static String invalidEmail = "Email ist nicht gültig.";

	static String latitudeOutOfBounds = "Breitengrad muss zwischen -90 und 90 sein.";
	static String longitudeOutOfBounds = "Längengrad muss zwischen -180 und 180 sein.";
	static String sizeLocationDescription = "Beschreibung muss zwischen 6 und 400 Zeichen lang sein.";

	static String sizeTitle = "Titel muss zwischen 6 und 40 Zeichen lang sein.";
	static String sizeItemDescription = "Beschreibung muss zwischen 6 und 4000 Zeichen lang sein.";
	static String negativeValue = "Der Wert muss mindestens 1 betragen";
	static String invalidPeriod = "Das Anfangsdatum muss vor dem Enddatum liegen!";
	static String invalidAvailableFrom = "Das Anfangsdatum muss mindestens heute sein";

	static String itemNotAvailable = "Der Artikel ist im gewählten Zeitraum nicht verfügbar";
	static String invalidUser = "Der angegebene Nutzer stimmt nicht mit dem Login überein";
	static String ownItemAusleihe = "Ein eigener Artikel kann nicht ausgeliehen werden";
	static String notEnoughMoney = "Das Guthaben auf dem ProPay-Konto reicht nicht für die Kaution";

	static String propayUnavailable = "ProPay ist aktuell nicht verfügbar";
}
