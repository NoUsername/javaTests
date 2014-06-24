package at.paukl.javatest.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Paul Klingelhuber
 */
@Configuration
public class JettySpringMvcStarter implements ApplicationListener<ContextStartedEvent> {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	WebApplicationContext webApplicationContext;

	Server server;

	@PostConstruct
	public void foo() {
		server = new Server(8080);

		ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		contextHandler.setContextPath("/");
		contextHandler.setErrorHandler(null);
		contextHandler.addServlet(new ServletHolder(new DispatcherServlet(webApplicationContext)), "/*");
		contextHandler.addEventListener(new ContextLoaderListener(webApplicationContext));
//		try {
//			contextHandler.setResourceBase(new ClassPathResource("webapp").getURI().toString());
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
		server.setHandler(contextHandler);
	}

	@Override
	public void onApplicationEvent(ContextStartedEvent event) {
		try {
			server.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static class DumpServlet extends HttpServlet {

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			resp.getWriter().write("SUCCESS :)");
		}
	}

}
