package at.paukl.javatest;

public class Main {

	public static void main(String[] args) {
		System.out.println("starting...");
		Config config = new Config();
		config.service = new DummyServiceImpl();
		new DummyExecutor(config).run();
	}

}