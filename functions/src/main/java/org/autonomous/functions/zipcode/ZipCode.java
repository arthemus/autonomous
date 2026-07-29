package org.autonomous.functions.zipcode;

import java.io.Serializable;

/**
 * Represents a CEP (Brazilian postal code).
 *
 * @author Fabio Franco Uechi
 */
public class ZipCode implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String number;
	private final String logradouro;
	private final String bairro;
	private final String locality;
	private final String uf;

	public ZipCode(String num, String logradouro, String bairro, String locality,
			String uf) {
		this.number = num;
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.locality = locality;
		this.uf = uf;
	}

	public String getNumber() {
		return number;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public String getLocality() {
		return locality;
	}

	public String getUf() {
		return uf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result
				+ ((locality == null) ? 0 : locality.hashCode());
		result = prime * result
				+ ((logradouro == null) ? 0 : logradouro.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
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
		ZipCode other = (ZipCode) obj;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (locality == null) {
			if (other.locality != null)
				return false;
		} else if (!locality.equals(other.locality))
			return false;
		if (logradouro == null) {
			if (other.logradouro != null)
				return false;
		} else if (!logradouro.equals(other.logradouro))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (uf == null) {
			if (other.uf != null)
				return false;
		} else if (!uf.equals(other.uf))
			return false;
		return true;
	}
}
