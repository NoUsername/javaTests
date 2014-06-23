package at.paukl.javatest;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Paul Klingelhuber
 */
@Singleton
public class Config {

	@Inject
	public DummyService service;

	public boolean keepRunning = true;
}
