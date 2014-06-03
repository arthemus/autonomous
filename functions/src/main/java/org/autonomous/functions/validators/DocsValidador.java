package org.autonomous.functions.validators;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.caelum.stella.validation.ie.IEAcreValidator;
import br.com.caelum.stella.validation.ie.IEAlagoasValidator;
import br.com.caelum.stella.validation.ie.IEAmapaValidator;
import br.com.caelum.stella.validation.ie.IEAmazonasValidator;
import br.com.caelum.stella.validation.ie.IEBahiaValidator;
import br.com.caelum.stella.validation.ie.IECearaValidator;
import br.com.caelum.stella.validation.ie.IEDistritoFederalValidator;
import br.com.caelum.stella.validation.ie.IEEspiritoSantoValidator;
import br.com.caelum.stella.validation.ie.IEGoiasValidator;
import br.com.caelum.stella.validation.ie.IEMaranhaoValidator;
import br.com.caelum.stella.validation.ie.IEMatoGrossoDoSulValidator;
import br.com.caelum.stella.validation.ie.IEMatoGrossoValidator;
import br.com.caelum.stella.validation.ie.IEMinasGeraisValidator;
import br.com.caelum.stella.validation.ie.IEParaValidator;
import br.com.caelum.stella.validation.ie.IEParaibaValidator;
import br.com.caelum.stella.validation.ie.IEParanaValidator;
import br.com.caelum.stella.validation.ie.IEPernambucoValidator;
import br.com.caelum.stella.validation.ie.IEPiauiValidator;
import br.com.caelum.stella.validation.ie.IERioDeJaneiroValidator;
import br.com.caelum.stella.validation.ie.IERioGrandeDoNorteValidator;
import br.com.caelum.stella.validation.ie.IERioGrandeDoSulValidator;
import br.com.caelum.stella.validation.ie.IERondoniaValidator;
import br.com.caelum.stella.validation.ie.IERoraimaValidator;
import br.com.caelum.stella.validation.ie.IESantaCatarinaValidator;
import br.com.caelum.stella.validation.ie.IESaoPauloValidator;
import br.com.caelum.stella.validation.ie.IESergipeValidator;
import br.com.caelum.stella.validation.ie.IETocantinsValidator;

/**
 * Classe para validação de documentos: CNPJ, CPF e Inscrição Estadual. Outras validações deverão
 * ser centralizadas nessa classe.
 * 
 * Esta classe utiliza o componente da Caelum Stella Validation na versão 2.1.0
 * 
 * @author Walter Luiz Portugal
 * @since 29/04/2014
 *
 */

public final class DocsValidador {

	public static boolean isCnpj(String cnpj){
		
		/**
		 * Foi adicionado essa validação porque o que componente stella.validation não impede cnpj com 
		 * valor 0 (zero).
		 */
		if (cnpj.equals("00000000000000"))
			return false;
		
		try{
			CNPJValidator cnpjValidator = new CNPJValidator();
			
			cnpjValidator.assertValid(cnpj);
			
		} catch(InvalidStateException e){
			return false;
		}
		
		return true;
	}
	
	public static boolean isCpf(String cpf){	
		try{
			CPFValidator cpfValidator = new CPFValidator();
			
			cpfValidator.assertValid(cpf);			
			
		} catch (InvalidStateException err){
			return false;
		}
		
		return true;
	}
	
	public static boolean isInscricaoEstatual(UnidadeFederativa uf, String inscEstadual){
		return isInscricaoEstatual(uf, inscEstadual, false);
	}
	
