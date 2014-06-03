package org.autonomous.functions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

/**
 * Teste unitários para controle de horario.
 * 
 * @author arthemus
 * @since 12/07/2013
 * @see Horario
 * 
 */
public class HorarioTest {

	@Test
	public void obtemHorarioDeUmDate() {
		try {
			Horario result = Horario.getInstance(new SimpleDateFormat("dd/MM/yyyy").parse("24/08/1987"));
			Assert.assertEquals("00:00:00", result.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void comparaHora() {
		Horario horario = Horario.getInstance();
		Calendar calendar = new GregorianCalendar();
		int hora = calendar.get(Calendar.HOUR_OF_DAY);
		boolean result = (horario.getHora() == hora);
		Assert.assertTrue(
				"As horas são divergentes: Horario = " + horario.getHora()
						+ ", Calendar = " + hora, result);
	}

	@Test
	public void comparaMinuto() {
		Horario horario = Horario.getInstance();
		Calendar calendar = new GregorianCalendar();
		int minuto = calendar.get(Calendar.MINUTE);
		boolean result = (horario.getMinuto() == minuto);
		Assert.assertTrue(
				"Os minutos são divergentes: Horario = " + horario.getMinuto()
						+ ", Calendar = " + minuto, result);
	}

	@Test
	public void horarioAleatorioDeveSerDiferenteDoAtual() {
		Horario original = Horario.getInstance();
		String aleatorio = original.getHorarioAleatorio(10);
		boolean result = (original.equals(aleatorio));
		Assert.assertFalse("Os horarios são iguais: " + original + " - "
				+ aleatorio, result);
	}

	@Test
	public void testeDeHorarioAntesDaMadrugada() {
		Date dataReferente = new Date();
		Horario anterior = Horario.getInstance(dataReferente, "22:21:15");
		Horario posterior = Horario.getInstance(dataReferente, "00:00:11");
		boolean result = anterior.isMaior(posterior);
		Assert.assertTrue(
				"O horario " + posterior + " é menor que " + anterior, result);
	}

	@Test
	public void testeDeHorarioPosMadrugada() {
		Date dataReferente = new Date();
		Horario anterior = Horario.getInstance(dataReferente, "00:21:15");
		Horario posterior = Horario.getInstance(dataReferente, "02:00:11");
		boolean result = posterior.isMaior(anterior);
		Assert.assertTrue("O horario " + posterior + " é maior que " + anterior, result);
	}

	@Test
	public void verificaSeHorarioAtualEhMaiorQueExpoente() {
		Horario anterior = Horario.getInstance();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Horario atual = Horario.getInstance();
		boolean result = atual.isMaior(anterior);
		Assert.assertTrue("O horario atual " + atual + " é maior que anterior "
				+ anterior, result);
	}

	@Test
	public void calculaDiferencial() {
		Date dataReferente = new Date();
		Horario chegada = Horario.getInstance(dataReferente, "11:00:00");
		Horario saida = Horario.getInstance(dataReferente, "17:30:00");
		String tempoCalculado = HorarioUtil.between(chegada, saida);
		Assert.assertEquals("06:30:00", tempoCalculado);
	}

	@Test
	public void comparaHorarioFormadoManualmente() {
		String tempo = "15:12:31";
		Horario manual = Horario.getInstance(new Date(), tempo);
		Assert.assertEquals(tempo, manual.toString());
	}
	
	@Test
	public void almentaHora() {
		Horario anterior = Horario.getInstance(new Date(), "13:00:00");
		Horario novo = anterior.aumentaHora(2);
		Assert.assertEquals("15:00:00", novo.toString());
	}
	
	@Test
	public void somaDiversosHorariosComBaseZerada() {
		Date dataReferente = new Date();
		String horario = HorarioUtil.calcula()
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:30:00"))
				.soma(Horario.getInstance(dataReferente, "02:45:00"))
				.soma(Horario.getInstance(dataReferente, "23:15:12"))
				.getResultado();
		Assert.assertEquals("53:30:12", horario);
	}
	
	@Test
	public void somaDeDiversosHorariosComBaseDefinida() {
		Date dataReferente = new Date();
		String horario1 = HorarioUtil.calcula()
				.soma(Horario.getInstance(dataReferente, "03:00:00"))
				.getResultado();
		Assert.assertEquals("03:00:00", horario1);		
		String horario2 = new CalculaHorario()
				.soma(Horario.getInstance(dataReferente, "23:00:00"))
				.soma(Horario.getInstance(dataReferente, "01:00:00"))
				.getResultado();
		Assert.assertEquals("24:00:00", horario2);
	}
		
	@Test
	public void subtraiDeDiversosHorariosComBaseDefinida() {
		Date dataReferente = new Date();
		String horario = HorarioUtil.calcula()
				.soma(Horario.getInstance(dataReferente, "10:00:00"))
				.subtrai(Horario.getInstance(dataReferente, "01:00:00"))
				.getResultado();
		Assert.assertEquals("09:00:00", horario);
	}
	
}
