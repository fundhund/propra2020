package File_Processing_Component;

import fernuni.propra.file_processing.IncorrectShapeException;
import fernuni.propra.file_processing.Room;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class RoomTest {
	
	private List<Point2D.Float> getCornersForSquare(float sideLength) {
		List<Point2D.Float> corners = new ArrayList<>();
		corners.add(new Point2D.Float(0.0f, 0.0f));
		corners.add(new Point2D.Float(sideLength, 0.0f));
		corners.add(new Point2D.Float(sideLength, sideLength));
		corners.add(new Point2D.Float(0.0f, sideLength));
		return corners;
	}
	
	private List<Point2D.Float> getCornersForSquare()  {
		return getCornersForSquare(2.0f);
	}
	
	private List<Point2D.Float> getCornersForLShape(float sideLength) {
		List<Point2D.Float> corners = new ArrayList<>();
		corners.add(new Point2D.Float(0.0f, 0.0f));
		corners.add(new Point2D.Float(sideLength, 0.0f));
		corners.add(new Point2D.Float(sideLength, sideLength/2));
		corners.add(new Point2D.Float(sideLength/2, sideLength/2));
		corners.add(new Point2D.Float(sideLength/2, sideLength));
		corners.add(new Point2D.Float(0.0f, sideLength));
		return corners;
	}
	
	private List<Point2D.Float> getCornersForLShape()  {
		return getCornersForLShape(2.0f);
	}
	
	private boolean containEqualLines(List<Line2D.Float> lines1, List<Line2D.Float> lines2) {
		if (lines1.size() != lines2.size()) return false;
		
		for (int i = 0; i < lines1.size(); i++) {
			
			Line2D.Float line1 = lines1.get(i);
			Line2D.Float line2 = lines2.get(i);

			if (!line1.getP1().equals(line2.getP1())
					|| !line1.getP2().equals(line2.getP2())) return false;
		}
		
		return true;
	}
	
	@Test
	public void Room_constructor_GeneratesRoomWithIdAnsCornersAndLamps() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> expectedCorners = getCornersForSquare();
		List<Point2D.Float> expectedLamps = new ArrayList<>();
		expectedLamps.add(new Point2D.Float(1.0f, 1.0f));
		
		// Act
		Room room = new Room(expectedId, expectedCorners, expectedLamps);
		
		String actualId = room.getId();
		List<Point2D.Float> actualCorners = room.getCorners();
		List<Point2D.Float> actualLamps = room.getLamps();
		
		// Assert
		assertEquals("ID has not been passed correctly.", expectedId, actualId);
		assertEquals("Corners have not been passed correctly.", expectedCorners, actualCorners);
		assertEquals("Lamps have not been passed correctly.", expectedLamps, actualLamps);
	}
	
	@Test
	public void Room_constructor_GeneratesRoomWithIdAnsCorners() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		
		List<Point2D.Float> expectedCorners = getCornersForSquare();
		List<Point2D.Float> expectedLamps = new ArrayList<>();
		
		// Act
		Room room = new Room(expectedId, expectedCorners);
		
		String actualId = room.getId();
		List<Point2D.Float> actualCorners = room.getCorners();
		List<Point2D.Float> actualLamps = room.getLamps();
		
		// Assert
		assertEquals("ID has not been passed correctly.", expectedId, actualId);
		assertEquals("Corners have not been passed correctly.", expectedCorners, actualCorners);
		assertEquals("Lamps have not been passed correctly.", expectedLamps, actualLamps);
	}
	
	@Test
	public void Room_constructor_ThrowsExceptionForNonRectilinearWall() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> expectedCorners = new ArrayList<>();
		expectedCorners.add(new Point2D.Float(0.0f, 0.0f));
		expectedCorners.add(new Point2D.Float(2.0f, 1.0f));
		expectedCorners.add(new Point2D.Float(2.0f, 2.0f));
		expectedCorners.add(new Point2D.Float(0.0f, 2.0f));
		
		// Act/Assert
		assertThrows(IncorrectShapeException.class, () -> new Room(expectedId, expectedCorners));
	}
	
	@Test
	public void Room_constructor_ThrowsExceptionForNonRectilinearLineBetweenLastAndFirstPoint() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> expectedCorners = new ArrayList<>();
		expectedCorners.add(new Point2D.Float(0.0f, 0.0f));
		expectedCorners.add(new Point2D.Float(2.0f, 0.0f));
		expectedCorners.add(new Point2D.Float(2.0f, 2.0f));
		expectedCorners.add(new Point2D.Float(1.0f, 0.0f));
		
		// Act/Assert
		assertThrows(IncorrectShapeException.class, () -> new Room(expectedId, expectedCorners));
	}
	
	@Test
	public void Room_constructor_ThrowsExceptionForLessThanFourPoints() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> expectedCorners = new ArrayList<>();
		expectedCorners.add(new Point2D.Float(0.0f, 0.0f));
		expectedCorners.add(new Point2D.Float(2.0f, 0.0f));
		expectedCorners.add(new Point2D.Float(2.0f, 2.0f));
		
		// Act/Assert
		assertThrows(IncorrectShapeException.class, () -> new Room(expectedId, expectedCorners));
	}
	
	@Test
	public void Room_contains_ReturnsTrueForPointInsideOfRoom() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> corners = getCornersForSquare();
		Room room = new Room(expectedId, corners);
		Point2D.Float point = new Point2D.Float(1.0f, 1.0f);

		// Act
		boolean isInsideRoom = room.contains(point);
		
		// Assert
		assertTrue("Point inside of room mistaken for oustide.", isInsideRoom);
	}
	
	@Test
	public void Room_contains_ReturnsFalseForPointOutsideOfRoom() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> corners = getCornersForSquare();
		Room room = new Room(expectedId, corners);
		Point2D.Float point = new Point2D.Float(3.0f, 3.0f);

		// Act
		boolean isInsideRoom = room.contains(point);
		
		// Assert
		assertFalse("Point inside of room mistaken for oustide.", isInsideRoom);
	}
	
	@Test
	public void Room_isNorthWall_ReturnsTrueForNorthWalls() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> corners = getCornersForSquare();
		Room room = new Room(expectedId, corners);

		// Act
		boolean isNorthWallNorthWall = room.isNorthWall(room.getWalls().get(2));
		
		// Assert
		assertTrue("North wall not recognized as north wall.", isNorthWallNorthWall);
	}
	
	@Test
	public void Room_isNorthWall_ReturnsFalseForVerticalWalls() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> corners = getCornersForSquare();
		Room room = new Room(expectedId, corners);

		// Act
		boolean isEastWallNorthWall = room.isNorthWall(room.getWalls().get(1));
		boolean isWestWallNorthWall = room.isNorthWall(room.getWalls().get(3));
		
		// Assert
		assertFalse("East wall recognized as north wall.", isEastWallNorthWall);
		assertFalse("West wall recognized as north wall.", isWestWallNorthWall);
	}
	
	@Test
	public void Room_isNorthWall_ReturnsFalseForSouthWalls() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> corners = getCornersForSquare();
		Room room = new Room(expectedId, corners);

		// Act
		boolean isSouthWallNorthWall = room.isNorthWall(room.getWalls().get(0));
		
		// Assert
		assertFalse("South wall recognized as north wall.", isSouthWallNorthWall);
	}
	
	@Test
	public void Room_isSouthWall_ReturnsTrueForSouthWalls() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> corners = getCornersForSquare();
		Room room = new Room(expectedId, corners);

		// Act
		boolean isSouthWallSouthWall = room.isSouthWall(room.getWalls().get(0));
		
		// Assert
		assertTrue("South wall not recognized as south wall.", isSouthWallSouthWall);
	}
	
	@Test
	public void Room_isSouthWall_ReturnsFalseForVerticalWalls() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> corners = getCornersForSquare();
		Room room = new Room(expectedId, corners);

		// Act
		boolean isEastWallSouthWall = room.isSouthWall(room.getWalls().get(1));
		boolean isWestWallSouthWall = room.isSouthWall(room.getWalls().get(3));
		
		// Assert
		assertFalse("East wall recognized as south wall.", isEastWallSouthWall);
		assertFalse("West wall recognized as south wall.", isWestWallSouthWall);
	}
	
	@Test
	public void Room_isSouthWall_ReturnsFalseForNorthWalls() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> corners = getCornersForSquare();
		Room room = new Room(expectedId, corners);

		// Act
		boolean isNorthWallSouthWall = room.isSouthWall(room.getWalls().get(2));
		
		// Assert
		assertFalse("North wall recognized as south wall.", isNorthWallSouthWall);
	} 
	
	@Test
	public void Room_isWestWall_ReturnsTrueForWestWalls() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> corners = getCornersForSquare();
		Room room = new Room(expectedId, corners);

		// Act
		boolean isWestWallWestWall = room.isWestWall(room.getWalls().get(3));
		
		// Assert
		assertTrue("West wall not recognized as west wall.", isWestWallWestWall);
	}
	
	@Test
	public void Room_isWestWall_ReturnsFalseForHorizontalWalls() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> corners = getCornersForSquare();
		Room room = new Room(expectedId, corners);

		// Act
		boolean isNorthWallWestWall = room.isWestWall(room.getWalls().get(2));
		boolean isSouthWallWestWall = room.isWestWall(room.getWalls().get(0));
		
		// Assert
		assertFalse("North wall recognized as west wall.", isNorthWallWestWall);
		assertFalse("South wall recognized as west wall.", isSouthWallWestWall);
	}
	
	@Test
	public void Room_isWestWall_ReturnsFalseForEastWalls() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> corners = getCornersForSquare();
		Room room = new Room(expectedId, corners);

		// Act
		boolean isEastWallWestWall = room.isWestWall(room.getWalls().get(1));
		
		// Assert
		assertFalse("East wall recognized as west wall.", isEastWallWestWall);
	}
	
	@Test
	public void Room_isEastWall_ReturnsTrueForEastWalls() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> corners = getCornersForSquare();
		Room room = new Room(expectedId, corners);

		// Act
		boolean isEastWallEastWall = room.isEastWall(room.getWalls().get(1));
		
		// Assert
		assertTrue("East wall not recognized as east wall.", isEastWallEastWall);
	}
	
	@Test
	public void Room_isEastWall_ReturnsFalseForHorizontalWalls() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> corners = getCornersForSquare();
		Room room = new Room(expectedId, corners);

		// Act
		boolean isNorthWallEastWall = room.isEastWall(room.getWalls().get(2));
		boolean isSouthWallEastWall = room.isEastWall(room.getWalls().get(0));
		
		// Assert
		assertFalse("North wall recognized as east wall.", isNorthWallEastWall);
		assertFalse("South wall recognized as east wall.", isSouthWallEastWall);
	}
	
	@Test
	public void Room_isEastWall_ReturnsFalseForWestWalls() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> corners = getCornersForSquare();
		Room room = new Room(expectedId, corners);

		// Act
		boolean isWestWallEastWall = room.isEastWall(room.getWalls().get(3));
		
		// Assert
		assertFalse("West wall recognized as east wall.", isWestWallEastWall);
	}
	
	@Test
	public void Room_getVerticalWalls_ReturnsVerticalWalls() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> corners = getCornersForSquare();
		Room room = new Room(expectedId, corners);
		
		List<Line2D.Float> expectedVerticalWalls = new ArrayList<>();
		expectedVerticalWalls.add(room.getWalls().get(1));
		expectedVerticalWalls.add(room.getWalls().get(3));

		// Act
		List<Line2D.Float> actualVerticalWalls = room.getVerticalWalls();
		
		// Assert
		assertEquals("Did not return vertical walls.", expectedVerticalWalls, actualVerticalWalls);
	}
	
	@Test
	public void Room_getHorizontalWalls_ReturnsHorizontalWalls() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> corners = getCornersForSquare();
		Room room = new Room(expectedId, corners);
		
		List<Line2D.Float> expectedHorizontalWalls = new ArrayList<>();
		expectedHorizontalWalls.add(room.getWalls().get(0));
		expectedHorizontalWalls.add(room.getWalls().get(2));

		// Act
		List<Line2D.Float> actualHorizontalWalls = room.getHorizontalWalls();
		
		// Assert
		assertEquals("Did not return horizontal walls.", expectedHorizontalWalls, actualHorizontalWalls);
	}
	
	@Test
	public void Room_getIntervalsX_ReturnsCorrectXValues() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> corners = getCornersForSquare();
		Room room = new Room(expectedId, corners);
		
		List<java.lang.Float> expectedXs = new ArrayList<>();
		expectedXs.add(0.0f);
		expectedXs.add(2.0f);

		// Act
		List<java.lang.Float> actualXs = room.getIntervalX();
		
		// Assert
		assertEquals("Did not return correct interval values.", expectedXs, actualXs);
	}
	
	@Test
	public void Room_getIntervalsX_SortsXValues() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> corners = getCornersForSquare();
		Collections.reverse(corners);
		Room room = new Room(expectedId, corners);
		
		List<java.lang.Float> expectedXs = new ArrayList<>();
		expectedXs.add(0.0f);
		expectedXs.add(2.0f);

		// Act
		List<java.lang.Float> actualXs = room.getIntervalX();
		
		// Assert
		assertEquals("Did not sort interval values.", expectedXs, actualXs);
	}
	
	@Test
	public void Room_getIntervalsY_ReturnsCorrectYValues() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> corners = getCornersForSquare();
		Room room = new Room(expectedId, corners);
		
		List<java.lang.Float> expectedYs = new ArrayList<>();
		expectedYs.add(0.0f);
		expectedYs.add(2.0f);

		// Act
		List<java.lang.Float> actualYs = room.getIntervalsY();
		
		// Assert
		assertEquals("Did not return correct interval values.", expectedYs, actualYs);
	}
	
	@Test
	public void Room_getIntervalsY_SortsYValues() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> corners = getCornersForSquare();
		Collections.reverse(corners);
		Room room = new Room(expectedId, corners);
		
		List<java.lang.Float> expectedYs = new ArrayList<>();
		expectedYs.add(0.0f);
		expectedYs.add(2.0f);

		// Act
		List<java.lang.Float> actualYs = room.getIntervalsY();
		
		// Assert
		assertEquals("Did not sort interval values.", expectedYs, actualYs);
	}
	
	@Test
	public void Room_getIntervals_ReturnsCorrectXValuesForHorizontalWall() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = getCornersForLShape();
		Room room = new Room(id, corners);
		
		List<java.lang.Float> expectedXs = new ArrayList<>();
		expectedXs.add(0.0f);
		expectedXs.add(1.0f);
		expectedXs.add(2.0f);
		
		Line2D.Float wall = room.getWalls().get(0);

		// Act
		List<java.lang.Float> actualXs = room.getIntervals(wall);
		
		// Assert
		assertEquals("Did not return correct X coordinates.", expectedXs, actualXs);
	}
	
	@Test
	public void Room_getWallSections_ReturnsCorrectSectionsForHorizontalWall() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = getCornersForLShape();
		Room room = new Room(id, corners);
		
		List<Point2D.Float> points = new ArrayList<>();
		points.add(new Point2D.Float(0.0f, 0.0f));
		points.add(new Point2D.Float(1.0f, 0.0f));
		points.add(new Point2D.Float(2.0f, 0.0f));
		
		List<Line2D.Float> expectedSections = new ArrayList<>();
		expectedSections.add(new Line2D.Float(points.get(0), points.get(1)));
		expectedSections.add(new Line2D.Float(points.get(1), points.get(2)));
		
		Line2D.Float wall = room.getWalls().get(0);

		// Act
		List<Line2D.Float> actualSections = room.getWallSections(wall);
		
		// Assert
		assertTrue("Did not return correct wall sections.", containEqualLines(expectedSections, actualSections));
	} 
	
	@Test
	public void Room_getWallSections_ReturnsCorrectSectionsForVerticalWall() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = getCornersForLShape();
		Room room = new Room(id, corners);
		
		List<Point2D.Float> points = new ArrayList<>();
		points.add(new Point2D.Float(0.0f, 0.0f));
		points.add(new Point2D.Float(0.0f, 1.0f));
		points.add(new Point2D.Float(0.0f, 2.0f));
		
		List<Line2D.Float> expectedSections = new ArrayList<>();
		expectedSections.add(new Line2D.Float(points.get(0), points.get(1)));
		expectedSections.add(new Line2D.Float(points.get(1), points.get(2)));
		
		Line2D.Float wall = room.getWalls().get(5);

		// Act
		List<Line2D.Float> actualSections = room.getWallSections(wall);
		
		// Assert
		assertTrue("Did not return correct wall sections.", containEqualLines(expectedSections, actualSections));
	} 
}
