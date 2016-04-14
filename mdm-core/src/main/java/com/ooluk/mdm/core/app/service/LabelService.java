package com.ooluk.mdm.core.app.service;

import java.util.List;

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
	
	/**
	 * Updates a label.
	 *
	 * @param id
	 *            ID of label to be updated
	 * @param label
	 *            updated label data
	 *            
	 * @throws MetaObjectNotFoundException
	 *             if the label is not found
	 * @throws DuplicateKeyException
	 *             if another label with the same type and name already exists
	 */
	public void updateLabel(Long id, LabelCore label) throws MetaObjectNotFoundException, DuplicateKeyException; 
	
	/**
	 * Deletes a label.
	 * 
	 * @param id
	 *            ID of the label to be deleted
	 *            
	 * @throws MetaObjectNotFoundException
	 *             if the label is not found
	 */
	public void deleteLabel(Long id) throws MetaObjectNotFoundException;
	
	/**
	 * Returns all root labels.
	 * 
	 * @return list of labels without parent labels.
	 */
	public List<LabelCore> getRootLabels();
	
	/**
	 * Gets all child labels of the label with the specified ID.
	 * 
	 * @param id
	 *            ID of the label
	 *            
	 * @return list of all child labels
	 *            
	 * @throws MetaObjectNotFoundException
	 *             if the label is not found
	 */
	public List<LabelCore> getChildLabels(Long id) throws MetaObjectNotFoundException;
	
	/**
	 * Gets all parent labels of the label with the specified ID.
	 * 
	 * @param id
	 *            label ID
	 *            
	 * @return list of all parent labels
	 *            
	 * @throws MetaObjectNotFoundException
	 *             if the label is not found
	 */
	public List<LabelCore> getParentsLabels(Long id) throws MetaObjectNotFoundException;
	
	/**
	 * Adds one label as a child of another label.
	 * 
	 * @param parentId
	 *            parent label ID
	 * @param childId
	 *            child label ID
	 *            
	 * @throws MetaObjectNotFoundException
	 *             if parent of child label is not found
	 */
	public void addChild(Long parentId, Long childId) throws MetaObjectNotFoundException;

	/**
	 * Removes one label as a child of another label.
	 * 
	 * @param parentId
	 *            parent label ID
	 * @param childId
	 *            child label ID
	 *            
	 * @throws MetaObjectNotFoundException
	 *             if parent of child label is not found
	 */
	public void removeChild(Long parentId, long childId) throws MetaObjectNotFoundException;
}