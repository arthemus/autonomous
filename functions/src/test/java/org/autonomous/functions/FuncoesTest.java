package org.autonomous.functions;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class FuncoesTest {

	@Test
	public void removePreposicoes2() {
		String frase = "A data de saída não está saindo no danfe, pois ao informar no ped venda data de entrega não sai no danfe";
		String limpa = Funcoes.doRemovePreposicao(frase);		
		Assert.assertEquals("data saída não está saindo danfe, pois informar ped venda data entrega não sai danfe", limpa);
	}
	
	@Test
	public void removePreposicoesDeUmaFrase() {
		String frase = "Rotina para baixa de titulos no contas a receber";
		String limpa = Funcoes.doRemovePreposicao(frase);		
		Assert.assertEquals("Rotina baixa titulos contas receber", limpa);
	}
	
	@Test 
	public void montaUmaDescricaoDePeriodo() {
		String result = Funcoes.getDescricaoPeriodo(new Date(), new Date());		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String inicio = format.format(new Date());
		String fim = format.format(new Date());		
		Assert.assertEquals("De ".concat(inicio).concat(" à ").concat(fim), result);
	}
	
	@Test
	public void truncaStringDaDireitaParaEsquerda() {
		String value = "0,4 MULTIUSO BRANCO BRILHANTE(Quant.Ltda)";
		String newValue = Funcoes.getTruncateRigthToLeft(value, 30);		
		Assert.assertEquals("0,4 MULTIUSO BRANCO BRILHANTE(", newValue);		
	}
	
	@Test
	public void truncaStringDaEsquerdaParaDireita() {
		String value = "0,4 MULTIUSO BRANCO BRILHANTE(Quant.Ltda)";
		String newValue = Funcoes.getTruncateLeftToRigth(value, 30);		
		Assert.assertEquals("O BRANCO BRILHANTE(Quant.Ltda)", newValue);		
	}
	
	@Test
	public void truncaStringNull() {
		String value = null;
		String newValue = Funcoes.getTruncateRigthToLeft(value, 1);		
		Assert.assertTrue("".equals(newValue));
	}
	
	@Test
	public void truncaStringVazia() {
		String value = "";
		String newValue = Funcoes.getTruncateRigthToLeft(value, 1);		
		Assert.assertTrue("".equals(newValue));
	}

	@Test
	public void truncaStringMaiorQueLength() {
		String value = "0,4 MULTIUSO BRANCO BRILHANTE(Quant.Ltda)"; // size = 41
		String newValue = Funcoes.getTruncateRigthToLeft(value, 51);
		Assert.assertTrue((value.length() >= newValue.length()));		
	}

	@Test
	public void imprimeListaVertical() {
		List<String> lista = Arrays.asList("linha01", "linha02", "linha03");
		String result = Funcoes.doPrintListVertical(lista);
		Assert.assertNotNull(result);
		Assert.assertTrue(result.contains("\n"));		
	}
	
	@Test
	public void montaZerosAEsquerda() {
		String documento = "0256";
		String novoDocumento = Funcoes.getZerosAEsquerda(documento, 10);
		Assert.assertEquals("0000000256", novoDocumento);
	}
	
	@Test
	public void obtemMapaDeParametros() {
		Object[][] array = { 
				{ "chave1", new Integer(1) },
				{ "chave2", new Double(2) }, 
				{ "chave3", new String() },
				{ "chave4", new Character('1') }, 
				{ "chave5", new Object() } 
			};
		Map<String, Object> params = Funcoes.getParametros(array);		
		Assert.assertNotNull(params);
		Assert.assertTrue(params.size() == 5);
	}
	
	@Test
	public void roundNumberToDuasCasas(){
		Double number = Funcoes.RoundTo(156.5695545, 2);
		Double numberExpected = 156.57;
		
		Assert.assertEquals(numberExpected, number);
	}
	
	@Test
	public void roundNumberToTresCasas(){
		Double number = Funcoes.RoundTo(156.567, 3);
		Double numberExpected = 156.567;
		
		Assert.assertEquals(numberExpected, number);
	}
	
	@Test
	public void roundNumberToQuatroCasas(){
		Double number = Funcoes.RoundTo(156.5671988, 4);
		Double numberExpected = 156.5672;
		
		Assert.assertEquals(numberExpected, number);
	}
	
	@Test(expected = ArithmeticException.class)
	public void roundNumberToCasasNegativas(){
		Funcoes.RoundTo(156.5671988, -4);			
	}
	
	@Test
	public void roundNumberToZeroCasas(){
		Double number = Funcoes.RoundTo(156.616561, 0);
		Double numberExpected = 157d;
		
		Assert.assertEquals(numberExpected, number);
	}
	
}
