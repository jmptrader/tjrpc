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
package tjrpc.client;

import java.lang.reflect.Proxy;


public abstract class AbstractRpcClient implements RpcClient {

	@Override
	public <T> T createProxy(String objectName, Class<T> iface) {
		ProxyHelper helper = new ProxyHelper();
		helper.setClient(this);
		helper.setObjectName(objectName);
		@SuppressWarnings("unchecked")
		T proxy = (T) Proxy.newProxyInstance(iface.getClassLoader(),
				new Class<?>[] { iface }, helper);
		return proxy;
	}

}