package fernuni.propra.algorithm;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import fernuni.propra.file_processing.Room;

public class Validator {
	
	private Room room;

	public Validator(Room room) {
		this.room = room;
	}
	
	public boolean validate() {

		List<Point2D.Float> lamps = room.getLamps();
		Rectangle2D.Float[] rectangles = room.getRectangles();
		
		for (int i = 0; i < rectangles.length; i++) {
			Rectangle2D.Float rectangle = rectangles[i];
			if (lamps.stream().noneMatch(lamp -> rectangle.contains(lamp))) return false;
		}
		
		return true;
	}
}
