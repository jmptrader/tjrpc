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
package tjrpc.simpletcp.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public abstract class SimpleTcpServer {
	private static final Logger logger = LogManager
			.getLogger(SimpleTcpServer.class);

	private ServerSocket serverSocket;
	private ServerThread serverThread;

	private ConcurrentHashMap<ClientThread, ClientThread> clientThreads = new ConcurrentHashMap<ClientThread, ClientThread>();

	public SimpleTcpServer(ServerSocket serverSocket) {
		super();
		this.serverSocket = serverSocket;
		this.serverThread = new ServerThread();
	}

	public SimpleTcpServer(int port) throws IOException {
		super();
		this.serverSocket = new ServerSocket(port);
		this.serverThread = new ServerThread();
	}

	public void start() {
		serverThread.start();
	}

	public void stop() {
		logger.debug("Closing server socket...");
		try {
			serverSocket.close();
		} catch (IOException e) {
		}
		logger.debug("Interrupting server thread...");
		serverThread.interrupt();
		logger.debug("Interrupted.");
		synchronized (clientThreads) {
			for (ClientThread clientThread : clientThreads.keySet()) {
				try {
					clientThread.clientSocket.close();
				} catch (IOException e) {
				}
				clientThread.interrupt();
			}
		}
		clientThreads.clear();
	}

	private void serveAll() {
		while (!Thread.interrupted()) {
			Socket clientSocket;
			try {
				logger.debug("Accepting...");
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				logger.debug("Server socket closed");
				break;
			}
			logger.debug("Accepted.");
			ClientThread clientThread = new ClientThread(clientSocket);
			clientThreads.put(clientThread, clientThread);
			clientThread.start();
		}
		logger.debug("Exiting....");
	}

	public abstract void handleClient(Socket clientSocket);

	private class ServerThread extends Thread {
		@Override
		public void run() {
			serveAll();
		}
	}

	private class ClientThread extends Thread {
		private Socket clientSocket;

		public ClientThread(Socket clientSocket) {
			super();
			this.clientSocket = clientSocket;
		}

		@Override
		public void run() {
			try {
				handleClient(clientSocket);
			} finally {
				if (!clientSocket.isClosed()) {
					try {
						clientSocket.close();
					} catch (IOException e) {
					}
				}
			}
			clientThreads.remove(this);
		}
	}
}