/**
 * Copyright 2010 Kunshan Wang
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import tjrpc.rpc.RpcRequest;
import tjrpc.rpc.RpcResponse;

public class ObjectDispatcher implements IObjectDispatcher {
	private Map<String, Object> objects = new HashMap<String, Object>();

	@Override
	public Object addObject(String name, Object object) {
		return objects.put(name, object);
	}

	@Override
	public Object removeObject(String name) {
		return objects.remove(name);
	}

	@Override
	public Object call(String objectName, String methodName, Object[] args)
			throws NestedException, ObjectDispatcherException {

		Object obj = objects.get(objectName);
		if (obj == null) {
			throw new IllegalArgumentException("No such object: " + objectName);
		}

		Method method = null;
		Method[] methods = obj.getClass().getMethods();
		for (Method m : methods) {
			if (m.getName().equals(methodName)) {
				method = m;
			}
		}
		if (method == null) {
			throw new IllegalArgumentException("No such method: " + methodName);
		}

		Object returnValue;
		try {
			returnValue = method.invoke(obj, args);
		} catch (InvocationTargetException e) {
			throw new NestedException(e.getTargetException());
		} catch (Exception e) {
			throw new ObjectDispatcherException(e);
		}

		return returnValue;
	}

	@Override
	public RpcResponse invoke(RpcRequest request) {
		RpcResponse returnResponse = null;
		try {
			Object returnValue = call(request.getObject(), request.getMethod(),
					request.getParams());
			returnResponse = RpcResponse.normal(returnValue);
		} catch (Exception e) {
			returnResponse = RpcResponse.error(e.getMessage());
		}
		return returnResponse;
	}
}