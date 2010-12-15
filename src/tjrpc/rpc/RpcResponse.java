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
package tjrpc.rpc;

import tjrpc.util.JsonUtils;

import com.sdicons.json.model.JSONNull;
import com.sdicons.json.model.JSONObject;
import com.sdicons.json.model.JSONString;
import com.sdicons.json.model.JSONValue;

public class RpcResponse {
	private Object value;
	private String error;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public RpcResponse() {
		super();
	}

	public RpcResponse(Object value, String error) {
		super();
		this.value = value;
		this.error = error;
	}

	public JSONObject toJson() {
		JSONObject responseObj = new JSONObject();
		responseObj.getValue().put("value", JsonUtils.safeDecorate(value));
		responseObj.getValue().put("error", JsonUtils.safeDecorate(error));
		return responseObj;
	}

	public static RpcResponse fromJson(JSONValue response) {
		try {
			JSONObject rObj = (JSONObject) response;

			JSONValue jVal = rObj.get("value");
			JSONValue jErrVal = rObj.get("error");
			
			JSONValue jErr = JSONNull.NULL;
			if (!jErrVal.isNull()) {
				jErr = (JSONString) jErrVal;
			}

			Object value = JsonUtils.safeStrip(jVal);
			String error = (String) JsonUtils.safeStrip(jErr);

			RpcResponse resp = new RpcResponse();
			resp.setValue(value);
			resp.setError(error);
			return resp;
		} catch (ClassCastException e) {
			throw new RpcException("Bad Wks-Json-Rpc response", e);
		} catch (IllegalArgumentException e) {
			throw new RpcException("Bad Wks-Json-Rpc response", e);
		}
	}

	public static RpcResponse normal(Object value) {
		RpcResponse resp = new RpcResponse();
		resp.setValue(value);
		resp.setError(null);
		return resp;
	}

	public static RpcResponse error(String error) {
		RpcResponse resp = new RpcResponse();
		resp.setValue(null);
		resp.setError(error);
		return resp;
	}
}