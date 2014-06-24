package at.paukl.javatest;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

//@Configuration
public class Main {

	public static void main(String[] args) {
		System.out.println("starting jetty with spring webmvc test ...");

		AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
		webApplicationContext.setConfigLocation("at.paukl.javatest.web");
		webApplicationContext.refresh();

		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext("at.paukl.javatest.nonWeb");
		webApplicationContext.setParent(context);

		// if context.start is called also, the "contextStarted" events occur twice!
		//context.start();
		webApplicationContext.start();
	}

}