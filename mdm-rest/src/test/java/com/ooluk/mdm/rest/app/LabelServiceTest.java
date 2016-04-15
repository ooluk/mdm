package com.ooluk.mdm.rest.app;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ooluk.mdm.core.meta.app.Label;
import com.ooluk.mdm.core.meta.app.LabelRepository;
import com.ooluk.mdm.core.meta.app.LabelTypeRepository;
import com.ooluk.mdm.rest.dto.LabelCore;
import com.ooluk.mdm.rest.test.JerseyTestWrapper;
import com.ooluk.mdm.rest.test.RestServiceTest;
import com.ooluk.mdm.rest.test.TestUtils;

@RunWith ( SpringJUnit4ClassRunner.class )
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
	private LabelService ls;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		TestUtils.setMockToProxy(ls, "lblRepository", lblRepo);
		test = JerseyTestWrapper.getInstance(ls);
		test.setUp();
	}

	@Test
	public void getLabelById() throws Exception {

		Mockito.when(lblRepo.findById(1L)).thenReturn(new Label());
		final LabelCore label = test.target("label").path("1").request().get(LabelCore.class);
	}
}


