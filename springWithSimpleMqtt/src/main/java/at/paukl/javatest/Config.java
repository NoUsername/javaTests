package at.paukl.javatest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Paul Klingelhuber
 */
@Component
public class Config {

	@Autowired
	public DummyService service;

	public boolean keepRunning = true;
}
