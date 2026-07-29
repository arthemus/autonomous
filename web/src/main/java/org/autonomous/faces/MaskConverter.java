package org.autonomous.faces;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.autonomous.functions.Functions;

/**
 *
 * Class to remove special characters during the conversion of values from a JSF page.
 *
 * @author arthemus
 * @since 16/10/2012
 */
@ApplicationScoped
@ManagedBean(eager = true)
@FacesConverter(value = "maskConverter")
public final class MaskConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String string) {
		return Functions.removeSpecialCharacters(string);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return Functions.removeSpecialCharacters(value.toString());
	}

}
