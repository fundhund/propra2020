package fernuni.propra.user_interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Arc2D;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JPanel;
import fernuni.propra.file_processing.Room;

public class RoomView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Room room;
	private List<Point2D.Float> convertedCorners;
	private List<Point2D.Float> convertedLamps;
	private float offsetX;
	private float offsetY;
	private float yMaxRoom;
	private int margin = 40;
	private float windowMin;
	private float scale;
	private List<Shape> lampShapes;
	private Path2D.Float roomShape;
	
	public RoomView(Room room) {
		this(room, 600);
	}
	
	public RoomView(Room room, float windowMin) {
		super();
		this.room = room;
		this.windowMin = windowMin - 100;
		this.offsetX = room.getBoundaries().get("xMin");
		this.offsetY = room.getBoundaries().get("yMin");
		this.yMaxRoom = room.getBoundaries().get("yMax");
		this.scale = calculateScale();
		this.convertedCorners = createConvertedCorners();
		this.convertedLamps = createConvertedLamps();
		this.lampShapes = createLampShapes();
		this.roomShape = createRoomShape();
		
		setBackground(Color.BLACK);
		int width = (int) Math.ceil((room.getWidth() + 2 * margin) * scale);
		int height = (int) Math.ceil((room.getHeight() + 2 * margin) * scale);
    setPreferredSize(new Dimension(width, height));
    
    JLabel numberOfLamps = createNumerOfLamps();
    setLayout(new BorderLayout());
    add(numberOfLamps, BorderLayout.PAGE_END);
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
				.map(point -> new Point2D.Float(
						(point.x - offsetX + margin) * scale, 
						((yMaxRoom - point.y) - offsetY + margin) * scale))
				.collect(Collectors.toList());
		
		return convertedPoints;
	}
	
	private void drawRoom(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
    g2.setPaint(Color.WHITE);
		g2.fill(roomShape);
		g2.draw(roomShape);
	}
	
	private Path2D.Float createRoomShape() {
		Path2D.Float path = new Path2D.Float();
		path.moveTo(convertedCorners.get(0).x, convertedCorners.get(0).y);
		convertedCorners.stream().forEach(point -> path.lineTo(point.x, point.y));
		path.closePath();
		
		return path;
	}
	
	private List<Shape> createLampShapes() {
		return convertedLamps
			.stream()
			.map(point -> new Arc2D.Float(
				point.x,
				point.y,
				5 * scale,
				5 * scale,
				0,
				360,
				Arc2D.CHORD))
			.collect(Collectors.toList());
	}
	
	private void drawLamps(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setPaint(Color.RED);
		
		lampShapes
			.stream()
			.forEach(lamp -> {
				g2.fill(lamp);
				g2.draw(lamp);
			});
	}
	
	private JLabel createNumerOfLamps() {
  	String labelText = "#Lamps: " + convertedLamps.size();
  	JLabel label = new JLabel(labelText, JLabel.CENTER);
  	label.setFont(new Font("Serif", Font.PLAIN, 14));
  	label.setForeground(Color.WHITE);
  	
  	return label;
  }
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawRoom(g);
		drawLamps(g);
	}
	
	public void redraw(float windowMin) {
		removeAll();
	}
	
	private float calculateScale() {
		HashMap<String, java.lang.Float> boundaries = room.getBoundaries();
		float coordinateMax = Math.max(boundaries.get("xMax"), boundaries.get("yMax"));
		return windowMin/coordinateMax;
	}
}
