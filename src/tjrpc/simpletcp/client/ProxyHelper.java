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
package tjrpc.simpletcp.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHelper implements InvocationHandler {
	private ClientAgent clientAgent;
	private String objectName;

	public ClientAgent getClientAgent() {
		return clientAgent;
	}

	public void setClientAgent(ClientAgent clientAgent) {
		this.clientAgent = clientAgent;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		return clientAgent.call(objectName, method.getName(), args);
	}
	
	public void close() {
		clientAgent.getChannel().close();
	}

}