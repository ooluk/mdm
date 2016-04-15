package com.ooluk.mdm.rest.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ooluk.mdm.core.meta.MetaObjectType;
import com.ooluk.mdm.core.meta.app.Label;
import com.ooluk.mdm.core.meta.app.LabelRepository;
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
@RequestMapping ( 
		value = "/label", 
		produces = MediaType.APPLICATION_JSON_UTF8_VALUE, 
		consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
)
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
	@RequestMapping ( value = "/{id}", method = RequestMethod.GET)
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
	 * Returns the a list of LabelCore(s) from a list of Label(s).
	 * 
	 * @param labelList
	 *            list of Label(s)
	 *            
	 * @return list of LabelCore(s)
	 * 
	 * @throws MetaObjectNotFoundException 
	 */
	private List<RestResponse<LabelCore>> getLabelCoreList(Collection<Label> labelList) throws MetaObjectNotFoundException {

		List<RestResponse<LabelCore>> coreList = new ArrayList<>(labelList.size());
		for (Label label : labelList) {
			LabelCore coreItem = mapper.map(label, LabelCore.class);
			RestResponse<LabelCore> iLabel = (new RestResponse<>(coreItem))
					.addLink("self", LabelLinkSupport.buildSelfLink(coreItem.getId()));
			coreList.add(iLabel);
		}
		return coreList;
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
	@RequestMapping ( value = "/{id}/children", method = RequestMethod.GET)
	public List<RestResponse<LabelCore>> getChildLabels(@PathVariable ( "id" ) Long id) 
			throws MetaObjectNotFoundException {

		Label lbl = lblRepository.findById(id);
		if (lbl == null) {
			notFound(MetaObjectType.LABEL, id);
		}

		List<RestResponse<LabelCore>> children = getLabelCoreList(lbl.getChildren());
		return children;
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
	@RequestMapping ( value = "/{id}/parents", method = RequestMethod.GET)
	public RestResponse<List<RestResponse<LabelCore>>> getParentLabels(@PathVariable ( "id" ) Long id) 
			throws MetaObjectNotFoundException {

		Label lbl = lblRepository.findById(id);
		if (lbl == null) {
			notFound(MetaObjectType.LABEL, id);
		}

		List<RestResponse<LabelCore>> children = getLabelCoreList(lbl.getParents());
		return new RestResponse<>(children).addLink("self", LabelLinkSupport.buildChildrenLink(id));
	}
}