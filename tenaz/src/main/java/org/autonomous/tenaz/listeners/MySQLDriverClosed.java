package org.autonomous.tenaz.listeners;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.faces.application.Application;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import org.autonomous.tenaz.servidores.MySQL;

/**
 * Para evitar OutOfMemoryErro's decorrentes de varios hot deploys durante a produ��o.
 * 
 * @author Arthemus C. Moreira
 * @since 1.0.0
 */
public class MySQLDriverClosed implements SystemEventListener {

	@Override
	public boolean isListenerForSource(Object source) {
		return (source instanceof Application);
	}

	@Override
	public void processEvent(SystemEvent event) throws AbortProcessingException {
		if (event instanceof PreDestroyApplicationEvent) {
			try {	
				Driver driver = DriverManager.getDriver(MySQL.DRIVER);
				DriverManager.deregisterDriver(driver);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
