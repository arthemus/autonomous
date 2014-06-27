package org.autonomous.faces;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Conversor genérico para utilização de entidades da JPA em componentes 
 * JSF como combobox e seletores.
 * 
 * @author arthemus
 * @since 16/05/2013
 */
@ApplicationScoped
@ManagedBean(eager = true)
@FacesConverter(value = "entityConverter")
public class EntityConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && !value.isEmpty())
			return component.getAttributes().get(value);
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		int count = component.getAttributes().size() + 1;
		String key = String.valueOf(count); 
		component.getAttributes().put(key, object);
		return key;
	}
}