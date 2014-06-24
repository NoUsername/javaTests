package at.paukl.javatest;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Paul Klingelhuber
 */
@Service
public class SimpleMqClient implements ApplicationListener<ApplicationEvent>, MqttCallback {

	MqttClient mqttClient;
	ExecutorService reconnectWorker = Executors.newSingleThreadExecutor();
	ApplicationContext applicationContext;
	List<String> subscriptions = Lists.newArrayList();

	void connect() {
		String broker = "tcp://localhost:1883";
		String clientId = "SpringApp";
		MemoryPersistence persistence = new MemoryPersistence();
		try {
			mqttClient = new MqttClient(broker, clientId, persistence);
		} catch (MqttException e) {
			throw new RuntimeException("could not create mqtt client", e);
		}
		MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(true);
		System.out.println("Connecting to broker: "+broker);
		try {
			mqttClient.connect(connOpts);
		} catch (MqttException e) {
			throw new RuntimeException("could not connect to mqtt msgbus", e);
		}
		mqttClient.setCallback(this);
		for (String subscription : subscriptions) {
			registerInternal(subscription);
		}
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextStartedEvent) {
			applicationContext = ((ContextStartedEvent)event).getApplicationContext();
			connect();
		} else if (event instanceof PublishMqMessage) {
			PublishMqMessage msg = (PublishMqMessage)event;
			if (mqttClient.isConnected()) {
				try {
					mqttClient.publish(msg.topic, new MqttMessage(msg.msg.getBytes(Charsets.UTF_8)));
				} catch (MqttException e) {
					throw new RuntimeException("failed to send mqtt message", e);
				}
			}
		}
	}

	public void register(String topic) {
		subscriptions.add(topic);
		registerInternal(topic);
	}

	private void registerInternal(String topic) {
		if (mqttClient != null && mqttClient.isConnected()) {
			try {
				mqttClient.subscribe(topic);
			} catch (MqttException e) {
				throw new RuntimeException("", e);
			}
		}
	}

	@Override
	public void connectionLost(Throwable cause) {
		reconnectWorker.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(500);
					mqttClient.connect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		applicationContext.publishEvent(new MqMessage(this, topic, message));
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
	}

	public static class MqMessage extends ApplicationEvent {
		String topic;
		MqttMessage message;

		String getTopic() {
			return topic;
		}

		MqttMessage getMessage() {
			return message;
		}

		public MqMessage(Object source, String topic, MqttMessage message) {
			super(source);
			this.topic = topic;
			this.message = message;
		}
	}

	public static class PublishMqMessage extends ApplicationEvent {

		private String topic;
		private String msg;

		public String getTopic() {
			return topic;
		}

		public String getMsg() {
			return msg;
		}

		public PublishMqMessage(Object source, String topic, String msg) {
			super(source);
			this.topic = topic;
			this.msg = msg;
		}
	}
}

