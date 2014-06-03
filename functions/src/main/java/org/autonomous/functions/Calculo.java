package org.autonomous.functions;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Classe para encapsular regras matemáticas comuns dentro dos sistemas
 * comerciais como Arredondamento, Comparação e operações matemáticas simples
 * como soma, subtração, multiplicação e divisão.
 * 
 * @author arthemus
 * @since 18/12/2013
 */
public strictfp final class Calculo {

	private final BigDecimal valor;

	private Calculo(final BigDecimal valor) {
		this.valor = valor;
	}

	/**
	 * Obtem uma nova instancia a partir de um valor double com ou sem
	 * casas decimais.
	 * 
	 * @param valor
	 * @return
	 */
	public static Calculo newInstance(final double valor) {
		BigDecimal decimal = BigDecimal.valueOf(valor);
		return new Calculo(decimal);
	}

	/**
	 * Obtem uma nova instancia a partir de um inteiro simples.
	 * 
	 * @param valor
	 * @return
	 */
	public static Calculo newInstance(final int valor) {
		BigDecimal decimal = BigDecimal.valueOf(valor);
		return new Calculo(decimal);
	}

	/**
	 * Obtem uma nova instancia a partir de um valor String.
	 * Esse valor será arredondado sobre as regras da moeda nacional (duas casa decimais)
	 * e convertido para o formato padrão da classe de Calculo.
	 * 
	 * @param valor
	 * @return
	 */
	public static Calculo newInstance(final String valor) {
		double novoValor = Moeda.getInstance(valor).getValor();
		BigDecimal decimal = BigDecimal.valueOf(novoValor);
		return new Calculo(decimal);
	}

	/**
	 * Obtem uma nova instancia a partir de um valor encapsulado na classe BigDecimal.
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public static Calculo newInstance(BigDecimal bigDecimal) {
		return new Calculo(bigDecimal);
	}
	
	/**
	 * Retorna o valor original.
	 * 
	 * @return
	 */
	public BigDecimal getValor() {
		return this.valor;
	}

	/**
	 * Obtem uma nova classe Calculo com o valor arredondado para o padrão
	 * da moeda nacional (duas casas decimais).
	 * 
	 * Esse tipo de arredondamento se utiliza das regras básicas, examinando o 
	 * último algarismo:
	 * 
	 * Ver mais detalhes em:
	 * {@link http://www.brasilescola.com/matematica/arredondando-numeros.htm}
	 * 
	 * @return
	 */
	public Calculo doArredondamentoReal() {
		Moeda moeda = Moeda.getInstance(this.valor.doubleValue());
		return Calculo.newInstance(moeda.toString());
	}
	
	/**
	 * Obtem um novo calculo arredondado por quatro casas decimais.
	 * 
	 * @return
	 */
	public Calculo doArredondamentoPorQuantidade() {
		BigDecimal decimal = this.valor.setScale(4, RoundingMode.CEILING);
		return Calculo.newInstance(decimal);
	}
	
	/**
	 * Obtem um novo calculo arredondado por dez casas decimais.
	 * 
	 * @return
	 */
	public Calculo doArredondamentoPorUnidade() {
		BigDecimal decimal = this.valor.setScale(10, RoundingMode.CEILING);
		return Calculo.newInstance(decimal);
	}
	
	/**
	 * Obtem um novo calculo arredondado por duas casas decimais.
	 * 
	 * Esse tipo de arredondamento força o último algarismo a subir
	 * caso o valor tenha mais de duas casas decimais.
	 * 
	 * Comportamento diferente do método getArredondamentoReal que respita 
	 * as regras matemáticas para arredondamento.
	 * 
	 * @return
	 */
	public Calculo doArredondamentoPorTotalidade() {
		BigDecimal decimal = this.valor.setScale(2, RoundingMode.CEILING);
		return Calculo.newInstance(decimal);
	}
	
	/**
	 * Realiza o arredondamento de acordo com o número de casas decimais definido.
	 * 
	 * Respeita as regras de arredondamento matemáticas.
	 * 
	 * @param casasDecimais
	 * @return Calculo
	 */
	public Calculo doArredondamento(int casasDecimais){
		if (casasDecimais < 0)
			throw new ArithmeticException();
		
		BigDecimal decimal = this.valor.setScale(casasDecimais, RoundingMode.HALF_EVEN);
		return Calculo.newInstance(decimal);
	}
	
	/**
	 * Retorna um valor double do objeto.
	 * @return
	 */
	public double getDoubleValue() {
		return this.valor.doubleValue();
	}
	
	/**
	 * Retornar um valor inteiro do objeto truncando suas casas decimais.
	 * @return
	 */
	public int getIntValue() {
		return this.valor.intValue();
	}
	
	/**
	 * Retorna um novo objeto com o valor atual + adicao.
	 * 
	 * @param adicao
	 * @return
	 */
	public strictfp Calculo doSoma(double adicao) {
		return Calculo.newInstance(this.valor.add(BigDecimal.valueOf(adicao)));
	}
	
	/**
	 * Retorna um novo objeto com o valor atual - subtraendo.
	 * 
	 * @param subtraendo
	 * @return
	 */
	public strictfp Calculo doSubtrai(double subtraendo) {
		return Calculo.newInstance(this.valor.subtract(BigDecimal.valueOf(subtraendo)));
	}
	
	/**
	 * Retorna um novo objeto com o valor atual X multiplicando.
	 * 
	 * @param multiplicando
	 * @return
	 */
	public strictfp Calculo doMultiplica(double multiplicando) {
		return Calculo.newInstance(this.valor.multiply(BigDecimal.valueOf(multiplicando)));
	}
	
	/**
	 * Retorna um novo objeto com o valor atual / divisor.
	 * 
	 * @param divisor
	 * @return
	 */
	public strictfp Calculo doDivide(double divisor) {
		return Calculo.newInstance(this.valor.divide(BigDecimal.valueOf(divisor), MathContext.DECIMAL128));
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.valor.compareTo(((Calculo) obj).getValor()) == 0;
	}

	@Override
	public String toString() {
		return this.valor.toString();
	}
	
}
