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

import com.sdicons.json.model.JSONArray;
import com.sdicons.json.model.JSONObject;
import com.sdicons.json.model.JSONString;
import com.sdicons.json.model.JSONValue;

public class RpcRequest {
	private String object;
	private String method;
	private Object[] params;

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
	
	public RpcRequest() {
		super();
	}

	public RpcRequest(String object, String method, Object[] params) {
		super();
		this.object = object;
		this.method = method;
		this.params = params;
	}

	public JSONObject toJson() {
		JSONObject requestObj = new JSONObject();
		requestObj.getValue().put("object", JsonUtils.safeDecorate(object));
		requestObj.getValue().put("method", JsonUtils.safeDecorate(method));
		JSONArray paramsArray = new JSONArray();
		for (Object param : params) {
			paramsArray.getValue().add(JsonUtils.safeDecorate(param));
		}
		requestObj.getValue().put("params", paramsArray);
		return requestObj;
	}

	public static RpcRequest fromJson(JSONValue request) {
		try {
			JSONObject rObj = (JSONObject) request;
			JSONString jObj = (JSONString) rObj.get("object");
			JSONString jMet = (JSONString) rObj.get("method");
			JSONArray jPar = (JSONArray) rObj.get("params");

			String object = (String) JsonUtils.safeStrip(jObj);
			String method = (String) JsonUtils.safeStrip(jMet);
			Object[] params = new Object[jPar.size()];
			for (int i = 0; i < jPar.size(); i++) {
				params[i] = JsonUtils.safeStrip(jPar.get(i));
			}

			RpcRequest req = new RpcRequest();
			req.setObject(object);
			req.setMethod(method);
			req.setParams(params);
			return req;
		} catch (ClassCastException e) {
			throw new RpcException("Bad Wks-Json-Rpc request", e);
		} catch (IllegalArgumentException e) {
			throw new RpcException("Bad Wks-Json-Rpc request", e);
		}
	}

}