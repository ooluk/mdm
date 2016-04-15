package com.ooluk.mdm.rest.test;

import java.util.Arrays;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.ooluk.mdm.core.meta.app.LabelRepository;
import com.ooluk.mdm.core.meta.app.LabelTypeRepository;

@RunWith ( SpringJUnit4ClassRunner.class )
@ContextConfiguration
@WebAppConfiguration
public class RestServiceTest {

    @Autowired
    protected WebApplicationContext wac;
    
    protected MockMvc mvc;

	@Configuration
	@EnableWebMvc
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
	
	@Before
	public void baseSetUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
}