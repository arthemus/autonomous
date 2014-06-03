package org.autonomous.functions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe para tratamento de Datas - Dia, Mês e Ano.
 * 
 * Essa classe já trabalha com o formado de data Brasileiro.
 * 
 * @author Walter
 * @since 02/05/2014
 */

public final class Calendario {

	private static final String FORMATO = "dd/MM/yyyy";
	
	private final Date _data;
	
	public static final SimpleDateFormat FORMAT = new SimpleDateFormat(FORMATO);
	
	public static final Calendario getInstance(){
		return new Calendario(new Date());
	}
	
	private Calendario(Date data){
		_data = data;
	}
	
	public Date getData(){
		return Funcoes.getOnlyDate(_data);
	}
	
	/**
	 * Calcula uma nova data somando dias a data corrente.
	 * 
	 * A partir de Calendario.getData() se busca o resultado.
	 * 
	 * @param quantidadeDias
	 * @return Calendario
	 */
	
	public Calendario somarDiasDataAtual(int quantidadeDias){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, quantidadeDias);
		
		return new Calendario(calendar.getTime());
	}
	
	/**
	 * Calcula uma nova data subtraindo dias a data corrente.
	 * 
	 * A partir de Calendario.getData() se busca o resultado.
	 * 
	 * @param quantidadeDias
	 * @return Calendario
	 */
	
	public Calendario subtrairDiasDataAtual(int quantidadeDias){
		return somarDiasDataAtual(quantidadeDias * -1);
	}
	
	/**
	 * Calcula uma nova data somando dias a data passada como parâmetro.
	 * 
	 * A partir de Calendario.getData() se busca o resultado.
	 * 
	 * @param quantidadeDias
	 * @return Calendario
	 */
	
	public Calendario somarDias(Date data, int quantidadeDias){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		calendar.add(Calendar.DATE, quantidadeDias);
		
		return new Calendario(calendar.getTime());
	}
	
	/**
	 * Calcula uma nova data subtraindo dias a data passada como parâmetro.
	 * 
	 * A partir de Calendario.getData() se busca o resultado.
	 * 
	 * @param quantidadeDias
	 * @return Calendario
	 */
	public Calendario subtrairDias(Date data, int quantidadeDias){
		return somarDias(data, quantidadeDias * -1);
	}
	
	/**
	 * Retorna a data no formato string dd/MM/yyyy.	
	 */
	@Override
	public String toString(){	
		return FORMAT.format(getData());
		
	}
}
