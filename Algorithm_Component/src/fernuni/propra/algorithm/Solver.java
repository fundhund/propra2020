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
	
	private Room room;
	private int numberOfWalls;
	private List<Lamp> lamps;

	public Solver(Room room) {
		this.room = room;
		this.numberOfWalls = room.getWalls().size();
		this.lamps = createLamps();
	}

	public Map<Rectangle2D.Float, Set<Integer>> createCandidateRectangles() {
			
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Lamp> getCandidateLamps() {
		
		Map<Rectangle2D.Float, Set<Integer>> intersectionsMap = createCandidateRectangles();
		List<Lamp> candidateLamps = new ArrayList<>();
		
		Iterator<Entry<Rectangle2D.Float, Set<Integer>>> iterator = intersectionsMap.entrySet().iterator();
    while (iterator.hasNext()) {
    	
        Map.Entry entry = (Map.Entry)iterator.next();
        Rectangle2D.Float candidateRectangle = (Rectangle2D.Float) entry.getKey();
        Set<Integer> involvedRectangles = (Set<Integer>) entry.getValue();
        
        candidateLamps.add(getCandidateLamp(candidateRectangle, involvedRectangles));
        iterator.remove();
    }
		
		return candidateLamps;
	}
	
	public Lamp getCandidateLamp(Rectangle2D.Float candidateRectangle, Set<Integer> involvedRectangles) {
		
		float x = candidateRectangle.x + candidateRectangle.width/2;
		float y = candidateRectangle.y + candidateRectangle.height/2;
		
		Point2D.Float position = new Point2D.Float(x, y);
		int[] rectangles = toSortedArray(involvedRectangles);
		
		Lamp candidateLamp = new Lamp(position, rectangles);
		
		return candidateLamp;
	}
	
	public List<Lamp> createLamps() {
		return getCandidateLamps();
	}
	
//	public static List<Lamp> reduceNumberOfLamps(List<Lamp> candidateLamps, )
}
