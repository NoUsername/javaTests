package at.paukl.javatest.web;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Paul Klingelhuber
 */
@Retention(RetentionPolicy.RUNTIME)
@interface WebHandler {
	String value();
}
