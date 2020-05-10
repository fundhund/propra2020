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
import fernuni.propra.file_processing.IncorrectShapeException;
import fernuni.propra.file_processing.Room;
import fernuni.propra.test.TestHelper;

public class SolverTest {
	
	@Test
	public void Solver_toSortedArray_convertsAndSortsIntegerSet() throws IncorrectShapeException {
		
		// Arrange
		Set<Integer> intSet = new HashSet<>();
		intSet.add(3);
		intSet.add(0);
		intSet.add(2);
		intSet.add(1);
		int[] expectedArray = {0, 1, 2, 3};
		
		// Act
		int[] actualArray = Solver.toSortedArray(intSet);
		
		// Assert
		assertTrue("Did not return sorted integer array", Arrays.equals(expectedArray, actualArray));
	}
	
	@Test
	public void Solver_getCandidateRectangles_returnsCorrectIntersectionsForSquare() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
		Room room = new Room(id, corners);
		
		Rectangle2D.Float expectedIntersection = new Rectangle2D.Float(0, 0, 2, 2);
		Set<Integer> expectedInvolvedRectangles = new HashSet<>(Arrays.asList(0));
		
		// Act
		Solver solver = new Solver(room);
		Map<Rectangle2D.Float, Set<Integer>> actualIntersections = solver.createCandidateRectangles();
		
