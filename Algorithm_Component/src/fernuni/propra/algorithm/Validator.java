package fernuni.propra.algorithm;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import fernuni.propra.file_processing.Room;

/**
 * Validates lamp positions for a given room.
 * 
 * @author Marius Mielke (4531230)
 *
 */
public class Validator {
	
	private Room room;

	public Validator(Room room) {
		this.room = room;
	}
	
	/**
	 * Validates lamps positions.
	 * 
	 * @return a boolean value
	 */
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
