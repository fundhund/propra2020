package fernuni.propra.algorithm;

import static fernuni.propra.algorithm.RectangleHelper.getIntersection;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fernuni.propra.file_processing.Room;

public class Solver {

	public static Map<Rectangle2D.Float, Set<Integer>> getCandidateRectangles(Room room) {
			
		Rectangle2D.Float[] rectangles = room.getRectangles();
		List<Rectangle2D.Float> intersections = new ArrayList<>();
		
		Arrays.stream(rectangles).forEach(rectangle -> intersections.add(rectangle));
		
		boolean foundNewIntersections = false;
		
		Map<Rectangle2D.Float, Set<Integer>> intersectionsMap = new HashMap<>();
		for (int k = 0; k < rectangles.length; k++) {
			intersectionsMap.put(rectangles[k], new HashSet<>(Arrays.asList(k)));
		}
		
		List<Rectangle2D.Float> obsoleteIntersections = new ArrayList<>();
		
		do {
			foundNewIntersections = false;
			
			List<Rectangle2D.Float> rectangleList = new ArrayList<>();
			rectangleList.addAll(intersectionsMap.keySet());
			int numberOfRectangles = rectangleList.size();
			
			for (int i = 0; i < numberOfRectangles; i++) {
				Rectangle2D.Float current = rectangleList.get(i);
				
				for (int j = i + 1; j < numberOfRectangles; j++) {
					Rectangle2D.Float candidate = rectangleList.get(j);
					
					if (current.intersects(candidate)) {
						
						Rectangle2D.Float intersection = getIntersection(current, candidate);
						Set<Integer> involvedRectangles = new HashSet<>();
						
						involvedRectangles.addAll(intersectionsMap.get(current));
						involvedRectangles.addAll(intersectionsMap.get(candidate));
						
						obsoleteIntersections.add(current);
						obsoleteIntersections.add(candidate);
						
						foundNewIntersections = true;
						intersectionsMap.put(intersection, involvedRectangles);
					}
				}
			}
						
			obsoleteIntersections
			.stream()
			.forEach(intersection -> intersectionsMap.remove(intersection));
			
		} while (foundNewIntersections);
		
		return intersectionsMap;
	}
	
	public static int[] toSortedArray(Set<Integer> rectangleSet) {
		
		int[] rectangleArray = new int[rectangleSet.size()];
		int i = 0;
		
		for(int rectangle : rectangleSet) {
			rectangleArray[i++] = rectangle;
		}
		
		Arrays.sort(rectangleArray);
		
		return rectangleArray;
	}
	
//	public static List<Lamp> getCandidateLamps(Room room) {
//		
//		return candidateLamps;
//	}
	
//	public static List<Lamp> getCandidateLamp(Rectangle2D.Float intersection, Set<Integer> rectangles) {
//		
//		return candidateLamp;
//	}
	
//	public static List<Lamp> reduceNumberOfLamps(List<Lamp> candidateLamps, )
}
