package at.paukl.javatest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("at.paukl.javatest")
@Configuration
public class Main {

	public static void main(String[] args) {
		System.out.println("starting spring test...");
		ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
		// or without componentscan:
		//ApplicationContext context = new AnnotationConfigApplicationContext(at.paukl.alternateBeanConfig.SpringConfiguration.class);
		DummyExecutor executor = (DummyExecutor)context.getBean("dummyExecutor");
		executor.run();
	}

}