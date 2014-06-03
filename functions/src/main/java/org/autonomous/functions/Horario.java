package org.autonomous.functions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * Classe para controle de tempo, hora, minuto e segundo.
 * 
 * @author Arthemus C. Moreira
 * @since 16/01/2013
 *  
 */
public final class Horario {

	private static final String FORMATO = "HH:mm:ss";
	
	/**
	 * Constantes temporáis.
	 */
	public static final int SECOND = 1000;	
	public static final int MINUTE = (60 * SECOND);	
	public static final int HOUR = (60 * MINUTE);	
	public static final int DAY = (24 * HOUR);

	/**
	 * Formato original de um horario, Hora : Minuto : Segundo 
	 * Por exemplo: 23:45:21
	 */
	public static final SimpleDateFormat FORMAT = new SimpleDateFormat(FORMATO);

	/**
	 * Para obter um diferencial do horário.
	 * 
	 * @param millis Horário em milisegundos a ser calculado.
	 * @param param DAY, HOUR, MINUTE ou SECOND
	 * @return Valor presente no horário.
	 */
	public static final long get(final long millis, final int param) {
		return (millis / param);
	}
	
	/**
	 * Converte um valor de milesegundos para um formato 
	 * de tempo separando as horas, minutos e segundos mas
	 * desconsiderando os dias. 
	 * 
	 * @param milliseconds Valor a ser convertido.
	 * @return horário String no formado HH:mm:ss
	 */
	public static final String toStringOffDays(final long milliseconds) {
		
		long millis = milliseconds;
		
		long hours = TimeUnit.MILLISECONDS.toHours(millis);        
		millis -= TimeUnit.HOURS.toMillis(hours);
        
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);        
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        final String _sep = ":";
        
        StringBuilder sb = new StringBuilder(64);
        sb.append(Funcoes.getZerosAEsquerda(String.valueOf(hours), 2));
        sb.append(_sep);
        sb.append(Funcoes.getZerosAEsquerda(String.valueOf(minutes), 2));
        sb.append(_sep);
        sb.append(Funcoes.getZerosAEsquerda(String.valueOf(seconds), 2));
		
