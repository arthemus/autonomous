package org.autonomous.tenaz.core;

import java.util.Collection;
import java.util.List;

import org.autonomous.functions.ExceptionResolver;
import org.autonomous.functions.messaging.Messages;
import org.autonomous.tenaz.commons.Crud;
import org.autonomous.tenaz.commons.CrudException;
import org.autonomous.tenaz.commons.PersistentEntity;

/**
 * Abstract class providing the standard implementation for all Crud modules
 * of the system, avoiding code duplication and allowing customization and
 * extension by different modules.
 *
 * @author arthemus
 *
 * @param <T>
 *            The entity type managed by the Crud.
 */
public abstract class AbstractCrud<T extends PersistentEntity> implements Crud<T> {

	private final Persist persist;
	private final Messages i18n;

	protected AbstractCrud(final Persist persist, final Messages i18n) {
		this.persist = persist;
		this.i18n = i18n;
	}

	protected void onBeforeCreate(final T object) {
		// No operation by default.
	}

	protected void onBeforeUpdate(final T object) {
		// No operation by default.
	}

	protected void onBeforeDelete(final T object) {
		// No operation by default.
	}

	@Override
	public String doCreate(final T object) throws CrudException {
		try {
			onBeforeCreate(object);
			persist.save(object);
		} catch (Exception e) {
			String msg = i18n.getMessage("crud.docreate.unsuccess").concat(ExceptionResolver.by(e).getNewMessage());
			throw new CrudException(msg, e);
		}
		return i18n.getMessage("crud.docreate.success");
	}

	@Override
	public T getRead(final Class<T> classReference, final Object key) throws CrudException {
		T object = null;
		try {
			object = persist.get(classReference, key);
		} catch (PersistException e) {
			String msg = i18n.getMessage("crud.search.unsuccess").concat(ExceptionResolver.by(e).getNewMessage());
			throw new CrudException(msg, e);
		}
		return object;
	}

	@Override
	public Collection<T> getList(final Class<T> classReference) throws CrudException {
		List<T> list = null;
		try {
			list = (List<T>) persist.list(classReference);
		} catch (PersistException e) {
			String msg = i18n.getMessage("crud.search.unsuccess").concat(ExceptionResolver.by(e).getNewMessage());
			throw new CrudException(msg, e);
		}
		return list;
	}

	@Override
	public String doUpdate(final T object) throws CrudException {
		try {
			onBeforeUpdate(object);
			persist.update(object);
		} catch (Exception e) {
			String msg = i18n.getMessage("crud.doupdate.unsuccess").concat(ExceptionResolver.by(e).getNewMessage());
			throw new CrudException(msg, e);
		}
		return i18n.getMessage("crud.doupdate.success");
	}

	@Override
	public String doDelete(final T object) throws CrudException {
		try {
			onBeforeDelete(object);
			persist.delete(object);
		} catch (Exception e) {
			String msg = i18n.getMessage("crud.dodelete.unsuccess").concat(ExceptionResolver.by(e).getNewMessage());
			throw new CrudException(msg, e);
		}
		return i18n.getMessage("crud.dodelete.success");
	}

	@Override
	public String doDelete(final Collection<T> listObject) throws CrudException {
		try {
			persist.delete(listObject);
		} catch (Exception e) {
			String msg = i18n.getMessage("crud.dodeleteplural.unsuccess").concat(ExceptionResolver.by(e).getNewMessage());
			throw new CrudException(msg, e);
		}
		return i18n.getMessage("crud.dodeleteplural.success");
	}

	@Override
	public void doPrint() throws CrudException {
		// No operation by default.
	}

}
