package Algorithm_Component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fernuni.propra.algorithm.Ausleuchtung;
import fernuni.propra.algorithm.IAusleuchtung;
import fernuni.propra.algorithm.Solver;
import fernuni.propra.file_processing.IncorrectShapeException;
import fernuni.propra.file_processing.Room;
import fernuni.propra.test.TestHelper;

public class SolverTest {
	
	@Test
	public void Solver_getIntersections_returnsCorrectIntersectionsForSquare() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
		Room room = new Room(id, corners);
		
		List<Rectangle2D.Float> expectedRectangles = new ArrayList<>();
		expectedRectangles.add(new Rectangle2D.Float(0, 0, 2, 2));
		
		// Act
		List<Rectangle2D.Float> actualRectangles = Solver.getCandidateRectangles(room);
		
		// Assert
		assertEquals("Did not return correct number of rectangles", 1, actualRectangles.size());
		assertEquals("Did not return correct rectangle", expectedRectangles.get(0), actualRectangles.get(0));
	}
	
	@Test
	public void Solver_getIntersections_returnsCorrectIntersectionsForPlusShape() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForPlusShape();
		Room room = new Room(id, corners);
		
		List<Rectangle2D.Float> expectedRectangles = new ArrayList<>();
		expectedRectangles.add(new Rectangle2D.Float(1, 1, 1, 1));
		
		// Act
		List<Rectangle2D.Float> actualRectangles = Solver.getCandidateRectangles(room);
		
		// Assert
		assertEquals("Did not return correct number of rectangles", 1, actualRectangles.size());
		assertEquals("Did not return correct rectangle", expectedRectangles.get(0), actualRectangles.get(0));
	}
	
	@Test
	public void Solver_getIntersections_returnsCorrectIntersectionsForArcShape() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForArcShape();
		Room room = new Room(id, corners);
		
		List<Rectangle2D.Float> expectedRectangles = new ArrayList<>();
		expectedRectangles.add(new Rectangle2D.Float(0, 1, 1, 1));
		expectedRectangles.add(new Rectangle2D.Float(2, 1, 1, 1));
		
		// Act
		List<Rectangle2D.Float> actualRectangles = Solver.getCandidateRectangles(room);
		
		// Assert
		assertEquals("Did not return correct number of rectangles", 2, actualRectangles.size());
		assertEquals("Did not return correct rectangle", expectedRectangles.get(0), actualRectangles.get(0));
		assertEquals("Did not return correct rectangle", expectedRectangles.get(1), actualRectangles.get(1));
	}
}
