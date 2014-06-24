package at.paukl.javatest.nonWeb;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Paul Klingelhuber
 */
@Service
public class MoneyService {

	AtomicLong money = new AtomicLong();

	public long changeBalance(Long amount) {
		return money.addAndGet(amount);
	}

	public String getStockValue(String name) {
		return "WAT?";
	}

}
