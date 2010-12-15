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
package tjrpc.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import com.sdicons.json.model.*;

public class JsonUtils {
	public static Object recursiveGetValue(JSONValue value) {
		if (value instanceof JSONString) {
			return ((JSONString) value).getValue();
		} else if (value instanceof JSONInteger) {
			return ((JSONInteger) value).getValue().intValue();
		} else if (value instanceof JSONDecimal) {
			return ((JSONDecimal) value).getValue().doubleValue();
		} else if (value instanceof JSONBoolean) {
			return ((JSONBoolean) value).getValue();
		} else if (value instanceof JSONArray) {
			ArrayList<Object> rv = new ArrayList<Object>();
			for (JSONValue value2 : ((JSONArray) value).getValue()) {
				rv.add(recursiveGetValue(value2));
			}
			return rv;
		} else if (value instanceof JSONObject) {
			HashMap<String, Object> rv = new HashMap<String, Object>();
			HashMap<String, JSONValue> valueAsMap = ((JSONObject) value).getValue();
			for (String key2 : valueAsMap.keySet()) {
				JSONValue value2 = valueAsMap.get(key2);
				rv.put(key2, recursiveGetValue(value2));
			}
			return rv;
		}
		
		throw new IllegalArgumentException();
	}
	

	public static Object safeStrip(JSONValue jsonValue) {
		if (jsonValue.isNull() || jsonValue.isBoolean() || jsonValue.isString()) {
			return jsonValue.strip();
		} else if (jsonValue.isInteger()) {
			return ((BigInteger) jsonValue.strip()).intValue();
		} else if (jsonValue.isDecimal()) {
			return ((BigDecimal) jsonValue.strip()).doubleValue();
		} else {
			throw new IllegalArgumentException(jsonValue.getClass()
					.getSimpleName() + " is not allowed.");
		}
	}

	public static JSONValue safeDecorate(Object object) {
		if (object instanceof Integer || object instanceof Long
				|| object instanceof Byte || object instanceof Short) {
			object = BigInteger.valueOf(((Number) object).longValue());
		} else if (object instanceof Double || object instanceof Float) {
			object = BigDecimal.valueOf(((Number) object).doubleValue());
		}

		if (object == null) {
			return new JSONNull();
		} else if (object instanceof Boolean) {
			return object.equals(Boolean.TRUE) ? JSONBoolean.TRUE
					: JSONBoolean.FALSE;
		} else if (object instanceof BigDecimal) {
			return new JSONDecimal((BigDecimal) object);
		} else if (object instanceof BigInteger) {
			return new JSONInteger((BigInteger) object);
		} else if (object instanceof String) {
			return new JSONString((String) object);
		} else {
			throw new IllegalArgumentException(object.getClass()
					.getSimpleName() + " is not allowed.");
		}
	}
}