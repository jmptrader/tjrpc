package tjrpc.server;

import tjrpc.server.SocketRpcServer;

class Adder {
	public int add(int a, int b) {
		return a + b;
	}
	
	public int sub(int a, int b) {
		throw new RuntimeException("Adder says 'sub' method is not implemented.");
	}
	
	public int div(int a, int b) {
		return a / b; // Try dividing by 0.
	}
}

public class ServerDemo {

	/**
	 * @author Kunshan Wang
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		SocketRpcServer svr = new SocketRpcServer(1244);
		
		Adder adder = new Adder();
		svr.addObject("adder", adder);

		svr.start();

	}
}
