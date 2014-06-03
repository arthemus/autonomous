package org.autonomous.functions;

import javax.xml.bind.annotation.XmlElement;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author arthemus
 * @since 30/08/2013
 */
public class AnnotationsTest {

	class XmlElementTest {		
		@XmlElement(name="ELEMENTO", defaultValue="Vazio", namespace="SPACES", nillable=true, required=true, type=String.class)
		private String elementoRequerido;
		@XmlElement
		private String elementoNaoRequerido;
	}
	
	@Test
	public void lePropriedadeRequiredDeUmXmlElement() {		
		boolean result = Annotations
				.onObject(new XmlElementTest())
				.forAttribute("elementoRequerido")
				.forAnnotation(XmlElement.class)				
				.getValue("required", Boolean.class);
		Assert.assertEquals(true, result);
	}
		
	@Test
	public void testaAtributoComAnotacaoSemParametro() {
		boolean result = Annotations
				.onObject(new XmlElementTest())
				.forAttribute("elementoNaoRequerido")
				.forAnnotation(XmlElement.class)
				.getValue("required", Boolean.class);
		Assert.assertEquals(false, result);
	}
}
