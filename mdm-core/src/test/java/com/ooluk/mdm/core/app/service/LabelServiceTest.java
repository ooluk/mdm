package com.ooluk.mdm.core.app.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.ooluk.mdm.core.app.repository.LabelRepository;
import com.ooluk.mdm.core.dto.LabelCore;
import com.ooluk.mdm.core.test.AbstractServiceTest;
import com.ooluk.mdm.core.test.TestData;
import com.ooluk.mdm.core.test.TestUtils;

@ContextConfiguration
public class LabelServiceTest extends AbstractServiceTest {
	
	@Configuration
	static class ContextConfiguration {
		
		@Bean
		public LabelService getLabelService() {
			return new LabelServiceImpl();
		}
		
		@Bean
		public LabelRepository getLabelRepository() {
			return null;
		}
	}
	
	@Mock
	private LabelRepository lblRepository;
	
	@InjectMocks
	@Autowired
	private LabelService ls;

	@Before
	public void beforeTest() {
		MockitoAnnotations.initMocks(this);
		TestUtils.setMockToProxy(ls, "lblRepository", lblRepository);
	}
	
	@Test
	public void get() {
		Mockito.when(lblRepository.findById(1L)).thenReturn(TestData.getLabel("T1", "L1"));
		LabelCore dto = ls.getLabel(1L);
		System.out.println(dto.getName());
	}
	
}
