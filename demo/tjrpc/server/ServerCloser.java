package tjrpc.server;

import tjrpc.simpletcp.server.SocketRpcServer;

public class ServerCloser {
	private final SocketRpcServer server;

	public ServerCloser(SocketRpcServer server) {
		this.server = server;
	}
	
	public void close() {
		System.out.println("Trying to close server...");
		this.server.stop();
		System.out.println("Done.");
	}
}