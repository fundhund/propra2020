package fernuni.propra.file_processing;

import java.util.Map;

/**
 * Orientations for walls.
 * 
 * @author Marius Mielke (4531230)
 *
 */
public enum Orientation {
	HORIZONTAL,
	VERTICAL;
	
	private static final Map<Orientation, Orientation> OPPOSITES = Map.of(
			HORIZONTAL, VERTICAL,
			VERTICAL,  HORIZONTAL
  );
	
	/**
	 * Returns the opposite orientation.
	 * @return opposite orientation
	 */
  public Orientation getOpposite() {
      return OPPOSITES.get(this);
  }
}
