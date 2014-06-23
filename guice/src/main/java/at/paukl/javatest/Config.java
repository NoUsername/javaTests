package at.paukl.javatest;

import com.google.inject.Inject;

/**
 * @author Paul Klingelhuber
 */
public class Config {

	@Inject
	public DummyService service;

	public boolean keepRunning = true;
}
