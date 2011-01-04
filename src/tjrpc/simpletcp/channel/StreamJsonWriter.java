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

import java.io.*;

import com.sdicons.json.model.JSONValue;

public class StreamJsonWriter implements JsonWriter {
	private Writer writer;
	
	public StreamJsonWriter(Writer writer) {
		this.writer = writer;
	}

	@Override
	public void close() {
		try {
			writer.close();
		} catch (IOException e) {
			throw new JsonIOException(e);
		}
	}

	@Override
	public void write(JSONValue value) {
		try {
			writer.write(value.render(false)+" ");
			writer.flush();
		} catch (IOException e) {
			throw new JsonIOException(e);
		}
	}

}