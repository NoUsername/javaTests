package at.paukl.javatest;

import com.google.inject.ImplementedBy;

/**
 * @author Paul Klingelhuber
 */
@ImplementedBy(DummyServiceImpl.class)
public interface DummyService {
	void handleMessage(String message);
}
