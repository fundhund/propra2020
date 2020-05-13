package fernuni.propra.algorithm;

public class TimeLimitExceededException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TimeLimitExceededException(String errorMessage) {
    super(errorMessage);
  }
}
