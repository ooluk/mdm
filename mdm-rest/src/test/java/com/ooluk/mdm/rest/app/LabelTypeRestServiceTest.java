package com.ooluk.mdm.rest.app;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

import com.ooluk.mdm.core.meta.app.LabelType;
import com.ooluk.mdm.core.meta.app.LabelTypeRepository;
import com.ooluk.mdm.rest.app.dto.LabelTypeData;
import com.ooluk.mdm.rest.test.RestServiceTest;
import com.ooluk.mdm.rest.test.TestData;
import com.ooluk.mdm.rest.test.TestUtils;

@ContextConfiguration
public class LabelTypeRestServiceTest extends RestServiceTest {
	
	@Configuration
	static class ContextConfiguration {		
		@Bean
		public LabelTypeRestService getLabelTypeService() {
			return new LabelTypeRestService();
		}
	}
	@Mock
	private LabelTypeRepository typeRepo;
	
	@InjectMocks
	@Autowired
	private LabelTypeRestService service;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		TestUtils.setMockToProxy(service, "typeRepository", typeRepo);
	}
	
	private LabelType getLabelType() {		
		LabelType type = TestData.getLabelType("T1", 1L);
		return type;
	}
	
	private LabelTypeData getTypeData() {
		LabelTypeData type = new LabelTypeData();
		type.setId(1L);
		type.setName("T1");
		type.setProperties(TestData.getDynamicProperties());
		return type;
	}

	@Test
	public void getTypeById() throws Exception {
		Mockito.when(typeRepo.findById(1L)).thenReturn(getLabelType());
		mvc.perform(get("/labeltypes/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.id").value(1))
				.andExpect(jsonPath("$.content.name").value("T1"))
				.andExpect(jsonPath("$.content.properties.number").value(100))
				.andExpect(jsonPath("$.links.self").value(Matchers.endsWith("/labeltypes/1")))
				.andExpect(jsonPath("$.links.labels").value(Matchers.endsWith("/labels?type=1")));
	}

	@Test
	public void getTypeById_Not_Found() throws Exception {
		Mockito.when(typeRepo.findById(1L)).thenReturn(null);
		mvc.perform(get("/labeltypes/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void getType() throws Exception {
		List<LabelType> list = new ArrayList<>();
		list.add(TestData.getLabelType("T1", 1L));
		list.add(TestData.getLabelType("T2", 2L));
		Mockito.when(typeRepo.getAll()).thenReturn(list);
		mvc.perform(get("/labeltypes").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].content.id").value(Matchers.in(new Integer[]{1, 2})))
				.andExpect(jsonPath("$[1].content.id").value(Matchers.in(new Integer[]{1, 2})))
				.andExpect(jsonPath("$[0].links.self").value(Matchers.matchesPattern(".*/labeltypes/[12]")))
				.andExpect(jsonPath("$[1].links.self").value(Matchers.matchesPattern(".*/labeltypes/[12]")));
	}

	@Test
	public void getType_No_Types() throws Exception {
		Mockito.when(typeRepo.getAll()).thenReturn(Collections.emptyList());
		mvc.perform(get("/labeltypes").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(0));
	}
	
	@Test
	public void createType() throws Exception {
		Mockito.when(typeRepo.findById(1L)).thenReturn(getLabelType());
		String json = jsonMapper.writeValueAsString(getTypeData());
		mvc.perform(post("/labeltypes").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated())
				.andExpect(header().string("location", Matchers.endsWith("labeltypes/1")))
				.andExpect(jsonPath("$.content.id").value(1));
	}

	@Test
	public void createType_Invalid_Data() throws Exception {
		Mockito.when(typeRepo.findById(1L)).thenReturn(getLabelType());	
		LabelTypeData data = getTypeData(); data.setName("");		
		String content = jsonMapper.writeValueAsString(data);
		mvc.perform(post("/labeltypes")
				.contentType(MediaType.APPLICATION_JSON).content(content)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0]").value("Name is missing"));
	}
	
	@Test
	public void updateType() throws Exception {
		Mockito.when(typeRepo.findById(1L)).thenReturn(getLabelType());
		String json = jsonMapper.writeValueAsString(getTypeData());
		mvc.perform(put("/labeltypes/1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.id").value(1));
	}
	
	@Test
	public void updateType_Not_Found() throws Exception {
		Mockito.when(typeRepo.findById(1L)).thenReturn(null);
		String json = jsonMapper.writeValueAsString(getTypeData());
		mvc.perform(put("/labeltypes/1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void updateType_Invalid_Data() throws Exception {
		Mockito.when(typeRepo.findById(1L)).thenReturn(getLabelType());	
		LabelTypeData data = getTypeData(); data.setName("");
		String json = jsonMapper.writeValueAsString(data);
		mvc.perform(put("/labeltypes/1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0]").value("Name is missing"));;
	}
	
	@Test
	public void deleteType() throws Exception {
		Mockito.when(typeRepo.findById(1L)).thenReturn(getLabelType());
		mvc.perform(delete("/labeltypes/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}
	
	@Test
	public void deleteType_Not_Found() throws Exception {
		Mockito.when(typeRepo.findById(1L)).thenReturn(null);
		mvc.perform(delete("/labeltypes/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}