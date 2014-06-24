package at.paukl.javatest.web;

import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Paul Klingelhuber
 */
@Component
@WebHandler("/success/*")
public class TestAutoConfiguredServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().write("success, I haz it. Nao, where's cheezburger?");
	}
}
