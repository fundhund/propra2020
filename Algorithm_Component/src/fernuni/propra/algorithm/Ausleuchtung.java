package fernuni.propra.algorithm;

/**
 * Diese Klasse wird als API (Application Programming INteraface) verwendet. Das
 * bedeutet, dass diese Klasse als Bibliothek für andere Applikationen verwendet
 * werden kann.
 * 
 * Bitte achten Sie darauf, am bereits implementierten Rahmen (Klassenname,
 * Package, Methodensignaturen) KEINE Veränderungen vorzunehmen.
 * Selbstverständlich können und müssen Sie innerhalb der Methoden Änderungen
 * vornehmen
 */
public class Ausleuchtung implements IAusleuchtung {

	/**
	 * Überprüft die eingegebene Lösung auf Korrektheit
	 * @param xmlFile Dokument mit der Lösung, die validiert werden soll.
	 * @return true, falls die eingelesene Lösung korrekt ist
	 */
	@Override
	public boolean validateSolution(String xmlFile) {
		// TODO Logik implementieren
		return false;
	}

	/**
	 * Ermittelt eine Lösung zu den eingegebenen Daten
	 * @param xmlFile Dokument, das die zu lösende Probleminstanz enthält
	 * @param timeLimit Zeitlimit in Sekunden
	 * @return Anzahl der Lampen der ermittelten Lösung
	 */
	@Override
	public int solve(String xmlFile, int timeLimit) {
		// TODO Logik implementieren
		return 0;
	}

}
