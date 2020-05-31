package Algorithm_Component;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fernuni.propra.algorithm.Validator;
import fernuni.propra.file_processing.IncorrectShapeException;
import fernuni.propra.file_processing.Room;
import fernuni.propra.test.TestHelper;

public class ValidatorTest {
	
	@Test
	public void Validator_validate_returnsTrueForValidSquareRoom() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
		List<Point2D.Float> lamps = new ArrayList<>();
		lamps.add(new Point2D.Float(1, 1));
		Room room = new Room(id, corners, lamps);
		
		Validator validator = new Validator(room);
		
		// Act
		boolean isValid = validator.validate();
		
		// Assert
		assertTrue("Validator returned incorrect result.", isValid);
	}
	
	@Test
	public void Validator_validate_returnsFalseForNoLampsInSquareRoom() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
		List<Point2D.Float> lamps = new ArrayList<>();
		Room room = new Room(id, corners, lamps);
		
		Validator validator = new Validator(room);
		
		// Act
		boolean isValid = validator.validate();
		
		// Assert
		assertFalse("Validator returned incorrect result.", isValid);
	}
	
	@Test
	public void Validator_validate_returnsFalseForLampsOutsideOfRoomInSquareRoom() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
		List<Point2D.Float> lamps = new ArrayList<>();
		lamps.add(new Point2D.Float(3, 3));
		Room room = new Room(id, corners, lamps);
		
		Validator validator = new Validator(room);
		
		// Act
		boolean isValid = validator.validate();
		
		// Assert
		assertFalse("Validator returned incorrect result.", isValid);
	}
	
	@Test
	public void Validator_validate_returnsTrueForValidArcShapedRoom() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForArcShape();
		List<Point2D.Float> lamps = new ArrayList<>();
		lamps.add(new Point2D.Float(0.5f, 1.5f));
		lamps.add(new Point2D.Float(2.5f, 1.5f));
		Room room = new Room(id, corners, lamps);
		
		Validator validator = new Validator(room);
		
		// Act
		boolean isValid = validator.validate();
		
		// Assert
		assertTrue("Validator returned incorrect result.", isValid);
	}
	
	@Test
	public void Validator_validate_returnsFalseForOneLampInArcShapedRoom() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForArcShape();
		List<Point2D.Float> lamps = new ArrayList<>();
		lamps.add(new Point2D.Float(2.5f, 1.5f));
		Room room = new Room(id, corners, lamps);
		
		Validator validator = new Validator(room);
		
		// Act
		boolean isValid = validator.validate();
		
		// Assert
		assertFalse("Validator returned incorrect result.", isValid);
	}
	
	@Test
	public void Validator_validate_returnsTrueForValidArcShapedRoomWithMoreLampsThanNecessary() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForArcShape();
		List<Point2D.Float> lamps = new ArrayList<>();
		lamps.add(new Point2D.Float(0.5f, 1.5f));
		lamps.add(new Point2D.Float(2.5f, 1.5f));
		lamps.add(new Point2D.Float(1.5f, 1.5f));
		Room room = new Room(id, corners, lamps);
		
		Validator validator = new Validator(room);
		
		// Act
		boolean isValid = validator.validate();
		
		// Assert
		assertTrue("Validator returned incorrect result.", isValid);
	}
	
	@Test
	public void Validator_validate_returnsTrueForValidOpenRingShapedRoom() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForOpenRingShape();
		List<Point2D.Float> lamps = new ArrayList<>();
		lamps.add(new Point2D.Float(1.5f, 1.5f));
		lamps.add(new Point2D.Float(7.5f, 1.5f));
		lamps.add(new Point2D.Float(1.5f, 7.5f));
		Room room = new Room(id, corners, lamps);
		
		Validator validator = new Validator(room);
		
		// Act
		boolean isValid = validator.validate();
		
		// Assert
		assertTrue("Validator returned incorrect result.", isValid);
	}
	
	@Test
	public void Validator_validate_returnsTrueForValidOpenRingShapedRoomWithAlternativeLamps() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForOpenRingShape();
		List<Point2D.Float> lamps = new ArrayList<>();
		lamps.add(new Point2D.Float(1.5f, 1.5f));
		lamps.add(new Point2D.Float(7.5f, 1.5f));
		lamps.add(new Point2D.Float(7.5f, 7.5f));
		Room room = new Room(id, corners, lamps);
		
		Validator validator = new Validator(room);
		
		// Act
		boolean isValid = validator.validate();
		
		// Assert
		assertTrue("Validator returned incorrect result.", isValid);
	}
	
	@Test
	public void Validator_validate_returnsFalseForOpenRingShapedRoomWithTooFewLamps() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForOpenRingShape();
		List<Point2D.Float> lamps = new ArrayList<>();
		lamps.add(new Point2D.Float(1.5f, 1.5f));
		lamps.add(new Point2D.Float(7.5f, 1.5f));
		Room room = new Room(id, corners, lamps);
		
		Validator validator = new Validator(room);
		
		// Act
		boolean isValid = validator.validate();
		
		// Assert
		assertFalse("Validator returned incorrect result.", isValid);
	}
}
