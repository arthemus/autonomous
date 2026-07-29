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
 * Class for document validation: CNPJ, CPF, and state registration (Inscricao
 * Estadual). Other validations should be centralized in this class.
 *
 * This class uses the Caelum Stella Validation component version 2.1.0.
 *
 * @author Walter Luiz Portugal
 * @since 29/04/2014
 */

public final class DocumentValidator {

	public static boolean isCnpj(String cnpj) {

		/**
		 * This validation was added because the stella.validation component
		 * does not prevent a CNPJ with a value of 0 (zero).
		 */
		if (cnpj.equals("00000000000000"))
			return false;

		try {
			CNPJValidator cnpjValidator = new CNPJValidator();

			cnpjValidator.assertValid(cnpj);

		} catch (InvalidStateException e) {
			return false;
		}

		return true;
	}

	public static boolean isCpf(String cpf) {
		try {
			CPFValidator cpfValidator = new CPFValidator();

			cpfValidator.assertValid(cpf);

		} catch (InvalidStateException err) {
			return false;
		}

		return true;
	}

	public static boolean isStateRegistration(FederativeUnit uf, String stateRegistration) {
		return isStateRegistration(uf, stateRegistration, false);
	}

	public static boolean isStateRegistration(FederativeUnit uf, String stateRegistration, boolean formattedIE) {
		/**
		 * This validation was added because the stella.validation component
		 * does not prevent a state registration with a value of 0 (zero).
		 * Same problem as with CNPJ.
		 */
		if (stateRegistration.contains("0000000"))
			return false;

		try {
			switch (uf) {
			case AC: {
				IEAcreValidator acreValidator = new IEAcreValidator(formattedIE);
				acreValidator.assertValid(stateRegistration);
			} break;
			case AL: {
				IEAlagoasValidator alagoasValidator = new IEAlagoasValidator(formattedIE);
				alagoasValidator.assertValid(stateRegistration);
			} break;
			case AM: {
				IEAmazonasValidator amazonasValidator = new IEAmazonasValidator(formattedIE);
				amazonasValidator.assertValid(stateRegistration);
			} break;
			case AP: {
				IEAmapaValidator amapaValidator = new IEAmapaValidator(formattedIE);
				amapaValidator.assertValid(stateRegistration);
			} break;
			case BA: {
				IEBahiaValidator bahiaValidator = new IEBahiaValidator(formattedIE);
				bahiaValidator.assertValid(stateRegistration);
			} break;
			case CE: {
				IECearaValidator cearaValidator = new IECearaValidator(formattedIE);
				cearaValidator.assertValid(stateRegistration);
			} break;
			case DF: {
				IEDistritoFederalValidator distritoValidator = new IEDistritoFederalValidator(formattedIE);
				distritoValidator.assertValid(stateRegistration);
			} break;
			case ES: {
				IEEspiritoSantoValidator espiritoSantoValidator = new IEEspiritoSantoValidator(formattedIE);
				espiritoSantoValidator.assertValid(stateRegistration);
			} break;
			case GO: {
				IEGoiasValidator goiasValidator = new IEGoiasValidator(formattedIE);
				goiasValidator.assertValid(stateRegistration);
			} break;
			case MA: {
				IEMaranhaoValidator maranhaoValidator = new IEMaranhaoValidator(formattedIE);
				maranhaoValidator.assertValid(stateRegistration);
			} break;
			case MG: {
				IEMinasGeraisValidator minasGeraisValidator = new IEMinasGeraisValidator(formattedIE);
				minasGeraisValidator.assertValid(stateRegistration);
			} break;
			case MS: {
				IEMatoGrossoDoSulValidator matoGrossoDoSulValidator = new IEMatoGrossoDoSulValidator(formattedIE);
				matoGrossoDoSulValidator.assertValid(stateRegistration);
			} break;
			case MT: {
				IEMatoGrossoValidator matoGrossoValidator = new IEMatoGrossoValidator(formattedIE);
				matoGrossoValidator.assertValid(stateRegistration);
			} break;
			case PA: {
				IEParaValidator paraValidator = new IEParaValidator(formattedIE);
				paraValidator.assertValid(stateRegistration);
			} break;
			case PB: {
				IEParaibaValidator paraibaValidator = new IEParaibaValidator(formattedIE);
				paraibaValidator.assertValid(stateRegistration);
			} break;
			case PE: {
				IEPernambucoValidator pernambucoValidator = new IEPernambucoValidator(formattedIE);
				pernambucoValidator.assertValid(stateRegistration);
			} break;
			case PI: {
				IEPiauiValidator piauiValidator = new IEPiauiValidator(formattedIE);
				piauiValidator.assertValid(stateRegistration);
			} break;
			case PR: {
				IEParanaValidator paranaValidator = new IEParanaValidator(formattedIE);
				paranaValidator.assertValid(stateRegistration);
			} break;
			case RJ: {
				IERioDeJaneiroValidator rioDeJaneiroValidator = new IERioDeJaneiroValidator(formattedIE);
				rioDeJaneiroValidator.assertValid(stateRegistration);
			} break;
			case RN: {
				IERioGrandeDoNorteValidator rioGrandeDoNorteValidator = new IERioGrandeDoNorteValidator(formattedIE);
				rioGrandeDoNorteValidator.assertValid(stateRegistration);
			} break;
			case RO: {
				IERondoniaValidator rondoniaValidator = new IERondoniaValidator(formattedIE);
				rondoniaValidator.assertValid(stateRegistration);
			} break;
			case RR: {
				IERoraimaValidator roraimaValidator = new IERoraimaValidator(formattedIE);
				roraimaValidator.assertValid(stateRegistration);
			} break;
			case RS: {
				IERioGrandeDoSulValidator rioGrandeDoSulValidator = new IERioGrandeDoSulValidator(formattedIE);
				rioGrandeDoSulValidator.assertValid(stateRegistration);
			} break;
			case SC: {
				IESantaCatarinaValidator santaCatarinaValidator = new IESantaCatarinaValidator(formattedIE);
				santaCatarinaValidator.assertValid(stateRegistration);
			} break;
			case SE: {
				IESergipeValidator sergipeValidator = new IESergipeValidator(formattedIE);
				sergipeValidator.assertValid(stateRegistration);
			} break;
			case SP: {
				IESaoPauloValidator saoPauloValidator = new IESaoPauloValidator(formattedIE);
				saoPauloValidator.assertValid(stateRegistration);
			} break;
			case TO: {
				IETocantinsValidator tocantinsValidator = new IETocantinsValidator(formattedIE);
				tocantinsValidator.assertValid(stateRegistration);
			} break;
			default:
				return false;
			}

		} catch (InvalidStateException e) {
			return false;
		}

		return true;
	}
}
