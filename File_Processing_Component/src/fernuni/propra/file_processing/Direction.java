package fernuni.propra.file_processing;

import java.util.Map;

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

  public Direction getOpposite() {
      return OPPOSITES.get(this);
  }
  
  private static final Map<Direction, Orientation> ORIENTATIONS = Map.of(
      NORTH, Orientation.HORIZONTAL,
      EAST,  Orientation.VERTICAL,
      SOUTH, Orientation.HORIZONTAL,
      WEST,  Orientation.VERTICAL
  );

  public Orientation getOrientation() {
      return ORIENTATIONS.get(this);
  }
}
