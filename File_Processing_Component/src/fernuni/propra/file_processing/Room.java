package fernuni.propra.file_processing;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

public class Room {
	
	private String id;
	private List<Point2D.Float> corners;
	private List<Point2D.Float> lamps;
	private Path2D.Float shape;
	private List<Line2D.Float> walls;

	public Room(String id, List<Point2D.Float> corners, List<Point2D.Float> lamps) throws IncorrectShapeException {
		
		if (corners.size() < 4) throw new IncorrectShapeException("Not enough points given for creating a room.");
		if (!isRectilinear(corners)) throw new IncorrectShapeException("Given points do not shape a rectilinear polygon.");
		
		this.id = id;
		this.corners = corners;
		this.lamps = lamps;
		
		this.shape = createShape();
		this.walls = createWalls();
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
		return wall.y1 == wall.y2;
	}
	
	public boolean isVerticalWall(Line2D.Float wall) {
		return wall.x1 == wall.x2;
	}
	
	public boolean isRoomWall(Line2D.Float wall) {
		return this.walls.contains(wall);
	}
	
	public boolean isNorthWall(Line2D.Float wall) {
		return hasWallDirection(wall, Direction.NORTH);
	}
	
	public boolean isSouthWall(Line2D.Float wall) {
		return hasWallDirection(wall, Direction.SOUTH);
	}
	
	public boolean isWestWall(Line2D.Float wall) {
		return hasWallDirection(wall, Direction.WEST);
	}
	
	public boolean isEastWall(Line2D.Float wall) {
		return hasWallDirection(wall, Direction.EAST);
	}
	
	public boolean hasWallDirection(Line2D.Float wall, Direction direction) {
		
		Orientation orientation = direction.getOrientation();
		
		if (!isRoomWall(wall) 
				|| orientation.equals(Orientation.VERTICAL) && !isVerticalWall(wall)
				|| orientation.equals(Orientation.HORIZONTAL) && !isHorizontalWall(wall)) {
			return false;
		}
		
		float testPointX = orientation.equals(Orientation.HORIZONTAL) 
				?	((wall.x1 + wall.x2) / 2)
				:	wall.x1 + 0.005f * (direction.equals(Direction.WEST) ? 1 : -1);
		
		float testPointY = orientation.equals(Orientation.VERTICAL) 
				?	((wall.y1 + wall.y2) / 2)
				:	wall.y1 + 0.005f * (direction.equals(Direction.SOUTH) ? 1 : -1); 
		
		Point2D.Float testPoint = new Point2D.Float(testPointX, testPointY);
		return this.contains(testPoint);
	}

	public List<Line2D.Float> getWalls() {
		return walls;
	}
	
	public List<Line2D.Float> getDirectionWalls(Direction direction) {
		return walls.stream()
				.filter(wall -> hasWallDirection(wall, direction))
				.collect(Collectors.toList());
	}
	
	public List<Line2D.Float> getNorthWalls() {
		return getDirectionWalls(Direction.NORTH);
	}
	
	public List<Line2D.Float> getSouthWalls() {
		return getDirectionWalls(Direction.SOUTH);
	}
	
	public List<Line2D.Float> getWestWalls() {
		return getDirectionWalls(Direction.WEST);
	}
	
	public List<Line2D.Float> getEastWalls() {
		return getDirectionWalls(Direction.EAST);
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
	
	public List<java.lang.Float> getIntervalCoordinatesX() {
		return getIntervalCoordinates(Orientation.HORIZONTAL);
	}
	
	public List<java.lang.Float> getIntervalCoordinatesY() {
		return getIntervalCoordinates(Orientation.VERTICAL);
	}
	
	public List<java.lang.Float> getIntervalCoordinates(Orientation orientation) {
		Function<Line2D.Float, java.lang.Float> mapToCoordinate = 
				orientation == Orientation.HORIZONTAL
					? wall -> wall.x1 
					: wall -> wall.y1; 
		
		return getHorizontalWalls()
				.stream()
				.map(mapToCoordinate)
				.distinct()
				.sorted()
				.collect(Collectors.toList());
	}
	
	public List<java.lang.Float> getIntervalCoordinates(Line2D.Float wall) {
		if (isVerticalWall(wall)) return getIntervalCoordinates(wall, Orientation.VERTICAL);
		if (isHorizontalWall(wall)) return getIntervalCoordinates(wall, Orientation.HORIZONTAL);
		return null;
	}
	
	private List<java.lang.Float> getIntervalCoordinates(Line2D.Float wall, Orientation orientation) {
		float c1 = orientation == Orientation.HORIZONTAL ? wall.x1 : wall.y1;
		float c2 = orientation == Orientation.HORIZONTAL ? wall.x2 : wall.y2;
		
		Predicate<? super java.lang.Float> isInWallRange = c -> c >= Math.min(c1, c2) && c <= Math.max(c1, c2);
		
		return getIntervalCoordinates(orientation)
				.stream()
				.filter(isInWallRange)
				.collect(Collectors.toList());
	}
	
//	public List<Line2D.Float> getWallSections(Line2D.Float wall) {
//	}
	
//	public List<Line2D.Float> getNearestSouthWall(Line2D.Float northWall) {
//	}
}
