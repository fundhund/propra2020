package fernuni.propra.algorithm;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class RectangleHelper {
	
	// Modified Rectangle source code
	public static Rectangle2D.Float getIntersection(Rectangle2D.Float r1, Rectangle2D.Float r2) {
		float r1x1 = r1.x;
    float r1y1 = r1.y;
    float r2x1 = r2.x;
    float r2y1 = r2.y;
    float r1x2 = r1x1; r1x2 += r1.width;
    float r1y2 = r1y1; r1y2 += r1.height;
    float r2x2 = r2x1; r2x2 += r2.width;
    float r2y2 = r2y1; r2y2 += r2.height;
    if (r1x1 < r2x1) r1x1 = r2x1;
    if (r1y1 < r2y1) r1y1 = r2y1;
    if (r1x2 > r2x2) r1x2 = r2x2;
    if (r1y2 > r2y2) r1y2 = r2y2;
    r1x2 -= r1x1;
    r1y2 -= r1y1;
    if (r1x2 < Float.MIN_VALUE) r1x2 = Float.MIN_VALUE;
    if (r1y2 < Float.MIN_VALUE) r1y2 = Float.MIN_VALUE;
    return new Rectangle2D.Float(r1x1, r1y1, (float) r1x2, (float) r1y2);
	}
	
	public static Point2D.Float getCenter(Rectangle2D.Float rectangle) {
		float x = (rectangle.x + rectangle.width) / 2;
		float y = (rectangle.y + rectangle.height) / 2;
		return new Point2D.Float(x, y);
	}
}
