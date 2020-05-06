package fernuni.propra.algorithm;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lamp {

	private Point2D.Float position;
	private boolean isOn;
	private int[] rectangles;
	
	public Lamp(Point2D.Float position, boolean isOn, int[] rectangles) {
		this.position = position;
		this.isOn = isOn;
		this.rectangles = rectangles;
	}
	
	public Lamp() {
		this(new Point2D.Float(0, 0), false, null);
	}
	
	public void setPosition(Point2D.Float position) {
		this.position = position;
	}

	public Lamp(Point2D.Float position) {
		this(position, false, null);
	}
	
	public Lamp(Point2D.Float position, int[] rectangles) {
		this(position, false, rectangles);
	}
	
	public Lamp(Point2D.Float position, Rectangle2D.Float[] rectangles) {
		this(position, false, null);

		List<Integer> illuminatedRectanglesList = new ArrayList<>();
		
		for (int i = 0; i < rectangles.length; i++) {
			if (rectangles[i].contains(position)) {
				illuminatedRectanglesList.add(i);
			}
		}
		
		int numberOfIlluminatedRectangles = illuminatedRectanglesList.size();
		
		int[] illuminatedRectanglesArray = new int[numberOfIlluminatedRectangles];
		
		for (int j = 0; j < numberOfIlluminatedRectangles; j++) {
			illuminatedRectanglesArray[j] = illuminatedRectanglesList.get(j);
		}
		
		this.setRectangles(illuminatedRectanglesArray);
	}

	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}
	
	public void switchOn() {
		setOn(true);
	}
	
	public void switchOff() {
		setOn(false);
	}

	public Point2D.Float getPosition() {
		return position;
	}

	public void setRectangles(int[] rectangles) {
		this.rectangles = rectangles;
	}

	public int[] getRectangles() {
		return rectangles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isOn ? 1231 : 1237);
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + Arrays.hashCode(rectangles);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lamp other = (Lamp) obj;
		if (isOn != other.isOn)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (!Arrays.equals(rectangles, other.rectangles))
			return false;
		return true;
	}
	
}
