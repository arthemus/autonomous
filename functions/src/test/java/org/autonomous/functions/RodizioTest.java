package org.autonomous.functions;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testes unitários para a classe de Rodizios Municipais.
 * 
 * @author arthemus
 * @since 11/07/2013
 */
public class RodizioTest {

	@Test
	public void verificaSeDeterminadaPlacaEstaDeRodizioNaDataAtual() {
		RodizioMunicipal rodizio = new RodizioMunicipal("BNQ-3051");
		try {
			Assert.assertEquals(Boolean.TRUE, rodizio
					.isEmRodizioPara(new SimpleDateFormat("dd/MM/yyyy").parse("24/08/1987")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
