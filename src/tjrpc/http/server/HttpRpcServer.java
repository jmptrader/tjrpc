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
package tjrpc.http.server;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.AbstractHandler;
import com.sdicons.json.model.JSONObject;
import com.sdicons.json.model.JSONValue;
import com.sdicons.json.parser.JSONParser;

import tjrpc.rpc.RpcRequest;
import tjrpc.rpc.RpcResponse;
import tjrpc.server.AbstractRpcServer;

public class HttpRpcServer extends AbstractRpcServer {
	private static final Logger logger = Logger.getLogger(HttpRpcServer.class);

	private Server httpServer;
	private int port;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public HttpRpcServer() {
	}

	public HttpRpcServer(int port) {
		this.port = port;
	}

	@Override
	public synchronized void start() {
		super.start();
		if (httpServer == null) {
			httpServer = new Server(port);
			httpServer.setHandler(handler);
		}
		try {
			httpServer.start();
		} catch (Exception e) {
			logger.error("Cannot start server", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public synchronized void stop() {
		super.stop();
		try {
			httpServer.stop();
		} catch (Exception e) {
			logger.error("Cannot stop server", e);
			throw new RuntimeException(e);
		}
	}

	private JsonRequestHandler handler = new JsonRequestHandler();

	private class JsonRequestHandler extends AbstractHandler {
		public void handle(String target, Request baseRequest,
				HttpServletRequest request, HttpServletResponse response)
				throws java.io.IOException, javax.servlet.ServletException {

			BufferedReader reader = request.getReader();
			JSONParser parser = new JSONParser(reader);
			JSONValue jsonRequest;
			RpcRequest rpcRequest;
			try {
				jsonRequest = parser.nextValue();
				rpcRequest = serializer.jsonToRequest(jsonRequest);
			} catch (Exception e) {
				baseRequest.setHandled(true);
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//				response.setContentType("text/plain");
//				response.setCharacterEncoding(request.getCharacterEncoding());
//				response.getWriter().println("Bad Request");
//				e.printStackTrace(response.getWriter());

				return;
			}
			RpcResponse rpcResponse = callableService.invoke(rpcRequest);
			JSONObject jsonResponse = serializer.responseToJson(rpcResponse);
			String stringResponse = jsonResponse.render(false);

			baseRequest.setHandled(true);
			response.setStatus(200);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(stringResponse);
		};
	}

}
