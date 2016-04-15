package com.ooluk.mdm.rest.test;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.inmemory.InMemoryTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;

public class JerseyTestWrapper {

	public static JerseyTest getInstance(final Object... resources) {

		JerseyTest test = new JerseyTest() {
			@Override
			protected Application configure() {
				ResourceConfig rc = new ResourceConfig();
				for (Object obj : resources)
					rc.register(obj);
				return rc;
			}
			
		    @Override
		    protected TestContainerFactory getTestContainerFactory() {
		        return new InMemoryTestContainerFactory();
		    }
		};
		
		return test;
	}
}