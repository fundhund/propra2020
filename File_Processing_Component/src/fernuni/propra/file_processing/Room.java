package fernuni.propra.file_processing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * A rectilinear room shape defined by given points.
 * 
 * @author Marius Mielke (4531230)
 *
 */
public class Room {
	
	private String id;
	private List<Point2D.Float> corners;
	private List<Point2D.Float> lamps;
	private Path2D.Float shape;
	private List<Line2D.Float> walls;
	private HashMap<Direction, List<Line2D.Float>> wallsByDirection;
	private HashMap<Orientation, List<java.lang.Float>> intervals;
	private HashMap<String, java.lang.Float> boundaries;
	private Rectangle2D.Float[] rectangles;
	private float width;
	private float height;

	/**
	 * Main constructor.
	 * 
	 * @param id room ID
	 * @param corners list of points defining the room shape
	 * @param lamps list of lamp positions
	 * @throws IncorrectShapeException 
	 */
	public Room(String id, List<Point2D.Float> corners, List<Point2D.Float> lamps) throws IncorrectShapeException {
		
		if (corners.size() < 4) throw new IncorrectShapeException("Not enough points given for creating a room.");
		if (!isRectilinear(corners)) throw new IncorrectShapeException("Given points do not shape a rectilinear polygon.");
		
		this.id = id;
		this.corners = corners;
		this.lamps = lamps;
		
		this.shape = createShape();
		this.walls = createWalls();
		this.wallsByDirection = createWallsByDirection();
//		this.intervals = createIntervals();
		this.boundaries = createBoundaries();
		this.rectangles = createRectangles();
		this.width = calculateWidth();
		this.height = calculateHeight();
	}
	
	private Rectangle2D.Float[] createRectangles() {
		
		List<Rectangle2D.Float> rectanglesList = new ArrayList<>();
		
		for (int i = 0; i < walls.size(); i++) {
			
			Rectangle2D.Float currentRectangle = getRectangle(walls.get(i));
			
			if (!rectanglesList.contains(currentRectangle))
			rectanglesList.add(currentRectangle);
		}
		
		int numberOfRectangles = rectanglesList.size();
		Rectangle2D.Float[] rectanglesArray = new Rectangle2D.Float[numberOfRectangles];
		
		for (int j = 0; j < numberOfRectangles; j++) {
			rectanglesArray[j] = rectanglesList.get(j);
		}
		
		return rectanglesArray;
	}

