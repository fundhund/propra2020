package File_Processing_Component;

import fernuni.propra.file_processing.Direction;
import fernuni.propra.file_processing.IncorrectShapeException;
import fernuni.propra.file_processing.Room;
import fernuni.propra.test.TestHelper;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class RoomTest {

	@Test
	public void Room_getWalls_ReturnsCorrectWallsByDirection() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
		Room room = new Room(expectedId, corners);
		
		List<Line2D.Float> expectedNorthWalls = new ArrayList<>();
		expectedNorthWalls.add(room.getWalls().get(2));
		List<Line2D.Float> expectedEastWalls = new ArrayList<>();
		expectedEastWalls.add(room.getWalls().get(1));
		List<Line2D.Float> expectedSouthWalls = new ArrayList<>();
		expectedSouthWalls.add(room.getWalls().get(0));
		List<Line2D.Float> expectedWestWalls = new ArrayList<>();
		expectedWestWalls.add(room.getWalls().get(3));
		
		// Act
		List<Line2D.Float> actualNorthWalls = room.getWalls(Direction.NORTH);
		List<Line2D.Float> actualEastWalls = room.getWalls(Direction.EAST);
		List<Line2D.Float> actualSouthWalls = room.getWalls(Direction.SOUTH);
		List<Line2D.Float> actualWestWalls = room.getWalls(Direction.WEST);
		
		// Assert
		assertEquals("Did not return correct north walls.", expectedNorthWalls, actualNorthWalls);
		assertEquals("Did not return correct east walls.", expectedEastWalls, actualEastWalls);
		assertEquals("Did not return correct south walls.", expectedSouthWalls, actualSouthWalls);
		assertEquals("Did not return correct west walls.", expectedWestWalls, actualWestWalls);
	}
	
	@Test
	public void Room_constructor_GeneratesRoomWithIdAnsCornersAndLamps() throws IncorrectShapeException {
		
		// Arrange
		String expectedId = "id";
		List<Point2D.Float> expectedCorners = TestHelper.getCornersForSquare();
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
		
		List<Point2D.Float> expectedCorners = TestHelper.getCornersForSquare();
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
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
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
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
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
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
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
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
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
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
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
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
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
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
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
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
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
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
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
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
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
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
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
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
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
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
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
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
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
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
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
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
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
	public void Room_getNearestWall_ReturnsInputIfWallDirectionIsEqualToNearestWallDirection() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForZShape();
		Room room = new Room(id, corners);
		List<Line2D.Float> walls = room.getWalls();
		
		Line2D.Float northWall = walls.get(4);
		Line2D.Float eastWall = walls.get(3);
		Line2D.Float southWall = walls.get(0);
		Line2D.Float westWall = walls.get(7);
		
		Line2D.Float expectedNearestWallNorth = northWall;
		Line2D.Float expectedNearestWallEast = eastWall;
		Line2D.Float expectedNearestWallSouth = southWall;
		Line2D.Float expectedNearestWallWest = westWall;

		// Act
		Line2D.Float actualNearestWallNorth = room.getNearestWall(northWall, Direction.NORTH);
		Line2D.Float actualNearestWallEast = room.getNearestWall(eastWall, Direction.EAST);
		Line2D.Float actualNearestWallSouth = room.getNearestWall(southWall, Direction.SOUTH);
		Line2D.Float actualNearestWallWest = room.getNearestWall(westWall, Direction.WEST);
		
		// Assert
		assertEquals("Did not return correct nearest wall.", expectedNearestWallNorth, actualNearestWallNorth);
		assertEquals("Did not return correct nearest wall.", expectedNearestWallEast, actualNearestWallEast);
		assertEquals("Did not return correct nearest wall.", expectedNearestWallSouth, actualNearestWallSouth);
		assertEquals("Did not return correct nearest wall.", expectedNearestWallWest, actualNearestWallWest);
	}
	
	@Test
	public void Room_getNearestWall_ReturnsCorrectWallIfDirectionIsOppositeDirectionFromWall() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForZShape();
		Room room = new Room(id, corners);
		List<Line2D.Float> walls = room.getWalls();
		
		Line2D.Float northWall = walls.get(4);
		Line2D.Float eastWall = walls.get(3);
		Line2D.Float southWall = walls.get(0);
		Line2D.Float westWall = walls.get(7);
		
		Line2D.Float expectedNearestWallNorth = walls.get(6);
		Line2D.Float expectedNearestWallEast = walls.get(7);
		Line2D.Float expectedNearestWallSouth = walls.get(2);
		Line2D.Float expectedNearestWallWest =walls.get(3);

		// Act
		Line2D.Float actualNearestWallNorth = room.getNearestWall(northWall, Direction.SOUTH);
		Line2D.Float actualNearestWallEast = room.getNearestWall(eastWall, Direction.WEST);
		Line2D.Float actualNearestWallSouth = room.getNearestWall(southWall, Direction.NORTH);
		Line2D.Float actualNearestWallWest = room.getNearestWall(westWall, Direction.EAST);
		
		// Assert
		assertEquals("Did not return correct nearest wall.", expectedNearestWallNorth, actualNearestWallNorth);
		assertEquals("Did not return correct nearest wall.", expectedNearestWallEast, actualNearestWallEast);
		assertEquals("Did not return correct nearest wall.", expectedNearestWallSouth, actualNearestWallSouth);
		assertEquals("Did not return correct nearest wall.", expectedNearestWallWest, actualNearestWallWest);
	}
	
	@Test
	public void Room_getNearestWall_ReturnsCorrectWestWallForHorizontalWalls() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForPlusShape();
		Room room = new Room(id, corners);
		List<Line2D.Float> walls = room.getWalls();
		
		Line2D.Float northWall = walls.get(4);
		Line2D.Float southWall = walls.get(2);
		
		Line2D.Float expectedNearestWestWall = walls.get(9);

		// Act
		Line2D.Float actualNearestWestWallNorth = room.getNearestWall(northWall, Direction.WEST);
		Line2D.Float actualNearestWestWallSouth = room.getNearestWall(southWall, Direction.WEST);
		
		// Assert
		assertEquals("Did not return nearest west wall.", expectedNearestWestWall, actualNearestWestWallNorth);
		assertEquals("Did not return nearest west wall.", expectedNearestWestWall, actualNearestWestWallSouth);
	}
	
	@Test
	public void Room_getNearestWall_ReturnsCorrectEastWallForHorizontalWalls() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForPlusShape();
		Room room = new Room(id, corners);
		List<Line2D.Float> walls = room.getWalls();
		
		Line2D.Float northWall = walls.get(8);
		Line2D.Float southWall = walls.get(10);
		
		Line2D.Float expectedNearestEastWall = walls.get(3);

		// Act
		Line2D.Float actualNearestEastWallNorth = room.getNearestWall(northWall, Direction.EAST);
		Line2D.Float actualNearestEastWallSouth = room.getNearestWall(southWall, Direction.EAST);
		
		// Assert
		assertEquals("Did not return nearest east wall.", expectedNearestEastWall, actualNearestEastWallNorth);
		assertEquals("Did not return nearest east wall.", expectedNearestEastWall, actualNearestEastWallSouth);
	}
	
	@Test
	public void Room_getNearestWall_ReturnsCorrectNorthWallForVerticalWalls() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForPlusShape();
		Room room = new Room(id, corners);
		List<Line2D.Float> walls = room.getWalls();
		
		Line2D.Float westWall = walls.get(11);
		Line2D.Float eastWall = walls.get(1);
		
		Line2D.Float expectedNearestNorthWall = walls.get(6);

		// Act
		Line2D.Float actualNearestNorthWallWest = room.getNearestWall(westWall, Direction.NORTH);
		Line2D.Float actualNearestNorthWallEast = room.getNearestWall(eastWall, Direction.NORTH);
		
		// Assert
		assertEquals("Did not return nearest north wall.", expectedNearestNorthWall, actualNearestNorthWallWest);
		assertEquals("Did not return nearest north wall.", expectedNearestNorthWall, actualNearestNorthWallEast);
	}
	
	@Test
	public void Room_getNearestWall_ReturnsCorrectSouthWallForVerticalWalls() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForPlusShape();
		Room room = new Room(id, corners);
		List<Line2D.Float> walls = room.getWalls();
		
		Line2D.Float westWall = walls.get(7);
		Line2D.Float eastWall = walls.get(5);
		
		Line2D.Float expectedNearestSouthWall = walls.get(0);

		// Act
		Line2D.Float actualNearestSouthWallWest = room.getNearestWall(westWall, Direction.SOUTH);
		Line2D.Float actualNearestSouthWallEast = room.getNearestWall(eastWall, Direction.SOUTH);
		
		// Assert
		assertEquals("Did not return nearest south wall.", expectedNearestSouthWall, actualNearestSouthWallWest);
		assertEquals("Did not return nearest south wall.", expectedNearestSouthWall, actualNearestSouthWallEast);
	}
	
	@Test
	public void Room_getExtendedWall_ReturnsCorrectWallForSouthWall() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForPlusShape();
		Room room = new Room(id, corners);
		List<Line2D.Float> walls = room.getWalls();
		
		Line2D.Float wall = walls.get(2);
		
		Line2D.Float expectedExtendedWall = new Line2D.Float(0.0f, 1.0f, 3.0f, 1.0f);

		// Act
		Line2D.Float actualExtendedWall = room.getExtendedWall(wall);
		
		// Assert
		assertTrue("Did not return extended wall.", TestHelper.areEqual(expectedExtendedWall, actualExtendedWall));
	}
	
	@Test
	public void Room_getExtendedWall_ReturnsCorrectWallForNorthWall() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForPlusShape();
		Room room = new Room(id, corners);
		List<Line2D.Float> walls = room.getWalls();
		
		Line2D.Float wall = walls.get(6);
		
		Line2D.Float expectedExtendedWall = wall;

		// Act
		Line2D.Float actualExtendedWall = room.getExtendedWall(wall);
		
		// Assert
		assertTrue("Did not return extended wall.", TestHelper.areEqual(expectedExtendedWall, actualExtendedWall));
	}
	
	@Test
	public void Room_getExtendedWall_ReturnsCorrectWallForWestWall() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForPlusShape();
		Room room = new Room(id, corners);
		List<Line2D.Float> walls = room.getWalls();
		
		Line2D.Float wall = walls.get(9);
		
		Line2D.Float expectedExtendedWall = wall;

		// Act
		Line2D.Float actualExtendedWall = room.getExtendedWall(wall);
		
		// Assert
		assertTrue("Did not return extended wall.", TestHelper.areEqual(expectedExtendedWall, actualExtendedWall));
	}
	
	@Test
	public void Room_getExtendedWall_ReturnsCorrectWallForEastWall() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForPlusShape();
		Room room = new Room(id, corners);
		List<Line2D.Float> walls = room.getWalls();
		
		Line2D.Float wall = walls.get(5);
		
		Line2D.Float expectedExtendedWall = new Line2D.Float(2.0f, 0.0f, 2.0f, 3.0f);

		// Act
		Line2D.Float actualExtendedWall = room.getExtendedWall(wall);
		
		// Assert
		assertTrue("Did not return extended wall.", TestHelper.areEqual(expectedExtendedWall, actualExtendedWall));
	}
	
	@Test
	public void Room_getOppositeWall_ReturnsCorrectOppositeWallForNorthWall() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForPlusShape();
		Room room = new Room(id, corners);
		List<Line2D.Float> walls = room.getWalls();
		
		Line2D.Float wall = walls.get(6);
		
		Line2D.Float expectedOppositeWall = walls.get(0);

		// Act
		Line2D.Float actualOppositeWall = room.getOppositeWall(wall);
		
		// Assert
		assertEquals("Did not return extended wall.", expectedOppositeWall, actualOppositeWall);
	}
	
	@Test
	public void Room_getOppositeWall_ReturnsCorrectOppositeWallForSouthWall() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForPlusShape();
		Room room = new Room(id, corners);
		List<Line2D.Float> walls = room.getWalls();
		
		Line2D.Float wall = walls.get(0);
		
		Line2D.Float expectedOppositeWall = walls.get(6);

		// Act
		Line2D.Float actualOppositeWall = room.getOppositeWall(wall);
		
		// Assert
		assertEquals("Did not return extended wall.", expectedOppositeWall, actualOppositeWall);
	}
	
	@Test
	public void Room_getOppositeWall_ReturnsCorrectOppositeWallForEastWall() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForPlusShape();
		Room room = new Room(id, corners);
		List<Line2D.Float> walls = room.getWalls();
		
		Line2D.Float wall = walls.get(1);
		
		Line2D.Float expectedOppositeWall = walls.get(11);

		// Act
		Line2D.Float actualOppositeWall = room.getOppositeWall(wall);
		
		// Assert
		assertEquals("Did not return extended wall.", expectedOppositeWall, actualOppositeWall);
	}
	
	@Test
	public void Room_getOppositeWall_ReturnsCorrectOppositeWallForWestWall() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForPlusShape();
		Room room = new Room(id, corners);
		List<Line2D.Float> walls = room.getWalls();
		
		Line2D.Float wall = walls.get(9);
		
		Line2D.Float expectedOppositeWall = walls.get(3);

		// Act
		Line2D.Float actualOppositeWall = room.getOppositeWall(wall);
		
		// Assert
		assertEquals("Did not return extended wall.", expectedOppositeWall, actualOppositeWall);
	}
	
	@Test
	public void Room_getRectangle_ReturnsCorrectRectangleForNorthWall() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForPlusShape();
		Room room = new Room(id, corners);
		List<Line2D.Float> walls = room.getWalls();
		
		Line2D.Float wall = walls.get(6);
		
		Rectangle2D.Float expectedRectangle = new Rectangle2D.Float(1.0f, 0.0f, 1.0f, 3.0f);

		// Act
		Rectangle2D.Float actualRectangle = room.getRectangle(wall);
		
		// Assert
		assertTrue("Did not return correct rectangle.", TestHelper.areEqual(expectedRectangle, actualRectangle));
	}
	
	@Test
	public void Room_getRectangle_ReturnsCorrectRectangleForSouthWall() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForPlusShape();
		Room room = new Room(id, corners);
		List<Line2D.Float> walls = room.getWalls();
		
		Line2D.Float wall = walls.get(2);
		
		Rectangle2D.Float expectedRectangle = new Rectangle2D.Float(0.0f, 1.0f, 3.0f, 1.0f);

		// Act
		Rectangle2D.Float actualRectangle = room.getRectangle(wall);
		
		// Assert
		assertTrue("Did not return correct rectangle.", TestHelper.areEqual(expectedRectangle, actualRectangle));
	}
	
	@Test
	public void Room_getRectangle_ReturnsCorrectRectangleForEastWall() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForPlusShape();
		Room room = new Room(id, corners);
		List<Line2D.Float> walls = room.getWalls();
		
		Line2D.Float wall = walls.get(1);
		
		Rectangle2D.Float expectedRectangle = new Rectangle2D.Float(1.0f, 0.0f, 1.0f, 3.0f);

		// Act
		Rectangle2D.Float actualRectangle = room.getRectangle(wall);
		// Assert
		assertTrue("Did not return correct rectangle.", TestHelper.areEqual(expectedRectangle, actualRectangle));
	}
	
	@Test
	public void Room_getDirection_ReturnsCorrectDirectionForEastWall() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForPlusShape();
		Room room = new Room(id, corners);
		List<Line2D.Float> walls = room.getWalls();
		
		Line2D.Float wall = walls.get(1);
		
		Direction expectedDirection = Direction.EAST;

		// Act
		Direction actualDirection = room.getDirection(wall);
		
		// Assert
		assertEquals("Did not return correct direction.", expectedDirection, actualDirection);
	}
	
	@Test
	public void Room_constructor_CreatesCorrectRectanglesForSquare() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForSquare();
		Room room = new Room(id, corners);
		
		Rectangle2D.Float expectedRectangle = new Rectangle2D.Float(0, 0, 2, 2);

		// Act
		Rectangle2D.Float[] actualRectangles = room.getRectangles();
		
		// Assert
		assertEquals("Did not return correct number of rectangles.", 1, actualRectangles.length);
		assertTrue("Did not return correct rectangle.", TestHelper.areEqual(expectedRectangle, actualRectangles[0]));
	}
	
	@Test
	public void Room_constructor_CreatesCorrectRectanglesForPlusShape() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForPlusShape();
		Room room = new Room(id, corners);
		
		Rectangle2D.Float verticalRectangle = new Rectangle2D.Float(1, 0, 1, 3);
		Rectangle2D.Float horizontalRectangle = new Rectangle2D.Float(0, 1, 3, 1);

		// Act
		Rectangle2D.Float[] actualRectangles = room.getRectangles();
		
		// Assert
		assertEquals("Did not return correct number of rectangles.", 2, actualRectangles.length);
		assertTrue("Did not return correct rectangle.", TestHelper.areEqual(verticalRectangle, actualRectangles[0]));
		assertTrue("Did not return correct rectangle.", TestHelper.areEqual(horizontalRectangle, actualRectangles[1]));
	}
	
	@Test
	public void Room_constructor_CreatesCorrectRectanglesForArcShape() throws IncorrectShapeException {
		
		// Arrange
		String id = "id";
		List<Point2D.Float> corners = TestHelper.getCornersForArcShape();
		Room room = new Room(id, corners);
		
		Rectangle2D.Float[] expectedRectangles = new Rectangle2D.Float[3];
		expectedRectangles[0] = new Rectangle2D.Float(0, 0, 1, 2);
		expectedRectangles[1] = new Rectangle2D.Float(0, 1, 3, 1);
		expectedRectangles[2] = new Rectangle2D.Float(2, 0, 1, 2);

		// Act
		Rectangle2D.Float[] actualRectangles = room.getRectangles();
		
		// Assert
		assertEquals("Did not return correct number of rectangles.", 3, actualRectangles.length);
		assertEquals("Did not return correct rectangle.", expectedRectangles[0], actualRectangles[0]);
		assertEquals("Did not return correct rectangle.", expectedRectangles[1], actualRectangles[1]);
		assertEquals("Did not return correct rectangle.", expectedRectangles[2], actualRectangles[2]);
	}
}
