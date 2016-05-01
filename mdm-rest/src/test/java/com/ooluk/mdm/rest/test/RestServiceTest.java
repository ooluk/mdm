package com.ooluk.mdm.rest.test;

import java.util.Arrays;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ooluk.mdm.data.meta.DynamicPropertiesCache;
import com.ooluk.mdm.data.meta.DynamicPropertyRepository;
import com.ooluk.mdm.data.meta.app.LabelRepository;
import com.ooluk.mdm.data.meta.app.LabelTypeRepository;

@RunWith ( SpringJUnit4ClassRunner.class )
@ContextConfiguration
@WebAppConfiguration
public abstract class RestServiceTest {
	
    @Autowired
    protected WebApplicationContext wac;
	
	@Autowired
	private RequestMappingHandlerMapping requestMapping;
    
    protected MockMvc mvc;
    
    protected ObjectMapper jsonMapper = new ObjectMapper();

	@Configuration
	@EnableWebMvc
	@ComponentScan ( basePackages = {
			"com.ooluk.mdm.rest.commons",
			"com.ooluk.mdm.rest.validation",
			"com.ooluk.mdm.rest.app.validation"})
	static class ContextConfiguration {

		@Bean
		public Mapper getDozerMapper() {
			DozerBeanMapper mapper = new DozerBeanMapper();
			mapper.setMappingFiles(Arrays.asList(new String[]{"dozer.xml"}));
			return mapper;
		}
		
		@Bean 
		public DynamicPropertiesCache getDynamicPropertiesCache() {
			DynamicPropertiesCache cache = new DynamicPropertiesCache();
			DynamicPropertyRepository repository = Mockito.mock(DynamicPropertyRepository.class);
			cache.setRepository(repository);
			cache.build();
			return cache; 
		}	
		
		@Bean public LabelTypeRepository getLabelTypeRepository() { return null; }		
		@Bean public LabelRepository getLabelRepository() { return null; }
	}
	
	@Before
	public void baseSetUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        // Required to enable @MatrixVariables 
        requestMapping.setRemoveSemicolonContent(false);
	}
}