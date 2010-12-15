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
package tjrpc.server;

import java.io.*;
import java.net.*;

import com.sdicons.json.model.JSONObject;
import com.sdicons.json.model.JSONValue;

import tjrpc.channel.*;
import tjrpc.rpc.RpcException;
import tjrpc.rpc.RpcRequest;
import tjrpc.rpc.RpcResponse;
import tjrpc.rpc.RpcService;
import tjrpc.util.SimpleTcpServer;

public class SocketRpcServer extends ObjectDispatcher {

	private RpcService rpcService = new RpcService(this);
	private TCPServer server;
	
	public SocketRpcServer(int port) throws IOException {
		server = new TCPServer(port);
	}

	public SocketRpcServer(ServerSocket serverSocket) {
		server = new TCPServer(serverSocket);
	}
	
	public void start() {
		server.start();
	}

	public void stop() {
		server.stop();
	}

	private class TCPServer extends SimpleTcpServer {

		public TCPServer(int port) throws IOException {
			super(port);
		}

		public TCPServer(ServerSocket serverSocket) {
			super(serverSocket);
		}

		@Override
		public void handleClient(Socket clientSocket) {
			SocketJsonChannel channel = new SocketJsonChannel(clientSocket);
			try {
				while (true) {
					JSONValue jRequest = channel.read();
					if(jRequest == null) {
						// remote closed
						break;
					}
					RpcRequest request = RpcRequest.fromJson(jRequest);
					RpcResponse response = rpcService.invoke(request);
					JSONObject jResponse = response.toJson();
					channel.write(jResponse);
				}
			} catch (JsonIOException e) {
				// Connection broken
				e.printStackTrace();
			} catch (RpcException e) {
				// Bad request
				// Or a server-side object returned an un-convertable value. 
				e.printStackTrace();
			} finally {
				channel.close();
			}
		}
	}

}