package org.autonomous.functions;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;


public class CalendarioTest {
	
	@Test
	public void TesteSomarDiasDataAtual(){
		Calendario c = Calendario.getInstance().somarDiasDataAtual(30);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 30);
		
		Assert.assertEquals(Funcoes.getOnlyDate(calendar.getTime()), c.getData());
	}
	
	@Test
	public void TesteSubtrairDiasDataAtual(){
		Calendario c = Calendario.getInstance().subtrairDiasDataAtual(20);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -20);
		
		Assert.assertEquals(Funcoes.getOnlyDate(calendar.getTime()), c.getData());
	}
	
	@Test
	public void TesteSubtrairDiasDataDefinida() throws ParseException{
	
		Calendario c = Calendario.getInstance().subtrairDias(Funcoes.getDateBy("02/05/2014"), 20);
		
		Assert.assertEquals("12/04/2014", c.toString());
	}
	
	@Test
	public void TesteSomarDiasDataDefinida() throws ParseException{
		Calendario c = Calendario.getInstance().somarDias(Funcoes.getDateBy("02/05/2014"), 20);
		
		Assert.assertEquals("22/05/2014", c.toString());
	}

}
