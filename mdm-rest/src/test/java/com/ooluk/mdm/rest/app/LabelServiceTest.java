package com.ooluk.mdm.rest.app;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.MvcResult;

import com.ooluk.mdm.core.meta.app.Label;
import com.ooluk.mdm.core.meta.app.LabelRepository;
import com.ooluk.mdm.core.meta.app.LabelTypeRepository;
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
		Label lbl = TestData.getLabel("T1", "L1");
		Label c1 = TestData.getLabel("T1", "C1");
		TestUtils.setIdField(c1.getType(), 1L);
		TestUtils.setIdField(c1, 11L);
		Label c2 = TestData.getLabel("T1", "C2");
		TestUtils.setIdField(c2.getType(), 1L);
		TestUtils.setIdField(c2, 12L);
		lbl.getChildren().add(c1);
		lbl.getChildren().add(c2);
		TestUtils.setIdField(lbl.getType(), 1L);
		TestUtils.setIdField(lbl, 1L);
		return lbl;
	}

	@Test
	public void getLabelById() throws Exception {
		Label lbl = getLabel();
		Mockito.when(lblRepo.findById(1L)).thenReturn(lbl);
		MvcResult r  = mvc.perform(get("/label/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.name").value("L1"))
				.andReturn();
		System.out.println(r.getResponse().getContentAsString());
	}

	@Test
	public void getChildLabels() throws Exception {
		Label lbl = getLabel();
		Mockito.when(lblRepo.findById(1L)).thenReturn(lbl);
		MvcResult r  = mvc.perform(get("/label/1/children").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				//.andExpect(jsonPath("$.content.name").value("L1"))
				.andReturn();
		System.out.println(r.getResponse().getContentAsString());
	}
}


