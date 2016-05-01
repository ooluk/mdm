package com.ooluk.mdm.data.meta.dataobject;

import java.util.List;

import com.ooluk.mdm.data.meta.GenericRepository;
import com.ooluk.mdm.data.meta.app.Label;

/**
 * Repository interface for data objects.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public interface DataObjectRepository extends GenericRepository<DataObject> {
	
	/**
	 * Finds a data object by its name within a namespace
	 * 
	 * @param namespace
	 *            namespace
	 * @param name
	 *            data object name
	 *            
	 * @return data object with the specified name or null if not found
	 */
	public DataObject findByName(Namespace namespace, String name);
	
	/**
	 * Returns all data objects in a namespace.
	 * 
	 * @param namespace
	 *            namespace
	 *            
	 * @return list of data objects
	 */
	public List<DataObject> findByNamespace(Namespace namespace);
	
	/**
	 * Finds data objects that have all the specified labels attached.
	 * 
	 * @param labels
	 *            list of labels
	 *            
	 * @return list of data objects
	 */
	public List<DataObject> findByLabels(List<Label> labels);
}