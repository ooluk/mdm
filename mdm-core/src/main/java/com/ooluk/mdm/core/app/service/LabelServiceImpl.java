package com.ooluk.mdm.core.app.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ooluk.mdm.core.dto.LabelCore;
import com.ooluk.mdm.core.meta.MetaObjectType;
import com.ooluk.mdm.core.meta.app.Label;
import com.ooluk.mdm.core.meta.app.LabelRepository;
import com.ooluk.mdm.core.meta.app.LabelType;
import com.ooluk.mdm.core.meta.app.LabelTypeRepository;
import com.ooluk.mdm.core.service.AbstractService;
import com.ooluk.mdm.core.service.DuplicateKeyException;
import com.ooluk.mdm.core.service.MetaObjectNotFoundException;

/**
 * Service implementation for label functions.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Service
public class LabelServiceImpl extends AbstractService implements LabelService {
		
	@Autowired
	private LabelRepository lblRepository;
	
	@Autowired
	private LabelTypeRepository typeRepository;

	@Override
	public LabelCore getLabel(Long id) throws MetaObjectNotFoundException {
		Label label = lblRepository.findById(id);
		if (label == null) {
			notFound(MetaObjectType.LABEL, id);
		}
		return mapper.map(label, LabelCore.class);
	}

	@Override
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

	@Override
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

	@Override
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

	@Override
	public List<LabelCore> getRootLabels() {

		List<Label> rootLabels = lblRepository.findRootLabels();
		return getLabelCoreList(rootLabels);
	}

	@Override
	public List<LabelCore> getChildLabels(Long id) throws MetaObjectNotFoundException {

		Label lbl = lblRepository.findById(id);
		if (lbl == null) {
			notFound(MetaObjectType.LABEL, id);
		}

		return getLabelCoreList(lbl.getChildren());
	}

	@Override
	public List<LabelCore> getParentsLabels(Long id) throws MetaObjectNotFoundException {

		Label lbl = lblRepository.findById(id);
		if (lbl == null) {
			notFound(MetaObjectType.LABEL, id);
		}

		return getLabelCoreList(lbl.getParents());
	}

	@Override
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

	@Override
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