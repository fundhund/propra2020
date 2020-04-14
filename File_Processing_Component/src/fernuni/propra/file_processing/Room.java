package fernuni.propra.file_processing;

import java.util.ArrayList;
import java.util.List;
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
			
			Point2D.Float current = corners.get(i);
			Point2D.Float next = corners.get((i + 1) % numberOfCorners);
			
			if (!isLineParallelToAxes(current, next)) return false;
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
	
	public boolean isNorthWall(Line2D.Float wall) {
		if (!this.walls.contains(wall) || wall.y1 != wall.y2) return false;
		
		Point2D.Float testPoint = new Point2D.Float((wall.x1 + wall.x2) / 2, wall.y1 - 0.01f);
		return this.contains(testPoint);
	}

	public List<Line2D.Float> getWalls() {
		return walls;
	}
}
