package com.ooluk.mdm.rest.app;

import java.io.ByteArrayInputStream;

import javax.ws.rs.core.Response;

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
import com.ooluk.mdm.rest.commons.MetaObjectNotFoundException;
import com.ooluk.mdm.rest.test.JerseyTestComponent;
import com.ooluk.mdm.rest.test.RestServiceTest;
import com.ooluk.mdm.rest.test.TestData;
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
	private LabelService service;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		TestUtils.setMockToProxy(service, "lblRepository", lblRepo);
		rest = JerseyTestComponent.getInstance(service);
		rest.setUp();
	}
	
	private Label getLabel() {		
		Label lbl = TestData.getLabel("T1", "L1");
		TestUtils.setIdField(lbl.getType(), 1L);
		TestUtils.setIdField(lbl, 1L);
		return lbl;
	}

	@Test
	public void getLabelById() throws Exception {
		Label lbl = getLabel();
		Mockito.when(lblRepo.findById(1L)).thenReturn(lbl);
		Response resp = rest.target("label").path("1").request().get();
		ByteArrayInputStream bs = (ByteArrayInputStream) resp.getEntity();
		byte[] b = new byte[1024];
		bs.read(b);
		System.out.println(new String(b));
		System.out.println(resp.getHeaders());
		//DTOMatcher.verifyEqual(lbl, label);
	}
	
	@Test
	public void getLabelById_Not_Found() throws MetaObjectNotFoundException {
		
		Mockito.when(lblRepo.findById(1L)).thenReturn(null);
		//thrown.expect(MetaObjectNotFoundExceptionMatcher.getInstance(MetaObjectType.LABEL, 1L));
		Response resp = rest.target("label").path("1").request().get();
		System.out.println(resp.getStatus());
	}
}


