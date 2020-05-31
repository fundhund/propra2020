package fernuni.propra.algorithm;

import java.io.File;

import fernuni.propra.file_processing.Room;
import fernuni.propra.file_processing.XmlReader;

/**
 * Diese Klasse wird als API (Application Programming Interaface) verwendet. Das
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
		
		if (xmlFile == "" || !new File(xmlFile).isFile()) return false;
		
		try {
			
			XmlReader xmlReader = new XmlReader(xmlFile);
			Room room = xmlReader.createRoom();
			
			Validator validator = new Validator(room);
			return validator.validate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		
		if (xmlFile == "" || !new File(xmlFile).isFile()) return 0;
		
		try {
			
			System.out.println();
			System.out.println("Solving '" + xmlFile + "'...");
			
			XmlReader xmlReader = new XmlReader(xmlFile);
			Room room = xmlReader.createRoom();
			
			Solver solver = new Solver(room);
			return solver.solve(timeLimit);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
}
