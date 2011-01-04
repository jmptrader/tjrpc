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
package tjrpc.simpletcp.channel;

import java.net.Socket;
import java.io.*;

import com.sdicons.json.model.JSONValue;

public class SocketJsonChannel implements JsonChannel {
	private Socket socket;
	private JsonReader reader;
	private JsonWriter writer;

	public static final String CHARSET = "UTF-8";

	public SocketJsonChannel(Socket socket) {
		this.socket = socket;
		try {
			this.reader = new StreamJsonReader(new BufferedReader(
					new InputStreamReader(socket.getInputStream(), CHARSET)));
			this.writer = new StreamJsonWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream(), CHARSET)));
		} catch (UnsupportedEncodingException e) {
			// This is impossible
			throw new Error("UTF-8 should always be supported");
		} catch (IOException e) {
			throw new JsonIOException(e);
		}
	}

	@Override
	public JSONValue read() {
		return reader.read();
	}

	@Override
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			throw new JsonIOException(e);
		}
	}

	@Override
	public void write(JSONValue value) {
		writer.write(value);
	}

}