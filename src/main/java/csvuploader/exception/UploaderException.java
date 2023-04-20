package csvuploader.exception;

public class UploaderException extends Exception{
	private static final long serialVersionUID = -2829343735423632509L;
	
	private String errorCode;
	
	public UploaderException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public UploaderException(String message) {
		super(message);
	}
	
	public UploaderException(String message, Throwable throwable) {
		super(throwable);
		this.errorCode = errorCode;
	}
	
	public String getErrorCode() { 
		return errorCode;
	}
}
