package fernuni.propra.algorithm;

import static fernuni.propra.algorithm.RectangleHelper.getIntersection;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fernuni.propra.file_processing.Room;

/**
 * Calculates efficient lamp positions for a given room.
 * 
 * @author Marius Mielke (4531230)
 *
 */
public class Solver {
	
	private Room room;
	private int[] rectangleIndices;
	private List<Lamp> lamps;
	private int numberOfLamps;
	private long endTime = 0;
	private int timeLimit = 0;

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Solver(Room room) {
		this.room = room;
		this.rectangleIndices = IntStream.range(0, room.getRectangles().length).toArray();
		this.numberOfLamps = 0;
	}

	public List<Lamp> getLamps() {
		return lamps;
	}
	
	/**
	 * Returns positions of lamps.
	 * 
	 * @return a list of positions
	 */
	public List<Point2D.Float> getLampPositions() {
		List<Point2D.Float> lampPositions = new ArrayList<>();
		
		for (Lamp lamp : lamps) {
			lampPositions.add(lamp.getPosition());
		}
		
		return lampPositions;
	}

	/**
	 * Sets lamps and number of lamps.
	 * 
	 * @param lamps a list of lamps
	 */
	public void setLamps(List<Lamp> lamps) {
		this.lamps = new ArrayList<>(lamps);
		this.numberOfLamps = lamps.size();
	}

	/**
	 * Calculates intersections that contain parts of all room wall's rectangles.
	 * 
	 * @return a map of rectangles and lists of room wall rectangle indices
	 */
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
			obsoleteIntersections = new ArrayList<>();
			
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
						
						if (!intersection.equals(current) 
								&& !obsoleteIntersections.contains(current)) obsoleteIntersections.add(current);
						if (!intersection.equals(candidate) 
								&& !obsoleteIntersections.contains(candidate)) obsoleteIntersections.add(candidate);
						
						obsoleteIntersections.remove(intersection);
						
						foundNewIntersections = true;
						
						if (intersectionsMap.containsKey(intersection)) involvedRectangles.addAll(intersectionsMap.get(intersection));
							
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
	
	/**
	 * Sorts an array.
	 * 
	 * @param rectangleSet a set of rectangles.
	 * @return a sorted array
	 */
	public static int[] toSortedArray(Set<Integer> rectangleSet) {
		
		int[] rectangleArray = new int[rectangleSet.size()];
		int i = 0;
		
		for(int rectangle : rectangleSet) {
			rectangleArray[i++] = rectangle;
		}
		
		Arrays.sort(rectangleArray);
		
		return rectangleArray;
	}
	
	/**
	 * Creates a list of lamps for calculated candidate rectangles.
	 * 
	 * @return a list of lamps
	 */
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
	
	/**
	 * Creates a lamp from a rectangle and a set of illuminated rectangles.
	 * 
	 * @param candidateRectangle calculates rectangle
	 * @param involvedRectangles set room wall rectangles intersecting with candidate rectangle as indices
	 * @return a lamp
	 */
	public Lamp getCandidateLamp(Rectangle2D.Float candidateRectangle, Set<Integer> involvedRectangles) {
		
		float x = candidateRectangle.x + candidateRectangle.width/2;
		float y = candidateRectangle.y + candidateRectangle.height/2;
		
		Point2D.Float position = new Point2D.Float(x, y);
		int[] rectangles = toSortedArray(involvedRectangles);
		
		Lamp candidateLamp = new Lamp(position, true, rectangles);
		
		return candidateLamp;
	}
	
	/**
	 * Solves a room without any time limit.
	 * 
	 * @return number of lamps
	 * @throws TimeLimitExceededException time limit exception
	 */
	public int solve() throws TimeLimitExceededException {
		return solve(0);
	}
	