		// Assert
		assertEquals("Did not return correct number of rectangles", 1, actualIntersections.size());
		assertEquals("Did not return correct involved rectangles", expectedInvolvedRectangles, actualIntersections.get(expectedIntersection));
	}
	
	@Test
	public void Solver_getCandidateRectangles_returnsCorrectIntersectionsForPlusShape() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForPlusShape();
		Room room = new Room(id, corners);
		
		Rectangle2D.Float expectedIntersection = new Rectangle2D.Float(1, 1, 1, 1);
		Set<Integer> expectedInvolvedRectangles = new HashSet<>(Arrays.asList(0, 1));
		
		// Act
		Solver solver = new Solver(room);
		Map<Rectangle2D.Float, Set<Integer>> actualIntersections = solver.createCandidateRectangles();
	
		// Assert
		assertEquals("Did not return correct number of rectangles", 1, actualIntersections.size());
		assertEquals("Did not return correct involved rectangles", expectedInvolvedRectangles, actualIntersections.get(expectedIntersection));
	}
	
	@Test
	public void Solver_getCandidateRectangles_returnsCorrectIntersectionsForArcShape() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForArcShape();
		Room room = new Room(id, corners);
		
		Rectangle2D.Float expectedIntersection1 = new Rectangle2D.Float(0,1,1,1);
		Set<Integer> expectedInvolvedRectangles1 = new HashSet<>(Arrays.asList(0, 1));
		Rectangle2D.Float expectedIntersection2 = new Rectangle2D.Float(2,1,1,1);
		Set<Integer> expectedInvolvedRectangles2 = new HashSet<>(Arrays.asList(1, 2));
		
		// Act
		Solver solver = new Solver(room);
		Map<Rectangle2D.Float, Set<Integer>> actualIntersections = solver.createCandidateRectangles();
	
		// Assert
		assertEquals("Did not return correct number of rectangles", 2, actualIntersections.size());
		assertEquals("Did not return correct involved rectangles", expectedInvolvedRectangles1, actualIntersections.get(expectedIntersection1));
		assertEquals("Did not return correct involved rectangles", expectedInvolvedRectangles2, actualIntersections.get(expectedIntersection2));
	}
	
	@Test
	public void Solver_getCandidateLamp_returnsCorrectLampForSquare() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
		Room room = new Room(id, corners);
		
		Point2D.Float expectedPosition = new Point2D.Float(1, 1);
		int[] expectedRectangles = new int[1];
		expectedRectangles[0] = 0;
		
		Solver solver = new Solver(room);
		Map<Rectangle2D.Float, Set<Integer>> intersectionsMap = solver.createCandidateRectangles();
		Rectangle2D.Float intersection = new Rectangle2D.Float(0, 0, 2, 2);

		// Act
		Lamp candidateLamp = solver.getCandidateLamp(intersection, intersectionsMap.get(intersection));
		int[] actualRectangles = candidateLamp.getRectangles();
		Point2D.Float actualPosition = candidateLamp.getPosition();
		
		// Assert
		assertEquals("Did not return correct number of rectangles", 1, actualRectangles.length);
		assertTrue("Did not return correct rectangles", Arrays.equals(expectedRectangles, actualRectangles));
		assertEquals("Did not return correct position", expectedPosition, actualPosition);
	}

	@Test
	public void Solver_getCandidateLamps_returnsCorrectLampsForSquare() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
		Room room = new Room(id, corners);
		
		Point2D.Float expectedPosition1 = new Point2D.Float(1, 1);
		int[] expectedRectangles1 = {0};
		
		List<Lamp> expectedLamps = new ArrayList<>();
		expectedLamps.add(new Lamp(expectedPosition1, expectedRectangles1));
		
		Solver solver = new Solver(room);
		
		// Act
		List<Lamp> actualLamps = solver.getCandidateLamps();
		
		// Assert
		assertEquals("Did not return correct number of lamps", 1, actualLamps.size());
		for (Lamp expectedLamp : expectedLamps) {
			assertTrue("Does not contain expected lamp", actualLamps.contains(expectedLamp));
		}
	}
	
	@Test
	public void Solver_getCandidateLamps_returnsCorrectLampsForPlusShape() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForPlusShape();
		Room room = new Room(id, corners);
		
		Point2D.Float expectedPosition1 = new Point2D.Float(1.5f, 1.5f);
		int[] expectedRectangles1 = {0, 1};
		
		List<Lamp> expectedLamps = new ArrayList<>();
		expectedLamps.add(new Lamp(expectedPosition1, expectedRectangles1));
		
		Solver solver = new Solver(room);
		
		// Act
		List<Lamp> actualLamps = solver.getCandidateLamps();
		
		// Assert
		assertEquals("Did not return correct number of lamps", 1, actualLamps.size());
		for (Lamp expectedLamp : expectedLamps) {
			assertTrue("Does not contain expected lamp", actualLamps.contains(expectedLamp));
		}
	}
	
	@Test
	public void Solver_getCandidateLamps_returnsCorrectLampsForArcShape() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForArcShape();
		Room room = new Room(id, corners);
		
		Point2D.Float expectedPosition1 = new Point2D.Float(0.5f, 1.5f);
		int[] expectedRectangles1 = {0, 1};
		Point2D.Float expectedPosition2 = new Point2D.Float(2.5f, 1.5f);
		int[] expectedRectangles2 = {1, 2};
		
		List<Lamp> expectedLamps = new ArrayList<>();
		expectedLamps.add(new Lamp(expectedPosition1, expectedRectangles1));
		expectedLamps.add(new Lamp(expectedPosition2, expectedRectangles2));
		
		Solver solver = new Solver(room);
		
		// Act
		List<Lamp> actualLamps = solver.getCandidateLamps();
		
		// Assert
		assertEquals("Did not return correct number of lamps", 2, actualLamps.size());
		for (Lamp expectedLamp : expectedLamps) {
			assertTrue("Does not contain expected lamp", actualLamps.contains(expectedLamp));
		}
	}
	
	@Test
	public void Solver_getCandidateLamps_returnsCorrectLampsForOpenRingShape() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForOpenRingShape();
		Room room = new Room(id, corners);
		
		Point2D.Float expectedPosition1 = new Point2D.Float(1.5f, 1.5f);
		int[] expectedRectangles1 = {0, 1};
		Point2D.Float expectedPosition2 = new Point2D.Float(1.5f, 7.5f);
		int[] expectedRectangles2 = {1, 2};
		Point2D.Float expectedPosition3 = new Point2D.Float(7.5f, 7.5f);
		int[] expectedRectangles3 = {2, 3};
		Point2D.Float expectedPosition4 = new Point2D.Float(7.5f, 1.5f);
		int[] expectedRectangles4 = {3, 4};
		
		List<Lamp> expectedLamps = new ArrayList<>();
		expectedLamps.add(new Lamp(expectedPosition1, expectedRectangles1));
		expectedLamps.add(new Lamp(expectedPosition2, expectedRectangles2));
		expectedLamps.add(new Lamp(expectedPosition3, expectedRectangles3));
		expectedLamps.add(new Lamp(expectedPosition4, expectedRectangles4));
		
		Solver solver = new Solver(room);
		
		// Act
		List<Lamp> actualLamps = solver.getCandidateLamps();
		actualLamps.stream().forEach(System.out::println);
		
		// Assert
		assertEquals("Did not return correct number of lamps", 4, actualLamps.size());
		for (Lamp expectedLamp : expectedLamps) {
			assertTrue("Does not contain expected lamp: " + expectedLamp.toString(), actualLamps.contains(expectedLamp));
		}
	}
}
