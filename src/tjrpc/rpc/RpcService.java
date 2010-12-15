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
package tjrpc.rpc;

/**
 * Wks-Json-Rpc Protocol
 * <p>
 * Request: {"object": "a string", "method": "a string", "params": ["a",
 * "list"]}
 * <dl>
 * <dt>object
 * <dd>An arbitrary string that uniquely identifies an object.
 * <dt>method
 * <dd>The method name.
 * <dt>params
 * <dd>A list of params.
 * </dl>
 * <p>
 * Response: {"value": "return value", "error": "message"}
 * <dl>
 * <dt>value
 * <dd>The return value
 * <dt>error
 * <dd>The error message (string) when errors occur. none when okay.
 * </dl>
 * <p>
 * Params and return values are restricted to number, string, boolean, none.
 * 
 * @author Kunshan Wang
 * 
 */
public class RpcService {

	private RpcCallable callable;

	public RpcCallable getCallable() {
		return callable;
	}

	public void setCallable(RpcCallable callable) {
		this.callable = callable;
	}

	public RpcService(RpcCallable callable) {
		super();
		this.callable = callable;
	}

	public RpcResponse invoke(RpcRequest request) {

		RpcResponse response = null;

		try {
			Object returnValue = callable.call(request.getObject(),
					request.getMethod(), request.getParams());
			response = RpcResponse.normal(returnValue);
		} catch (Throwable e) {
			e.printStackTrace();
			response = RpcResponse.error(e.getMessage());
		}

		return response;
	}

}