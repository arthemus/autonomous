package org.autonomous.tenaz.hibernate;

import javax.faces.application.Application;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Responsible for closing the resources involved with Hibernate when the
 * application is shut down.
 *
 * @author arthemus
 * @since 26/09/2012
 * @see HibernatePersist
 */
public final class HibernateSessionCloseListener implements SystemEventListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(HibernateSessionCloseListener.class);

	@Override
	public boolean isListenerForSource(Object source) {
		return (source instanceof Application);
	}

	@Override
	public void processEvent(SystemEvent event) throws AbortProcessingException {
		if (event instanceof PreDestroyApplicationEvent) {
			try {
				HibernatePersist persist = HibernatePersist.getDefault();
				persist.closeSession();
				persist.closeFactory();
			} catch (Exception e) {
				LOGGER.debug("Ignoring error while closing Hibernate resources on application shutdown", e);
			}
		}
	}

}
