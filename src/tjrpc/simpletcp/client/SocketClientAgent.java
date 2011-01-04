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
package tjrpc.simpletcp.client;

import java.net.Socket;

import tjrpc.simpletcp.channel.SocketJsonChannel;

public class SocketClientAgent extends ClientAgent {

	public SocketClientAgent(Socket socket) {
		this.setChannel(new SocketJsonChannel(socket));
	}

	public SocketClientAgent(String host, int port) {
		Socket socket;
		try {
			socket = new Socket(host, port);
		} catch (Exception e) {
			throw new ClientException(e);
		}
		this.setChannel(new SocketJsonChannel(socket));
	}

	public void close() {
		getChannel().close();
	}

}