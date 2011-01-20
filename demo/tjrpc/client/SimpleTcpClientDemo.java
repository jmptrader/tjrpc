package tjrpc.client;

import java.io.IOException;
import java.net.UnknownHostException;

import tjrpc.simpletcp.client.SimpleTcpRpcClient;

public class SimpleTcpClientDemo {

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		RpcClient client = new SimpleTcpRpcClient("127.0.0.1", 1244);

		Object result = client.call("adder", "div", new Object[] { 3, 2 });
		System.out.println(result);

		Object result2 = client.call("closer", "close", new Object[] {});
		System.out.println(result2);

		client.close();
	}

}
