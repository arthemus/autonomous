package org.autonomous.ioc;

import java.util.Arrays;

import com.google.inject.Guice;
import com.google.inject.Module;
import com.google.inject.Stage;
import com.google.inject.internal.InternalInjectorCreator;

/**
 * Helper class for creating instances using Google Guice.
 * 
 * @author arthemus
 * @since 16/01/2013
 */
public final class GuiceAdapter {

	public static final <T> T get(Class<T> clazz, Iterable<? extends Module> modules) {		
		return Guice.createInjector(Stage.PRODUCTION, modules).getInstance(clazz);
	}

	public static final <T> T get(Class<T> clazz, Module... modules) {
		return Guice.createInjector(Stage.PRODUCTION, modules).getInstance(clazz);
	}

	public static final <T> T get(Class<T> clazz, Stage stage, Iterable<? extends Module> modules) {
		return new InternalInjectorCreator().stage(stage).addModules(modules).build().getInstance(clazz);
	}

	public static final <T> T get(Class<T> clazz, Stage stage, Module... modules) {
		return Guice.createInjector(stage, Arrays.asList(modules)).getInstance(clazz);
	}
}
