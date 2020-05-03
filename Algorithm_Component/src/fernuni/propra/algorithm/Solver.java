package fernuni.propra.algorithm;

import static fernuni.propra.file_processing.RectangleHelper.getIntersection;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fernuni.propra.file_processing.Room;

public class Solver {

	public static List<Rectangle2D.Float> getCandidateRectangles(Room room) {
			
		Rectangle2D.Float[] rectangles = room.getRectangles();
		List<Rectangle2D.Float> intersections = new ArrayList<>();
		
		Arrays.stream(rectangles).forEach(rectangle -> intersections.add(rectangle));
		
		List<Rectangle2D.Float> currentIntersections = intersections;
		int currentLength = currentIntersections.size();
		boolean foundNewIntersections = false;
		List<Rectangle2D.Float> newIntersections; 

		do {
			foundNewIntersections = false;
			newIntersections = new ArrayList<>();
			currentLength = currentIntersections.size();
			
			currentIntersections.stream().forEach(i -> System.out.println(i.toString()));
			System.out.println("####");
			
			Set<Rectangle2D.Float> rectanglesWithIntersections = new HashSet<>();
			
			for (int i = 0; i < currentLength; i++) {
				
				Rectangle2D.Float current = currentIntersections.get(i);
				
				for (int j = i + 1; j < currentLength; j++) {
					
					Rectangle2D.Float candidate = currentIntersections.get(j);
					
					if (current.intersects(candidate)) {

						rectanglesWithIntersections.add(current);
						rectanglesWithIntersections.add(candidate);
						
						foundNewIntersections = true;
						newIntersections.add(getIntersection(current, candidate));
					}
				}
				
				if (!rectanglesWithIntersections.contains(current)) {
					newIntersections.add(current);
				}
			}
			
			currentIntersections = newIntersections;
			
		} while (foundNewIntersections);
		
		return currentIntersections;
	}
}
