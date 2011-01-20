package tjrpc.json;

public class UnknownRemoteException extends RuntimeException {
	private static final long serialVersionUID = 8348741936286470431L;

	public UnknownRemoteException() {
		super();
	}

	public UnknownRemoteException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownRemoteException(String message) {
		super(message);
	}

	public UnknownRemoteException(Throwable cause) {
		super(cause);
	}

}
