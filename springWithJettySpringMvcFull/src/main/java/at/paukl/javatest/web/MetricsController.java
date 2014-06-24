package at.paukl.javatest.web;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.SortedMap;

/**
 * @author Paul Klingelhuber
 */
@Controller
public class MetricsController {

	@Autowired
	MetricRegistry metricRegistry;

	@RequestMapping("/metrics")
	@ResponseBody
	public String metrics() {
		StringBuilder result = new StringBuilder();
		SortedMap<String,Counter> counters = metricRegistry.getCounters();
		for (Map.Entry<String, Counter> entry : counters.entrySet()) {
			result.append(entry.getKey());
			result.append(" = ");
			result.append(entry.getValue().getCount());
			result.append("<br/>\n");
		}
		return result.toString();
	}
}
