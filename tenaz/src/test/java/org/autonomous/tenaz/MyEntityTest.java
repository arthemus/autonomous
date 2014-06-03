package org.autonomous.tenaz;

import static org.junit.Assert.assertEquals;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.autonomous.tenaz.hibernate.MyEntity;
import org.junit.Test;

/**
 * 
 * @author arthemus
 * @since 16/05/2013
 */
public class MyEntityTest {

	@Entity
	@Table(name = "TESTE_TABLE")
	class Teste {

		@Id
		private Integer id;
		@Column
		private String nome;

		private Teste(Integer id, String nome) {
			this.id = id;
			this.nome = nome;
		}

		public Integer getId() {
			return id;
		}

		public String getNome() {
			return nome;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}
	}

	@Test
	public void obtemUmaReferenciaAClasseVerdadeira() {
		Object object = new Teste(2, "Arthemus");
		assertEquals(Teste.class, object.getClass());
	}

	@Test
	public void verificaAClasseEmSi() {
		Object object = new Teste(25, "Teste Valor");
		MyEntity entity = new MyEntity(object);
		assertEquals(object.getClass(), entity.getRealClass());
	}

	@Test
	public void verificaNomeDoCampoID() {
		Object object = new Teste(5, "Teste Campo");
		MyEntity entity = new MyEntity(object);
		assertEquals("id", entity.getCampoId());
	}

	@Test
	public void verificaValorDoCampoID() {
		Object object = new Teste(25, "Teste Valor");
		MyEntity entity = new MyEntity(object);
		assertEquals(25, entity.getValueId());
	}

	@Test
	public void obtemONomeDaTabela() {
		assertEquals("TESTE_TABLE",
				MyEntity.getNomeTabelaRelacional(Teste.class));
	}
}
