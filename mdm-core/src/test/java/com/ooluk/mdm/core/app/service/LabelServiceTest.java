package com.ooluk.mdm.core.app.service;

import static org.junit.Assert.assertEquals;

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
import org.springframework.test.context.ContextConfiguration;

import com.ooluk.mdm.core.dto.LabelCore;
import com.ooluk.mdm.core.dto.LabelTypeCore;
import com.ooluk.mdm.core.meta.MetaObjectType;
import com.ooluk.mdm.core.meta.app.LabelRepository;
import com.ooluk.mdm.core.meta.app.LabelType;
import com.ooluk.mdm.core.meta.app.LabelTypeRepository;
import com.ooluk.mdm.core.service.DuplicateKeyException;
import com.ooluk.mdm.core.service.MetaObjectNotFoundException;
import com.ooluk.mdm.core.test.AbstractServiceTest;
import com.ooluk.mdm.core.test.TestData;
import com.ooluk.mdm.core.test.TestUtils;
import com.ooluk.mdm.core.test.matchers.MetaObjectNotFoundExceptionMatcher;

@ContextConfiguration
public class LabelServiceTest extends AbstractServiceTest {
	
	@Configuration
	static class ContextConfiguration {
		
		@Bean
		public LabelService getLabelService() {
			return new LabelServiceImpl();
		}
		
		@Bean
		public LabelTypeRepository getLabelTypeRepository() {
			return null;
		}
		
		@Bean
		public LabelRepository getLabelRepository() {
			return null;
		}
	}
	
	@Mock
	private LabelRepository lblRepo;
	@Mock
	private LabelTypeRepository lblTypeRepo;
	
	@InjectMocks
	@Autowired
	private LabelService ls;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void beforeTest() {
		MockitoAnnotations.initMocks(this);
		TestUtils.setMockToProxy(ls, "lblRepository", lblRepo);
	}
	
	@Test
	public void getLabel() throws MetaObjectNotFoundException {
		
		Mockito.when(lblRepo.findById(1L)).thenReturn(TestData.getLabel("T1", "L1"));
		LabelCore lbl = ls.getLabel(1L);
		assertEquals("T1", lbl.getType().getName());
		assertEquals("L1", lbl.getName());
		assertEquals(TestData.getDynamicProperties(), lbl.getProperties());
	}
	
	@Test
	public void getLabel_Not_Found() throws MetaObjectNotFoundException {
		
		Mockito.when(lblRepo.findById(1L)).thenReturn(null);
		thrown.expect(MetaObjectNotFoundExceptionMatcher.getInstance(MetaObjectType.LABEL, 1L));
		ls.getLabel(1L);
	}
	
	
	@Test
	public void createLabel() throws MetaObjectNotFoundException, DuplicateKeyException {
		
		Mockito.when(lblTypeRepo.findById(1L)).thenReturn(new LabelType());		
		LabelTypeCore labelType = new LabelTypeCore();
		labelType.setProperties(TestData.getDynamicProperties());
		labelType.setId(1L);
		LabelCore label = new LabelCore();
		label.setType(labelType);
		label.setName("L1");
		label.setProperties(TestData.getDynamicProperties());
		ls.createLabel(label);
		Mockito.verify(lblRepo, Mockito.times(1)).create(Mockito.any());
	}
	
	@Test
	public void createLabel_Type_Not_Found() throws MetaObjectNotFoundException, DuplicateKeyException {
		
		Mockito.when(lblTypeRepo.findById(1L)).thenReturn(null);		
		LabelTypeCore labelType = new LabelTypeCore();
		labelType.setId(1L);
		LabelCore label = new LabelCore();
		label.setType(labelType);
		label.setName("L1");
		
		thrown.expect(MetaObjectNotFoundExceptionMatcher.getInstance(MetaObjectType.LABEL_TYPE, 1L));
		ls.createLabel(label);
	}
}
