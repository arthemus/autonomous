package org.autonomous.faces;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Provider;

/**
 * Fornece uma implementação de HttpServletResponse, 
 * já adequado para trabalhar com o Injector do Google Guice. 
 * 
 * @author arthemus
 * @since 25/03/2013
 *
 */
public class HttpResponse implements Provider<HttpServletResponse> {

	@Override
	public HttpServletResponse get() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		return (HttpServletResponse) context.getResponse();
	}
}
