package sample.springsecurity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Global configuration for our Spring MVC application.
 * @see SecurityMEMConfiguration
 * @author angelo.boursin
 */
@EnableWebMvc
@Configuration
@ComponentScan({ "sample.springsecurity" })
public class GlobalConfiguration extends WebMvcConfigurerAdapter {

	public GlobalConfiguration(){
		super();
	}
 
	/**
	 * {@inheritDoc}
	 */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
	
    /**
     * {@inheritDoc}
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/css/**").addResourceLocations("/css/");
    	registry.addResourceHandler("/images/**").addResourceLocations("/images/");
    	registry.addResourceHandler("/js/**").addResourceLocations("/js/");
    }
    
    /**
     * View resolver bean.
     * @return New instance of {@link InternalResourceViewResolver}
     */
	@Bean
	public ViewResolver internalResourceViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
}
