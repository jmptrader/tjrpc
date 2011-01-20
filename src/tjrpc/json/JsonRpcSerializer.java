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
package tjrpc.json;

import tjrpc.rpc.RpcRequest;
import tjrpc.rpc.RpcResponse;
import tjrpc.util.JsonUtils;

import com.sdicons.json.model.JSONArray;
import com.sdicons.json.model.JSONBoolean;
import com.sdicons.json.model.JSONObject;
import com.sdicons.json.model.JSONString;
import com.sdicons.json.model.JSONValue;

public class JsonRpcSerializer {
	
	public JSONObject requestToJson(RpcRequest request)
			throws IllegalArgumentException {
		try {
			JSONObject requestObj = new JSONObject();
			requestObj.getValue().put("object",
					JsonUtils.safeDecorate(request.getObject()));
			requestObj.getValue().put("method",
					JsonUtils.safeDecorate(request.getMethod()));
			JSONArray paramsArray = new JSONArray();
			for (Object param : request.getParams()) {
				paramsArray.getValue().add(JsonUtils.safeDecorate(param));
			}
			requestObj.getValue().put("params", paramsArray);
			return requestObj;
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Bad request", e);
		}
	}

	public RpcRequest jsonToRequest(JSONValue value)
			throws IllegalArgumentException {
		try {
			JSONObject rObj = (JSONObject) value;
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
			throw new IllegalArgumentException("Bad request", e);
		} catch (NullPointerException e) {
			throw new IllegalArgumentException(e);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Bad request", e);
		}
	}

	public JSONObject responseToJson(RpcResponse response)
			throws IllegalArgumentException {
		try {
			JSONObject requestObj = new JSONObject();

			if (response.isSuccess()) {
				requestObj.getValue().put("success", JSONBoolean.TRUE);
				requestObj.getValue().put("value",
						JsonUtils.safeDecorate(response.getValue()));

			} else {
				requestObj.getValue().put("success", JSONBoolean.FALSE);
				requestObj.getValue().put("exceptionClass",
						new JSONString(response.getExceptionClass()));
				requestObj.getValue().put("exceptionClass",
						new JSONString(response.getExceptionMessage()));
			}
			return requestObj;
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("Bad response", e);
		}
	}

	public RpcResponse jsonToResponse(JSONValue value)
			throws IllegalArgumentException {
		try {
			RpcResponse response = null;
			JSONObject rObj = (JSONObject) value;

			boolean success = ((JSONBoolean) rObj.get("success")).getValue();

			if (success) {
				Object returnValue = JsonUtils.safeStrip(rObj.get("value"));
				response = RpcResponse.normal(returnValue);

			} else {
				String exceptionClass = ((JSONString) rObj
						.get("exceptionClass")).getValue();
				String exceptionMessage = ((JSONString) rObj
						.get("exceptionMessage")).getValue();
				response = RpcResponse.error(exceptionClass, exceptionMessage);
			}
			return response;
		} catch (ClassCastException e) {
			throw new IllegalArgumentException(e);
		} catch (NullPointerException e) {
			throw new IllegalArgumentException(e);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
