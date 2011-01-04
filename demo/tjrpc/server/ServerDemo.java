package tjrpc.server;

import tjrpc.simpletcp.server.SocketRpcServer;

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
		
		ServerCloser serverCloser = new ServerCloser(svr);
		svr.addObject("closer", serverCloser);

		svr.start();

	}
}
