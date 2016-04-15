package com.ooluk.mdm.rest.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ooluk.mdm.core.meta.MetaObjectType;
import com.ooluk.mdm.core.meta.app.Label;
import com.ooluk.mdm.core.meta.app.LabelRepository;
import com.ooluk.mdm.core.meta.app.LabelType;
import com.ooluk.mdm.core.meta.app.LabelTypeRepository;
import com.ooluk.mdm.core.service.DuplicateKeyException;
import com.ooluk.mdm.rest.commons.MetaObjectNotFoundException;
import com.ooluk.mdm.rest.commons.RestService;
import com.ooluk.mdm.rest.dto.LabelCore;

/**
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Service
@Path("label")
@Produces("application/json")
public class LabelService extends RestService {

	@Autowired
	private LabelRepository lblRepository;
	
	@Autowired
	private LabelTypeRepository typeRepository;

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
	@GET
	@Path("{id}")
	public Response getLabelById(@PathParam("id") Long id) throws MetaObjectNotFoundException {
		
		Label label = lblRepository.findById(id);
		if (label == null) {
			notFound(MetaObjectType.LABEL, id);
		}
		LabelCore entity = mapper.map(label, LabelCore.class);
		return Response.ok(entity).link("http://www.ooluk.com", "link").build();
	}
	
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
	public void createLabel(LabelCore label) throws MetaObjectNotFoundException, DuplicateKeyException {
		Long typeId = label.getType().getId();
		
		// Ensure label type is valid
		LabelType type = typeRepository.findById(typeId);
		if (type == null) {
			notFound(MetaObjectType.LABEL_TYPE, typeId);
		}
		
		// Ensure another label with the name does not already exist
		Label lbl = lblRepository.findByTypeAndName(type, label.getName());
		if (lbl != null) {
			Map<String, Object> key = new HashMap<>();
			key.put("type", type);
			key.put("name", label.getName());
			throw new DuplicateKeyException(MetaObjectType.LABEL, key);
		}
		
		lbl = mapper.map(label, Label.class);
		lblRepository.create(lbl);
	}

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
	public void updateLabel(Long id, LabelCore label) throws MetaObjectNotFoundException,
			DuplicateKeyException {
		
		Label lbl = lblRepository.findById(id);
		if (label == null) {
			notFound(MetaObjectType.LABEL, id);
		}
		
		// Ensure label type is valid
		Long typeId = label.getType().getId();
		LabelType type = typeRepository.findById(typeId);
		if (type == null) {
			notFound(MetaObjectType.LABEL_TYPE, typeId);
		}

		lbl.setType(type);
		lbl.setName(label.getName());
		lbl.setProperties(label.getProperties());
		lblRepository.update(lbl);
	}
	
	/**
	 * Deletes a label.
	 * 
	 * @param id
	 *            ID of the label to be deleted
	 *            
	 * @throws MetaObjectNotFoundException
	 *             if the label is not found
	 */
	public void deleteLabel(Long id) throws MetaObjectNotFoundException {
		
		Label lbl = lblRepository.findById(id);
		if (lbl == null) {
			notFound(MetaObjectType.LABEL, id);
		}
		lblRepository.delete(lbl);
	}
	
	/**
	 * Returns the a list of LabelCore(s) from a list of Label(s).
	 * 
	 * @param labelList
	 *            list of Label(s)
	 *            
	 * @return list of LabelCore(s)
	 */
	private List<LabelCore> getLabelCoreList(Collection<Label> labelList) {

		List<LabelCore> coreList = new ArrayList<>(labelList.size());
		for (Label label : labelList) {
			LabelCore coreItem = mapper.map(label, LabelCore.class);
			coreList.add(coreItem);
		}
		return coreList;
	}

	/**
	 * Returns all root labels.
	 * 
	 * @return list of labels without parent labels.
	 */
	public List<LabelCore> getRootLabels() {

		List<Label> rootLabels = lblRepository.findRootLabels();
		return getLabelCoreList(rootLabels);
	}

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
	public List<LabelCore> getChildLabels(Long id) throws MetaObjectNotFoundException {

		Label lbl = lblRepository.findById(id);
		if (lbl == null) {
			notFound(MetaObjectType.LABEL, id);
		}

		return getLabelCoreList(lbl.getChildren());
	}

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
	public List<LabelCore> getParentsLabels(Long id) throws MetaObjectNotFoundException {

		Label lbl = lblRepository.findById(id);
		if (lbl == null) {
			notFound(MetaObjectType.LABEL, id);
		}

		return getLabelCoreList(lbl.getParents());
	}

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
	public void addChild(Long parentId, Long childId) throws MetaObjectNotFoundException {
		
		Label parent = lblRepository.findById(parentId);
		if (parent == null) {
			notFound(MetaObjectType.LABEL, parentId);
		}
		
		Label child = lblRepository.findById(childId);
		if (child == null) {
			notFound(MetaObjectType.LABEL, childId);
		}
		
		parent.addChild(child);		
	}
	
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
	public void removeChild(Long parentId, long childId) throws MetaObjectNotFoundException {
		
		Label parent = lblRepository.findById(parentId);
		if (parent == null) {
			notFound(MetaObjectType.LABEL, parentId);
		}
		
		Label child = lblRepository.findById(childId);
		if (child == null) {
			notFound(MetaObjectType.LABEL, childId);
		}
		
		parent.removeChild(child);				
	}
}
