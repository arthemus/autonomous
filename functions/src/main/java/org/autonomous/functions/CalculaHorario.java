package org.autonomous.functions;

import java.util.Calendar;
import java.util.Date;

/**
 * Classe para realizar a soma de micro horários como abonos e horas extras.
 * 
 * A idéia é poder calcular entre diferentes períodos a diferença entre os
 * horários por exemplo, entre a 10:30:00 e as 18:00:00 existe um período de 8
 * horas e 30 minutos.
 * 
 * Ainda não é possível soma horários que ultrapasse 24 horas como por exemplo,
 * uma String como 43:00:00 representando um horário de 43 horas. Futuralmente
 * essa classe será melhorada para contemplar o calculo de períodos maiores como
 * dias, meses e anos.
 * 
 * Uma alteranativa a essa classe seria trabalhar com a API JodaTime:
 * {@link http://joda-time.sourceforge.net/}
 * 
 * @author arthemus
 * @since 15/07/2013
 * @see Horario
 * @see HorarioUtil
 */
public class CalculaHorario {

	private final Calendar calendar;

	public CalculaHorario() {		
		Horario temp = Horario.getInstance(new Date(), "00:00:00");		
		this.calendar = temp.getGregorianCalendar();			
		long millis = this.calendar.getTimeInMillis() - temp.horario;
		this.calendar.setTimeInMillis(millis);
	}

	public CalculaHorario soma(final Horario horario) {
		calendar.add(Calendar.HOUR_OF_DAY, horario.getHora());
		calendar.add(Calendar.MINUTE, horario.getMinuto());
		calendar.add(Calendar.SECOND, horario.getSegundo());
		return this;
	}

	public CalculaHorario subtrai(final Horario horario) {
		calendar.add(Calendar.HOUR_OF_DAY, horario.getHora() * -1);
		calendar.add(Calendar.MINUTE, horario.getMinuto() * -1);
		calendar.add(Calendar.SECOND, horario.getSegundo() * -1);
		return this;
	}

	public String getResultado() {
		long millis = calendar.getTimeInMillis();
		return Horario.toStringOffDays(millis);
	}
}