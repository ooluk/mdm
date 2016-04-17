package com.ooluk.mdm.rest.app;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ooluk.mdm.core.meta.MetaObjectType;
import com.ooluk.mdm.core.meta.app.Label;
import com.ooluk.mdm.core.meta.app.LabelRepository;
import com.ooluk.mdm.core.meta.app.LabelType;
import com.ooluk.mdm.core.meta.app.LabelTypeRepository;
import com.ooluk.mdm.rest.commons.MetaObjectNotFoundException;
import com.ooluk.mdm.rest.commons.RestService;
import com.ooluk.mdm.rest.dto.LabelData;
import com.ooluk.mdm.rest.dto.RestResponse;
import com.ooluk.mdm.rest.validation.ValidationFailedException;

/**
 * REST service for label processing
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
@RestController
@RequestMapping ( value = "/labels" )
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
	 * @return {@link com.ooluk.mdm.rest.dto.RestResponse} wrapping a label.
	 * 
	 * @throws MetaObjectNotFoundException
	 *             if a label with the specified ID is not found
	 */
	@RequestMapping ( value = "/{id}", method = GET, produces = APPLICATION_JSON_VALUE )
	public RestResponse<LabelData> getLabelById(@PathVariable ( "id" ) Long id)
			throws MetaObjectNotFoundException {

		Label label = lblRepository.findById(id);
		if (label == null) {
			notFound(MetaObjectType.LABEL, id);
		}
		LabelData lbl = mapper.map(label, LabelData.class);
		
		return new RestResponse<>(lbl)
				.addLink("self", LabelLinkSupport.buildSelfLink(id))
				.addLink("children", LabelLinkSupport.buildChildrenLink(id))
				.addLink("parents", LabelLinkSupport.buildParentsLink(id));
	}

	/**
	 * Gets all child labels of a label.
	 * 
	 * @param id
	 *            ID of the label
	 * 
	 * @return list of all child labels
	 * 
	 * @throws MetaObjectNotFoundException
	 *             if the label is not found
	 */
	@RequestMapping ( value = "/{id}/children", method = GET, produces = APPLICATION_JSON_VALUE )
	public List<RestResponse<LabelData>> getChildLabels(@PathVariable ( "id" ) Long id) 
			throws MetaObjectNotFoundException {

		Label lbl = lblRepository.findById(id);
		if (lbl == null) {
			notFound(MetaObjectType.LABEL, id);
		}
		return getLabelCoreList(lbl.getChildren());
	}

	/**
	 * Gets all parent labels of a label.
	 * 
	 * @param id
	 *            label ID
	 * 
	 * @return list of all parent labels
	 * 
	 * @throws MetaObjectNotFoundException
	 *             if the label is not found
	 */
	@RequestMapping ( value = "/{id}/parents", method = GET, produces = APPLICATION_JSON_VALUE )
	public List<RestResponse<LabelData>> getParentLabels(@PathVariable ( "id" ) Long id) 
			throws MetaObjectNotFoundException {
		
		Label lbl = lblRepository.findById(id);
		if (lbl == null) {
			notFound(MetaObjectType.LABEL, id);
		}
		return getLabelCoreList(lbl.getParents());
	}

	/**
	 * Returns all root labels.
	 * 
	 * @return list of labels without parent labels
	 */
	@RequestMapping ( value = "/roots", method = GET, produces = APPLICATION_JSON_VALUE )
	public List<RestResponse<LabelData>> getRootLabels() {
		
		List<Label> rootLabels = lblRepository.findRootLabels();
		return getLabelCoreList(rootLabels);
	}

	/**
	 * Gets all labels of a specified type.
	 * 
	 * @param typeId
	 *            label type ID
	 * 
	 * @return list of labels of the specified type.
	 * 
	 * @throws MetaObjectNotFoundException
	 *             if the label type is not found
	 */
	@RequestMapping ( value = "/type/{type}", method = GET, produces = APPLICATION_JSON_VALUE )
	public List<RestResponse<LabelData>> getLabelsByType(@PathVariable ("type") Long typeId) 
			throws MetaObjectNotFoundException {
		
		// Ensure label type is valid
		LabelType type = typeRepository.findById(typeId);
		if (type == null) {
			notFound(MetaObjectType.LABEL_TYPE, typeId);
		}
		
		List<Label> labels = lblRepository.findByType(type);
		return getLabelCoreList(labels);
	}
	
	/**
	 * Creates a new label of the specified type.
	 * 
	 * @param typeId
	 *            ID of the label type
	 * @param lblData
	 *            label data
	 * 
	 * @return the created label
	 * 
	 * @throws ValidationFailedException
	 *             if label data validation fails
	 *             
	 * @throws MetaObjectNotFoundException
	 *             if label type is not found
	 */
	@RequestMapping ( value = "/type/{type}", method = POST, consumes = APPLICATION_JSON_VALUE )
	public ResponseEntity<RestResponse<LabelData>> createLabel(
			@PathVariable ("type") Long typeId, @RequestBody LabelData lblData) 
			throws ValidationFailedException, MetaObjectNotFoundException {
		
		LabelType type = typeRepository.findById(typeId);
		if (type == null) {
			notFound(MetaObjectType.LABEL_TYPE, typeId);
		}
		validator.<LabelData>validate(lblData);
		
		Label label = mapper.map(lblData, Label.class);
		label.setType(type);
		lblRepository.create(label);
		
		RestResponse<LabelData> body = this.getLabelById(label.getId());
		HttpHeaders headers = new HttpHeaders();
		headers.add("location", LabelLinkSupport.buildSelfLink(label.getId()).toString());
		return new ResponseEntity<>(body, headers, HttpStatus.CREATED);
	}
	
	/**
	 * Updates a label.
	 * 
	 * @param id
	 *            ID of the label 
	 * @param lblData
	 *            label data
	 * 
	 * @throws ValidationFailedException
	 *             if label data validation fails
	 *             
	 * @throws MetaObjectNotFoundException
	 *             if label is not found
	 */
	@RequestMapping ( value = "{id}", method = PUT, consumes = APPLICATION_JSON_VALUE )
	public ResponseEntity<RestResponse<LabelData>> updateLabel(
			@PathVariable ("id") Long id, @RequestBody LabelData lblData) 
			throws ValidationFailedException, MetaObjectNotFoundException {
		
		Label label = lblRepository.findById(id);
		if (label == null) {
			notFound(MetaObjectType.LABEL, id);
		}
		validator.<LabelData>validate(lblData);
		
		label.setName(lblData.getName());
		label.setProperties(lblData.getProperties());
		lblRepository.update(label);
		
		RestResponse<LabelData> body = this.getLabelById(label.getId());
		return ResponseEntity.ok(body);
	}
	
	/**
	 * Converts a list of Labels to a list of LabelCore(s).
	 * 
	 * @param labelList
	 *            list of Label(s)
	 *            
	 * @return list of LabelCore(s)
	 * 
	 * @throws MetaObjectNotFoundException 
	 */
	private List<RestResponse<LabelData>> getLabelCoreList(Collection<Label> labelList) {

		List<RestResponse<LabelData>> coreList = new ArrayList<>(labelList.size());
		for (Label label : labelList) {
			LabelData coreItem = mapper.map(label, LabelData.class);
			RestResponse<LabelData> iLabel = (new RestResponse<>(coreItem))
					.addLink("self", LabelLinkSupport.buildSelfLink(coreItem.getId()));
			coreList.add(iLabel);
		}
		return coreList;
	}
}