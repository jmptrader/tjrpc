package tjrpc.client;

import java.io.IOException;
import java.net.UnknownHostException;

import tjrpc.http.client.HttpRpcClient;

public class HttpClientDemo {

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		RpcClient client = new HttpRpcClient("http://localhost:1245/");

		Object result = client.call("adder", "div", new Object[] { 3, 2 });
		System.out.println(result);

		Object result2 = client.call("closer", "close", new Object[] {});
		System.out.println(result2);

		client.close();
	}

}
