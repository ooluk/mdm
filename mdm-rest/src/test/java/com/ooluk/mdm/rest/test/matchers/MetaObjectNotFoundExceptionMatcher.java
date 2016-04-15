package com.ooluk.mdm.rest.test.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.ooluk.mdm.core.meta.MetaObjectType;
import com.ooluk.mdm.rest.commons.MetaObjectNotFoundException;

/**
 * A custom matcher for MetaObjectNotFoundException.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class MetaObjectNotFoundExceptionMatcher extends
		TypeSafeMatcher<MetaObjectNotFoundException> {

	// Expected Values
	private MetaObjectType expType;
	private Long expId;

	// Actual Values
	private MetaObjectType actType;
	private Long actId;

	
	public static MetaObjectNotFoundExceptionMatcher getInstance(
			MetaObjectType expectedType, Long expectedId) {
		return new MetaObjectNotFoundExceptionMatcher(expectedType, expectedId);
	}

	private MetaObjectNotFoundExceptionMatcher(MetaObjectType type, Long id) {
		this.expType = type;
		this.expId = id;
	}
	
	@Override
	protected boolean matchesSafely(MetaObjectNotFoundException exception) {
		actType = exception.getType();
		actId = exception.getId();
		return actType.equals(expType) && actId.equals(expId);
	}

	public void describeTo(Description description) {
		String expected = "[" + expType + "," + expId + "]";
		description.appendValue(expected);		
	}
	
	@Override
	protected void describeMismatchSafely(final MetaObjectNotFoundException item, 
			final Description mismatchDescription) {
		String found = "[" + actType + "," + actId + "]";
		mismatchDescription.appendText(" was ").appendValue(found);
	}

}