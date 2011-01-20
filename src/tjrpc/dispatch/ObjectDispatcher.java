package tjrpc.dispatch;

import tjrpc.rpc.RpcService;

/**
 * The interface to the {@link ObjectDispatcherImpl} class. It exists so that other
 * class can masquerade this object by implement this interface and delegate its
 * methods to a nested {@link ObjectDispatcherImpl} object.
 * 
 * @author wks
 * 
 */
public interface ObjectDispatcher extends Callable {

	public abstract Object addObject(String name, Object object);

	public abstract Object removeObject(String name);

}