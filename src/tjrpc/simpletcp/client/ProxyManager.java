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

import java.lang.reflect.Proxy;

public class ProxyManager {
	public static <IFace> IFace newProxy(String host, int port, String objectName, Class<IFace> iface) {
		SocketClientAgent agent = new SocketClientAgent(host, port);
		ProxyHelper helper = new ProxyHelper();
		helper.setClientAgent(agent);
		helper.setObjectName(objectName);
		
		@SuppressWarnings("unchecked")
		IFace proxy = (IFace) Proxy.newProxyInstance(iface.getClassLoader(), new Class<?>[]{iface}, helper);
		return proxy;
	}
	
	public static void closeProxy(Object proxy) {
		ProxyHelper helper = (ProxyHelper) Proxy.getInvocationHandler(proxy);
		helper.close();
	}
}