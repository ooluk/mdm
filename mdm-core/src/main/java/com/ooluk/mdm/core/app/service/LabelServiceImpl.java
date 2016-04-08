package com.ooluk.mdm.core.app.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ooluk.mdm.core.app.data.Label;
import com.ooluk.mdm.core.app.data.LabelType;
import com.ooluk.mdm.core.app.repository.LabelRepository;
import com.ooluk.mdm.core.app.repository.LabelTypeRepository;
import com.ooluk.mdm.core.base.data.MetaObjectType;
import com.ooluk.mdm.core.dto.LabelCore;
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
}