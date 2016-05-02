package com.ooluk.mdm.rest.app;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Collections;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import com.ooluk.mdm.data.meta.app.Label;
import com.ooluk.mdm.data.meta.app.LabelRepository;
import com.ooluk.mdm.data.meta.app.LabelType;
import com.ooluk.mdm.data.meta.app.LabelTypeRepository;
import com.ooluk.mdm.rest.app.dto.LabelData;
import com.ooluk.mdm.rest.app.dto.LabelTypeData;
import com.ooluk.mdm.rest.test.RestServiceTest;
import com.ooluk.mdm.rest.test.TestData;
import com.ooluk.mdm.rest.test.TestUtils;
import com.ooluk.mdm.rest.validation.VM;

@ContextConfiguration
public class LabelRestServiceTest extends RestServiceTest {
	
	@Configuration
	static class ContextConfiguration {		
		@Bean
		public LabelRestService getLabelService() {
			return new LabelRestService();
		}
	}
	
	@Mock
	private LabelRepository lblRepo;	
	@Mock
	private LabelTypeRepository typeRepo;
	
	@InjectMocks
	@Autowired
	private LabelRestService service;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		TestUtils.setMockToProxy(service, "lblRepository", lblRepo);
	}
	
	private Label getLabel() {		
		Label lbl = TestData.getLabel("T1", "L1", 1L);
		// Add children
		Label c1 = TestData.getLabel("T1", "C1", 11L);
		Label c2 = TestData.getLabel("T1", "C2", 12L);
		lbl.getChildren().add(c1);
		lbl.getChildren().add(c2);
		// Add parents
		Label p1 = TestData.getLabel("T1", "P1", 21L);
		Label p2 = TestData.getLabel("T1", "P2", 22L);
		lbl.getParents().add(p1);
		lbl.getParents().add(p2);
		return lbl;
	}
	
	private LabelTypeData getTypeData() {
		LabelTypeData type = new LabelTypeData();
		type.setId(1L);
		type.setName("T1");
		type.setProperties(TestData.getDynamicProperties());
		return type;
	}

	private LabelData getLabelData() {
		LabelData label = new LabelData();
		label.setId(1L);
		label.setType(getTypeData());
		label.setName("L1");
		label.setProperties(TestData.getDynamicProperties());
		return label;
	}

	/*
	 * --------------------------------------------
	 * GET: /labels/{id}
	 * --------------------------------------------
	 */
	@Test
	public void getLabelById() throws Exception {
		Mockito.when(lblRepo.findById(1L)).thenReturn(getLabel());
		mvc.perform(get("/labels/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.id").value(1))
				.andExpect(jsonPath("$.content.name").value("L1"))
				.andExpect(jsonPath("$.content.type.id").value(1))
				.andExpect(jsonPath("$.content.type.name").value("T1"))
				.andExpect(jsonPath("$.content.properties.number").value(100))
				.andExpect(jsonPath("$.links.self").value(Matchers.endsWith("/labels/1")))
				.andExpect(jsonPath("$.links.children").value(Matchers.endsWith("/labels/1/children")))
				.andExpect(jsonPath("$.links.parents").value(Matchers.endsWith("/labels/1/parents")));
	}

	@Test
	public void getLabelById_Not_Found() throws Exception {
		Mockito.when(lblRepo.findById(1L)).thenReturn(null);
		mvc.perform(get("/labels/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	/*
	 * --------------------------------------------
	 * GET: /labels/{id}/children
	 * --------------------------------------------
	 */
	@Test
	public void getChildLabels() throws Exception {
		Mockito.when(lblRepo.findById(1L)).thenReturn(getLabel());
		mvc.perform(get("/labels/1/children").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].content.id").value(Matchers.in(new Integer[]{11, 12})))
				.andExpect(jsonPath("$[1].content.id").value(Matchers.in(new Integer[]{11, 12})))
				.andExpect(jsonPath("$[0].links.self").value(Matchers.matchesPattern(".*/labels/1[12]")))
				.andExpect(jsonPath("$[1].links.self").value(Matchers.matchesPattern(".*/labels/1[12]")));
	}

	@Test
	public void getChildLabels_No_Children() throws Exception {
		Label lbl = getLabel();
		lbl.getChildren().clear();
		Mockito.when(lblRepo.findById(1L)).thenReturn(lbl);
		mvc.perform(get("/labels/1/children").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(0));
	}

	/*
	 * --------------------------------------------
	 * GET: /labels/{id}/parents
	 * --------------------------------------------
	 */
	@Test
	public void getParentsLabels() throws Exception {
		Mockito.when(lblRepo.findById(1L)).thenReturn(getLabel());
		mvc.perform(get("/labels/1/parents").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].content.id").value(Matchers.in(new Integer[]{21, 22})))
				.andExpect(jsonPath("$[1].content.id").value(Matchers.in(new Integer[]{21, 22})))
				.andExpect(jsonPath("$[0].links.self").value(Matchers.matchesPattern(".*/labels/2[12]")))
				.andExpect(jsonPath("$[1].links.self").value(Matchers.matchesPattern(".*/labels/2[12]")));
	}

	@Test
	public void getParentLabels_No_Parents() throws Exception {
		Label lbl = getLabel();
		lbl.getParents().clear();
		Mockito.when(lblRepo.findById(1L)).thenReturn(lbl);
		mvc.perform(get("/labels/1/parents").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(0));
	}

	/*
	 * --------------------------------------------
	 * GET: /labels/roots
	 * --------------------------------------------
	 */
	@Test
	public void getRootLabels() throws Exception {
		// Return parents of test label - any set of labels is fine
		Mockito.when(lblRepo.findRootLabels()).thenReturn(new ArrayList<>(getLabel().getParents()));
		mvc.perform(get("/labels/roots").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].content.id").value(Matchers.in(new Integer[]{21, 22})))
				.andExpect(jsonPath("$[1].content.id").value(Matchers.in(new Integer[]{21, 22})))
				.andExpect(jsonPath("$[0].links.self").value(Matchers.matchesPattern(".*/labels/2[12]")))
				.andExpect(jsonPath("$[1].links.self").value(Matchers.matchesPattern(".*/labels/2[12]")));
	}

	@Test
	public void getRootLabels_No_Roots() throws Exception {
		Mockito.when(lblRepo.findRootLabels()).thenReturn(Collections.<Label>emptyList());
		mvc.perform(get("/labels/roots").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(0));
	}

	/*
	 * --------------------------------------------
	 * GET: /labels?type=
	 * --------------------------------------------
	 */
	@Test
	public void getLabels_Query_Type() throws Exception {
		Label lbl = getLabel();
		LabelType type = lbl.getType();
		Long typeId = type.getId();
		Mockito.when(typeRepo.findById(typeId)).thenReturn(type);
		Mockito.when(lblRepo.findByType(type)).thenReturn(new ArrayList<>(lbl.getParents()));
		mvc.perform(get("/labels?type=" + typeId).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].content.id").value(Matchers.in(new Integer[]{21, 22})))
				.andExpect(jsonPath("$[1].content.id").value(Matchers.in(new Integer[]{21, 22})))
				.andExpect(jsonPath("$[0].links.self").value(Matchers.matchesPattern(".*/labels/2[12]")))
				.andExpect(jsonPath("$[1].links.self").value(Matchers.matchesPattern(".*/labels/2[12]")));
	}

	@Test
	public void getLabels_Query_Type_No_Labels() throws Exception {
		LabelType type = new LabelType().setId(1L);
		Mockito.when(typeRepo.findById(type.getId())).thenReturn(type);
		Mockito.when(lblRepo.findByType(type)).thenReturn(Collections.<Label>emptyList());
		mvc.perform(get("/labels?type=1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(0));
	}

	@Test
	public void getLabels_Query_Type_Not_Found() throws Exception {
		LabelType type = new LabelType().setId(1L);
		Mockito.when(typeRepo.findById(1L)).thenReturn(type);
		Mockito.when(lblRepo.findByType(type)).thenReturn(Collections.<Label>emptyList());
		mvc.perform(get("/labels?type=1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(0));
	}

	@Test
	public void getLabels_No_Query() throws Exception {
		mvc.perform(get("/labels").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(Matchers.blankString()));
	}

	@Test
	public void getLabels_No_Query_Value() throws Exception {
		mvc.perform(get("/labels?type=").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(VM.msg("param.type.required")));
	}

	/*
	 * --------------------------------------------
	 * POST: /labels
	 * --------------------------------------------
	 */
	@Test
	public void createLabel() throws Exception {
		
		Label label = TestData.getLabel("T1", "L1", 1L);
		LabelType type = label.getType();
		Mockito.when(typeRepo.findById(type.getId())).thenReturn(type);
		Mockito.when(lblRepo.findById(1L)).thenReturn(label);
		
		String content = jsonMapper.writeValueAsString(getLabelData());
		mvc.perform(post("/labels")
				.contentType(MediaType.APPLICATION_JSON).content(content)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(header().string("location", Matchers.endsWith("labels/1")))
				.andExpect(jsonPath("$.content.id").value(1));
	}

	@Test
	public void createLabel_Type_Not_Found() throws Exception {		
		Mockito.when(typeRepo.findById(Mockito.anyLong())).thenReturn(null);		
		String content = jsonMapper.writeValueAsString(getLabelData());
		mvc.perform(post("/labels")
				.contentType(MediaType.APPLICATION_JSON).content(content)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	private void invalidDataRequest(String content, String message) throws Exception {
		mvc.perform(post("/labels")
				.contentType(MediaType.APPLICATION_JSON).content(content)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0]").value(message));
	}

	@Test
	public void createLabel_Invalid_Data_Empty_Name() throws Exception {		
		LabelData label = getLabelData(); label.setName("");
		String content = jsonMapper.writeValueAsString(label);
		invalidDataRequest(content, VM.msg("label.name.missing"));		
	}

	@Test
	public void createLabel_Invalid_Data_No_Type() throws Exception {		
		LabelData label = getLabelData(); label.setType(null);
		String content = jsonMapper.writeValueAsString(label);
		invalidDataRequest(content, VM.msg("label.type.missing"));			
	}

	@Test
	public void createLabel_Invalid_Data_No_Type_Id() throws Exception {		
		LabelData label = getLabelData(); label.getType().setId(null);;
		String content = jsonMapper.writeValueAsString(label);
		invalidDataRequest(content, VM.msg("label.type.missing"));			
	}

	/*
	 * --------------------------------------------
	 * PUT: /labels/{id}
	 * --------------------------------------------
	 */
	@Test
	public void updateLabel() throws Exception {		
		Mockito.when(lblRepo.findById(1L)).thenReturn(new Label().setId(1L));		
		String content = jsonMapper.writeValueAsString(getLabelData());
		mvc.perform(put("/labels/1")
				.contentType(MediaType.APPLICATION_JSON).content(content)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.id").value(1));
	}

	@Test
	public void updateLabel_Not_Found() throws Exception {		
		Mockito.when(lblRepo.findById(1L)).thenReturn(null);		
		String content = jsonMapper.writeValueAsString(getLabelData());
		mvc.perform(put("/labels/1")
				.contentType(MediaType.APPLICATION_JSON).content(content)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void updateLabel_Invalid_Data() throws Exception {			
		Mockito.when(lblRepo.findById(1L)).thenReturn(new Label().setId(1L));		
		LabelData data = getLabelData(); data.setName("");		
		String content = jsonMapper.writeValueAsString(data);
		mvc.perform(put("/labels/1")
				.contentType(MediaType.APPLICATION_JSON).content(content)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0]").value(VM.msg("label.name.missing")));
	}

	/*
	 * --------------------------------------------
	 * DELETE: /labels/{id}
	 * --------------------------------------------
	 */
	@Test
	public void deleteLabel() throws Exception {	
		Mockito.when(lblRepo.findById(1L)).thenReturn(new Label().setId(1L));		
		mvc.perform(delete("/labels/1"))
				.andExpect(status().isNoContent());
	}

	@Test
	public void deleteLabel_Not_Found() throws Exception {		
		Mockito.when(lblRepo.findById(1L)).thenReturn(null);		
		mvc.perform(delete("/labels/1"))
				.andExpect(status().isNotFound());
	}

	/*
	 * --------------------------------------------
	 * PUT: /labels/{parent_id}/child/{child_id}
	 * --------------------------------------------
	 */	
	@Test
	public void addChild() throws Exception {	
		Label p = new Label();
		Label c = new Label();
		assertFalse(p.getChildren().contains(c));
		Mockito.when(lblRepo.findById(1L)).thenReturn(p);
		Mockito.when(lblRepo.findById(2L)).thenReturn(c);
		mvc.perform(put("/labels/1/child/2"))
			.andExpect(status().isNoContent()).andReturn();
		assertTrue(p.getChildren().contains(c));
	}
	
	@Test
	public void addChild_Parent_Not_Found() throws Exception {
		Mockito.when(lblRepo.findById(1L)).thenReturn(null);
		mvc.perform(put("/labels/1/child/2"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void addChild_Child_Not_Found() throws Exception {
		Mockito.when(lblRepo.findById(1L)).thenReturn(new Label());
		Mockito.when(lblRepo.findById(2L)).thenReturn(null);
		mvc.perform(put("/labels/1/child/2"))
			.andExpect(status().isNotFound());
	}

	/*
	 * --------------------------------------------
	 * DELETE: /labels/{parent_id}/child/{child_id}
	 * --------------------------------------------
	 */	
	@Test
	public void removeChild() throws Exception {	
		Label p = new Label();
		Label c = new Label();
		p.getChildren().add(c);
		assertTrue(p.getChildren().contains(c));
		Mockito.when(lblRepo.findById(1L)).thenReturn(p);
		Mockito.when(lblRepo.findById(2L)).thenReturn(c);
		mvc.perform(delete("/labels/1/child/2"))
			.andExpect(status().isNoContent()).andReturn();
		assertFalse(p.getChildren().contains(c));
	}
	
	@Test
	public void removeChild_Parent_Not_Found() throws Exception {
		Mockito.when(lblRepo.findById(1L)).thenReturn(null);
		mvc.perform(delete("/labels/1/child/2"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void removeChild_Child_Not_Found() throws Exception {
		Mockito.when(lblRepo.findById(1L)).thenReturn(new Label());
		Mockito.when(lblRepo.findById(2L)).thenReturn(null);
		mvc.perform(delete("/labels/1/child/2"))
			.andExpect(status().isNotFound());
	}
}