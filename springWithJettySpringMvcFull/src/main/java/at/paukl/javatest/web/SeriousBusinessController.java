package at.paukl.javatest.web;

import at.paukl.javatest.nonWeb.MoneyService;
import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;

/**
 * @author Paul Klingelhuber
 */
@Controller
@RequestMapping("/money")
public class SeriousBusinessController {

	@Autowired
	public SeriousBusinessController(MetricRegistry metricRegistry) {
		depositCounter = metricRegistry.counter(MetricRegistry.name(SeriousBusinessController.class, "deposit"));
		stockCounter = metricRegistry.counter(MetricRegistry.name(SeriousBusinessController.class, "stock"));
	}

	private final Counter depositCounter, stockCounter;

	@Autowired
	MoneyService moneyService;

	@RequestMapping("/deposit/{amount}")
	@ResponseBody
	public String deposit(@PathVariable("amount") Integer amount) {
		depositCounter.inc();
		if (amount < 0) {
			return "error, can only deposit positive values";
		}
		return String.format("thank you, current balance is %s", moneyService.changeBalance(amount.longValue()));
	}

	@RequestMapping("/stock/{name}")
	@ResponseBody
	public String getStockValue(@PathVariable("name") String name) {
		stockCounter.inc();
		return moneyService.getStockValue(name);
	}

}
