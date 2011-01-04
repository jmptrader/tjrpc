package tjrpc.server;

public class Adder {
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