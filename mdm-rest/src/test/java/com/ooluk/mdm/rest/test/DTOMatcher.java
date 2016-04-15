package com.ooluk.mdm.rest.test;

import static org.junit.Assert.assertEquals;

import com.ooluk.mdm.core.meta.app.Label;
import com.ooluk.mdm.core.meta.app.LabelType;
import com.ooluk.mdm.rest.dto.LabelCore;
import com.ooluk.mdm.rest.dto.LabelTypeCore;


/**
 * A class to verify DTO against entities.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class DTOMatcher {

	public static void verifyEqual(LabelType exp, LabelTypeCore act) {
		assertEquals(exp.getId(), act.getId());
		assertEquals(exp.getName(), act.getName());
		assertEquals(exp.getProperties(), act.getProperties());
	}

	public static void verifyEqual(Label exp, LabelCore act) {
		assertEquals(exp.getId(), act.getId());
		verifyEqual(exp.getType(), act.getType());
		assertEquals(exp.getName(), act.getName());
		assertEquals(exp.getProperties(), act.getProperties());
	}
}
