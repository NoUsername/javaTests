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
public class JettyStarter implements ApplicationListener<ContextStartedEvent> {

	@Autowired
	ApplicationContext applicationContext;

	Server server;

	@PostConstruct
	public void foo() {
		server = new Server(8080);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);

		context.addServlet(org.eclipse.jetty.servlet.DefaultServlet.class, "/");
		context.addServlet(new ServletHolder(new DumpServlet()), "/dump/*");

		Map<String,Object> handlers = applicationContext.getBeansWithAnnotation(WebHandler.class);
		for (Object handler : handlers.values()) {
			WebHandler handlerInfo = handler.getClass().getAnnotation(WebHandler.class);
			if (handler instanceof HttpServlet) {
				System.out.println("configuring handler for " + handlerInfo.value());
				context.addServlet(new ServletHolder((HttpServlet)handler), handlerInfo.value());
			} else {
				System.out.println("error auto-configuring handler, wrong type");
			}
		}
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
