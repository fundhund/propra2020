package fernuni.propra.file_processing;

/**
 * Thrown if a room cannot be built due to an invalid set of given points.
 * 
 * @author Marius Mielke (4531230)
 *
 */
public class IncorrectShapeException extends Exception {
	private static final long serialVersionUID = 1L;

	public IncorrectShapeException(String errorMessage) {
    super(errorMessage);
  }
}
