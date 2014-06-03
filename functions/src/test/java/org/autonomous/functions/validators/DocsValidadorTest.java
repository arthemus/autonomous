package org.autonomous.functions.validators;

import org.junit.Assert;
import org.junit.Test;

public class DocsValidadorTest {
	
	/**
	 * Testes de CPF
	 */
	
	@Test
	public void CpfInvalido(){
		boolean resultado = DocsValidador.isCpf("04402024691");
		Assert.assertEquals(false, resultado);
	}
	
	@Test
	public void CpfValido(){
		boolean resultado = DocsValidador.isCpf("04402024692");
		Assert.assertEquals(true, resultado);
	}
	
	@Test
	public void CpfInvalidoNumeroIgual(){
		boolean resultado = DocsValidador.isCpf("44444444444");
		Assert.assertEquals(false, resultado);
	}
	
	@Test
	public void CpfInvalidoComZeros(){
		boolean resultado = DocsValidador.isCpf("00000000000");
		Assert.assertEquals(false, resultado);
	}
	
	@Test
	public void CpfInvalidoComLetras(){
		boolean resultado = DocsValidador.isCpf("A4402024692");
		Assert.assertEquals(false, resultado);
	}	
	
	@Test
	public void CpfValidoComPontosETraco(){
		boolean resultado = DocsValidador.isCpf("044.020.246-92");
		Assert.assertEquals(true, resultado);
	}
	
	@Test
	public void CpfInValidoComSeparadoresErrados(){
		boolean resultado = DocsValidador.isCpf("044.020246-92");
		Assert.assertEquals(false, resultado);
	}
	
	/**
	 * Testes de CNPJ
	 */
	
	@Test
	public void CnpjValido(){
		boolean resultado = DocsValidador.isCnpj("16832651000340");
		Assert.assertEquals(true, resultado);
	}
	
	@Test
	public void CnpjInvalido(){
		boolean resultado = DocsValidador.isCnpj("16832351000340");
		Assert.assertEquals(false, resultado);
	}
	
	@Test
	public void CnpjInvalidoComZeros(){
		boolean resultado = DocsValidador.isCnpj("00000000000000");
		Assert.assertEquals(false, resultado);
	}
	
	@Test
	public void CnpjInvalidoComNumerosIguais(){
		boolean resultado = DocsValidador.isCnpj("55555555555555");
		Assert.assertEquals(false, resultado);
	}

	@Test
	public void CnpjInvalidoComLetras(){
		boolean resultado = DocsValidador.isCnpj("1683265A000340");
		Assert.assertEquals(false, resultado);
	}

	@Test
	public void CnpjValidoComSeparadores(){
		boolean resultado = DocsValidador.isCnpj("16.832.651/0003-40");
		Assert.assertEquals(true, resultado);
	}
	
	@Test
	public void CnpjInValidoComSeparadoresErrados(){
		boolean resultado = DocsValidador.isCnpj("16.832651-0003-40");
		Assert.assertEquals(false, resultado);
	}

	/* Testes Inscrição Estadual */
	
	@Test
	public void IESaoPauloValida(){
		boolean resultado = DocsValidador.isInscricaoEstatual(UnidadeFederativa.SP, "116696126113");
		Assert.assertEquals(true, resultado);
	}
	
	@Test
	public void IESaoPauloInvalida(){
		boolean resultado = DocsValidador.isInscricaoEstatual(UnidadeFederativa.SP, "116696126110");
		Assert.assertEquals(false, resultado);
	}
	
	@Test
	public void IEMinasGeraisInvalida(){
		boolean resultado = DocsValidador.isInscricaoEstatual(UnidadeFederativa.MG, "116696126113");
		Assert.assertEquals(false, resultado);
	}
	
	@Test
	public void IEMinasGeraisValida(){
		boolean resultado = DocsValidador.isInscricaoEstatual(UnidadeFederativa.MG, "5572192270035");
		Assert.assertEquals(true, resultado);
	}
	
	@Test
	public void IESomenteZeros(){													 
		boolean resultado = DocsValidador.isInscricaoEstatual(UnidadeFederativa.MG, "0000000000000");
		Assert.assertEquals(false, resultado);
	}
}
