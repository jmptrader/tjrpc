package tjrpc.dispatch;

import java.lang.reflect.InvocationTargetException;

/**
 * Thrown if the dispatched method throws an exception. It is an unchecked
 * {@link RuntimeException}-based alternative to the checked
 * {@link InvocationTargetException} exception.
 * 
 * @author wks
 * 
 */
public class NestedException extends RuntimeException {

	private static final long serialVersionUID = -1914866603140468233L;

	private Throwable nestedThrowable;

	public Throwable getNestedThrowable() {
		return nestedThrowable;
	}

	public NestedException(Throwable nestedThrowable) {
		super();
		this.nestedThrowable = nestedThrowable;
	}
}
