
public class Errors extends RuntimeException{
	public Errors (String message, Object ... values) {
		super(String.format(message, values));
	}
	
	public Errors (Throwable cause, String message, Object ...values) {
		super(String.format(message, values), cause);
	}
	
	public Errors (Throwable exception) {
		super(exception);
	}
	
}
