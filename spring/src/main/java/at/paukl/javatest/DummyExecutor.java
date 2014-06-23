package at.paukl.javatest;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Paul Klingelhuber
 */
public class DummyExecutor {

	Config config;

	public DummyExecutor() {
	}

	@Autowired
	public void setConfig(Config config) {
		this.config = config;
	}

	public void run() {
		while (config.keepRunning) {
			String message = RandomStringUtils.randomAlphanumeric(30);
			config.service.handleMessage(message);
		}
	}

}
