package com.ooluk.mdm.core.test;

import java.util.Arrays;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A common superclass for repository tests.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
@RunWith ( SpringJUnit4ClassRunner.class )
@ContextConfiguration
public abstract class AbstractServiceTest {

	@Configuration
	static class ContextConfiguration {

		@Bean
		public Mapper getDozerMapper() {
			DozerBeanMapper mapper = new DozerBeanMapper();
			mapper.setMappingFiles(Arrays.asList(new String[]{"dozer.xml"}));
			return mapper;
		}
	}
}