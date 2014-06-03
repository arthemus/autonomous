package org.autonomous.functions;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Utilitário para gerenciamento dos dias da Semana.
 * 
 * @author arthemus
 * @since 28/05/2013
 */
public enum Semana {

	DOMINGO("Domingo"),

	SEGUNDA("Segunda-Feira"),

	TERCA("Terça-Feira"),

	QUARTA("Quarta-Feira"),

	QUINTA("Quinta-Feira"),

	SEXTA("Sexta-Feira"),

	SABADO("Sábado");

	public static Semana getDia(Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		return Semana.getDia(calendar.get(Calendar.DAY_OF_WEEK));
	}

	/**
	 * Retorna o dia da semana com base em um indice.
	 * 
	 * @author Arthemus C. Moreira
	 * @param index
	 *            Codigo do dia obtido, por exemplo, por meio da constante
	 *            Calendar.DAY_OF_WEEK.
	 * @return Dia da semana.
	 */
	public static Semana getDia(Integer index) {
		switch (index) {
		case 1:
			return DOMINGO;
		case 2:
			return SEGUNDA;
		case 3:
			return TERCA;
		case 4:
			return QUARTA;
		case 5:
			return QUINTA;
		case 6:
			return SEXTA;
		case 7:
			return SABADO;
		default:
			return SEGUNDA;
		}
	}

	public static Semana getDia(String index) {
		return Semana.getDia(Integer.parseInt(index));
	}

	/**
	 * Retorna o dia da semana atual.
	 * 
	 * @author Arthemus C. Moreira
	 * @return Dia da semana.
	 */
	public static Semana hoje() {
		Calendar calendar = new GregorianCalendar();
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		case 1:
			return DOMINGO;
		case 2:
			return SEGUNDA;
		case 3:
			return TERCA;
		case 4:
			return QUARTA;
		case 5:
			return QUINTA;
		case 6:
			return SEXTA;
		case 7:
			return SABADO;
		default:
			return SEGUNDA;
		}
	}

	private final String descricao;

	Semana(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public Integer getIndex() {
		switch (this) {
		case DOMINGO:
			return 1;
		case SEGUNDA:
			return 2;
		case TERCA:
			return 3;
		case QUARTA:
			return 4;
		case QUINTA:
			return 5;
		case SEXTA:
			return 6;
		case SABADO:
			return 7;
		default:
			return 2;
		}
	}
}
