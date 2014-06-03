package org.autonomous.tenaz.commons;

import java.util.Collection;


/**
 * Padrões de uma classe responsavel pelo cadastro de uma determinada entidade.
 * 
 * Essas operação podem ser complementadas por um sistema de persistência 
 * expecifico afim de delegar a responsabilidade de comunicação com o banco 
 * de dados, provendo apenas um caminho comum entre as camadas 
 * de Controle e Serviço/Negócio (Bean -> Services).
 * 
 * {@link http://pt.wikipedia.org/wiki/CRUD}
 * 
 * @author arthemus
 * @since 06/11/2013
 */
public interface Crud<T extends VulgarClass> {

	/**
	 * Realiza a operação de gravação da entidade no banco de dados.
	 * 
	 * @param object
	 * @return Mensagem de retorna da operação, uma confirmação de sucesso.
	 * @throws CrudException
	 */
	String doCreate(final T object) throws CrudException;
	
	/**
	 * Busca o registro de uma determinada entidade.
	 * 
	 * @param key
	 * @return
	 * @throws CrudException
	 */
	T getRead(final Class<T> classReference, final Object key) throws CrudException;
	
	/**
	 * Obtem uma lista com todos os registros da entidade no banco de dados.
	 * 	
	 * @return
	 * @throws CrudException
	 */
	Collection<T> getList(final Class<T> classReference) throws CrudException;
	
	/**
	 * Realiza a atualização da entidade no banco de dados.
	 * 
	 * @param object
	 * @return Mensagem de retorna da operação, uma confirmação de sucesso.
	 * @throws CrudException
	 */
	String doUpdate(final T object) throws CrudException;
	
	/**
	 * Realiza a exclusão da entidade no banco de dados.
	 * 
	 * @param object
	 * @return Mensagem de retorna da operação, uma confirmação de sucesso.
	 * @throws CrudException
	 */
	String doDelete(final T object) throws CrudException;
	
	/**
	 * Realiza a exclusão de vários registro do banco de dados.
	 * 
	 * @param object
	 * @return Mensagem de retorna da operação, uma confirmação de sucesso.
	 * @throws CrudException
	 */
	String doDelete(final Collection<T> listObject) throws CrudException;

	/**
	 * Realiza a impressão de todos os registros ou de um determinado
	 * relatório do módulo.
	 * 
	 * @throws CrudException
	 */
	void doPrint() throws CrudException;

	/**
	 * Obtem uma nova referência da entidade a ser trabalhada.
	 * 
	 * @return
	 */
	T getNewInstance();
}
