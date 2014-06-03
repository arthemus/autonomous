package org.autonomous.functions;

import java.util.Date;
import java.util.HashMap;

/**
 * Analisa se um determinado veiculo esta ou estará de rodizio de acordo com sua
 * placa e uma data expecifica.
 * 
 * @author arthemus
 * @since 11/07/2013
 * @see Semana
 * 
 */
public class RodizioMunicipal {

	private final String placa;

	private static final HashMap<Semana, int[]> rodizioMunicipal = new HashMap<Semana, int[]>(5);

	static {
		rodizioMunicipal.put(Semana.SEGUNDA, new int[] { 1, 2 });
		rodizioMunicipal.put(Semana.TERCA, new int[] { 3, 4 });
		rodizioMunicipal.put(Semana.QUARTA, new int[] { 5, 6 });
		rodizioMunicipal.put(Semana.QUINTA, new int[] { 7, 8 });
		rodizioMunicipal.put(Semana.SEXTA, new int[] { 9, 0 });
	}

	/**
	 * Placa que deve ser analisada quanto ao seu dia de rodizio.
	 * 
	 * @param placa
	 */
	public RodizioMunicipal(final String placa) {
		this.placa = placa;
	}

	/**
	 * Verifica se uma determinada placa está em Rodizio na data atual.
	 * 
	 * @return
	 */
	public boolean isEmRodizioHoje() {
		return isEmRodizioPara(new Date());
	}

	/**
	 * Verifica se uma determinada placa está em rodizio para uma determinada data.
	 * 
	 * @param data
	 * @return
	 */
	public boolean isEmRodizioPara(Date data) {				
		int finalPlaca = Integer.parseInt(placa.substring(placa.length() - 1));		
		int[] finais = rodizioMunicipal.get(Semana.getDia(data));		
		for (int item : finais)
			if (item == finalPlaca)
				return Boolean.TRUE;		
		return Boolean.FALSE;
	}
}
