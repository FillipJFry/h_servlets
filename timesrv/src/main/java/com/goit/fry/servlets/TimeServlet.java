package com.goit.fry.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {

	private DateFormat dtFormat;

	@Override
	public void init() throws ServletException {

		dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dtFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String timezoneStr = req.getParameter("timezone");
		if (timezoneStr != null) {
			timezoneStr = UTCHandler.replaceIfNecessary(timezoneStr);
			dtFormat.setTimeZone(TimeZone.getTimeZone(timezoneStr));
		}
		else
			dtFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		resp.setContentType("text/html");
		try (PrintWriter out = resp.getWriter()) {

			out.println("<html>");
			out.println("<body>");
			out.println("<head>");
			out.println("<title>Current UTC time</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<p>" + dtFormat.format(new Date()) + ' ' +
					(timezoneStr != null ? timezoneStr : "UTC") + "</p>");
			out.println("</body>");
			out.println("</html>");
		}
	}

	@Override
	public void destroy() {

		dtFormat = null;
	}
}