		return sb.toString();
	}
	
	/**
	 * Converte um período de milesegundos para um formato tempo,
	 * separando as horas, minutos e segudos e considerando os dias do período.
	 * 
	 * @param milliseconds Valor a ser convertido.
	 * @return String no formato HH:mm:ss
	 */
	public static final String toStringWithDays(final long milliseconds) {
		
		long millis = milliseconds;
		
		long days = TimeUnit.MILLISECONDS.toDays(millis);        
		millis -= TimeUnit.DAYS.toMillis(days);
		
		long hours = TimeUnit.MILLISECONDS.toHours(millis);        
		millis -= TimeUnit.HOURS.toMillis(hours);
        
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);        
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        final String _sep = ":";
        
        StringBuilder sb = new StringBuilder(64);
        sb.append(Funcoes.getZerosAEsquerda(String.valueOf(hours), 2));
        sb.append(_sep);
        sb.append(Funcoes.getZerosAEsquerda(String.valueOf(minutes), 2));
        sb.append(_sep);
        sb.append(Funcoes.getZerosAEsquerda(String.valueOf(seconds), 2));
		
		return sb.toString();
	}
	
	/**
	 * Horario definido em mili-segundos.
	 */
	public final long horario;
	
	public static final Horario getInstance() {
		return new Horario(new Date());
	}
	
	public static final Horario getInstance(final long dateInMilleSeconds) {
		return new Horario(dateInMilleSeconds);
	}
	
	public static final Horario getInstance(final Date date) {
		return new Horario(date);
	}
	
	public static final Horario getInstance(final Date dataBase, final String horarioBase) {		
		return new Horario(Funcoes.getOnlyDate(dataBase), horarioBase);
	}
	
	private Horario(final Date date) {		
		this.horario = date.getTime();
	}
	
	private Horario(final long horario) {
		this.horario = horario;
	}

	private Horario(final Date dataBase, final String horarioBase) {		
		Calendar calendar = new GregorianCalendar();		
		calendar.setTime(dataBase);
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horarioBase.substring(0, 2)));
		calendar.set(Calendar.MINUTE, Integer.parseInt(horarioBase.substring(3, 5)));
		calendar.set(Calendar.SECOND, Integer.parseInt(horarioBase.substring(6, 8)));		
		this.horario = calendar.getTimeInMillis();
	}

	/**
	 * Diminui a hora.
	 * 
	 * @param quantidade de horas a serem removidas do horário.
	 * @return Novo horário
	 */
	public Horario diminuiHora(final int quantidade) {
		validaValorNegativo(quantidade);
		return aumentaHora(quantidade * -1);
	}

	/**
	 * Diminui os minutos.
	 * 
	 * @param quantidade de minutos a serem removidos do horário.
	 * @return Novo horário
	 */
	public Horario diminuiMinuto(final int quantidade) {
		validaValorNegativo(quantidade);
		return aumentaMinuto(quantidade * -1);		
	}

	/**
	 * Retorna um objeto caledar com base no horário base.
	 * 
	 * @return
	 */
	public Calendar getGregorianCalendar() {
		Calendar atual = new GregorianCalendar();
		atual.setTimeInMillis(horario);		
		return (Calendar) atual.clone();
	}

	/**
	 * Hora do horário
	 * @return Se horario = 12:35:23, retorna 12.
	 */
	public int getHora() {
		Calendar atual = getGregorianCalendar();		
		return atual.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Minutos do horario.
	 * @return Se horario = 12:35:23, retorna 35.
	 */
	public int getMinuto() {
		Calendar atual = getGregorianCalendar();		
		return atual.get(Calendar.MINUTE);
	}

	/**
	 * Segundos do horario.
	 * @return Se horario = 12:35:23, retorna 23.
	 */
	public int getSegundo() {
		Calendar atual = getGregorianCalendar();		
		return atual.get(Calendar.SECOND);
	}
	
	/**
	 * Milisegundos do horario.
	 * @return 
	 */
	public int getMilisegundo() {
		Calendar atual = getGregorianCalendar();		
		return atual.get(Calendar.MILLISECOND);
	}
	
	/**
	 * Obtem um horário aleatório de acorcom uma divergencia.
	 * Ex: Divergencia = 15 (15 minutos)
	 * Horario = 15:30:12
	 * Retorno entre 15:15:12 e 15:45:12
	 * 
	 * @param divergencia
	 *            Diferença entre o horário atual e o novo horario.
	 * @return Novo horário
	 */
	public String getHorarioAleatorio(final int divergencia) {

		if (divergencia < 0)
			throw new RuntimeException("O valor de tolerância deve ser maior que Zero.");

		/*
		 * Se a tolerancia for 5 por exemplo, quais os numeros que compoem os
		 * dois polos de 5, positivo e negativo? -5 -4 -3 -2 -1 0 1 2 3 4 5 
		 * que é igual a 11 elementos: 
		 * Formula = (5 * 2) + 1
		 */
		int rand = (int) (Math.random() * (divergencia * 2) + 1);

		int minuto = divergencia - rand;

		Date data = new Date(horario);

		Calendar atual = new GregorianCalendar();

		// Obtem o horario a partir da data configurada...
		atual.setTime(data);

		// adiciona a nova quantidade de tempo...
		atual.add(Calendar.MINUTE, minuto);

		return FORMAT.format(atual.getTime());
	}

	/**
	 * Verifica se horario expoente é ou não maior que 
	 * o horário da instancia.
	 * 
	 * @param horarioExpoente
	 * @return True ou False.
	 */
	public boolean isMaior(final Horario horarioExpoente) {

		Calendar calendarBase = getGregorianCalendar();
		
		Calendar calendarExp = new GregorianCalendar();
		calendarExp.setTimeInMillis(horarioExpoente.horario);		

		/**
		 * Base > Exp = 1 
		 * Base == Exp = 0 
		 * Base < Exp = -1
		 */
		int result = calendarBase.compareTo(calendarExp);

		if (result == 1)
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}

	/**
	 * Eleva o valor de horas do horário atual.
	 * 
	 * @param quantidade de horas a serem almentadas.
	 * @return Novo horario.
	 */
	public Horario aumentaHora(final int quantidade) {
		Calendar atual = getGregorianCalendar();
		atual.add(Calendar.HOUR_OF_DAY, quantidade);
		return new Horario(atual.getTime());		
	}

	/**
	 * Eleva o valor de minutos do horário atual.
	 * 
	 * @param quantidade
	 * @return Novo horario.
	 */
	public Horario aumentaMinuto(final int quantidade) {
		Calendar atual = getGregorianCalendar();
		atual.add(Calendar.MINUTE, quantidade);
		return new Horario(atual.getTime());		
	}

	private void validaValorNegativo(final int valor) {
		if (valor < 0) 
			throw new NumberFormatException("O valor não pode ser negativo");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (horario ^ (horario >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Horario other = (Horario) obj;
		if (horario != other.horario)
			return false;
		return true;
	}

	@Override
	public String toString() {		
		Calendar temp = getGregorianCalendar();
		return FORMAT.format(temp.getTime());
	}
}
