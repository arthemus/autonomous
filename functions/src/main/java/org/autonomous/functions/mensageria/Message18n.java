package org.autonomous.functions.mensageria;

/**
 * Interface de representação às classes que trabalham com internacionalização e
 * mensageria.
 * 
 * @author arthemus
 * @since 16/05/2014
 * 
 */
public interface Message18n {

	/**
	 * Obtem uma mensagem configurada nos arquivos de internacionalização.
	 * 
	 * @param propriedade
	 * @return
	 */
	String getMessage(String propriedade);

	/**
	 * Obtem uma mensagem configurada nos arquivos de internacionalização com
	 * base em alguns paramêtros.
	 * 
	 * @param propriedade
	 * @param parametros
	 * @return
	 */
	String getMessage(String propriedade, Object... parametros);
}
