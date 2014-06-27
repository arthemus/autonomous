package org.autonomous.faces.mensageria;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.autonomous.functions.mensageria.Message18n;
import org.autonomous.functions.mensageria.UnitMessage18n;

/**
 * Implementação do sistema de internacionalização a partir do contexto do JSF.
 * 
 * @author arthemus
 * @since 16/05/2014
 * @see Message18n
 * @see UnitMessage18n
 * 
 */
public class FacesMessage18n implements Message18n {

	@Override
	public String getMessage(String propriedade) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
		return bundle.getString(propriedade);
	}

	@Override
	public String getMessage(String propriedade, Object... parametros) {
		String message = this.getMessage(propriedade);
		MessageFormat formatter = new MessageFormat(message);
		return formatter.format(parametros);
	}
}
