package com.ooluk.mdm.rest.dto;

import org.dozer.DozerConverter;

import com.ooluk.mdm.core.meta.DynamicProperties;

/**
 * Custom mapper for DynamicProperties. This is required because dynamic properties are maintained
 * as Map<String, Object> and when dozer converts Object to Object it constructs String objects.
 * 
 * <p>
 * The destination will maintain a copy of the source's <code>properties</code> map but it will
 * maintain a reference to the map's values. This generally should not be an issue because the
 * values of the <code>properties</code> map of DynamicProperties are expected to be of type String
 * or primitive wrappers, both of which are immutable. The action would therefore be an equivalent
 * of deep copying for immutable types.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
public class DynamicPropertiesMapper extends DozerConverter<DynamicProperties, DynamicProperties> {

	public DynamicPropertiesMapper() {
		super(DynamicProperties.class, DynamicProperties.class);
	}

	@Override
	public DynamicProperties convertTo(DynamicProperties source, DynamicProperties destination) {
		destination = new DynamicProperties(source.getProperties());
		return destination;
	}

	@Override
	public DynamicProperties convertFrom(DynamicProperties source, DynamicProperties destination) {
		destination = new DynamicProperties(source.getProperties());
		return destination;
	}
}