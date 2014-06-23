package at.paukl.javatest;

import com.google.inject.Inject;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Paul Klingelhuber
 */
public class DummyExecutor {

	Config config;

	@Inject
	public DummyExecutor(Config config) {
		this.config = config;
	}

	public void run() {
		while (config.keepRunning) {
			String message = RandomStringUtils.randomAlphanumeric(30);
			config.service.handleMessage(message);
		}
	}

}
