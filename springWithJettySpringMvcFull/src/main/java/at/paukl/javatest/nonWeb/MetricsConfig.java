package at.paukl.javatest.nonWeb;

import com.codahale.metrics.MetricRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Paul Klingelhuber
 */
@Configuration
public class MetricsConfig {

	@Bean
	public MetricRegistry metricsRegistry() {
		return new MetricRegistry();
	}
}
