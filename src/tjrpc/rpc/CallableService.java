package tjrpc.rpc;

import tjrpc.dispatch.Callable;
import tjrpc.dispatch.NestedException;

/**
 * @author wks
 * 
 */
public class CallableService implements RpcService {

	private Callable callable;

	public Callable getCallable() {
		return callable;
	}

	public void setCallable(Callable callable) {
		this.callable = callable;
	}

	public CallableService() {
		super();
	}

	public CallableService(Callable callable) {
		super();
		this.callable = callable;
	}

	@Override
	public RpcResponse invoke(RpcRequest request) {
		try {
			Object returnValue = callable.call(request.getObject(),
					request.getMethod(), request.getParams());
			return RpcResponse.normal(returnValue);
		} catch (NestedException ne) {
			Throwable e = ne.getNestedThrowable();
			return RpcResponse.error(e);
		} catch (Exception e) {
			return RpcResponse.error(e);
		}
	}
}
