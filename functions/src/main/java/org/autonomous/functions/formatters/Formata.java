package org.autonomous.functions.formatters;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

/**
 * Formata um determinado valor com base em uma mascara.
 * Para se utilizado em documentos ou campos de formulario.
 * 
 * @author arthemus
 * @since 28/05/2013
 *
 */
public class Formata {

	private final Formatter formatter;
	private final String valorAFormatar;

	public Formata(Formatter formatterClass, String valorAFormatar) {
		this.formatter = formatterClass;
		this.valorAFormatar = valorAFormatar;
	}

	public String getValorFormatado() {
		MaskFormatter mf = null;
		try {
			mf = new MaskFormatter(formatter.getMascara());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		mf.setValueContainsLiteralCharacters(false);
		String novoValor = new String();
		try {
			novoValor = mf.valueToString(valorAFormatar);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return novoValor;
	}
}
