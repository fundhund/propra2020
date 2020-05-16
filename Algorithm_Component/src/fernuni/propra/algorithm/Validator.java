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
