package at.paukl.javatest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;

/**
 * @author Paul Klingelhuber
 */
@Service
public class SimpleMqUser implements ApplicationListener<ApplicationEvent> {

	@Autowired
	SimpleMqClient mqClient;

	@Autowired
	ApplicationContext applicationContext;

	@PostConstruct
	void init() {
		mqClient.register("/toJava");
		mqClient.register("/foo");
	}

	void busySend() {
		while (true) {
			applicationContext.publishEvent(new SimpleMqClient.PublishMqMessage(Main.class, "/toWorld", "hello from java @ " + System.currentTimeMillis()));
			try {
				Thread.sleep(500);
			} catch (InterruptedException ignored) {}
		}
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextStartedEvent) {
			System.out.println("starting mq sender");
			Executors.newSingleThreadExecutor().execute(new Runnable() {
				public void run() {
					busySend();
				}
			});
		} else if (event instanceof SimpleMqClient.MqMessage) {
			SimpleMqClient.MqMessage message = (SimpleMqClient.MqMessage) event;
			System.out.println("received mq event on " + message.topic + " msg: " + message.message);
		}
	}

}
