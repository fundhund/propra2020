package File_Processing_Component;

import org.junit.Test;

import fernuni.propra.algorithm.RectangleHelper;

import static org.junit.Assert.*;
import java.awt.geom.Rectangle2D;

public class RectangleHelperTest {
	
	@Test
	public void Room_getIntersection_returnsCorrectIntersection() {
		
		// Arrange
		Rectangle2D.Float rectangle1 = new Rectangle2D.Float (1.0f, 0.0f, 1.0f, 3.0f);
		Rectangle2D.Float rectangle2 = new Rectangle2D.Float (0.0f, 1.0f, 3.0f, 1.0f);
		
		Rectangle2D.Float expectedRectangle = new Rectangle2D.Float(1.0f, 1.0f, 1.0f, 1.0f);

		// Act
		Rectangle2D.Float actualRectangle = RectangleHelper.getIntersection(rectangle1, rectangle2);
		
		// Assert
		assertEquals("Did not return correct rectangle.", expectedRectangle, actualRectangle);
	}
}
