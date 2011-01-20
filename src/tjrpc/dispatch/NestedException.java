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
package tjrpc.dispatch;

import java.lang.reflect.InvocationTargetException;

/**
 * Thrown if the dispatched method throws an exception. It is an unchecked
 * {@link RuntimeException}-based alternative to the checked
 * {@link InvocationTargetException} exception.
 * 
 * @author Kunshan Wang
 * 
 */
public class NestedException extends RuntimeException {

	private static final long serialVersionUID = -1914866603140468233L;

	private Throwable nestedThrowable;

	public Throwable getNestedThrowable() {
		return nestedThrowable;
	}

	public NestedException(Throwable nestedThrowable) {
		super();
		this.nestedThrowable = nestedThrowable;
	}
}
