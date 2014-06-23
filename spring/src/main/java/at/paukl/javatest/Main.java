package at.paukl.javatest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Main {

	public static void main(String[] args) {
		System.out.println("starting spring test...");
		ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
		DummyExecutor executor = (DummyExecutor)context.getBean("dummyExecutor");
		executor.run();
	}

	@Bean
	DummyExecutor dummyExecutor(Config config) {
		DummyExecutor executor = new DummyExecutor();
		executor.setConfig(config);
		return executor;
	}

	@Bean Config config(DummyService dummyService) {
		Config config = new Config();
		config.service = dummyService;
		return config;
	}

	@Bean DummyService dummyService() {
		return new DummyServiceImpl();
	}

}