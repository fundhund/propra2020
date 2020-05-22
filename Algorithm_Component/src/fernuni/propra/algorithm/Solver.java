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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fernuni.propra.file_processing.Room;

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
		this.lamps = getCandidateLamps();
		this.numberOfLamps = lamps.size();
	}

	public List<Lamp> getLamps() {
		return lamps;
	}
	
	public List<Point2D.Float> getLampPositions() {
		List<Point2D.Float> lampPositions = new ArrayList<>();
		
		for (Lamp lamp : lamps) {
			lampPositions.add(lamp.getPosition());
		}
		
		return lampPositions;
	}

	public void setLamps(List<Lamp> lamps) {
		this.lamps = new ArrayList<>(lamps);
		this.numberOfLamps = lamps.size();
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
						
						if (!intersection.equals(current)) obsoleteIntersections.add(current);
						if (!intersection.equals(candidate)) obsoleteIntersections.add(candidate);
						obsoleteIntersections.remove(intersection);
						
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
		
		Lamp candidateLamp = new Lamp(position, true, rectangles);
		
		return candidateLamp;
	}
	
	public int solve() throws TimeLimitExceededException {
		return solve(0);
	}
	
	public int solve(int timeLimit) throws TimeLimitExceededException {
		
		long startTime = System.currentTimeMillis();
		
		List<Lamp> switchedOffLamps = new ArrayList<>(this.lamps);
		switchedOffLamps.stream().forEach(lamp -> lamp.switchOff());
		
		Map<Integer, Integer> rectangleIlluminationMap = new HashMap<>();
		Arrays.stream(rectangleIndices).forEach(index -> rectangleIlluminationMap.put(index, 0));
		
		if (timeLimit > 0) {
			this.timeLimit = timeLimit;
			this.endTime = startTime + timeLimit * 1000;
		}
		
		reduceLamps(switchedOffLamps, 0, rectangleIlluminationMap);
		
		System.out.println("Solved in " + ((System.currentTimeMillis() - startTime)/1000f) + " seconds.");
		
		return numberOfLamps;
	}
	
	public void reduceLamps(List<Lamp> candidateLamps, int index, Map<Integer, Integer> rectangleIlluminationMap) throws TimeLimitExceededException {
		
		if (endTime > 0 && System.currentTimeMillis() > endTime) {
			throw new TimeLimitExceededException("Computation took longer than the set time limit of " + timeLimit + " seconds.");
		}
		
		System.out.println("LAMPS:");
		for (Lamp lamp : candidateLamps) {
			if (lamp.isOn()) System.out.println(lamp.getPosition().toString() + ", " + Arrays.toString(lamp.getRectangles()));
		}
		System.out.println(isRoomIlluminated(rectangleIlluminationMap));
		System.out.println();
		
		
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
	
	public boolean isRoomIlluminated(Map<Integer, Integer> rectangleIlluminationMap) {
		return Arrays.stream(rectangleIndices).allMatch(index -> rectangleIlluminationMap.get(index) > 0);
	}
	
	public int getNumberOfSwitchedOnLamps(List<Lamp> lamps) {
		return (int) lamps.stream().filter(lamp -> lamp.isOn()).count();
	}
	
	public void printLamps(List<Lamp> lamps) {
		lamps.stream().forEach(System.out::println);
	}
}
