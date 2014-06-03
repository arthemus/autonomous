package org.autonomous.functions.validators;

/**
 * Enum com as unidades federativas do Brasil. Utilizado, inicialmente, na validação da Inscrição 
 * Estadual, mas sua utilização poderá se extender a outras partes do sistema.
 * 
 * @author Walter Luiz Portugal
 * @since 29/04/2014
 * @see DocsValidador
 */

public enum UnidadeFederativa {
	AC("Acre"),
	AL("Alagoas"),
	AM("Amazonas"),
	AP("AMAPA"),
	BA("BAHIA"),
	CE("CEARA"),
	DF("DISTRITO FEDERAL"),
	ES("ESPIRITO SANTO"),
	GO("GOIAS"),
	MA("MARANHAO"),
	MG("MINAS GERAIS"),
	MS("MATO GROSSO DO SUL"),
	MT("MATO GROSSO"),
	PA("PARA"),
	PB("PARAIBA"),
	PE("PERNAMBUCO"),
	PI("PIAUI"),
	PR("PARANA"),
	RJ("RIO DE JANEIRO"),
	RN("RIO GRANDE DO NORTE"),
	RO("RONDONIA"),
	RR("RORAIMA"),
	RS("RIO GRANDE DO SUL"),
	SC("SANTA CATARINA"),
	SE("SERGIPE"),
	SP("SAO PAULO"),
	TO("TOCANTINS");
	
	private final String _descricao;
	
	UnidadeFederativa(String descricao){
		this._descricao = descricao;
	}
	
	public String getDescricao() {
		return _descricao;
	}
	
	/**
	 * Retorna o valor enum com base no parâmetro string que foi passado.
	 * @param uf
	 * @return
	 * @throws Exception 
	 */	
	public static UnidadeFederativa getUfEnum(String uf) throws Exception{
		try{
			return UnidadeFederativa.valueOf(uf);
		} catch (Exception e){
			throw new Exception("Unidade Federativa inválida. UF: " + uf);
		}
		
	}
}
