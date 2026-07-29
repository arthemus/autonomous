package org.autonomous.functions.security;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CryptoObjectTest {

	public class Data{

		private int code;
		private String name;
		private double salary;

		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public double getSalary() {
			return salary;
		}
		public void setSalary(double salary) {
			this.salary = salary;
		}
		private CryptoObjectTest getOuterType() {
			return CryptoObjectTest.this;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + code;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			long temp;
			temp = Double.doubleToLongBits(salary);
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
			Data other = (Data) obj;
			if (code != other.code)
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (Double.doubleToLongBits(salary) != Double
					.doubleToLongBits(other.salary))
				return false;
			return true;
		}
	}

	private Data _data;

	@Before
	public void setUp(){
		_data = new Data();

		_data.setCode(1);
		_data.setName("Arthur Luiz");
		_data.setSalary(100000);
	}


	@Test
	public void shouldEncryptAndDecryptObject() throws Exception {
		Crypto crypto = new Crypto();
		String freshKey = crypto.getPrivateKey();
		CryptoObject<Data> crypt = new CryptoObject<CryptoObjectTest.Data>(freshKey);

		try {
			String encrypted = crypt.doEncrypt(_data);

			Data decrypted = crypt.doDecrypt(encrypted, Data.class);

			Assert.assertTrue(_data.equals(decrypted));
		} catch (CryptoException e) {
			throw new RuntimeException(e);
		}
	}
}
