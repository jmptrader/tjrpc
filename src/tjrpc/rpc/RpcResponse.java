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

public class RpcResponse {
	private boolean success;
	private Object value;
	private String exceptionClass;
	private String exceptionMessage;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getExceptionClass() {
		return exceptionClass;
	}

	public void setExceptionClass(String exceptionClass) {
		this.exceptionClass = exceptionClass;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public static RpcResponse normal(Object value) {
		RpcResponse resp = new RpcResponse();
		resp.setSuccess(true);
		resp.setValue(value);
		resp.setExceptionClass(null);
		resp.setExceptionMessage(null);
		return resp;
	}

	public static RpcResponse error(String exceptionClass, String exceptionMessage) {
		RpcResponse resp = new RpcResponse();
		resp.setSuccess(false);
		resp.setValue(null);
		resp.setExceptionClass(exceptionClass);
		resp.setExceptionMessage(exceptionMessage);
		return resp;
	}

	public static RpcResponse error(Throwable e) {
		String exceptionClass = e.getClass().getCanonicalName();
		String exceptionMessage = e.getMessage();
		return error(exceptionClass, exceptionMessage);
	}
}