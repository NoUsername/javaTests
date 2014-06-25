package at.paukl.javatest;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("at.paukl.javatest")
@Configuration
public class Main {

	public static void main(String[] args) {
		System.out.println("starting spring with mq test ...");
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
		context.start();
        System.out.println("Started, to see output, publish messages to the topic 'bar' on your local mqtt mq, e.g.:");
        System.out.println("mosquitto_pub -t \"bar\" -m \"hello there\"");
	}

}