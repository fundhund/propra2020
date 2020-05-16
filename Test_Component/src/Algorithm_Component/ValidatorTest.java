package Algorithm_Component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import fernuni.propra.algorithm.Ausleuchtung;
import fernuni.propra.algorithm.IAusleuchtung;
import fernuni.propra.algorithm.Lamp;
import fernuni.propra.algorithm.Solver;
import fernuni.propra.algorithm.TimeLimitExceededException;
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
}
