package org.autonomous.ioc;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.google.inject.Injector;

/**
 * <pre>
 * Evento responsável por inicializar e excluir o objeto Injector utilizado pelo
 * Guice na injeção de dependência antes da Servlet ser criada durante a
 * requisição.
 * 
 * Dessa forma é possível utilizar a injeção de dependência nos Manageds Beans
 * pelos métodos 'set'.
 * </pre>
 * 
 * @author arthemus
 * @since 28/11/2012
 * 
 */
public class GuiceBeanInjectListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();
		servletContext.removeAttribute(Injector.class.getName());
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {		
		// Injector injector = Guice.createInjector(new InjectModule());		
		// ServletContext servletContext = servletContextEvent.getServletContext();
		// servletContext.setAttribute(Injector.class.getName(), injector);
	}

}
