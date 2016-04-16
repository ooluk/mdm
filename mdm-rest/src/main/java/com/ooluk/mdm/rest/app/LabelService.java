package com.ooluk.mdm.rest.app;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ooluk.mdm.core.meta.MetaObjectType;
import com.ooluk.mdm.core.meta.app.Label;
import com.ooluk.mdm.core.meta.app.LabelRepository;
import com.ooluk.mdm.core.meta.app.LabelType;
import com.ooluk.mdm.core.meta.app.LabelTypeRepository;
import com.ooluk.mdm.rest.commons.MetaObjectNotFoundException;
import com.ooluk.mdm.rest.commons.RestService;
import com.ooluk.mdm.rest.dto.LabelCore;
import com.ooluk.mdm.rest.dto.RestResponse;

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
	public RestResponse<LabelCore> getLabelById(@PathVariable ( "id" ) Long id)
			throws MetaObjectNotFoundException {

		Label label = lblRepository.findById(id);
		if (label == null) {
			notFound(MetaObjectType.LABEL, id);
		}
		LabelCore lbl = mapper.map(label, LabelCore.class);
		
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
	public List<RestResponse<LabelCore>> getChildLabels(@PathVariable ( "id" ) Long id) 
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
	public List<RestResponse<LabelCore>> getParentLabels(@PathVariable ( "id" ) Long id) 
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
	public List<RestResponse<LabelCore>> getRootLabels() {
		
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
	public List<RestResponse<LabelCore>> getLabelsByType(@PathVariable ("type") Long typeId) 
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
	 * Returns a list of LabelCore(s) from a list of Label(s).
	 * 
	 * @param labelList
	 *            list of Label(s)
	 *            
	 * @return list of LabelCore(s)
	 * 
	 * @throws MetaObjectNotFoundException 
	 */
	private List<RestResponse<LabelCore>> getLabelCoreList(Collection<Label> labelList) {

		List<RestResponse<LabelCore>> coreList = new ArrayList<>(labelList.size());
		for (Label label : labelList) {
			LabelCore coreItem = mapper.map(label, LabelCore.class);
			RestResponse<LabelCore> iLabel = (new RestResponse<>(coreItem))
					.addLink("self", LabelLinkSupport.buildSelfLink(coreItem.getId()));
			coreList.add(iLabel);
		}
		return coreList;
	}
}