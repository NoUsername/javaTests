package at.paukl.javatest.nonWeb;

import org.springframework.stereotype.Service;

/**
 * @author Paul Klingelhuber
 */
@Service
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
