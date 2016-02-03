package sample.springldap.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.test.LdapTestUtils;

/**
 * LDAP configuration for our application.
 * @see GlobalConfiguration
 * @author angelo.boursin
 */
@Profile("std")
@Configuration
@PropertySource("classpath:ldap.properties")
public class LdapConfiguration implements  DisposableBean {

	private static Logger LOGGER = LoggerFactory.getLogger(LdapConfiguration.class);
	
	@Autowired
	private Environment env;
	
	public LdapConfiguration(){
		super();
		LOGGER.info("Starting...");
	}
	
	/**
	 * LDAP Context source bean.
	 * @return New instance of {@link LdapContextSource}
	 * @throws Exception
	 */
	@Bean
	public ContextSource contextSource() throws Exception{
		LdapContextSource contextSource = new LdapContextSource();
		contextSource.setUrl(env.getProperty("ldap.url"));
		contextSource.setBase(env.getProperty("ldap.base"));
		contextSource.setUserDn(env.getProperty("ldap.userDn"));
		contextSource.setPassword(env.getProperty("ldap.password"));
		contextSource.afterPropertiesSet();
		return contextSource;
	}
	
	/**
	 * LDAP Template bean.
	 * @return New instance of {@link LdapTemplate}
	 * @throws Exception
	 */
	@Bean
	public LdapTemplate ldapTemplate() throws Exception{
		LdapTemplate ldapTemplate = new LdapTemplate();
		ldapTemplate.setContextSource(contextSource());
		return ldapTemplate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void destroy() throws Exception {
		
		// For Demo purpose : stop embedded LDAP Server
		LdapTestUtils.shutdownEmbeddedServer();
	}
}
