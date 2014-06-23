package at.paukl.javatest;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("at.paukl.javatest")
public class Main {

	public static void main(String[] args) {
		System.out.println("starting...");
		ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
		context.start();
	}

}