package at.paukl.javatest;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Module;

public class Main {

	public static void main(String[] args) {
		System.out.println("Starting guice example");
		DummyExecutor executor = Guice.createInjector(new DefaultModule()).getInstance(DummyExecutor.class);
		executor.run();
	}

	static class DefaultModule implements Module {
		@Override
		public void configure(Binder binder) {
		}
	}

}