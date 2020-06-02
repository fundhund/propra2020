package fernuni.propra.algorithm;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Helper methods for rectangles.
 * 
 * @author Marius Mielke (4531230)
 *
 */
public class RectangleHelper {
	
	/**
	 * Computes the intersection of two rectangles.
	 * 
	 * @param r1 first rectangle
	 * @param r2 second rectangle
	 * @return the largest rectangle contained in both rectangles
	 */
	public static Rectangle2D.Float getIntersection(Rectangle2D.Float r1, Rectangle2D.Float r2) {
		float r1x1 = r1.x;
		float r1x2 = r1x1 + r1.width;
    float r1y1 = r1.y;
    float r1y2 = r1y1 + r1.height;
    float r2x1 = r2.x;
    float r2x2 = r2x1 + r2.width;
    float r2y1 = r2.y;
    float r2y2 = r2y1 + r2.height;
    
    float ix = r1x1 > r2x1 ? r1x1 : r2x1;
    float iy = r1y1 > r2y1 ? r1y1 : r2y1;
    float iw = (r1x2 < r2x2 ? r1x2 : r2x2) - ix;
    float ih = (r1y2 < r2y2 ? r1y2 : r2y2) - iy;
    
    return new Rectangle2D.Float(ix, iy, iw, ih);
	}
	
	/**
	 * Computes the center of a rectangle
	 * 
	 * @param rectangle a rectangle
	 * @return the center of given rectangle
	 */
	public static Point2D.Float getCenter(Rectangle2D.Float rectangle) {
		float x = (rectangle.x + rectangle.width) / 2;
		float y = (rectangle.y + rectangle.height) / 2;
		return new Point2D.Float(x, y);
	}
}
