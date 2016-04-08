package com.ooluk.mdm.core.app.service;

import com.ooluk.mdm.core.dto.LabelCore;
import com.ooluk.mdm.core.service.DuplicateKeyException;
import com.ooluk.mdm.core.service.MetaObjectNotFoundException;

/**
 * Service interface for label functions.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public interface LabelService {

	/**
	 * Gets the label with the specified ID.
	 * 
	 * @param id
	 *            label ID
	 * 
	 * @return label
	 * 
	 * @throws MetaObjectNotFoundException
	 *             if a label with the specified ID is not found
	 */
	public LabelCore getLabel(Long id) throws MetaObjectNotFoundException;
	
	/**
	 * Creates a label.
	 * 
	 * @param label
	 *            label data
	 *            
	 * @throws MetaObjectNotFoundException
	 *             if the label type is not found
	 * @throws DuplicateKeyException
	 *             if another label with the same type and name already exists
	 */
	public void createLabel(LabelCore label) throws MetaObjectNotFoundException, DuplicateKeyException;
}
