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

import org.autonomous.tenaz.servers.MySQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * To avoid OutOfMemoryError's arising from several hot deploys during
 * production.
 *
 * @author Arthemus C. Moreira
 * @since 1.0.0
 */
public class MySQLDriverClosed implements SystemEventListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(MySQLDriverClosed.class);

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
					if (MySQL.DRIVER.equals(driver.getClass().getName())) {
						DriverManager.deregisterDriver(driver);
					}
				}
			} catch (SQLException e) {
				LOGGER.error("Could not deregister the MySQL JDBC driver", e);
			}
		}
	}

}
