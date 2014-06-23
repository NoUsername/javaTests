package at.paukl.javatest;

/**
 * @author Paul Klingelhuber
 */
public class DummyServiceImpl implements DummyService {

	@Override
	public void handleMessage(String message) {
		System.out.println("received message: " + message);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ignored) {
		}
	}
}
