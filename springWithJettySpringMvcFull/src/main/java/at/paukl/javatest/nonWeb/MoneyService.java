package at.paukl.javatest.nonWeb;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.google.common.base.Charsets;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.io.CharStreams;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Paul Klingelhuber
 */
@Service
public class MoneyService {

	Logger log = LoggerFactory.getLogger(MoneyService.class);

	@Autowired
	public MoneyService(MetricRegistry metricRegistry) {
		stockRequestCounter = metricRegistry.counter(MetricRegistry.name(MoneyService.class, "stockRequest"));
		stockRequestHttpCounter = metricRegistry.counter(MetricRegistry.name(MoneyService.class, "stockRequestHttp"));
	}

	private final Counter stockRequestCounter, stockRequestHttpCounter;


	AtomicLong money = new AtomicLong();

	LoadingCache<String, String> stockCache = CacheBuilder.newBuilder().concurrencyLevel(5).
			expireAfterWrite(1, TimeUnit.MINUTES).maximumSize(2000).build(new CacheLoader<String, String>() {
				@Override
				public String load(String key) throws Exception {
					log.info(String.format("loading stock value for %s from server", key));
					stockRequestHttpCounter.inc();
					return loadStockValueFromServer(key);
				}
			});

	public long changeBalance(Long amount) {
		return money.addAndGet(amount);
	}

	public String getStockValue(String name) {
		try {
			stockRequestCounter.inc();
			return stockCache.get(name);
		} catch (ExecutionException e) {
			throw new RuntimeException("WAT?", e);
		}
	}

	private String loadStockValueFromServer(String name) {
		HttpGet httpGet = new HttpGet("http://download.finance.yahoo.com/d/quotes.csv?f=n1&s="  + name);
		try {
			CloseableHttpResponse response = HttpClients.createDefault().execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				String result = CharStreams.toString(new InputStreamReader(response.getEntity().getContent(), Charsets.UTF_8));
				return parseStockResult(result);
			}
			return "error";
		} catch (IOException e) {
			return "error";
		}
	}

	Pattern resultPattern = Pattern.compile("<b>([0-9.,]+)</b>");

	private String parseStockResult(String result) {
		if (result.startsWith("N/A")) {
			return "error";
		}
		Matcher matcher = resultPattern.matcher(result);
		if (!matcher.find()) {
			return "error";
		}
		if (matcher.groupCount() < 1) {
			return "error";
		}
		return matcher.group(1);
	}

}
