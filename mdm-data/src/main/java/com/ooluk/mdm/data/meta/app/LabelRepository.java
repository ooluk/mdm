package com.ooluk.mdm.data.meta.app;

import java.util.List;

import com.ooluk.mdm.data.meta.GenericRepository;

/**
 * Repository interface for labels.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public interface LabelRepository extends GenericRepository<Label> {
	
	/**
	 * Finds a label by its type and name
	 * 
	 * @param type
	 *            label type 
	 * @param name
	 *            label name
	 *            
	 * @return label with the specified type and name or null if not found
	 */
	public Label findByTypeAndName(LabelType type, String name);	
	
	/**
	 * Returns all labels of a specific type
	 * 
	 * @param type
	 *            label type
	 * 
	 * @return list of all label of the specified type.
	 */
	public List<Label> findByType(LabelType type);
	
	/**
	 * Finds all root labels that is labels without parents.
	 * 
	 * @return list of root labels.
	 */
	public List<Label> findRootLabels();
}