package tjrpc.dispatch;

import tjrpc.rpc.RpcService;

/**
 * The interface to the {@link ObjectDispatcher} class. It exists so that other
 * class can masquerade this object by implement this interface and delegate its
 * methods to a nested {@link ObjectDispatcher} object.
 * 
 * @author wks
 * 
 */
public interface IObjectDispatcher extends RpcService {

	public abstract Object addObject(String name, Object object);

	public abstract Object removeObject(String name);

	/**
	 * Call a method with a given name for the object, a name of the method and
	 * the arguments.
	 * 
	 * @author wks
	 * 
	 * @param objectName
	 *            The name of the object assigned when the object is added to
	 *            this ObjectDispatcher.
	 * @param methodName
	 *            The name of the method in the object.
	 * @param args
	 *            The arguments to the method.
	 * @return The return value of the dispatched method.
	 * @throws NestedException
	 *             If the dispatched method throws an exception, it will be
	 *             nested in this exception.
	 * @throws IllegalArgumentException
	 *             Thrown if the object or method specified by the objectName or
	 *             the methodName parameter is not found.
	 * @throws ObjectDispatcherException
	 *             Thrown if anything else went wrong except the dispatched
	 *             method itself throws an exception.
	 */
	public abstract Object call(String objectName, String methodName,
			Object[] args) throws NestedException, ObjectDispatcherException;

}