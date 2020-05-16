package fernuni.propra.algorithm;

import static fernuni.propra.algorithm.RectangleHelper.getIntersection;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fernuni.propra.file_processing.Room;

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
