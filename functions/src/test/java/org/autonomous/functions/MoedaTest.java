package org.autonomous.functions;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testes unitários para a classe Moeda.
 * 
 * @author arthemus
 * @since 18/12/2013
 */
public class MoedaTest {

	@Test
	public void imprimeUmValorSemCasaDecimais() {
		double valor = 10;
		Moeda moeda = Moeda.getInstance(valor);
		Assert.assertEquals("R$ 10,00", moeda.getValorParaImpressao());
	}
	
	@Test
	public void imprimeUmValorComplexoComCasaDecimais() {
		double valor = 10345.98;
		Moeda moeda = Moeda.getInstance(valor);
		Assert.assertEquals("R$ 10.345,98", moeda.getValorParaImpressao());
	}
	
	/**
	 * Ver regras de arredondamento:
	 * 
	 * http://www.brasilescola.com/matematica/arredondando-numeros.htm
	 */
	@Test
	public void imprimeSemEfetuarArredondamento() {
		double valor = 10345.985;
		Moeda moeda = Moeda.getInstance(valor);
		Assert.assertEquals("R$ 10.345,98", moeda.getValorParaImpressao());
	}
	
	@Test
	public void imprimeComEfetuarArredondamento() {
		double valor = 10345.975;
		Moeda moeda = Moeda.getInstance(valor);
		Assert.assertEquals("R$ 10.345,98", moeda.getValorParaImpressao());
	}
	
}
