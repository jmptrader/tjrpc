package tjrpc.server;

import tjrpc.http.server.HttpRpcServer;

public class HttpServerDemo {

	/**
	 * @author Kunshan Wang
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		RpcServer svr = new HttpRpcServer(1244);
		
		Adder adder = new Adder();
		svr.addObject("adder", adder);
		
		ServerCloser serverCloser = new ServerCloser(svr);
		svr.addObject("closer", serverCloser);

		svr.start();

	}
}
