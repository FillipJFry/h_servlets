package com.goit.fry.servlets;

import org.junit.jupiter.api.Test;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class TimezoneValidateFilterTest {

	@Test
	void doFilter() {

		TimezoneValidateFilter filter = new TimezoneValidateFilter();
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);

		StringWriter writer = new StringWriter();
		when(req.getParameter("timezone")).thenReturn("GMT-2");
		try {
			when(resp.getWriter()).thenReturn(new PrintWriter(writer));
			filter.doFilter(req, resp, chain);

			assertEquals("", writer.toString());
			verify(chain).doFilter(req, resp);
		}
		catch (Exception e) {

			assertNull(e);
		}
	}
}