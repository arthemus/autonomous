package org.autonomous.faces.ioc.modules;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.autonomous.faces.HttpRequest;
import org.autonomous.faces.HttpResponse;

import com.google.inject.AbstractModule;

/**
 * Módulo do Guice responsável pelas injeções de dependência referêntes ao 
 * contexto Web de uma aplicação JSF.
 * 
 * @author arthemus
 * @since 18/01/2013
 * 
 */
public class WebModule extends AbstractModule implements Serializable {

	private static final long serialVersionUID = -213767059805003128L;

	@Override
	protected void configure() {
		bind(HttpServletRequest.class).toProvider(HttpRequest.class);
		bind(HttpServletResponse.class).toProvider(HttpResponse.class);
	}
}
