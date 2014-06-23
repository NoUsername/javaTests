package at.paukl.javatest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Paul Klingelhuber
 */
@Service
public class DummyExecutor implements ApplicationListener<ApplicationEvent> {

	Config config;
	AtomicBoolean wasStarted = new AtomicBoolean(false);

	@Autowired
	public void setConfig(Config config) {
		this.config = config;
	}

	public void run() {
		if (wasStarted.compareAndSet(false, true)) {
			while (config.keepRunning) {
				String message = RandomStringUtils.randomAlphanumeric(30);
				config.service.handleMessage(message);
			}
		}
	}

	public void startAsync() {
		if (wasStarted.get() == false) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					DummyExecutor.this.run();
				}
			}).start();
		}
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		System.out.println("event=" + event.getClass().getSimpleName());
		if (event instanceof ContextStartedEvent) {
			startAsync();
		}
	}
}
