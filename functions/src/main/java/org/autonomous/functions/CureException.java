package org.autonomous.functions;

/**
 *
 * Classe para tratar exceções comuns dos sistemas como
 * problemas de chave duplicada no banco, arquivo não encontrado, etc.
 * 
 * @author arthemus
 * @since 30/09/2013
 */
public final class CureException {

	private final Throwable exceptionOrigin;

	private CureException(final Throwable exceptionOrigin) {
		this.exceptionOrigin = exceptionOrigin;
	}

	/**
	 * Define a exception que deve ser tratada.
	 * 
	 * @param e
	 * @return
	 */
	public static CureException by(final Throwable e) {
		return new CureException(e);
	}
	
	/**
	 * Obtem uma nova mensagem para a exceção ou caso o tipo de exceção ainda não
	 * tenha tratamento definido, voltará a mensagem original da exceção.
	 * 
	 * @return
	 */
	public String getNewMessage() {
		String result = exceptionOrigin.getMessage();
		if (result.contains("O sistema não pode encontrar o arquivo especificado")) {
			result = "O arquivo informado não pode ser localizado";
		} 
		else if (result.contains("FOREIGN KEY")){
			result = "Este registro está sendo utilizado pelo sistema e não pode ser excluído!";
		}
		return result;
	}
}
