package fernuni.propra.file_processing;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Float;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class Room {
	
	private String id;
	private List<Point2D.Float> corners;
	private List<Point2D.Float> lamps;
	private Path2D.Float shape;
	private List<Line2D.Float> walls;
	private HashMap<Direction, List<Line2D.Float>> wallsByDirection;
	private HashMap<Orientation, List<java.lang.Float>> intervals;

	public Room(String id, List<Point2D.Float> corners, List<Point2D.Float> lamps) throws IncorrectShapeException {
		
		if (corners.size() < 4) throw new IncorrectShapeException("Not enough points given for creating a room.");
		if (!isRectilinear(corners)) throw new IncorrectShapeException("Given points do not shape a rectilinear polygon.");
		
		this.id = id;
		this.corners = corners;
		this.lamps = lamps;
		
		this.shape = createShape();
		this.walls = createWalls();
		this.wallsByDirection = createWallsByDirection();
		this.intervals = createIntervals();
	}
	
	public Room(String id, List<Point2D.Float> corners) throws IncorrectShapeException {
		this(id, corners, new ArrayList<Point2D.Float>());
	}

	public Path2D.Float getShape() {
		return shape;
	}

	public List<Point2D.Float> getLamps() {
		return lamps;
	}

	public void setLamps(List<Point2D.Float> lamps) {
		this.lamps = lamps;
	}

	public String getId() {
		return id;
	}

	public List<Point2D.Float> getCorners() {
		return corners;
	}
	
	private Path2D.Float createShape() {
		Path2D.Float shape = new Path2D.Float();
		
		shape.moveTo(corners.get(0).x, corners.get(0).y);
		for (int i = 1; i < this.corners.size(); i++) {
			shape.lineTo(this.corners.get(i).x, this.corners.get(i).y);
		}
		shape.lineTo(this.corners.get(0).x, this.corners.get(0).y);
		
		return shape;
	}
	
	private List<Line2D.Float> createWalls() {
		List<Line2D.Float> walls = new ArrayList<>();
		int numberOfCorners = this.corners.size();
		
		for (int i = 0; i < numberOfCorners; i++) {
			Point2D.Float pointA = this.corners.get(i);
			Point2D.Float pointB = this.corners.get((i + 1) % numberOfCorners);
			walls.add(new Line2D.Float(pointA, pointB));
		}
		
		return walls;
	}
	
	private HashMap<Direction, List<Line2D.Float>> createWallsByDirection() {
		HashMap<Direction, List<Line2D.Float>> wallsByDirection = new HashMap<>();
		
		for (Direction direction : Direction.values()) {
			wallsByDirection.put(direction, new ArrayList<>());
		}
		
		for (Line2D.Float wall : walls) {
			wallsByDirection.get(getDirection(wall)).add(wall);
		}
		
		return wallsByDirection;
	}
	
	private boolean isRectilinear(List<Point2D.Float> corners) {
		int numberOfCorners = corners.size();
		
		for (int i = 0; i < numberOfCorners; i++) {
			
			Point2D.Float previous = corners.get((i - 1 + numberOfCorners) % numberOfCorners);
			Point2D.Float current = corners.get(i);
			Point2D.Float following = corners.get((i + 1) % numberOfCorners);
			
			if (!isLineParallelToAxes(current, following) || hasStraightAngle(previous, current, following)) return false;
		}
		
		return true;
	}
	
	private boolean isLineParallelToAxes(Point2D.Float pointA, Point2D.Float pointB) {
		return pointA.x == pointB.x || pointA.y == pointB.y;
	}
	
	private boolean hasStraightAngle(Point2D.Float previous, Point2D.Float vertex, Point2D.Float following) {
		return vertex.x == previous.x && vertex.x == following.x || vertex.y == previous.y && vertex.y == following.y;
	}
	
	public boolean contains(Point2D.Float point) {
		return this.shape.contains(point);
	}
	
	public boolean isHorizontalWall(Line2D.Float wall) {
		return getOrientation(wall).equals(Orientation.HORIZONTAL);
	}
	
	public boolean isVerticalWall(Line2D.Float wall) {
		return getOrientation(wall).equals(Orientation.VERTICAL);
	}
	
	public Orientation getOrientation(Line2D.Float wall) {
		if (wall.y1 == wall.y2) return Orientation.HORIZONTAL;
		if (wall.x1 == wall.x2) return Orientation.VERTICAL;
		return null;
	}
	
	public boolean isRoomWall(Line2D.Float wall) {
		return getWalls().contains(wall);
	}
	
	public boolean isNorthWall(Line2D.Float wall) {
		return getDirection(wall).equals(Direction.NORTH);
	}
	
	public boolean isSouthWall(Line2D.Float wall) {
		return getDirection(wall).equals(Direction.SOUTH);
	}
	
	public boolean isWestWall(Line2D.Float wall) {
		return getDirection(wall).equals(Direction.WEST);
	}
	
	public boolean isEastWall(Line2D.Float wall) {
		return getDirection(wall).equals(Direction.EAST);
	}
	
	public boolean hasWallDirection(Line2D.Float wall, Direction direction) {
		return getDirection(wall).equals(direction);
	}
	
	public Direction getDirection(Line2D.Float wall) {
		
		if (getOrientation(wall).equals(Orientation.HORIZONTAL)) {
			float x = ((wall.x1 + wall.x2) / 2);
			
			float yNorthOfWall = wall.y1 + 0.005f;
			if (contains(new Point2D.Float(x, yNorthOfWall))) return Direction.SOUTH;
			
			float ySouthOfWall = wall.y1 - 0.005f;
			if (contains(new Point2D.Float(x, ySouthOfWall))) return Direction.NORTH;
			
			return null;
		}
		
		if (getOrientation(wall).equals(Orientation.VERTICAL)) {
			float y = ((wall.y1 + wall.y2) / 2);
			
			float xEastOfWall = wall.x1 + 0.005f;
			if (contains(new Point2D.Float(xEastOfWall, y))) return Direction.WEST;
			
			float xWestOfWall = wall.x1 - 0.005f;
			if (contains(new Point2D.Float(xWestOfWall, y))) return Direction.EAST;
			
			return null;
		}
		
		return null;
	}

	public List<Line2D.Float> getWalls() {
		return walls;
	}
	
	public List<Line2D.Float> getWalls(Direction direction) {
		return wallsByDirection.get(direction);
	}
	
	public List<Line2D.Float> getNorthWalls() {
		return getWalls(Direction.NORTH);
	}
	
	public List<Line2D.Float> getSouthWalls() {
		return getWalls(Direction.SOUTH);
	}
	
	public List<Line2D.Float> getWestWalls() {
		return getWalls(Direction.WEST);
	}
	
	public List<Line2D.Float> getEastWalls() {
		return getWalls(Direction.EAST);
	}
	
	public List<Line2D.Float> getVerticalWalls() {
		return this.walls
				.stream()
				.filter(wall -> isVerticalWall(wall))
				.collect(Collectors.toList());
	}
	
	public List<Line2D.Float> getHorizontalWalls() {
		return this.walls
				.stream()
				.filter(wall -> isHorizontalWall(wall))
				.collect(Collectors.toList());
	}
	
	public List<java.lang.Float> getIntervalX() {
		return getIntervals(Orientation.HORIZONTAL);
	}
	
	public List<java.lang.Float> getIntervalsY() {
		return getIntervals(Orientation.VERTICAL);
	}
	
	public List<java.lang.Float> getIntervals(Orientation orientation) {
		return this.intervals.get(orientation);
	}
	
	public List<java.lang.Float> getIntervals(Line2D.Float wall) {
		if (isVerticalWall(wall)) return getIntervals(wall, Orientation.VERTICAL);
		if (isHorizontalWall(wall)) return getIntervals(wall, Orientation.HORIZONTAL);
		return null;
	}
	
	private List<java.lang.Float> getIntervals(Line2D.Float wall, Orientation orientation) {
		float c1 = orientation == Orientation.HORIZONTAL ? wall.x1 : wall.y1;
		float c2 = orientation == Orientation.HORIZONTAL ? wall.x2 : wall.y2;
		
		Predicate<? super java.lang.Float> isInWallRange = c -> c >= Math.min(c1, c2) && c <= Math.max(c1, c2);
		
		return getIntervals(orientation)
				.stream()
				.filter(isInWallRange)
				.collect(Collectors.toList());
	}
	
	private HashMap<Orientation, List<java.lang.Float>> createIntervals() {
		HashMap<Orientation, List<java.lang.Float>> intervals = new HashMap<>();
		for (Orientation o : Orientation.values()) {
			intervals.put(o, createIntervals(o));
		}
		return intervals;
	}
	
	private List<java.lang.Float> createIntervals(Orientation orientation) {
		Function<Point2D.Float, java.lang.Float> mapToCoordinate = 
			orientation == Orientation.HORIZONTAL
				? point -> point.x
				: point -> point.y;
		
		List<java.lang.Float> intervals = this.corners
				.stream()
				.map(mapToCoordinate)
				.distinct()
				.sorted()
				.collect(Collectors.toList());
		
		return intervals;
	}
	
	public List<Line2D.Float> getWallSections(Line2D.Float wall) {
		if (isVerticalWall(wall)) return getWallSections(wall, Orientation.VERTICAL);
		if (isHorizontalWall(wall)) return getWallSections(wall, Orientation.HORIZONTAL);
		return null;
	}
	
	public List<Line2D.Float> getWallSections(Line2D.Float wall, Orientation orientation) {
		
		List<Line2D.Float> wallSections = new ArrayList<>(); 
		List<java.lang.Float> intervals = getIntervals(wall, orientation);
		
		if (orientation == Orientation.HORIZONTAL) {
			
			float y = wall.y1;
			
			List<Point2D.Float> points = intervals
					.stream()
					.map(x -> new Point2D.Float(x, y))
					.collect(Collectors.toList());
			
			for (int i = 0; i < intervals.size() - 1; i++) {
				wallSections.add(new Line2D.Float(points.get(i), points.get(i+1)));
			}
			
		} else {

			float x = wall.x1;
			
			List<Point2D.Float> points = intervals
					.stream()
					.map(y -> new Point2D.Float(x, y))
					.collect(Collectors.toList());
			
			for (int i = 0; i < intervals.size() - 1; i++) {
				wallSections.add(new Line2D.Float(points.get(i), points.get(i+1)));
			}
		}
		
		return wallSections;
	}
	
	public float getDistanceToNearestSouthWall(Point2D.Float point) {
		Line2D.Float nearestSouthWall = getNearestSouthWall(point);
		float distance = point.y - nearestSouthWall.y1;
		return distance;
	}

	private Line2D.Float getNearestSouthWall(Point2D.Float point) {
		
		Predicate<? super Line2D.Float> isSouthOfPoint = 
				wall -> wall.y1 <= point.y
					&& Math.min(wall.x1, wall.x2) <= point.x
					&& Math.max(wall.x1, wall.x2) > point.x;
		
		Line2D.Float nearestSouthWall = this.getWalls(Direction.SOUTH)
			.stream()
			.filter(isSouthOfPoint)
			.sorted((w1, w2) -> w1.y1 > w2.y1 ? 1 : -1 )
			.collect(Collectors.toList())
			.get(0);
		
		return nearestSouthWall;
	}
	
	public Line2D.Float getExtendedWall(Line2D.Float wall) {
		
		float x1, x2, y1, y2;
		
		if (getOrientation(wall).equals(Orientation.HORIZONTAL)) {
			x1 = getNearestWall(wall, Direction.WEST).x1;
			x2 = getNearestWall(wall, Direction.EAST).x2;
			y1 = wall.y1;
			y2 = wall.y2;
		} else {
			x1 = wall.x1;
			x2 = wall.x2;
			y1 = getNearestWall(wall, Direction.SOUTH).y1;
			y2 = getNearestWall(wall, Direction.NORTH).y2;
		}
		
		return new Line2D.Float(x1, y1, x2, y2);
	}

	public Line2D.Float getNearestWall(Line2D.Float wall, Direction nearestWallDirection) {
		
		Direction startWallDirection = getDirection(wall);
		
		if (startWallDirection.equals(nearestWallDirection)) {
			return wall;
		}
		
		Predicate<? super Line2D.Float> isOnCorrectSide = getIsOnCorrectSide(wall, nearestWallDirection);
		Comparator<? super Line2D.Float> sortByDistance = getSortByDistance(nearestWallDirection);
		
		if (startWallDirection.getOpposite().equals(nearestWallDirection)) {
			
			Predicate<? super Line2D.Float> overlapsWall = getOverlapsWall(wall);
			
			Line2D.Float nearestWall = getWalls(nearestWallDirection)
				.stream()
				.filter(isOnCorrectSide)
				.filter(overlapsWall)
				.sorted(sortByDistance)
				.collect(Collectors.toList())
				.get(0);
		
			return nearestWall;
		}
		
		Predicate<? super Line2D.Float> intersectsExtension = getIntersectsExtension(wall); 

		Line2D.Float nearestWall = getWalls(nearestWallDirection)
				.stream()
				.filter(isOnCorrectSide)
				.filter(intersectsExtension)
				.sorted(sortByDistance)
				.collect(Collectors.toList())
				.get(0);
		
		return nearestWall;
	}
	
	private Predicate<? super Line2D.Float> getIsOnCorrectSide(Line2D.Float wall, Direction nearestWallDirection) {
		
		switch(nearestWallDirection) {
			case NORTH:
				float yMax = Math.max(wall.y1, wall.y2);
				return c -> c.y1 >= yMax;
			case EAST:
				float xMax = Math.max(wall.x1, wall.x2);
				return c -> c.x1 >= xMax;
			case SOUTH:
				float yMin = Math.min(wall.y1, wall.y2);
				return c -> c.y1 <= yMin;
			case WEST:
				float xMin = Math.min(wall.x1, wall.x2);
				return c -> c.x1 <= xMin;
			default:
				return null;
		}
	}
	
	private Predicate<? super Line2D.Float> getOverlapsWall(Line2D.Float wall) {
		
		Orientation orientation = getOrientation(wall);
		
		switch(orientation) {
		
			case HORIZONTAL:
				
				float xMin = Math.min(wall.x1, wall.x2);
				float xMax = Math.max(wall.x1, wall.x2);
				
				return c -> 
					Math.min(c.x1, c.x2) > xMin && Math.min(c.x1, c.x2) < xMax
						|| Math.max(c.x1, c.x2) > xMin && Math.max(c.x1, c.x2) < xMax;
						
			case VERTICAL:
				
				float yMin = Math.min(wall.y1, wall.y2);
				float yMay = Math.max(wall.y1, wall.y2);
				
				return c -> 
					Math.min(c.y1, c.y2) > yMin && Math.min(c.y1, c.y2) < yMay
						|| Math.max(c.y1, c.y2) > yMin && Math.max(c.y1, c.y2) < yMay;
						
			default:
				return null;
		}
	}
	
	private Predicate<? super Line2D.Float> getIntersectsExtension(Line2D.Float wall) {
		
		Direction direction = getDirection(wall);
		
		switch(direction) {
			case NORTH:
				return c -> Math.max(c.y1,  c.y2) >= wall.y1 && Math.min(c.y1,  c.y2) < wall.y1;
			case EAST:
				return c -> Math.max(c.x1,  c.x2) >= wall.x1 && Math.min(c.x1,  c.x2) < wall.x1;
			case SOUTH:
				return c -> Math.min(c.y1,  c.y2) <= wall.y1 && Math.max(c.y1,  c.y2) > wall.y1;
			case WEST:
				return c -> Math.min(c.x1,  c.x2) <= wall.x1 && Math.max(c.x1,  c.x2) > wall.x1;
			default:
				return null;
		}
	}
	
	private Comparator<? super Line2D.Float> getSortByDistance(Direction nearestWallDirection) {
		
		switch(nearestWallDirection) {
			case NORTH:
				return (c1, c2) -> c1.y1 > c2.y1 ? 1 : -1;
			case EAST:
				return (c1, c2) -> c1.x1 > c2.x1 ? 1 : -1;
			case SOUTH:
				return (c1, c2) -> c1.y1 < c2.y1 ? 1 : -1;
			case WEST:
				return (c1, c2) -> c1.x1 < c2.x1 ? 1 : -1;
			default:
				return null;
		}
	}
}
