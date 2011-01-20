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

import tjrpc.rpc.RpcService;

/**
 * The interface to the {@link ObjectDispatcherImpl} class. It exists so that other
 * class can masquerade this object by implement this interface and delegate its
 * methods to a nested {@link ObjectDispatcherImpl} object.
 * 
 * @author Kunshan Wang
 * 
 */
public interface ObjectDispatcher extends Callable {

	public abstract Object addObject(String name, Object object);

	public abstract Object removeObject(String name);

}