	/**
	 * Solves a room with a given time limit.
	 * 
	 * @param timeLimit time limit in seconds
	 * @return number of lamps
	 * @throws TimeLimitExceededException time limit exception
	 */
	public int solve(int timeLimit) throws TimeLimitExceededException {
		
		long startTime = System.currentTimeMillis();
		
		// Calculate candidate rectangles
		System.out.println();
		System.out.println("Calculating candidate lamp positions...");
		long rectanglesStartTime = System.currentTimeMillis();
		setLamps(getCandidateLamps());
		System.out.println("Calculated candidate lamp positions in " + ((System.currentTimeMillis() - rectanglesStartTime)/1000f) + " seconds.");
		
		
		List<Lamp> switchedOffLamps = new ArrayList<>(this.lamps);
		switchedOffLamps.stream().forEach(lamp -> lamp.switchOff());
		
		Map<Integer, Integer> rectangleIlluminationMap = new HashMap<>();
		Arrays.stream(rectangleIndices).forEach(index -> rectangleIlluminationMap.put(index, 0));
		
		if (timeLimit > 0) {
			this.timeLimit = timeLimit;
			this.endTime = startTime + timeLimit * 1000;
		}
		
		// Reduce lamps
		System.out.println();
		System.out.println("Running lamp reduction...");
		int oldNumberOfLamps = this.numberOfLamps;
		long reduceStartTime = System.currentTimeMillis();
		reduceLamps(switchedOffLamps, 0, rectangleIlluminationMap);
		
		String lampInfo = oldNumberOfLamps == this.numberOfLamps
				? "No further reduction was carried out."
				: "Reduced number of lamps from " + oldNumberOfLamps + " to " + this.numberOfLamps + ".";
		
		System.out.println("Lamp reduction took " + ((System.currentTimeMillis() - reduceStartTime)/1000f) + " seconds. " + lampInfo);
		System.out.println();
		System.out.println("Solved with " + numberOfLamps + " lamp(s) in " + ((System.currentTimeMillis() - startTime)/1000f) + " seconds.");
		
		return numberOfLamps;
	}
	
	/**
	 * Reduces the number of candidate lamps to the necessary amount.
	 * 
	 * @param candidateLamps a list of lamps
	 * @param index lamp index
	 * @param rectangleIlluminationMap a map showing how many lamps are illuminating one rectangle (by index)
	 * @throws TimeLimitExceededException time limit exception
	 */
	public void reduceLamps(List<Lamp> candidateLamps, int index, Map<Integer, Integer> rectangleIlluminationMap) throws TimeLimitExceededException {
		
		if (endTime > 0 && System.currentTimeMillis() > endTime) {
			throw new TimeLimitExceededException("Computation took longer than the set time limit of " + timeLimit + " seconds.");
		}
		
		if (isRoomIlluminated(rectangleIlluminationMap)) {
			setLamps(candidateLamps
					.stream()
					.filter(lamp -> lamp.isOn())
					.collect(Collectors.toList()));
			return;
		}
		
		if (index >= candidateLamps.size()) return;
		
		if (getNumberOfSwitchedOnLamps(candidateLamps) < numberOfLamps) {
			
			Lamp currentLamp = candidateLamps.get(index);
			int[] currentRectangleIndices = currentLamp.getRectangles();
			
			currentLamp.switchOn();
			
			Arrays.stream(currentRectangleIndices)
				.forEach(rectangleIndex -> {
					rectangleIlluminationMap.merge(rectangleIndex, 1, (a, b) -> a + b);
				});
		
			reduceLamps(new ArrayList<>(candidateLamps), index + 1, new HashMap<>(rectangleIlluminationMap));
		
			currentLamp.switchOff();
			
			Arrays.stream(currentRectangleIndices)
				.forEach(rectangleIndex -> {
					rectangleIlluminationMap.merge(rectangleIndex, 1, (a, b) -> a - b);
				});
			
			reduceLamps(new ArrayList<>(candidateLamps), index + 1, new HashMap<>(rectangleIlluminationMap));
		}
	}
	
	/**
	 * Checks whether a room is completely illuminated.
	 * 
	 * @param rectangleIlluminationMap a map showing how many lamps are illuminating one rectangle (by index)
	 * @return a boolean value
	 */
	public boolean isRoomIlluminated(Map<Integer, Integer> rectangleIlluminationMap) {
		return Arrays.stream(rectangleIndices).allMatch(index -> rectangleIlluminationMap.get(index) > 0);
	}
	
	/**
	 * Returns the number of switched on lamps in a given list of lamps.
	 * 
	 * @param lamps a list of lamps
	 * @return the number of switched on lamps
	 */
	public int getNumberOfSwitchedOnLamps(List<Lamp> lamps) {
		return (int) lamps.stream().filter(lamp -> lamp.isOn()).count();
	}
	
	/**
	 * Prints a list of lamps in human-readable form.
	 * 
	 * @param lamps a list of lamps
	 */
	public void printLamps(List<Lamp> lamps) {
		lamps.stream().forEach(System.out::println);
	}
}
