/**
 * Copyright 2010,2011 Kunshan Wang
 * 
 * This file is part of TJRPC
 * 
 * TJRPC is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * 
 * TJRPC is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with TJRPC.  If not, see <http://www.gnu.org/licenses/>.
 */
package tjrpc.dispatch;

public interface Callable {

	/**
	 * Call a method with a given name for the object, a name of the method and
	 * the arguments.
	 * 
	 * @author Kunshan Wang
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
	 * @throws DispatchException
	 *             Thrown if anything else went wrong except the dispatched
	 *             method itself throws an exception.
	 */
	public abstract Object call(String objectName, String methodName,
			Object[] args) throws NestedException, DispatchException;

}