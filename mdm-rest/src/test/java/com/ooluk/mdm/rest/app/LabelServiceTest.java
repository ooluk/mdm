package com.ooluk.mdm.rest.app;

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

import com.ooluk.mdm.core.meta.app.Label;
import com.ooluk.mdm.core.meta.app.LabelRepository;
import com.ooluk.mdm.core.meta.app.LabelType;
import com.ooluk.mdm.core.meta.app.LabelTypeRepository;
import com.ooluk.mdm.rest.dto.LabelData;
import com.ooluk.mdm.rest.test.RestServiceTest;
import com.ooluk.mdm.rest.test.TestData;
import com.ooluk.mdm.rest.test.TestUtils;

@ContextConfiguration
public class LabelServiceTest extends RestServiceTest {
	
	@Configuration
	static class ContextConfiguration {		
		@Bean
		public LabelService getLabelService() {
			return new LabelService();
		}
	}
	
	@Mock
	private LabelRepository lblRepo;	
	@Mock
	private LabelTypeRepository lblTypeRepo;
	
	@InjectMocks
	@Autowired
	private LabelService service;
	
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

	@Test
	public void getLabelsByType() throws Exception {
		Label lbl = getLabel();
		LabelType type = lbl.getType();
		Long typeId = type.getId();
		Mockito.when(lblTypeRepo.findById(typeId)).thenReturn(type);
		Mockito.when(lblRepo.findByType(type)).thenReturn(new ArrayList<>(lbl.getParents()));
		mvc.perform(get("/labels/type/" + typeId).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].content.id").value(Matchers.in(new Integer[]{21, 22})))
				.andExpect(jsonPath("$[1].content.id").value(Matchers.in(new Integer[]{21, 22})))
				.andExpect(jsonPath("$[0].links.self").value(Matchers.matchesPattern(".*/labels/2[12]")))
				.andExpect(jsonPath("$[1].links.self").value(Matchers.matchesPattern(".*/labels/2[12]")));
	}

	@Test
	public void getLabelsByType_No_Labels() throws Exception {
		Mockito.when(lblRepo.findRootLabels()).thenReturn(Collections.<Label>emptyList());
		mvc.perform(get("/labels/roots").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(0));
	}

	@Test
	public void getLabelsByType_Type_Not_Found() throws Exception {
		Label lbl = getLabel();
		LabelType type = lbl.getType();
		Long typeId = type.getId();
		Mockito.when(lblTypeRepo.findById(typeId)).thenReturn(type);
		Mockito.when(lblRepo.findByType(type)).thenReturn(Collections.<Label>emptyList());
		mvc.perform(get("/labels/type/" + typeId).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(0));
	}

	private LabelData getLabelData() {
		LabelData label = new LabelData();
		label.setId(1L);
		label.setName("L1");
		label.setProperties(TestData.getDynamicProperties());
		return label;
	}

	@Test
	public void createLabel() throws Exception {
		
		Label label = TestData.getLabel("T1", "L1", 1L);
		LabelType type = label.getType();
		Mockito.when(lblTypeRepo.findById(type.getId())).thenReturn(type);
		Mockito.when(lblRepo.findById(1L)).thenReturn(label);
		
		String content = jsonMapper.writeValueAsString(getLabelData());
		mvc.perform(post("/labels/type/" + type.getId())
				.contentType(MediaType.APPLICATION_JSON).content(content)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(header().string("location", Matchers.endsWith("labels/1")))
				.andExpect(jsonPath("$.content.id").value(1));
	}

	@Test
	public void createLabel_Type_Not_Found() throws Exception {
		
		Mockito.when(lblTypeRepo.findById(Mockito.anyLong())).thenReturn(null);
		
		String content = jsonMapper.writeValueAsString(getLabelData());
		mvc.perform(post("/labels/type/1")
				.contentType(MediaType.APPLICATION_JSON).content(content)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void createLabel_Invalid_Data() throws Exception {
		
		Mockito.when(lblTypeRepo.findById(1L)).thenReturn(new LabelType());
		
		LabelData data = getLabelData(); data.setName("");
		
		String content = jsonMapper.writeValueAsString(data);
		mvc.perform(post("/labels/type/1")
				.contentType(MediaType.APPLICATION_JSON).content(content)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0]").value("Name is missing"));
	}

	@Test
	public void updateLabel() throws Exception {
		
		Label label =new Label();
		label.setId(1L);
		Mockito.when(lblRepo.findById(1L)).thenReturn(label);
		
		String content = jsonMapper.writeValueAsString(getLabelData());
		mvc.perform(put("/labels/1")
				.contentType(MediaType.APPLICATION_JSON).content(content)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.id").value(1))
				.andExpect(jsonPath("$.content.name").value("L1"))
				.andExpect(jsonPath("$.content.properties.number").value(100))
				.andExpect(jsonPath("$.links.self").value(Matchers.endsWith("/labels/1")))
				.andExpect(jsonPath("$.links.children").value(Matchers.endsWith("/labels/1/children")))
				.andExpect(jsonPath("$.links.parents").value(Matchers.endsWith("/labels/1/parents")));
	}
}


