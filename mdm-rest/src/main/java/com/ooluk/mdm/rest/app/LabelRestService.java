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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ooluk.mdm.data.meta.MetaObjectType;
import com.ooluk.mdm.data.meta.app.Label;
import com.ooluk.mdm.data.meta.app.LabelRepository;
import com.ooluk.mdm.data.meta.app.LabelType;
import com.ooluk.mdm.data.meta.app.LabelTypeRepository;
import com.ooluk.mdm.rest.app.dto.LabelData;
import com.ooluk.mdm.rest.commons.BadRequestException;
import com.ooluk.mdm.rest.commons.MetaObjectNotFoundException;
import com.ooluk.mdm.rest.commons.RestService;
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
public class LabelRestService extends RestService {

	@Autowired
	private LabelRepository lblRepository;
	@Autowired
	private LabelTypeRepository typeRepository;	

	/**
	 * Gets a label by ID.
	 * 
	 * @param id
	 *            label ID
	 * 
	 * @return {@link com.ooluk.mdm.rest.dto.RestResponse} wrapping a label.
	 * 
	 * @throws MetaObjectNotFoundException
	 *             if a label with the specified ID is not found
	 */
	@Transactional (readOnly = true)
	@RequestMapping ( value = "/{id}", method = GET, produces = APPLICATION_JSON_VALUE )
	public RestResponse<LabelData> getLabelById(@PathVariable ( "id" ) Long id) {

		Label label = lblRepository.findById(id);
		if (label == null) {
			notFound(MetaObjectType.LABEL, id);
		}
		LabelData lbl = mapper.map(label, LabelData.class);
		
		return new RestResponse<>(lbl)
				.addLink("self", LabelLinks.buildSelfLink(id))
				.addLink("children", LabelLinks.buildChildrenLink(id))
				.addLink("parents", LabelLinks.buildParentsLink(id));
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
	@Transactional (readOnly = true)
	@RequestMapping ( value = "/{id}/children", method = GET, produces = APPLICATION_JSON_VALUE )
	public List<RestResponse<LabelData>> getChildLabels(@PathVariable ( "id" ) Long id) {

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
	@Transactional (readOnly = true)
	@RequestMapping ( value = "/{id}/parents", method = GET, produces = APPLICATION_JSON_VALUE )
	public List<RestResponse<LabelData>> getParentLabels(@PathVariable ( "id" ) Long id) {
		
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
	@Transactional (readOnly = true)
	@RequestMapping ( value = "/roots", method = GET, produces = APPLICATION_JSON_VALUE )
	public List<RestResponse<LabelData>> getRootLabels() {
		
		List<Label> rootLabels = lblRepository.findRootLabels();
		return getLabelCoreList(rootLabels);
	}

	/**
	 * Gets all labels satisfying the query parameters.
	 * 
	 * @param typeId
	 *            label type ID
	 * 
	 * @return list of labels of the specified type.
	 * 
	 * @throws MetaObjectNotFoundException
	 *             if the label type is not found
	 *             
	 * @throws BadRequestException
	 * 			   if query parameters are missing 
	 */
	@Transactional (readOnly = true)
	@RequestMapping ( method = GET, produces = APPLICATION_JSON_VALUE )
	public List<RestResponse<LabelData>> getLabels(@RequestParam ("type") Long typeId) {
		
		if (typeId == null) {
			throw new BadRequestException("[type] parameter is mandatory.");
		}
		
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
	 * @param data
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
	@Transactional 
	@RequestMapping ( value = "/type/{type}", method = POST, consumes = APPLICATION_JSON_VALUE )
	public ResponseEntity<RestResponse<LabelData>> createLabel(
			@PathVariable ("type") Long typeId, @RequestBody LabelData data) {
		
		LabelType type = typeRepository.findById(typeId);
		if (type == null) {
			notFound(MetaObjectType.LABEL_TYPE, typeId);
		}
		validator.validate(data);
		
		Label label = mapper.map(data, Label.class);
		label.setType(type);
		lblRepository.create(label);
		
		RestResponse<LabelData> entity = this.getLabelById(label.getId());
		HttpHeaders headers = new HttpHeaders();
		headers.add("location", LabelLinks.buildSelfLink(label.getId()).toString());
		return new ResponseEntity<>(entity, headers, HttpStatus.CREATED);
	}
	
	/**
	 * Updates a label.
	 * 
	 * @param id
	 *            ID of the label 
	 * @param data
	 *            label data
	 * 
	 * @throws ValidationFailedException
	 *             if label data validation fails
	 *             
	 * @throws MetaObjectNotFoundException
	 *             if label is not found
	 */
	@Transactional 
	@RequestMapping ( value = "/{id}", method = PUT, consumes = APPLICATION_JSON_VALUE )
	public ResponseEntity<RestResponse<LabelData>> updateLabel(
			@PathVariable ("id") Long id, @RequestBody LabelData data) {
		
		Label label = lblRepository.findById(id);
		if (label == null) {
			notFound(MetaObjectType.LABEL, id);
		}
		validator.validate(data);
		
		label.setName(data.getName());
		label.setProperties(data.getProperties());
		lblRepository.update(label);
		
		RestResponse<LabelData> entity = this.getLabelById(label.getId());
		return ResponseEntity.ok(entity);
	}
	
	/**
	 * Deletes a label.
	 * 
	 * @param id
	 *            ID of the label 
	 *             
	 * @throws MetaObjectNotFoundException
	 *             if label is not found
	 */
	@Transactional 
	@RequestMapping ( value = "/{id}", method = DELETE )
	public ResponseEntity<Void> deleteLabel(@PathVariable ("id") Long id) {
		
		Label label = lblRepository.findById(id);
		if (label == null) {
			notFound(MetaObjectType.LABEL, id);
		}				
		lblRepository.delete(label);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Sets one label as a child of another.
	 * 
	 * @param id
	 *            ID of the parent label 
	 * @param childId
	 *            ID of label to be added as a child
	 *             
	 * @throws MetaObjectNotFoundException
	 *             if parent or child label is not found
	 */
	@Transactional 
	@RequestMapping ( value = "/{pid}/child/{cid}", method = PUT )
	public ResponseEntity<Void> addChildLabel(@PathVariable ("pid") Long pid, 
			@PathVariable ("cid") Long cid) {
		
		Label label = lblRepository.findById(pid);
		if (label == null) {
			notFound(MetaObjectType.LABEL, pid);
		}
		
		Label child = lblRepository.findById(cid);
		if (child == null) {
			notFound(MetaObjectType.LABEL, cid);
		}
		
		label.addChild(child);
		lblRepository.update(label);
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Removes the parent-child relationship between 2 labels.
	 * 
	 * @param id
	 *            ID of the parent label 
	 * @param childId
	 *            ID of label to be removed as a child
	 *             
	 * @throws MetaObjectNotFoundException
	 *             if parent or child label is not found
	 */
	@Transactional 
	@RequestMapping ( value = "/{pid}/child/{cid}", method = DELETE )
	public ResponseEntity<Void> removeChildLabel(@PathVariable ("pid") Long pid, 
			@PathVariable ("cid") Long cid) throws MetaObjectNotFoundException {
		
		Label label = lblRepository.findById(pid);
		if (label == null) {
			notFound(MetaObjectType.LABEL, pid);
		}
		
		Label child = lblRepository.findById(cid);
		if (child == null) {
			notFound(MetaObjectType.LABEL, cid);
		}
		
		label.removeChild(child);
		lblRepository.update(label);
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Converts a list of Labels to a list of LabelData.
	 */
	private List<RestResponse<LabelData>> getLabelCoreList(Collection<Label> labelList) {

		List<RestResponse<LabelData>> list = new ArrayList<>(labelList.size());
		for (Label label : labelList) {
			LabelData data = mapper.map(label, LabelData.class);
			RestResponse<LabelData> item = (new RestResponse<>(data))
					.addLink("self", LabelLinks.buildSelfLink(data.getId()));
			list.add(item);
		}
		return list;
	}
}