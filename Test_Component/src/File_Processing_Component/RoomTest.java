package File_Processing_Component;

import fernuni.propra.file_processing.Room;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class RoomTest {
	
	@Test
	public void Room_Constructor_GeneratesRoomWithIdAnsCornersAndLamps() {
		
		// Arrange
		String expectedId = "id";
		
		List<Point2D.Float> expectedCorners = new ArrayList<>();
		expectedCorners.add(new Point2D.Float(0.0f, 0.0f));
		expectedCorners.add(new Point2D.Float(2.0f, 0.0f));
		expectedCorners.add(new Point2D.Float(2.0f, 2.0f));
		expectedCorners.add(new Point2D.Float(2.0f, 0.0f));
		
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
}
