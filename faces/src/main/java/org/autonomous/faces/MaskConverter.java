package org.autonomous.faces;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.autonomous.functions.Funcoes;

/**
 * 
 * Classe para remover caracteres especiais durante a conversão de valores da página JSF.
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
		return Funcoes.doRemoveCaracteres(string);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return Funcoes.doRemoveCaracteres(value.toString());
	}
	
}
