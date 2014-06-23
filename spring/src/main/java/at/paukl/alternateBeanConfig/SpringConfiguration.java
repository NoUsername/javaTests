package at.paukl.alternateBeanConfig;

import at.paukl.javatest.Config;
import at.paukl.javatest.DummyExecutor;
import at.paukl.javatest.DummyService;
import at.paukl.javatest.DummyServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Paul Klingelhuber
 */
@Configuration
public class SpringConfiguration {

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
