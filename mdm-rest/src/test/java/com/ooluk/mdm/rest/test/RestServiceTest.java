package com.ooluk.mdm.rest.test;

import java.util.Arrays;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ooluk.mdm.core.meta.app.LabelRepository;
import com.ooluk.mdm.core.meta.app.LabelTypeRepository;

@RunWith ( SpringJUnit4ClassRunner.class )
@ContextConfiguration
public class RestServiceTest {
	
	protected JerseyTest test;

	@Configuration
	static class ContextConfiguration {

		@Bean
		public Mapper getDozerMapper() {
			DozerBeanMapper mapper = new DozerBeanMapper();
			mapper.setMappingFiles(Arrays.asList(new String[]{"dozer.xml"}));
			return mapper;
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
	
	@After
	public void tearDown() throws Exception {
		test.tearDown();
	}
}