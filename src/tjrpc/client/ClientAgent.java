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
package tjrpc.client;

import tjrpc.channel.JsonChannel;
import tjrpc.rpc.RpcCallable;
import tjrpc.rpc.RpcRequest;
import tjrpc.rpc.RpcResponse;

public class ClientAgent implements RpcCallable {

	private JsonChannel channel;

	public JsonChannel getChannel() {
		return channel;
	}

	public void setChannel(JsonChannel channel) {
		this.channel = channel;
	}

	@Override
	public Object call(String object, String method, Object[] args) {
		RpcRequest req = new RpcRequest(object, method, args);
		channel.write(req.toJson());
		RpcResponse resp = RpcResponse.fromJson(channel.read());

		if (resp.getError() != null) {
			throw new RemoteException(resp.getError());
		}

		return resp.getValue();
	}

}