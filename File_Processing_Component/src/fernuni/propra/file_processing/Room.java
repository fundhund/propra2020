package fernuni.propra.file_processing;

import java.util.List;
import java.awt.Point;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class Room {
	
	private String id;
	private List<Point2D.Float> corners;
	private List<Point2D.Float> lamps;
	private Path2D.Float shape;
//	private List<Wall>

	public Room(String id, List<Point2D.Float> corners, List<Point2D.Float> lamps) {
		
//		System.out.println(isRectilinear(corners));
		
		this.id = id;
		this.corners = corners;
		this.lamps = lamps;
		
		this.shape = createShape();
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
		for (int i = 1; i < corners.size(); i++) {
			shape.lineTo(corners.get(i).x, corners.get(i).y);
		}
		shape.lineTo(corners.get(0).x, corners.get(0).y);
		
		return shape;
	}
	
//	private boolean isRectilinear(List<Point> corners) {
//		boolean isRectilinear = true;
//		
//		for (int i = 0; i < corners.size(); i++) {
//			
//		}
//	}
}
