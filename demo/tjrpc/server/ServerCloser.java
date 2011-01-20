package tjrpc.server;


public class ServerCloser {
	private final RpcServer server;

	public ServerCloser(RpcServer server) {
		this.server = server;
	}
	
	public void close() {
		new Thread() {
			@Override
			public void run() {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
				}
				System.out.println("Trying to close server...");
				ServerCloser.this.server.stop();
				System.out.println("Done.");
			}
		}.start();
	}
}