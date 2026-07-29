package org.autonomous.tenaz.hibernate;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * <pre>
 * This class implements a control design for Hibernate sessions, removing the
 * need for the same control in every business class.
 *
 * The session will be 'opened' and 'closed' on each call to a JSF page.
 * </pre>
 *
 * @author arthemus
 * @since 07/11/2012
 *
 */
public class OpenSessionInView implements Filter {

	private HibernatePersist persist;

	@Override
	public void destroy() {
		if (persist != null) {
			persist.closeFactory();
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		persist.getSession();
		try {
			chain.doFilter(request, response);
		} finally {
			persist.closeSession();
		}
	}

	@Override
	public void init(FilterConfig filter) throws ServletException {
		persist = HibernatePersist.getDefault();
		persist.getFactory();
	}
}
