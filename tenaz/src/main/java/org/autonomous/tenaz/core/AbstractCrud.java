package org.autonomous.tenaz.core;

import java.util.Collection;
import java.util.List;

import org.autonomous.functions.CureException;
import org.autonomous.functions.mensageria.Message18n;
import org.autonomous.tenaz.commons.Crud;
import org.autonomous.tenaz.commons.CrudException;
import org.autonomous.tenaz.commons.VulgarClass;

/**
 * Classe abstrata para prover a implementação padrão para todos os módulo Crud
 * do sistema, evitando a duplicidade de código e permitindo a personalização
 * e extenção por diferentes módulos.
 * 
 * @author arthemus
 *
 * @param <T>
 */
public abstract class AbstractCrud<T extends VulgarClass> implements Crud<T> {

	private final Persist _persist;
	private final Message18n _i18n;
	
	protected AbstractCrud(final Persist persist, final Message18n i18n) {
		this._persist = persist;
		this._i18n = i18n;
	}

	protected void onBeforeCreate(final T object){
		//NoCommand
	}
	
	protected void onBeforeUpdate(final T object){
		//NoCommand
	}
	
	protected void onBeforeDelete(final T object){
		//NoCommand
	}
	
	@Override
	public String doCreate(final T object) throws CrudException {
		try {	
			onBeforeCreate(object);
			_persist.save(object);			
		} catch (Exception e) {
			String msg = _i18n.getMessage("crud.docreate.unsuccess").concat(CureException.by(e).getNewMessage());
			throw new CrudException(msg);
		}
		return _i18n.getMessage("crud.docreate.success");
	}

	@Override
	public T getRead(final Class<T> classReference, final Object key) throws CrudException {
		T object = null;
		try {
			object = _persist.get(classReference, key);
		} catch (PersistException e) {
			String msg = _i18n.getMessage("crud.search.unsuccess").concat(CureException.by(e).getNewMessage());
			throw new CrudException(msg);
		}
		return object;
	}

	@Override
	public Collection<T> getList(final Class<T> classReference) throws CrudException {
		List<T> lista = null;
		try {
			lista = (List<T>) _persist.list(classReference);
		} catch (PersistException e) {
			String msg = _i18n.getMessage("crud.search.unsuccess").concat(CureException.by(e).getNewMessage());
			throw new CrudException(msg);
		}		
		return lista;
	}

	@Override
	public String doUpdate(final T object) throws CrudException {
		try {			
			onBeforeUpdate(object);
			_persist.update(object);
		} catch (Exception e) {
			String msg = _i18n.getMessage("crud.doupdate.unsuccess").concat(CureException.by(e).getNewMessage());
			throw new CrudException(msg);
		}
		return _i18n.getMessage("crud.doupdate.success");
	}

	@Override
	public String doDelete(final T object) throws CrudException {
		try {
			onBeforeDelete(object);
			_persist.delete(object);
		} catch (Exception e) {
			String msg = _i18n.getMessage("crud.dodelete.unsuccess").concat(CureException.by(e).getNewMessage());
			throw new CrudException(msg);
		}
		return _i18n.getMessage("crud.dodelete.success");
	}

	@Override
	public String doDelete(final Collection<T> listObject) throws CrudException {
		try {
			_persist.delete(listObject);
		} catch (Exception e) {
			String msg = _i18n.getMessage("crud.dodeleteplural.unsuccess").concat(CureException.by(e).getNewMessage());
			throw new CrudException(msg);
		}	
		return _i18n.getMessage("crud.dodeleteplural.success");
	}

	@Override
	public void doPrint() throws CrudException {
		// NoCommand		
	}

}
