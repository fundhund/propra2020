package fernuni.propra.file_processing;

public class Corner extends Point {
	
	private float x;
	private float y;
	
	public Corner(float x, float y) {
		super(x, y);
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Corner [x=" + x + ", y=" + y + "]";
	}
}
