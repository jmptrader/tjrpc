package tjrpc.client;

import tjrpc.simpletcp.client.SimpleTcpRpcClient;

interface IAdder {
	public int add(int a, int b);
}

public class SimpleTcpClientReflectionDemo {

	public static void main(String[] args) {
		SimpleTcpRpcClient client = new SimpleTcpRpcClient("127.0.0.1", 1244);
		IAdder adderProxy = client.createProxy("adder", IAdder.class);

		int invocationCount = 0;
		long startTime = System.currentTimeMillis();

		try {
			while (true) {
				int a = (int) (Math.random() * 100);
				int b = (int) (Math.random() * 100);
				int result = adderProxy.add(a, b);
				invocationCount++;
				if (invocationCount % 10000 == 0) {
					long currentTime = System.currentTimeMillis();
					long elapsedTime = currentTime - startTime;
					double ips = (double) invocationCount * 1000 / elapsedTime;
					System.out.format("Last result: %d + %d = %d\n", a, b,
							result);
					System.out.format(
							"%d invocations in %d milliseconds. %f i/s\n",
							invocationCount, elapsedTime, ips);
				}
			}
		} finally {
			client.close();
		}
	}

}
