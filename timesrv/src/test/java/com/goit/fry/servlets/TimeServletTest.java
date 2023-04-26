package com.goit.fry.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TimeServletTest {

	private static final Logger logger = LogManager.getRootLogger();

	@Test
	void service() {

		logger.info("testing TimeServlet::service()");
		TimeServlet servlet = new TimeServlet();
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);

		StringWriter writer = new StringWriter();
		try {
			when(resp.getWriter()).thenReturn(new PrintWriter(writer));
			servlet.init();
			servlet.service(req, resp);
			servlet.destroy();
		}
		catch (Exception e) {

			assertNull(e);
		}

		String body = writer.toString();
		logger.info("response body: " + body);
		Pattern p = Pattern.compile("""
				<html>
				<body>
				<head>
				<title>Current UTC time</title>
				</head>
				<body>
				<p>([1-9][0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) UTC</p>
				</body>
				</html>""");
		Matcher m = p.matcher(body);
		assertTrue(m.find());
		DateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		assertDoesNotThrow(() -> dtFormat.parse(m.group(1)));
	}
}