	public static boolean isInscricaoEstatual(UnidadeFederativa uf, String inscEstadual, boolean ieFormatada){
		/**
		 * Foi adicionado essa validação porque o que componente stella.validation não impede Inscrição Estadual com 
		 * valor 0 (zero). Mesmo problema com CNPJ.
		 */
		if (inscEstadual.contains("0000000"))
			return false;
		
		try{
			switch (uf) {
			case AC: {
				IEAcreValidator acreValidator = new IEAcreValidator(ieFormatada);
				acreValidator.assertValid(inscEstadual);		
			} break;
			case AL:{
				IEAlagoasValidator alagoasValidator = new IEAlagoasValidator(ieFormatada);
				alagoasValidator.assertValid(inscEstadual);
			} break;
			case AM:{
				IEAmazonasValidator amazonasValidator = new IEAmazonasValidator(ieFormatada);
				amazonasValidator.assertValid(inscEstadual);
			} break;
			case AP:{
				IEAmapaValidator amapaValidator = new IEAmapaValidator(ieFormatada);
				amapaValidator.assertValid(inscEstadual);
			} break;
			case BA:{
				IEBahiaValidator bahiaValidator = new IEBahiaValidator(ieFormatada);
				bahiaValidator.assertValid(inscEstadual);
			} break;
			case CE:{
				IECearaValidator cearaValidator = new IECearaValidator(ieFormatada);
				cearaValidator.assertValid(inscEstadual);
			} break;
			case DF:{
				IEDistritoFederalValidator distritoValidator = new IEDistritoFederalValidator(ieFormatada);
				distritoValidator.assertValid(inscEstadual);
			} break;
			case ES:{
				IEEspiritoSantoValidator espiritoSantoValidator = new IEEspiritoSantoValidator(ieFormatada);
				espiritoSantoValidator.assertValid(inscEstadual);
			} break;
			case GO:{
				IEGoiasValidator goiasValidator = new IEGoiasValidator(ieFormatada);
				goiasValidator.assertValid(inscEstadual);
			} break;
			case MA:{
				IEMaranhaoValidator maranhaoValidator = new IEMaranhaoValidator(ieFormatada);
				maranhaoValidator.assertValid(inscEstadual);
			} break;
			case MG:{
				IEMinasGeraisValidator minasGeraisValidator = new IEMinasGeraisValidator(ieFormatada);
				minasGeraisValidator.assertValid(inscEstadual);
			} break;
			case MS:{
				IEMatoGrossoDoSulValidator matoGrossoDoSulValidator = new IEMatoGrossoDoSulValidator(ieFormatada);
				matoGrossoDoSulValidator.assertValid(inscEstadual);
			} break;
			case MT:{
				IEMatoGrossoValidator matoGrossoValidator = new IEMatoGrossoValidator(ieFormatada);
				matoGrossoValidator.assertValid(inscEstadual);
			} break;
			case PA:{
				IEParaValidator paraValidator = new IEParaValidator(ieFormatada);
				paraValidator.assertValid(inscEstadual);
			} break;
			case PB:{
				IEParaibaValidator paraibaValidator = new IEParaibaValidator(ieFormatada);
				paraibaValidator.assertValid(inscEstadual);
			} break;
			case PE:{
				IEPernambucoValidator pernambucoValidator = new IEPernambucoValidator(ieFormatada);
				pernambucoValidator.assertValid(inscEstadual);				
			} break;
			case PI:{
				IEPiauiValidator piauiValidator = new IEPiauiValidator(ieFormatada);
				piauiValidator.assertValid(inscEstadual);
			} break;
			case PR:{
				IEParanaValidator paranaValidator = new IEParanaValidator(ieFormatada);
				paranaValidator.assertValid(inscEstadual);
			} break;
			case RJ:{
				IERioDeJaneiroValidator rioDeJaneiroValidator = new IERioDeJaneiroValidator(ieFormatada);
				rioDeJaneiroValidator.assertValid(inscEstadual);
			} break;
			case RN:{
				IERioGrandeDoNorteValidator rioGrandeDoNorteValidator = new IERioGrandeDoNorteValidator(ieFormatada);
				rioGrandeDoNorteValidator.assertValid(inscEstadual);
			} break;
			case RO:{
				IERondoniaValidator rondoniaValidator = new IERondoniaValidator(ieFormatada);
				rondoniaValidator.assertValid(inscEstadual);
			} break;
			case RR:{
				IERoraimaValidator roraimaValidator = new IERoraimaValidator(ieFormatada);
				roraimaValidator.assertValid(inscEstadual);
			} break;
			case RS:{
				IERioGrandeDoSulValidator rioGrandeDoSulValidator = new IERioGrandeDoSulValidator(ieFormatada);
				rioGrandeDoSulValidator.assertValid(inscEstadual);
			} break;
			case SC:{
				IESantaCatarinaValidator santaCatarinaValidator = new IESantaCatarinaValidator(ieFormatada);
				santaCatarinaValidator.assertValid(inscEstadual);
			} break;
			case SE:{
				IESergipeValidator sergipeValidator = new IESergipeValidator(ieFormatada);
				sergipeValidator.assertValid(inscEstadual);
			} break;
			case SP:{
				IESaoPauloValidator saoPauloValidator = new IESaoPauloValidator(ieFormatada);
				saoPauloValidator.assertValid(inscEstadual);
			} break;
			case TO:{
				IETocantinsValidator tocantinsValidator = new IETocantinsValidator(ieFormatada);
				tocantinsValidator.assertValid(inscEstadual);
			}
			default:
				return false;
			}
			
		} catch (InvalidStateException e){
			return false;
		}		
		
		return true;
	}
}
