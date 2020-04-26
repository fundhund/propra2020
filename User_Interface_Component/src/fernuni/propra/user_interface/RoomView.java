package fernuni.propra.user_interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JPanel;
import fernuni.propra.file_processing.Room;

public class RoomView extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Room room;
	private List<Point2D.Float> convertedCorners;
	private List<Point2D.Float> convertedLamps;
	private float offsetX;
	private float offsetY;
	private int margin = 40;
	
	public RoomView(Room room) {
		super();
		this.room = room;
		this.offsetX = room.getBoundaries().get("xMin");
		this.offsetY = room.getBoundaries().get("yMin");
		this.convertedCorners = createConvertedCorners();
		this.convertedLamps = createConvertedLamps();
		
		setBackground(Color.BLACK);
		int width = (int) Math.ceil(room.getWidth() + 2 * margin);
		int height = (int) Math.ceil(room.getHeight() + 2 * margin);
    setPreferredSize(new Dimension(width, height));
	}
	
	private List<Point2D.Float> createConvertedCorners() {
		List<Point2D.Float> corners = room.getCorners();
		return createConvertedPoints(corners);
	}
	
	private List<Point2D.Float> createConvertedLamps() {
		List<Point2D.Float> lamps = room.getLamps();
		return createConvertedPoints(lamps);
	}
	
	private List<Point2D.Float> createConvertedPoints(List<Point2D.Float> points) {
		List<Point2D.Float> convertedPoints = points
				.stream()
				.map(point -> new Point2D.Float(point.x - offsetX + margin, point.y - offsetY + margin))
				.collect(Collectors.toList());
		
		return convertedPoints;
	}
	
	
	private void drawRoom(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
    g2.setPaint(Color.WHITE);
		
		Path2D.Float path = new Path2D.Float();
		
		path.moveTo(convertedCorners.get(0).x, convertedCorners.get(0).y);
		convertedCorners.stream().forEach(point -> path.lineTo(point.x, point.y));
		path.closePath();
		
		g2.fill(path);
		
		g2.draw(path);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		drawRoom(g);
	    
	}
}
