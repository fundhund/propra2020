package Algorithm_Component;

import static org.junit.Assert.assertEquals;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fernuni.propra.algorithm.Lamp;
import fernuni.propra.file_processing.Direction;
import fernuni.propra.file_processing.IncorrectShapeException;
import fernuni.propra.file_processing.Room;
import fernuni.propra.test.TestHelper;

public class LampTest {
	
	@Test
	public void Lamp_constructor_returnsBasicLamp() throws IncorrectShapeException {
		
		// Assert
		Point2D.Float expectedPosition = new Point2D.Float(0, 0);
		boolean expectedIsOn = false;
		int[] expectedRectangles = null;
		
		// Act
		Lamp lamp = new Lamp();
		Point2D.Float actualPosition = lamp.getPosition();
		boolean actualIsOn = lamp.isOn();
		int[] actualRectangles = lamp.getRectangles();
		
		// Assert
		assertEquals("Did not return correct position.", expectedPosition, actualPosition);
		assertEquals("Did not return correct lamp state.", expectedIsOn, actualIsOn);
		assertEquals("Did not return correct position.", expectedRectangles, actualRectangles);
	}

	@Test
	public void Lamp_constructor_returnsCorrectRectanglesForSquare() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
		Room room = new Room(id, corners);
		Rectangle2D.Float[] rectangles = room.getRectangles();
		Point2D.Float position = new Point2D.Float(1, 1);
		
		int expectedRectangle = 0;

		// Act
		Lamp lamp = new Lamp(position, rectangles);
		int[] actualRectangles = lamp.getRectangles();
		
		// Assert
		assertEquals("Did not return correct number of rectangles.", 1, actualRectangles.length);
		assertEquals("Did not return correct rectangle", expectedRectangle, actualRectangles[0]);
	}
	
	@Test
	public void Lamp_constructor_returnsEmptyRectangleArrayForSquareIfLampIsOutside() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
		Room room = new Room(id, corners);
		Rectangle2D.Float[] rectangles = room.getRectangles();
		Point2D.Float position = new Point2D.Float(3, 3);
		

		// Act
		Lamp lamp = new Lamp(position, rectangles);
		int[] actualRectangles = lamp.getRectangles();
		
		// Assert
		assertEquals("Did not return correct number of rectangles.", 0, actualRectangles.length);
	}
	
	@Test
	public void Lamp_constructor_returnsCorrectRectanglesForPlusShapeWhenLampInCenter() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForPlusShape();
		Room room = new Room(id, corners);
		Rectangle2D.Float[] rectangles = room.getRectangles();
		
		int[] expectedRectangles = new int[2];
		expectedRectangles[0] = 0;
		expectedRectangles[1] = 1;
		
		Point2D.Float position = new Point2D.Float(1.5f, 1.5f);

		// Act
		Lamp lamp = new Lamp(position, rectangles);
		int[] actualRectangles = lamp.getRectangles();
		
		// Assert
		assertEquals("Did not return correct number of rectangles.", 2, actualRectangles.length);
		assertEquals("Did not return correct rectangle", expectedRectangles[0], actualRectangles[0]);
		assertEquals("Did not return correct rectangle", expectedRectangles[1], actualRectangles[1]);
	}
	
	@Test
	public void Lamp_constructor_returnsCorrectRectanglesForPlusShapeWhenLampInRightMiddlePart() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForPlusShape();
		Room room = new Room(id, corners);
		Rectangle2D.Float[] rectangles = room.getRectangles();
		
		int[] expectedRectangles = new int[2];
		expectedRectangles[0] = 1;
		
		Point2D.Float position = new Point2D.Float(2.5f, 1.5f);

		// Act
		Lamp lamp = new Lamp(position, rectangles);
		int[] actualRectangles = lamp.getRectangles();
		
		// Assert
		assertEquals("Did not return correct number of rectangles.", 1, actualRectangles.length);
		assertEquals("Did not return correct rectangle", expectedRectangles[0], actualRectangles[0]);
	}
}
