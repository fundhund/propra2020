package fernuni.propra.file_processing;

import java.util.Map;

public enum Orientation {
	HORIZONTAL,
	VERTICAL;
	
	private static final Map<Orientation, Orientation> OPPOSITES = Map.of(
			HORIZONTAL, VERTICAL,
			VERTICAL,  HORIZONTAL
  );

  public Orientation getOpposite() {
      return OPPOSITES.get(this);
  }
}
