package fernuni.propra.algorithm;

import java.awt.geom.Point2D;
import java.util.List;

public class Lamp {

	private Point2D.Float position;
	private boolean isOn;
	private List<Integer> rectangles;
	
	public Lamp(Point2D.Float position, boolean isOn, List<Integer> rectangles) {
		this.position = position;
		this.isOn = isOn;
		this.rectangles = rectangles;
	}
	
	public Lamp(Point2D.Float position, List<Integer> rectangles) {
		this(position, false, rectangles);
	}

	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}

	public Point2D.Float getPosition() {
		return position;
	}

	public List<Integer> getRectangles() {
		return rectangles;
	}
}
