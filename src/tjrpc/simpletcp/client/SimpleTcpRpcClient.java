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

import java.net.Socket;

import tjrpc.client.AbstractRpcClient;
import tjrpc.client.ClientException;
import tjrpc.client.RemoteException;
import tjrpc.json.JsonRpcSerializer;
import tjrpc.rpc.RpcRequest;
import tjrpc.rpc.RpcResponse;
import tjrpc.simpletcp.channel.SocketJsonChannel;

public class SimpleTcpRpcClient extends AbstractRpcClient {

	SocketJsonChannel channel;
	private JsonRpcSerializer serializer = new JsonRpcSerializer();

	public SimpleTcpRpcClient(Socket socket) {
		this.channel = new SocketJsonChannel(socket);
	}

	public SimpleTcpRpcClient(String host, int port) {
		Socket socket;
		try {
			socket = new Socket(host, port);
		} catch (Exception e) {
			throw new ClientException(e);
		}
		this.channel = new SocketJsonChannel(socket);
	}

	public void close() {
		channel.close();
	}

	@Override
	public Object call(String object, String method, Object[] args) {
		RpcRequest req = new RpcRequest(object, method, args);
		channel.write(serializer.requestToJson(req));
		RpcResponse resp = serializer.jsonToResponse(channel.read());

		if (!resp.isSuccess()) {
			throw new RemoteException(resp.getExceptionClass(),
					resp.getExceptionMessage());
		}

		return resp.getValue();
	}
}
