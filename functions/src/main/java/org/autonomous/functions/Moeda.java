package org.autonomous.functions;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Currency;

/**
 * Converte e imprime valores para o FORMATO da moeda nocional (Real).
 * 
 * @author arthemus
 * @since 18/12/2013
 */
public final class Moeda {

	private static final Currency CURRENCY = Currency.getInstance("BRL");

	private static final DecimalFormat FORMATO = new DecimalFormat("#,##0.00");

	private final double valor;

	private Moeda(final double valor) {
		this.valor = valor;
	}

	/**
	 * Obtem uma nova instancia da classe a partir de um valor double simples.
	 * 
	 * @param valor
	 * @return
	 */
	public static Moeda getInstance(final double valor) {
		return new Moeda(valor);
	}

	/**
	 * Obtem uma nova instancia da classe a partir de um valor por string.
	 * Esse valor será convertido e arredondado para assim poder ser imprimido
	 * no padrão da moeda nacional.
	 * 
	 * @param valor
	 * @return
	 */
	public static Moeda getInstance(final String valor) {
		double result = 0D;
		try {
			result = FORMATO.parse(valor).doubleValue();
		} catch (ParseException e) {
			throw new ArithmeticException("Problemas para converter o valor ".concat(valor));
		}
		return new Moeda(result);
	}
	
	/**
	 * Obtem o valor original.
	 * Caso a instancia tenha cido criada por um valor String, o retorno
	 * será arredondado.
	 * 
	 * @return
	 */
	public double getValor() {
		return this.valor;
	}

	/**
	 * Obtem um retorno ideal para impressão ao usuário incluido o simbolo da 
	 * moeda com o valor.
	 * 
	 * Ex: R$ 15,00
	 * 
	 * @return
	 */
	public String getValorParaImpressao() {
		return CURRENCY.getSymbol().concat(" ").concat(toString());	
	}
	
	@Override
	public String toString() {
		return FORMATO.format(valor);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(valor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Moeda other = (Moeda) obj;
		if (Double.doubleToLongBits(valor) != Double
				.doubleToLongBits(other.valor))
			return false;
		return true;
	}

}
