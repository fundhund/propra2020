package fernuni.propra.algorithm;

/**
 * Thrown if a given time limit is exceeded.
 * 
 * @author Marius Mielke (4531230)
 *
 */
public class TimeLimitExceededException extends Exception {
	private static final long serialVersionUID = 1L;

	public TimeLimitExceededException(String errorMessage) {
    super(errorMessage);
  }
}
