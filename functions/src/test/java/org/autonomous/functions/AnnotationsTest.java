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
		@XmlElement(name="ELEMENT", defaultValue="Empty", namespace="SPACES", nillable=true, required=true, type=String.class)
		private String requiredElement;
		@XmlElement
		private String optionalElement;
	}

	@Test
	public void shouldReadRequiredPropertyFromXmlElement() {
		boolean result = Annotations
				.onObject(new XmlElementTest())
				.forAttribute("requiredElement")
				.forAnnotation(XmlElement.class)
				.getValue("required", Boolean.class);
		Assert.assertEquals(true, result);
	}

	@Test
	public void shouldTestAttributeWithoutAnnotationParameter() {
		boolean result = Annotations
				.onObject(new XmlElementTest())
				.forAttribute("optionalElement")
				.forAnnotation(XmlElement.class)
				.getValue("required", Boolean.class);
		Assert.assertEquals(false, result);
	}
}
