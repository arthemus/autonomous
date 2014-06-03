package org.autonomous.functions;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testes unitários sobre a classe Calculo.
 * 
 * @author arthemus
 * @since 18/12/2013
 */
public class CalculoTest {

	@Test
	public void novoCalculoPorUmValorDouble() {
		double valor = Math.PI;
		Calculo calculo = Calculo.newInstance(valor);
		Assert.assertEquals(3.141592653589793, calculo.getValor().doubleValue(), 0);
	}

	@Test
	public void novoCalculoPorUmValorDoubleComArredondamento() {
		double valor = 2539.0050251256;
		Calculo calculo = Calculo.newInstance(valor).doArredondamentoReal();
		Assert.assertEquals(2539.01, calculo.getValor().doubleValue(), 0);
	}
	
	@Test
	public void arredondaPorQuantidade() {
		double valor = Math.PI;
		Calculo calculo = Calculo.newInstance(valor).doArredondamentoPorQuantidade();
		Assert.assertEquals(3.1416, calculo.getValor().doubleValue(), 0);
	}

	@Test
	public void arredondaPorUnitario() {
		double valor = Math.PI;
		Calculo calculo = Calculo.newInstance(valor).doArredondamentoPorUnidade();
		Assert.assertEquals(3.1415926536, calculo.getValor().doubleValue(), 0);
	}
	
	@Test
	public void arredondaPorTotal() {
		double valor = Math.PI;
		Calculo calculo = Calculo.newInstance(valor).doArredondamentoPorTotalidade();
		Assert.assertEquals(3.15, calculo.getValor().doubleValue(), 0);
	}
	
	@Test
	public void comparaValores() {
		Calculo c1 = Calculo.newInstance(15.23456).doArredondamentoPorQuantidade();
		Calculo c2 = Calculo.newInstance(15.23457).doArredondamentoPorQuantidade();
		Assert.assertTrue(c1.equals(c2));
	}
	
	@Test 
	public void imprimeUmValorDouble() {
		Calculo calculo = Calculo.newInstance(Math.PI);
		Assert.assertEquals(3.141592653589793, calculo.getDoubleValue(), 0);		
	}
	
	@Test
	public void imprimeUmValorInteiro() {
		Calculo calculo = Calculo.newInstance(Math.PI);
		Assert.assertEquals(3, calculo.getIntValue(), 0);		
	}
	
	@Test
	public void somaSemArredondar() {
		Calculo calculo = Calculo.newInstance(32.65).doSoma(0.3512);
		Assert.assertEquals(33.0012, calculo.getDoubleValue(), 0);
	}
	
	@Test
	public void subtraiSemArredondar() {
		Calculo calculo = Calculo.newInstance(32.65).doSubtrai(0.3512);
		Assert.assertEquals(32.2988, calculo.getDoubleValue(), 0);
	}
	
	@Test
	public void multiplicaSemArredondar() {
		Calculo calculo = Calculo.newInstance(32.65).doMultiplica(0.3512);
		Assert.assertEquals(11.46668, calculo.getDoubleValue(), 0);
	}
		
	@Test
	public void divideSemArredonda() {
		Calculo calculo = Calculo.newInstance(32.65).doDivide(2);
		Assert.assertEquals(16.325, calculo.getDoubleValue(), 0);
	}
	
	@Test
	public void divideComDivisorQuebrado() {
		Calculo calculo = Calculo.newInstance(14.2718).doDivide(92.316);
		Assert.assertEquals(0.15459725291390441, calculo.getDoubleValue(), 0);
	}
	
	@Test
	public void arredondamentoDuasCasas(){
		Calculo calc = Calculo.newInstance(150.265944).doArredondamento(2);
		Assert.assertEquals(150.27, calc.getDoubleValue(), 0);
	}
	
	@Test
	public void arredondamentoTresCasas(){
		Calculo calc = Calculo.newInstance(150.265944).doArredondamento(3);
		Assert.assertEquals(150.266, calc.getDoubleValue(), 0);
	}
	
	@Test
	public void arredondamentoTresCasasMultiplicando(){
		Calculo calc = Calculo.newInstance(150.236).doMultiplica(1.563).doArredondamento(3);
		Assert.assertEquals(234.819, calc.getDoubleValue(), 0);
	}
	
	@Test(expected = ArithmeticException.class)
	public void arredondamentoCasasNegativasMultiplicando(){
		Calculo calc = Calculo.newInstance(150.236).doMultiplica(1.563).doArredondamento(-3);
		Assert.assertEquals(234.819, calc.getDoubleValue(), 0);
	}
	
	@Test
	public void arredondamentoZeroCasasMultiplicando(){
		Calculo calc = Calculo.newInstance(150.236).doMultiplica(1.563).doArredondamento(0);
		Assert.assertEquals(235, calc.getDoubleValue(), 0);
	}
	
}
