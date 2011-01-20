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
package tjrpc.server;

import tjrpc.dispatch.DispatchException;
import tjrpc.dispatch.NestedException;
import tjrpc.dispatch.ObjectDispatcher;
import tjrpc.dispatch.ObjectDispatcherImpl;
import tjrpc.json.JsonRpcSerializer;
import tjrpc.rpc.CallableService;

public class AbstractRpcServer implements RpcServer {

	private ObjectDispatcher objectDispatcher = new ObjectDispatcherImpl();
	protected CallableService callableService = new CallableService(
			objectDispatcher);
	protected JsonRpcSerializer serializer = new JsonRpcSerializer();

	public AbstractRpcServer() {
		super();
	}

	@Override
	public Object addObject(String name, Object object) {
		return objectDispatcher.addObject(name, object);
	}

	@Override
	public Object removeObject(String name) {
		return objectDispatcher.removeObject(name);
	}

	@Override
	public Object call(String objectName, String methodName, Object[] args)
			throws NestedException, DispatchException {
		return objectDispatcher.call(objectName, methodName, args);
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}

}