package org.autonomous.tenaz.hibernate;

import javax.faces.application.Application;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;


/**
 * Respons�vel por fechar os recursos envolvidos com o Hibernate quando a
 * aplica��o � finalizada.
 * 
 * @author arthemus
 * @since 26/09/2012
 * @see HibernateDao
 */
public final class HibernateSessionClosed implements SystemEventListener {

	@Override
	public boolean isListenerForSource(Object source) {
		return (source instanceof Application);
	}

	@Override
	public void processEvent(SystemEvent event) throws AbortProcessingException {
		if (event instanceof PreDestroyApplicationEvent) {
			try {
				HibernatePersist.closeSession();
				HibernatePersist.closeFactory();
			} catch (Exception e) {
			}
		}
	}

}
