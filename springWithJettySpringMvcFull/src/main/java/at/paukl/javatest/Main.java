package at.paukl.javatest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class Main {

	static Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		log.info("starting jetty with spring webmvc test ...");

		AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
		webApplicationContext.setConfigLocation("at.paukl.javatest");
		webApplicationContext.refresh();

		//ConfigurableApplicationContext context = new AnnotationConfigApplicationContext("at.paukl.javatest.nonWeb");
		//webApplicationContext.setParent(context);

		// if context.start is called also, the "contextStarted" events occur twice!
		//context.start();
		webApplicationContext.start();
	}

}