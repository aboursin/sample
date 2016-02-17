package sample.springldap.configuration;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Global configuration for our Spring MVC application.
 * @see LdapConfiguration
 * @author angelo.boursin
 */
@EnableWebMvc
@Configuration
@ComponentScan({ "sample.springldap" })
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
	
	/**
	 * Reloadable Resource Bundle Message Source bean. 
	 * @return New instance of {@link ReloadableResourceBundleMessageSource}
	 */
	@Bean
	public MessageSource messageSource(){
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
	
	/**
	 * Cookie locale resolver bean.
	 * @return New instance of {@link CookieLocaleResolver}
	 */
	@Bean
	public LocaleResolver localeResolver(){
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("fr"));
		localeResolver.setCookieName("lang");
		localeResolver.setCookieMaxAge(4800);
		return localeResolver;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("lang");
		registry.addInterceptor(interceptor);
    }	

}
