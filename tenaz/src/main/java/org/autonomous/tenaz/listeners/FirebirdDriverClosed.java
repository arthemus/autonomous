package org.autonomous.tenaz.listeners;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.faces.application.Application;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import org.autonomous.tenaz.servers.Firebird;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Responsible for removing the Firebird JDBC driver registration when the
 * application is shut down, avoiding memory leaks caused by repeated hot
 * deploys.
 *
 * @author Arthemus C. Moreira
 * @since 1.0.0
 */
public class FirebirdDriverClosed implements SystemEventListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(FirebirdDriverClosed.class);

	@Override
	public boolean isListenerForSource(Object source) {
		return (source instanceof Application);
	}

	@Override
	public void processEvent(SystemEvent event) throws AbortProcessingException {
		if (event instanceof PreDestroyApplicationEvent) {
			// DriverManager.getDriver() expects a JDBC URL, not a class name.
			// Iterate the registered drivers and match by class name instead.
			try {
				Enumeration<Driver> drivers = DriverManager.getDrivers();
				while (drivers.hasMoreElements()) {
					Driver driver = drivers.nextElement();
					if (Firebird.DRIVER.equals(driver.getClass().getName())) {
						DriverManager.deregisterDriver(driver);
					}
				}
			} catch (SQLException e) {
				LOGGER.error("Could not deregister the Firebird JDBC driver", e);
			}
		}
	}

}
