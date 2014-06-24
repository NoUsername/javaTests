package at.paukl.javatest.web;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Paul Klingelhuber
 */
@Controller
@RequestMapping("/test")
public class TestAutoConfiguredController {

	@RequestMapping("/*")
	@ResponseBody
	public String one() {
		return "one - success";
	}

	@RequestMapping("/two")
	@ResponseBody
	public String two() {
		return "two - even more success";
	}

}
