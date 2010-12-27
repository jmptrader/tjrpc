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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public abstract class SimpleTcpServer {
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
		System.out.println("Closing server socket...");
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Interrupting server thread...");
		serverThread.interrupt();
		System.out.println("Interrupted.");
		synchronized (clientThreads) {
			for (ClientThread clientThread : clientThreads.keySet()) {
				try {
					clientThread.clientSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
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
				System.out.println("Accepting...");
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
			System.out.println("Accept broken.");
			ClientThread clientThread = new ClientThread(clientSocket);
			clientThreads.put(clientThread, clientThread);
			clientThread.start();
		}
		System.out.println("Exiting....");
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
						e.printStackTrace();
					}
				}
			}
			clientThreads.remove(this);
		}
	}
}