package fernuni.propra.file_processing;

import java.util.Map;

/**
 * Directions for walls.
 * 
 * @author Marius Mielke (4531230)
 *
 */
public enum Direction {
	NORTH,
  EAST,
  SOUTH,
  WEST;
	
	private static final Map<Direction, Direction> OPPOSITES = Map.of(
      NORTH, SOUTH,
      EAST,  WEST,
      SOUTH, NORTH,
      WEST,  EAST
  );

	/**
	 * Returns the opposite direction.
	 * @return opposite direction
	 */
  public Direction getOpposite() {
      return OPPOSITES.get(this);
  }
  
  private static final Map<Direction, Orientation> ORIENTATIONS = Map.of(
      NORTH, Orientation.HORIZONTAL,
      EAST,  Orientation.VERTICAL,
      SOUTH, Orientation.HORIZONTAL,
      WEST,  Orientation.VERTICAL
  );

  /**
   * Returns orientation of a direction.
   * @return direction orientation
   */
  public Orientation getOrientation() {
      return ORIENTATIONS.get(this);
  }
}
