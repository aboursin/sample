package sample.springsecurity.configuration;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Web Initializer (instead of web.xml).
 * @author angelo.boursin
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected WebApplicationContext createRootApplicationContext() {
		WebApplicationContext context = (WebApplicationContext) super.createRootApplicationContext();
	 	((ConfigurableEnvironment) context.getEnvironment()).setDefaultProfiles("MEM");
		return context;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { GlobalConfiguration.class };
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
}
