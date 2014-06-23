package at.paukl.javatest;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Paul Klingelhuber
 */
public class Config {

	@Autowired
	public DummyService service;

	public boolean keepRunning = true;
}
