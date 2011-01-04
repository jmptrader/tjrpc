package tjrpc.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import tjrpc.simpletcp.channel.SocketJsonChannel;
import tjrpc.simpletcp.client.ClientAgent;

public class ClientDemo {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("127.0.0.1",1244);
		SocketJsonChannel channel = new SocketJsonChannel(socket);
		ClientAgent agent = new ClientAgent();
		agent.setChannel(channel);
		
		Object result = agent.call("adder", "div", new Object[]{3,2});
		System.out.println(result);

		Object result2 = agent.call("closer", "close", new Object[]{});
		System.out.println(result2);
	}

}
