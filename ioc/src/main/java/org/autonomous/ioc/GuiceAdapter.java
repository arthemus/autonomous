package org.autonomous.ioc;

import java.util.Arrays;

import com.google.inject.Guice;
import com.google.inject.Module;
import com.google.inject.Stage;
import com.google.inject.internal.InternalInjectorCreator;

/**
 * Classe para auxiliar a criação de instancias utilizando o Google Guice.
 * 
 * @author arthemus
 * @since 16/01/2013
 */
public final class GuiceAdapter {

	public static final <T> T get(Class<T> classe, Iterable<? extends Module> modules) {		
		return Guice.createInjector(Stage.PRODUCTION, modules).getInstance(classe);
	}

	public static final <T> T get(Class<T> classe, Module... modules) {
		return Guice.createInjector(Stage.PRODUCTION, modules).getInstance(classe);
	}

	public static final <T> T get(Class<T> classe, Stage stage, Iterable<? extends Module> modules) {
		return new InternalInjectorCreator().stage(stage).addModules(modules).build().getInstance(classe);
	}

	public static final <T> T get(Class<T> classe, Stage stage, Module... modules) {
		return Guice.createInjector(stage, Arrays.asList(modules)).getInstance(classe);
	}
}
