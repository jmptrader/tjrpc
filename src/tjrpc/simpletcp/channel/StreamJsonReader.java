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

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.sdicons.json.model.JSONValue;
import com.sdicons.json.parser.JSONParser;

public class StreamJsonReader implements JsonReader {
	private Reader reader;
	private JSONParser parser;
	
	public StreamJsonReader(Reader reader) {
		this.reader = reader;
		this.parser = new JSONParser(reader);
	}

	@Override
	public void close() {
		try {
			reader.close();
		} catch (IOException e) {
			throw new JsonIOException(e);
		}
	}

	@Override
	public JSONValue read() {
		try {
			return parser.nextValue();
		} catch (TokenStreamException e) {
			throw new JsonIOException(e);
		} catch (RecognitionException e) {
			return null;
		}
	}
}