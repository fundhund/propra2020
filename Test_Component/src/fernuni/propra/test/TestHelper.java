package fernuni.propra.test;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class TestHelper {
	
	public static boolean areEqual(Line2D.Float w1, Line2D.Float w2) {
		return w1.x1 == w2.x1 && w1.y1 == w2.y1 && w1.x2 == w2.x2 && w1.y2 == w2.y2
				|| w1.x1 == w2.x2 && w1.y1 == w2.y2 && w1.x2 == w2.x1 && w1.y2 == w2.y1;
	}
	
	public static boolean areEqual(Rectangle2D.Float r1, Rectangle2D.Float r2) {
		return r1.x == r2.x && r1.y == r2.y && r1.width == r2.width && r1.height == r2.height;
	}
	
	public static List<Point2D.Float> getCornersForSquare(float sideLength) {
		List<Point2D.Float> corners = new ArrayList<>();
		corners.add(new Point2D.Float(0.0f, 0.0f));
		corners.add(new Point2D.Float(sideLength, 0.0f));
		corners.add(new Point2D.Float(sideLength, sideLength));
		corners.add(new Point2D.Float(0.0f, sideLength));
		return corners;
	}
	
	public static List<Point2D.Float> getCornersForSquare()  {
		return getCornersForSquare(2.0f);
	}
	
	public static List<Point2D.Float> getCornersForLShape(float sideLength) {
		List<Point2D.Float> corners = new ArrayList<>();
		corners.add(new Point2D.Float(0.0f, 0.0f));
		corners.add(new Point2D.Float(sideLength, 0.0f));
		corners.add(new Point2D.Float(sideLength, sideLength/2));
		corners.add(new Point2D.Float(sideLength/2, sideLength/2));
		corners.add(new Point2D.Float(sideLength/2, sideLength));
		corners.add(new Point2D.Float(0.0f, sideLength));
		return corners;
	}
	
	public static List<Point2D.Float> getCornersForLShape()  {
		return getCornersForLShape(2.0f);
	}
	
	public static List<Point2D.Float> getCornersForArcShape(float unit) {
		List<Point2D.Float> corners = new ArrayList<>();
		corners.add(new Point2D.Float(0.0f, 0.0f));
		corners.add(new Point2D.Float(unit, 0.0f));
		corners.add(new Point2D.Float(unit, unit));
		corners.add(new Point2D.Float(2 * unit, unit));
		corners.add(new Point2D.Float(2 * unit, 0.0f));
		corners.add(new Point2D.Float(3 * unit, 0.0f));
		corners.add(new Point2D.Float(3 * unit, 2 * unit));
		corners.add(new Point2D.Float(0, 2 * unit));
		return corners;
	}
	
	public static List<Point2D.Float> getCornersForArcShape()  {
		return getCornersForArcShape(1.0f);
	}
	
	public static List<Point2D.Float> getCornersForZShape(float unit) {
		List<Point2D.Float> corners = new ArrayList<>();
		corners.add(new Point2D.Float(unit, 0.0f));
		corners.add(new Point2D.Float(3 * unit, 0.0f));
		corners.add(new Point2D.Float(3 * unit, unit));
		corners.add(new Point2D.Float(2 * unit, unit));
		corners.add(new Point2D.Float(2 * unit, 3 * unit));
		corners.add(new Point2D.Float(0.0f, 3 * unit));
		corners.add(new Point2D.Float(0.0f, 2 * unit));
		corners.add(new Point2D.Float(unit, 2 * unit));
		return corners;
	}
	
	public static List<Point2D.Float> getCornersForZShape()  {
		return getCornersForZShape(1.0f);
	}
	
	public static List<Point2D.Float> getCornersForPlusShape(float unit) {
		List<Point2D.Float> corners = new ArrayList<>();
		corners.add(new Point2D.Float(unit, 0.0f));
		corners.add(new Point2D.Float(2 * unit, 0.0f));
		corners.add(new Point2D.Float(2 * unit, unit));
		corners.add(new Point2D.Float(3 * unit, unit));
		corners.add(new Point2D.Float(3 * unit, 2 * unit));
		corners.add(new Point2D.Float(2 * unit, 2 * unit));
		corners.add(new Point2D.Float(2 * unit, 3 * unit));
		corners.add(new Point2D.Float(1 * unit, 3 * unit));
		corners.add(new Point2D.Float(1 * unit, 2 * unit));
		corners.add(new Point2D.Float(0.0f, 2 * unit));
		corners.add(new Point2D.Float(0.0f, unit));
		corners.add(new Point2D.Float(unit, unit));
		return corners;
	}
	
	public static List<Point2D.Float> getCornersForPlusShape()  {
		return getCornersForPlusShape(1.0f);
	}
	
	public static boolean containEqualLines(List<Line2D.Float> lines1, List<Line2D.Float> lines2) {
		if (lines1.size() != lines2.size()) return false;
		
		for (int i = 0; i < lines1.size(); i++) {
			
			Line2D.Float line1 = lines1.get(i);
			Line2D.Float line2 = lines2.get(i);

			if (!line1.getP1().equals(line2.getP1())
					|| !line1.getP2().equals(line2.getP2())) return false;
		}
		
		return true;
	}
}
