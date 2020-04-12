package fernuni.propra.file_processing;

public class Point {
	
	private float x;
	private float y;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

	public double getDistance(Point point) {
		return (Math.sqrt(Math.pow(point.x - this.x, 2)) + Math.pow(point.y - this.y, 2));
	}
}
