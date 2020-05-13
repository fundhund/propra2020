package fernuni.propra.file_processing;

public class IncorrectShapeException extends Exception {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IncorrectShapeException(String errorMessage) {
    super(errorMessage);
  }
}
