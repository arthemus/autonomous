package org.autonomous.functions;

/**
 * Class for handling common system exceptions such as duplicate key
 * problems in the database, file not found, etc.
 *
 * @author arthemus
 * @since 30/09/2013
 */
public final class ExceptionResolver {

	private final Throwable exceptionOrigin;

	private ExceptionResolver(final Throwable exceptionOrigin) {
		this.exceptionOrigin = exceptionOrigin;
	}

	/**
	 * Defines the exception to be handled.
	 *
	 * @param e
	 *            The exception to resolve.
	 * @return A new ExceptionResolver instance.
	 */
	public static ExceptionResolver by(final Throwable e) {
		return new ExceptionResolver(e);
	}

	/**
	 * Obtains a new message for the exception, or if the exception type does
	 * not yet have a defined handling, returns the original exception message.
	 *
	 * @return The resolved message.
	 */
	public String getNewMessage() {
		String result = exceptionOrigin.getMessage();
		if (result.contains("The system cannot find the file specified")) {
			result = "The specified file could not be located";
		}
		else if (result.contains("FOREIGN KEY")) {
			result = "This record is currently in use by the system and cannot be deleted!";
		}
		return result;
	}
}
