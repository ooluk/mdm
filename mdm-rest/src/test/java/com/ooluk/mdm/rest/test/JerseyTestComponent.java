package com.ooluk.mdm.rest.test;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.inmemory.InMemoryTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;

/**
 * The usual way to use JerseyTest is to have the test class subclass it. JerseyTest requires the
 * resource (service) instance to be specified to its configuration that it completes in its no
 * argument constructor, which is called as soon as the test class is instantiated upon a call to
 * super().
 * 
 * <p>
 * This presents a problem while using it with Spring and Mockito annotations because you do not
 * have an opportunity to inject annotated mocks into the resource instance since the Spring beans
 * and Mockito mocks are only instantiated and injected after the main test class is and by that
 * time the resource instance is supposed to be instantiated and configured. If the resource class
 * instance is configured as a spring bean it is not yet instantiated at that point resulting in a
 * NPE in the configure() method.
 * 
 * <p>
 * We could programmatically create the beans and inject the mocks into the service class instance
 * before passing it to ResourceConfig but that may still leave issues with other Spring annotations
 * such as @Secured as the instance would not be Spring managed.
 * 
 * <p>
 * Our approach is to let Spring create a spring managed instance of the service class and have the
 * mocks injected as usual. We then instantiate JerseyTest as a component passing the spring managed
 * instance of the service class to its configuration. This component is then used for testing.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
public class JerseyTestComponent {

	/**
	 * Creates an instance of JerseyTest with the specified resources.
	 * 
	 * @param resources
	 *            array of Jersey resources
	 *            
	 * @return instance of JerseyTest
	 */
	public static JerseyTest getInstance(final Object... resources) {

		JerseyTest test = new JerseyTest() {
			@Override
			protected Application configure() {
				ResourceConfig rc = new ResourceConfig();
				rc.packages("com.ooluk.mdm.rest.commons");
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