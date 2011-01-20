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
package tjrpc.http.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.sdicons.json.model.JSONObject;
import com.sdicons.json.model.JSONValue;
import com.sdicons.json.parser.JSONParser;

import tjrpc.client.AbstractRpcClient;
import tjrpc.client.ClientException;
import tjrpc.client.RemoteException;
import tjrpc.json.JsonRpcSerializer;
import tjrpc.rpc.RpcRequest;
import tjrpc.rpc.RpcResponse;

public class HttpRpcClient extends AbstractRpcClient {
	private String url = null;
	private JsonRpcSerializer serializer = new JsonRpcSerializer();
	private static final Charset utf8Charset = Charset.forName("UTF-8");

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HttpRpcClient() {
	}

	public HttpRpcClient(String url) {
		this.url = url;
	}

	public void close() {
	}

	@Override
	public Object call(String object, String method, Object[] args) {
		RpcRequest request = new RpcRequest(object, method, args);
		JSONObject jsonRequest = serializer.requestToJson(request);
		String stringRequest = jsonRequest.render(false);

		URL uurl;
		try {
			uurl = new URL(url);
		} catch (MalformedURLException e) {
			throw new ClientException("Malformed URL", e);
		}

		HttpURLConnection conn;

		try {
			conn = (HttpURLConnection) uurl.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "text/json; charset=UTF-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					conn.getOutputStream(), utf8Charset));

			writer.append(stringRequest);
			writer.flush();
		} catch (IOException e) {
			throw new ClientException("Error sending request to server", e);
		}

		RpcResponse response;
		try {
			int responseCode = conn.getResponseCode();

			if (responseCode != 200) {
				throw new ClientException("Server returns error status: "
						+ responseCode);
			}

			Reader reader;
			JSONValue jsonResponse;

			// TODO: Infer encoding from HTTP header.
			reader = new InputStreamReader(conn.getInputStream(),
					utf8Charset);
			jsonResponse = new JSONParser(reader).nextValue();

			response = serializer.jsonToResponse(jsonResponse);
		} catch (UnsupportedEncodingException e) {
			throw new ClientException("Error receiving response from server", e);
		} catch (IOException e) {
			throw new ClientException("Error receiving response from server", e);
		} catch (TokenStreamException e) {
			throw new ClientException("Bad response", e);
		} catch (RecognitionException e) {
			throw new ClientException("Bad response", e);
		} catch (IllegalArgumentException e) {
			throw new ClientException("Bad response", e);
		}

		if (!response.isSuccess()) {
			throw new RemoteException(response.getExceptionClass(),
					response.getExceptionMessage());
		}

		return response.getValue();
	}
}