	public HashMap<String, java.lang.Float> getBoundaries() {
		return boundaries;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
	
	/**
	 * Constructor without lamps.
	 * 
	 * @param id room ID
	 * @param corners list of points defining the room shape
	 * @throws IncorrectShapeException 
	 */
	public Room(String id, List<Point2D.Float> corners) throws IncorrectShapeException {
		this(id, corners, new ArrayList<Point2D.Float>());
	}
	
	/**
	 * Returns a map of the room's greatest and least X and Y values.
	 * 
	 * @return a map of the room's greatest and least X and Y values
	 */
	private HashMap<String, java.lang.Float> createBoundaries() {
		HashMap<String, java.lang.Float> boundaries = new HashMap<>();
		
		float xMin = this.corners.stream().map(point -> point.x).sorted().findFirst().get();
		float xMax = this.corners.stream().map(point -> point.x).sorted(Collections.reverseOrder()).findFirst().get();
		float yMin = this.corners.stream().map(point -> point.y).sorted().findFirst().get();
		float yMax = this.corners.stream().map(point -> point.y).sorted(Collections.reverseOrder()).findFirst().get();
				
		boundaries.put("xMin", xMin);
		boundaries.put("xMax", xMax);
		boundaries.put("yMin", yMin);
		boundaries.put("yMax", yMax);
		
		return boundaries;
	}
	
	public Rectangle2D.Float[] getRectangles() {
		return rectangles;
	}

	/**
	 * Calculates the room's width
	 * 
	 * @return the room's width
	 */
	private float calculateWidth() {
		return boundaries.get("xMax") - boundaries.get("xMin");
	}
	
	/**
	 * Calculates the room's height
	 * 
	 * @return the room's height
	 */
	private float calculateHeight() {
		return boundaries.get("yMax") - boundaries.get("yMin");
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
	
	/**
	 * Creates the room's shape.
	 * 
	 * @return the room's shape as a path
	 */
	private Path2D.Float createShape() {
		Path2D.Float shape = new Path2D.Float();
		
		shape.moveTo(corners.get(0).x, corners.get(0).y);
		for (int i = 1; i < this.corners.size(); i++) {
			shape.lineTo(this.corners.get(i).x, this.corners.get(i).y);
		}
		shape.lineTo(this.corners.get(0).x, this.corners.get(0).y);
		
		return shape;
	}
	
	/**
	 * Creates the room's walls.
	 * 
	 * @return a list of lines
	 */
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
	
	/**
	 * Stores the room's walls in a map depending on their direction (NORTH, EAST, SOUTH, WEST).
	 * 
	 * @return a map of walls
	 */
	private HashMap<Direction, List<Line2D.Float>> createWallsByDirection() {
		HashMap<Direction, List<Line2D.Float>> wallsByDirection = new HashMap<>();
		
		Function<Line2D.Float, Direction> getInitialDirection = getGetInitialDirection();
		
		for (Direction direction : Direction.values()) {
			wallsByDirection.put(direction, new ArrayList<>());
		}
		
		for (Line2D.Float wall : walls) {
			wallsByDirection.get(getInitialDirection.apply(wall)).add(wall);
		}
		
		return wallsByDirection;
	}
	
	/**
	 * Returns a function for the initial determination of wall directions.
	 * 
	 * @return a function for the initial determination of wall directions
	 */
	private Function<Line2D.Float, Direction> getGetInitialDirection() {
		
		/* If the southernmost wall's x1 value is smaller than its x2 value,
		 * all south walls will have this property and vice versa. The same applies
		 * to other direction (with comparison of y1 and y2 for east and west walls 
		 * respectively).
		 */
		Line2D.Float southernmostWall = this.getWalls()
				.stream()
				.filter(w -> w.y1 == w.y2)
				.sorted((w1, w2) -> w1.y1 > w2.y1 ? 1 : -1)
				.collect(Collectors.toList())
				.get(0);
		
		Function<Line2D.Float, Boolean> isSouthWall = w -> 
			southernmostWall.x1 < southernmostWall.x2 ? w.x1 < w.x2 : w.x1 > w.x2;
		
		Line2D.Float westernmostWall = this.getWalls()
				.stream()
				.filter(w -> w.x1 == w.x2)
				.sorted((w1, w2) -> w1.x1 > w2.x1 ? 1 : -1)
				.collect(Collectors.toList())
				.get(0);
		
		Function<Line2D.Float, Boolean> isWestWall = w -> 
			westernmostWall.y1 < westernmostWall.y2 ? w.y1 < w.y2 : w.y1 > w.y2;
		
		Function<Line2D.Float, Direction> getInitialDirection = wall ->
			this.isHorizontalWall(wall)
			? isSouthWall.apply(wall)
					? Direction.SOUTH
					: Direction.NORTH
			:	isWestWall.apply(wall)
					? Direction.WEST
					: Direction.EAST;
			
		return getInitialDirection;
	}
	
	/**
	 * Checks if given points create a rectilinear shape.
	 * 
	 * @param corners given points
	 * @return a boolean value
	 */
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
	
	/**
	 * Checks if a line given by two points is linear to one of the axes.
	 * 
	 * @param pointA starting point of the line
	 * @param pointB endpoint of the line
	 * @return a boolean value
	 */
	private boolean isLineParallelToAxes(Point2D.Float pointA, Point2D.Float pointB) {
		return pointA.x == pointB.x || pointA.y == pointB.y;
	}
	
	/**
	 * Checks if a point describes a straight angle with two adjacent points.
	 * 
	 * @param previous the previous point
	 * @param vertex the angle crest
	 * @param following the following point
	 * @return
	 */
	private boolean hasStraightAngle(Point2D.Float previous, Point2D.Float vertex, Point2D.Float following) {
		return vertex.x == previous.x && vertex.x == following.x || vertex.y == previous.y && vertex.y == following.y;
	}
	
	/**
	 * Checks if a point lies within the room.
	 * 
	 * @param point point
	 * @return a boolean value
	 */
	public boolean contains(Point2D.Float point) {
		return this.shape.contains(point);
	}
	
	/**
	 * Checks if a wall is horizontal.
	 * 
	 * @param wall a wall
	 * @return a boolean value
	 */
	public boolean isHorizontalWall(Line2D.Float wall) {
		return getOrientation(wall).equals(Orientation.HORIZONTAL);
	}
	
	/**
	 * Checks if a wall is vertical.
	 * 
	 * @param wall a wall
	 * @return a boolean value
	 */
	public boolean isVerticalWall(Line2D.Float wall) {
		return getOrientation(wall).equals(Orientation.VERTICAL);
	}
	
	/**
	 * Returns the orientation of a wall.
	 * 
	 * @param wall a wall
	 * @return its orientation
	 */
	public Orientation getOrientation(Line2D.Float wall) {
		if (wall.y1 == wall.y2) return Orientation.HORIZONTAL;
		if (wall.x1 == wall.x2) return Orientation.VERTICAL;
		return null;
	}
	
	/**
	 * Checks whether a given wall is part of the room.
	 * 
	 * @param wall a wall
	 * @return a boolean value
	 */
	public boolean isRoomWall(Line2D.Float wall) {
		return getWalls().contains(wall);
	}
	
	/**
	 * Checks whether a given wall is a north wall.
	 * 
	 * @param wall a wall
	 * @return a boolean value
	 */
	public boolean isNorthWall(Line2D.Float wall) {
		return getDirection(wall).equals(Direction.NORTH);
	}
	
	/**
	 * Checks whether a given wall is a south wall.
	 * 
	 * @param wall a wall
	 * @return a boolean value
	 */
	public boolean isSouthWall(Line2D.Float wall) {
		return getDirection(wall).equals(Direction.SOUTH);
	}
	
	/**
	 * Checks whether a given wall is a west wall.
	 * 
	 * @param wall a wall
	 * @return a boolean value
	 */
	public boolean isWestWall(Line2D.Float wall) {
		return getDirection(wall).equals(Direction.WEST);
	}
	
	/**
	 * Checks whether a given wall is an east wall.
	 * 
	 * @param wall a wall
	 * @return a boolean value
	 */
	public boolean isEastWall(Line2D.Float wall) {
		return getDirection(wall).equals(Direction.EAST);
	}
	
	/**
	 * Checks whether a given wall has a given direction.
	 * 
	 * @param wall a wall
	 * @param direction a direction
	 * @return a boolean value
	 */
	public boolean hasWallDirection(Line2D.Float wall, Direction direction) {
		return getDirection(wall).equals(direction);
	}
	
	/**
	 * Returns the direction of a given wall
	 * 
	 * @param wall a wall
	 * @return a direction
	 */
	public Direction getDirection(Line2D.Float wall) {
		
		if (!this.walls.contains(wall)) {
			Orientation orientation = getOrientation(wall);
			
			// Return direction of first found room wall with same direction that intersects wall
			return getDirection(this.walls
				.stream()
				.filter(c -> getOrientation(c).equals(orientation))
				.filter(c -> c.intersectsLine(wall))
				.findFirst()
				.get());
		}
		
		if (this.wallsByDirection.get(Direction.NORTH).contains(wall)) return Direction.NORTH;
		if (this.wallsByDirection.get(Direction.EAST).contains(wall)) return Direction.EAST;
		if (this.wallsByDirection.get(Direction.SOUTH).contains(wall)) return Direction.SOUTH;
		if (this.wallsByDirection.get(Direction.WEST).contains(wall)) return Direction.WEST;
				
		return null;
	}

	public List<Line2D.Float> getWalls() {
		return walls;
	}
	
	/**
	 * Returns all walls of a given direction.
	 * 
	 * @param direction direction
	 * @return a list of walls
	 */
	public List<Line2D.Float> getWalls(Direction direction) {
		return wallsByDirection.get(direction);
	}
	
	/**
	 * Returns all north walls.
	 * 
	 * @return a list of walls
	 */
	public List<Line2D.Float> getNorthWalls() {
		return getWalls(Direction.NORTH);
	}
	
	/**
	 * Returns all south walls.
	 * 
	 * @return a list of walls
	 */
	public List<Line2D.Float> getSouthWalls() {
		return getWalls(Direction.SOUTH);
	}
	
	/**
	 * Returns all west walls.
	 * 
	 * @return a list of walls
	 */
	public List<Line2D.Float> getWestWalls() {
		return getWalls(Direction.WEST);
	}
	
	/**
	 * Returns all east walls.
	 * 
	 * @return a list of walls
	 */
	public List<Line2D.Float> getEastWalls() {
		return getWalls(Direction.EAST);
	}
	
	/**
	 * Returns all vertical walls.
	 * 
	 * @return a list of walls
	 */
	public List<Line2D.Float> getVerticalWalls() {
		return this.walls
				.stream()
				.filter(wall -> isVerticalWall(wall))
				.collect(Collectors.toList());
	}
	
	/**
	 * Returns all horizontal walls.
	 * 
	 * @return a list of walls
	 */
	public List<Line2D.Float> getHorizontalWalls() {
		return this.walls
				.stream()
				.filter(wall -> isHorizontalWall(wall))
				.collect(Collectors.toList());
	}
	
	/**
	 * Calculates the extension of a given wall.
	 * 
	 * @param wall a wall
	 * @return an extended wall
	 */
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

	/**
	 * Calculates the nearest wall in a given direction.
	 * 
	 * @param wall a wall
	 * @param nearestWallDirection direction of the required nearest wall
	 * @return the nearest wall in the given direction
	 */
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
	
	/**
	 * Returns a predicate that determines whether a given wall is on the correct side of another wall. 
	 * 
	 * @param wall a wall
	 * @param nearestWallDirection
	 * @return a predicate
	 */
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
	
	/**
	 * Returns a predicate that determines whether a wall overlaps with another wall.
	 * 
	 * @param wall a wall
	 * @return a predicate
	 */
	private Predicate<? super Line2D.Float> getOverlapsWall(Line2D.Float wall) {
		Orientation orientation = getOrientation(wall);
		
		switch(orientation) {
		
			case HORIZONTAL:
				
				float xMin = Math.min(wall.x1, wall.x2);
				float xMax = Math.max(wall.x1, wall.x2);
				
				return c -> 
					Math.max(c.x1, c.x2) >= xMax && Math.min(c.x1, c.x2) < xMax
						|| Math.min(c.x1, c.x2) <= xMin && Math.max(c.x1, c.x2) > xMin
						|| Math.min(c.x1, c.x2) >= xMin && Math.max(c.x1, c.x2) <= xMax;
						
			case VERTICAL:
				
				float yMin = Math.min(wall.y1, wall.y2);
				float yMax = Math.max(wall.y1, wall.y2);
				
				return c -> 
					Math.max(c.y1, c.y2) >= yMax && Math.min(c.y1, c.y2) < yMax
						|| Math.min(c.y1, c.y2) <= yMin && Math.max(c.y1, c.y2) > yMin
						|| Math.min(c.y1, c.y2) >= yMin && Math.max(c.y1, c.y2) <= yMax;
						
			default:
				return null;
		}
	}
	
	/**
	 * Returns a predicate that determines whether a given wall intersects an extension of another wall.
	 * 
	 * @param wall a wall
	 * @return a predicate
	 */
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
	
	/**
	 * Returns a comparator that sorts walls by distance to the relevant axis.
	 * 
	 * @param nearestWallDirection sorting direction
	 * @return a comparator
	 */
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
	
	/**
	 * Returns the opposite wall of a given one.
	 * 
	 * @param wall a wall
	 * @return the nearest wall of the opposite direction
	 */
	public Line2D.Float getOppositeWall(Line2D.Float wall) {
		return getNearestWall(wall, getDirection(wall).getOpposite());
	}
	
	/**
	 * Returns a rectangle which contains all points from where a lamp can reach a given wall.
	 * 
	 * @param wall a wall
	 * @return the associated rectangle
	 */
	public Rectangle2D.Float getRectangle(Line2D.Float wall) {
		
		Direction direction = getDirection(wall);
		Direction oppositeDirection = direction.getOpposite();

		Line2D.Float extendedWall = getExtendedWall(wall);
		Line2D.Float oppositeWall = getNearestWall(extendedWall, oppositeDirection);
		
		float x, y, w, h;
		
		switch(direction) {
			case NORTH:
				x = Math.min(extendedWall.x1, extendedWall.x2);
				y = oppositeWall.y1;
				w = Math.abs(extendedWall.x2 - extendedWall.x1);
				h = Math.abs(oppositeWall.y1 - extendedWall.y1);
				break;
			case EAST:
				x = oppositeWall.x1;
				y = Math.min(extendedWall.y1, extendedWall.y2);
				w = Math.abs(extendedWall.x1 - oppositeWall.x1);
				h = Math.abs(extendedWall.y2 - extendedWall.y1);
				break;
			case SOUTH:
				x = Math.min(extendedWall.x1, extendedWall.x2);
				y = extendedWall.y1;
				w = Math.abs(extendedWall.x2 - extendedWall.x1);
				h = Math.abs(extendedWall.y1 - oppositeWall.y1);
				break;
			case WEST:
				x = extendedWall.x1;
				y = Math.min(extendedWall.y1, extendedWall.y2);
				w = Math.abs(extendedWall.x1 - oppositeWall.x1);
				h = Math.abs(extendedWall.y2 - extendedWall.y1);
				break;
			default:
				x = y = w = h = 0;
				break;
		}

		return new Rectangle2D.Float(x, y, w, h);
	}
}
