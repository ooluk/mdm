package com.ooluk.mdm.rest.app;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ooluk.mdm.core.meta.MetaObjectType;
import com.ooluk.mdm.core.meta.app.LabelType;
import com.ooluk.mdm.core.meta.app.LabelTypeRepository;
import com.ooluk.mdm.rest.app.dto.LabelTypeData;
import com.ooluk.mdm.rest.commons.MetaObjectNotFoundException;
import com.ooluk.mdm.rest.commons.RestService;
import com.ooluk.mdm.rest.dto.RestResponse;
import com.ooluk.mdm.rest.validation.ValidationFailedException;

/**
 * REST service for label type processing.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
@RestController
@RequestMapping ( value = "/labeltypes" )
public class LabelTypeRestService extends RestService {
	
	private static Logger LOGGER = LogManager.getLogger();

	@Autowired
	private LabelTypeRepository typeRepository;

	/**
	 * Gets a label type by ID.
	 * 
	 * @param id
	 *            label type ID
	 *            
	 * @return label type
	 * 
	 * @throws MetaObjectNotFoundException
	 *             if a label type with the specified ID is not found
	 */
	@RequestMapping ( value = "/{id}", method = GET, produces = APPLICATION_JSON_VALUE )
	public RestResponse<LabelTypeData> getTypeById(@PathVariable ( "id" ) Long id)
			throws MetaObjectNotFoundException {

		LabelType type= typeRepository.findById(id);
		if (type == null) {
			notFound(MetaObjectType.LABEL_TYPE, id);
		}
		LabelTypeData data = mapper.map(type, LabelTypeData.class);
		
		return new RestResponse<>(data)
				.addLink("self", LabelTypeHateoas.buildSelfLink(id))
				.addLink("labels", LabelTypeHateoas.buildLabelsLink(id));
	}
	
	/**
	 * Gets all label types.
	 * 
	 * @return list of label types
	 */
	@RequestMapping ( method = GET, produces = APPLICATION_JSON_VALUE )
	public List<RestResponse<LabelTypeData>> getTypes() {		
		return getLabelTypeDataList(typeRepository.getAll());
	}
	
	/**
	 * Creates a label type.
	 * 
	 * @param data
	 *            label type data
	 *            
	 * @return the created label type
	 * 
	 * @throws ValidationFailedException
	 *             if validation of the label type data fails
	 *             
	 */
	@RequestMapping ( method = POST, consumes = APPLICATION_JSON_VALUE )
	public ResponseEntity<RestResponse<LabelTypeData>> createType( 
			@RequestBody LabelTypeData data) throws ValidationFailedException {
		
		validator.validate(data);
		
		LabelType type = mapper.map(data, LabelType.class);
		typeRepository.create(type);
		
		RestResponse<LabelTypeData> body = null;
		try {
			body = this.getTypeById(type.getId());
		} catch (MetaObjectNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException("ERROR: Unable to fetch created label type "
					+ "(ID=" + type.getId() + ")");
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("location", LabelTypeHateoas.buildSelfLink(type.getId()).toString());
		return new ResponseEntity<>(body, headers, HttpStatus.CREATED);
	}
	
	/**
	 * Updates a label type.
	 * 
	 * @param id
	 *            label type ID
	 * @param data
	 *            label type data
	 * 
	 * @return the updated label type
	 * 
	 * @throws MetaObjectNotFoundException
	 *             if a label type with the specified ID is not found
	 * @throws ValidationFailedException
	 *             if validation of the label type data fails
	 * 
	 */
	@RequestMapping ( value = "/{id}", method = PUT, consumes = APPLICATION_JSON_VALUE )
	public ResponseEntity<RestResponse<LabelTypeData>> updateType( 
			@PathVariable("id") Long id, @RequestBody LabelTypeData data)  
			throws MetaObjectNotFoundException, ValidationFailedException {
		
		LabelType type = typeRepository.findById(id);
		if (type == null) {
			notFound(MetaObjectType.LABEL_TYPE, id);
		}		
		validator.validate(data);
		
		type.setName(data.getName());
		type.setProperties(data.getProperties());
		typeRepository.update(type);
		
		RestResponse<LabelTypeData> entity = getTypeById(type.getId());
		return ResponseEntity.ok(entity);
	}
	
	/**
	 * Deletes a label type.
	 * 
	 * @param id
	 *            label type ID
	 * 
	 * @return the deleted label type
	 * 
	 * @throws MetaObjectNotFoundException
	 *             if a label type with the specified ID is not found
	 * 
	 */
	@RequestMapping ( value = "/{id}", method = DELETE )
	public ResponseEntity<Void> deleteType(@PathVariable("id") Long id) 
			throws MetaObjectNotFoundException {
		
		LabelType type = typeRepository.findById(id);
		if (type == null) {
			notFound(MetaObjectType.LABEL_TYPE, id);
		}		
		typeRepository.delete(type);		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Converts a list of LabelTypes to a list of LabelTypeData.
	 */
	private List<RestResponse<LabelTypeData>> getLabelTypeDataList(Collection<LabelType> typeList) {

		List<RestResponse<LabelTypeData>> list = new ArrayList<>(typeList.size());
		for (LabelType type : typeList) {
			LabelTypeData data = mapper.map(type, LabelTypeData.class);
			RestResponse<LabelTypeData> item = (new RestResponse<>(data))
					.addLink("self", LabelTypeHateoas.buildSelfLink(data.getId()));
			list.add(item);
		}
		return list;
	}
}