package org.autonomous.faces;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.google.inject.Provider;

/**
 * Provides an HttpServletRequest implementation,
 * suitable for working with the Google Guice Injector.
 *
 * @author arthemus
 * @since 25/03/2013
 *
 */
public class HttpRequest implements Provider<HttpServletRequest> {

	@Override
	public HttpServletRequest get() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		return (HttpServletRequest) context.getRequest();
	}
}
