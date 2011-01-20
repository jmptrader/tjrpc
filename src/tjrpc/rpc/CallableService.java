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
package tjrpc.rpc;

import tjrpc.dispatch.Callable;
import tjrpc.dispatch.NestedException;

/**
 * @author Kunshan Wang
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
