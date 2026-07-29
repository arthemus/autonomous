package org.autonomous.functions.validators;

/**
 * Enum representing the federative units (states) of Brazil. Initially used
 * for state registration (Inscricao Estadual) validation, but its use may
 * extend to other parts of the system.
 *
 * @author Walter Luiz Portugal
 * @since 29/04/2014
 * @see DocumentValidator
 */

public enum FederativeUnit {
	AC("Acre"),
	AL("Alagoas"),
	AM("Amazonas"),
	AP("Amapa"),
	BA("Bahia"),
	CE("Ceara"),
	DF("Distrito Federal"),
	ES("Espirito Santo"),
	GO("Goias"),
	MA("Maranhao"),
	MG("Minas Gerais"),
	MS("Mato Grosso do Sul"),
	MT("Mato Grosso"),
	PA("Para"),
	PB("Paraiba"),
	PE("Pernambuco"),
	PI("Piaui"),
	PR("Parana"),
	RJ("Rio de Janeiro"),
	RN("Rio Grande do Norte"),
	RO("Rondonia"),
	RR("Roraima"),
	RS("Rio Grande do Sul"),
	SC("Santa Catarina"),
	SE("Sergipe"),
	SP("Sao Paulo"),
	TO("Tocantins");

	private final String description;

	FederativeUnit(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	/**
	 * Returns the enum value based on the given string parameter.
	 *
	 * @param uf
	 *            The federative unit abbreviation (e.g. "SP", "RJ").
	 * @return The matching FederativeUnit enum constant.
	 * @throws Exception
	 *             if the abbreviation does not match any federative unit.
	 */
	public static FederativeUnit fromAbbreviation(String uf) throws Exception {
		try {
			return FederativeUnit.valueOf(uf);
		} catch (Exception e) {
			throw new Exception("Invalid Federative Unit. UF: " + uf);
		}

	}
}
