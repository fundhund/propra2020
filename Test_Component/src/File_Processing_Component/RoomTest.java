package File_Processing_Component;

import fernuni.propra.file_processing.IncorrectShapeException;
import fernuni.propra.file_processing.Room;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;
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
	
	@Test
	public void Room_Constructor_GeneratesRoomWithIdAnsCornersAndLamps() throws IncorrectShapeException {
		
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
	public void Room_Constructor_GeneratesRoomWithIdAnsCorners() throws IncorrectShapeException {
		
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
	public void Room_Constructor_ThrowsExceptionForNonRectilinearWall() throws IncorrectShapeException {
		
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
	public void Room_Constructor_ThrowsExceptionForNonRectilinearLineBetweenLastAndFirstPoint() throws IncorrectShapeException {
		
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
	public void Room_Constructor_ThrowsExceptionForLessThanFourPoints() throws IncorrectShapeException {
		
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
	public void Room_Contains_ReturnsTrueForPointInsideOfRoom() throws IncorrectShapeException {
		
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
	public void Room_Contains_ReturnsFalseForPointOutsideOfRoom() throws IncorrectShapeException {
		
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
	public void Room_IsNorthWall_ReturnsTrueForNorthWalls() throws IncorrectShapeException {
		
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
	public void Room_IsNorthWall_ReturnsFalseForVerticalWalls() throws IncorrectShapeException {
		
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
	public void Room_IsNorthWall_ReturnsFalseForSouthWalls() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> corners = getCornersForSquare();
		Room room = new Room(expectedId, corners);

		// Act
		boolean isSouthWallNorthWall = room.isNorthWall(room.getWalls().get(0));
		
		// Assert
		assertFalse("South wall recognized as north wall.", isSouthWallNorthWall);
	} 
}
