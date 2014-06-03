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
 * Essa classe tem a função de implementar um Designe de controle das Sessões do
 * Hibernate, eliminando a necessidade do mesmo controle em cada classe de negócio.
 * 
 * A Sessão será 'Aberta' e 'Fechada' a cada chamada à uma página JSF.
 * 
 * {@link https://community.jboss.org/wiki/OpenSessioninView?_sscc=t}
 * </pre>
 * 
 * @author arthemus
 * @since 07/11/2012
 * 
 */
public class OpenSessionInView implements Filter {

	@Override
	public void destroy() {
		HibernatePersist.closeFactory();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HibernatePersist.getSession();
		try {
			chain.doFilter(request, response);
		} finally {
			HibernatePersist.closeSession();
		}
	}

	@Override
	public void init(FilterConfig filter) throws ServletException {
		HibernatePersist.getFactory();
	}
}