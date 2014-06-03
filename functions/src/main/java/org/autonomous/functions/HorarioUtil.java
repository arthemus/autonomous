package org.autonomous.functions;

/**
 * Utilitários para trabalhar com horários.
 * 
 * @author arthemus
 * @since 12/07/2013
 * @see Horario
 * @see CalculaHorario
 */
public class HorarioUtil {

	/**
	 * Calcula a diferença entre horarios, ajuda a verifica quantas horas foram
	 * gastas entre determinadas marcações de tempo.
	 * 
	 * @param horarioInicial, horarioFinal Periodo a ser calculado.
	 * @return Um horário de referença. O retorno desse método não deve ser
	 *         tratado como uma determinada "hora" do dia mas sim uma
	 *         "quantidade" de horas calculada dentro de um peŕiodo.
	 */
	public static String between(final Horario horarioInicial,
			final Horario horarioFinal) {
		long millis = (horarioFinal.horario - horarioInicial.horario);
		return Horario.toStringOffDays(millis);
	}
	
	/**
	 * Retorna uma nova instancia da classe CalculaHorario para
	 * somar ou subtrair horários.
	 * 
	 * @param horario Horario a ser calculado
	 * @return Nova instancia da classe {@link CalculaHorario}
	 */
	public static CalculaHorario calcula() {
		return new CalculaHorario();
	}
}