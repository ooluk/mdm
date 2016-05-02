package com.ooluk.mdm.rest.validation;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class VM {
	
	public static ResourceBundle messages = null;
	
	static {
		Locale locale = Locale.getDefault();
	    messages = ResourceBundle.getBundle("ValidationMessages", locale);
	}
	
	public static String msg(String key, Object... params) {
		return MessageFormat.format(messages.getString(key), params);
	}
}