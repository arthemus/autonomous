package org.autonomous.tenaz.listeners;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.faces.application.Application;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import org.autonomous.tenaz.servidores.MSSQLServer2005;

/**
 * Responsavel por remover o registro do driver jdbc do SQL Server ao finalizar
 * a aplica��o.
 * 
 * @author arthemus
 * @since 26/09/2012
 * @see MSSQLServer2005
 */
public class SQLServerDriverClosed implements SystemEventListener {

	@Override
	public boolean isListenerForSource(Object source) {
		return (source instanceof Application);
	}

	@Override
	public void processEvent(SystemEvent event) throws AbortProcessingException {
		if (event instanceof PreDestroyApplicationEvent) {
			try {
				Driver driver = DriverManager.getDriver(MSSQLServer2005.DRIVER);
				DriverManager.deregisterDriver(driver);
			} catch (SQLException e) {
				// NoCommand
			}
		}
	}

}
