package fernuni.propra.file_processing;

import java.awt.geom.Point2D;

public class Lamp extends Point2D.Float {
	
	private float x;
	private float y;
	
	public Lamp(float x, float y) {
		super(x, y);
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Lamp [x=" + x + ", y=" + y + "]";
	}
}
