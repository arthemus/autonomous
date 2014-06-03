package org.autonomous.functions.mensageria;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Implementação do sistema de internacionalização para ser utilizando nos
 * testes unitários.
 * 
 * @author arthemus
 * @since 16/05/2014
 * @see Message18n
 * @see FacesMessage18n
 * 
 */
public class UnitMessage18n implements Message18n {

	@Override
	public String getMessage(String propriedade) {
		ResourceBundle bundle = ResourceBundle.getBundle("mensagens", new Locale("pt", "BR")); 
		String message = bundle.getString(propriedade);
		return message;
	}

	@Override
	public String getMessage(String propriedade, Object... parametros) {
		String message = this.getMessage(propriedade);
		MessageFormat formatter = new MessageFormat(message);
		return formatter.format(parametros);
	}

}
