package org.autonomous.functions.security;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CryptoObjectTest {

	public class Dados{
		
		private int codigo;
		private String nome;		
		private double salario;
		
		public int getCodigo() {
			return codigo;
		}
		public void setCodigo(int codigo) {
			this.codigo = codigo;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public double getSalario() {
			return salario;
		}
		public void setSalario(double salario) {
			this.salario = salario;
		}
		private CryptoObjectTest getOuterType() {
			return CryptoObjectTest.this;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + codigo;
			result = prime * result + ((nome == null) ? 0 : nome.hashCode());
			long temp;
			temp = Double.doubleToLongBits(salario);
			result = prime * result + (int) (temp ^ (temp >>> 32));
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
			Dados other = (Dados) obj;
			if (codigo != other.codigo)
				return false;
			if (nome == null) {
				if (other.nome != null)
					return false;
			} else if (!nome.equals(other.nome))
				return false;
			if (Double.doubleToLongBits(salario) != Double
					.doubleToLongBits(other.salario))
				return false;
			return true;
		}
	}
	
	private Dados _dados;
	
	@Before
	public void SetUp(){
		_dados = new Dados();
		
		_dados.setCodigo(1);
		_dados.setNome("Arthur Luiz");
		_dados.setSalario(100000);
	}
	
	
	@Test
	public void testCryptObject() throws Exception {
		CryptoObject<Dados> crypt = new CryptoObject<CryptoObjectTest.Dados>("f9ZB44Vyy02UOi1q8pAGfA==");
		
		try {
			String strEncrypt = crypt.doEncrypt(_dados);
			
			Dados dadosDecrypted = crypt.doDecript(strEncrypt, Dados.class);
			
			Assert.assertTrue(_dados.equals(dadosDecrypted));
		} catch (CryptoException e) {
			e.printStackTrace();
		}
	}
}
