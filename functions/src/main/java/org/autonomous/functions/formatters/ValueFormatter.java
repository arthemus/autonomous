package org.autonomous.functions.formatters;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Formats a given value based on a mask. Intended for use with documents
 * or form fields.
 *
 * @author arthemus
 * @since 28/05/2013
 *
 */
public class ValueFormatter {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValueFormatter.class);

	private final Formatter formatter;
	private final String valueToFormat;

	public ValueFormatter(Formatter formatterClass, String valueToFormat) {
		this.formatter = formatterClass;
		this.valueToFormat = valueToFormat;
	}

	public String getFormattedValue() {
		MaskFormatter mf = null;
		try {
			mf = new MaskFormatter(formatter.getMask());
		} catch (ParseException e) {
			LOGGER.error("Failed to create mask formatter for value: {}", valueToFormat, e);
		}
		mf.setValueContainsLiteralCharacters(false);
		String newValue = new String();
		try {
			newValue = mf.valueToString(valueToFormat);
		} catch (ParseException e) {
			LOGGER.error("Failed to apply mask to value: {}", valueToFormat, e);
		}
		return newValue;
	}
}
