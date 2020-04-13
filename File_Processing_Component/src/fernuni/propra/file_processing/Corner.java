package fernuni.propra.file_processing;

import java.awt.geom.Point2D;

public class Corner extends Point2D.Float {
	
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
