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
	
	public List<Rectangle2D.Float> getCandidateRectangles(Room room) {
		
		Rectangle2D.Float[] rectangles = room.getRectangles();
		List<Rectangle2D.Float> intersections = new ArrayList<>();
		
		Arrays.stream(rectangles).forEach(rectangle -> intersections.add(rectangle));
		
		List<Rectangle2D.Float> currentIntersections = intersections;
		int currentLength = currentIntersections.size();
		boolean foundNewIntersections = false;
		List<Rectangle2D.Float> newIntersections;

		do {
			newIntersections = new ArrayList<>();
			currentLength = currentIntersections.size();
			
			for (int i = 0; i < currentLength; i++) {
				
				Rectangle2D.Float current = currentIntersections.get(i);
				boolean hasIntersection = false;
				
				for (int j = i + 1; j < currentLength; j++) {
					
					Rectangle2D.Float candidate = currentIntersections.get(j);
					if (current.intersects(candidate)) {
						hasIntersection = true;
						foundNewIntersections = true;
						newIntersections.add(getIntersection(current, candidate));
					}
				}
				
				if (!hasIntersection) {
					newIntersections.add(current);
				}
			}
			
			currentIntersections = newIntersections;
			
		} while (foundNewIntersections);
		
		return currentIntersections;
	}
}
