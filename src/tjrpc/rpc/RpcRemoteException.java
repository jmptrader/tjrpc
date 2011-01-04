package tjrpc.rpc;

public class RpcRemoteException extends RpcException {

	private static final long serialVersionUID = -6373813806499330179L;

	public RpcRemoteException() {
		super();
	}

	public RpcRemoteException(String message, Throwable cause) {
		super(message, cause);
	}

	public RpcRemoteException(String message) {
		super(message);
	}

	public RpcRemoteException(Throwable cause) {
		super(cause);
	}
}
