package sample.springldap.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.ldap.test.LdapTestUtils;

/**
 * LDAP configuration for our application.
 * @see GlobalConfiguration
 * @author angelo.boursin
 */
@Profile("demo")
@Configuration
@PropertySource("classpath:ldap.properties")
public class LdapDemoConfiguration implements InitializingBean, DisposableBean {

	private static Logger LOGGER = LoggerFactory.getLogger(LdapDemoConfiguration.class);
	
	@Autowired
	private Environment env;
	
	public LdapDemoConfiguration(){
		super();
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
	 * @param contextSource LDAP context source
	 * @return New instance of {@link LdapTemplate}
	 * @throws Exception
	 */
	@Bean
	public LdapTemplate ldapTemplate(ContextSource contextSource) throws Exception{
		LdapTemplate ldapTemplate = new LdapTemplate();
		ldapTemplate.setContextSource(contextSource);
		return ldapTemplate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// For Demo purpose : start embedded LDAP Server
		LOGGER.info("Start LDAP embedded server !");
		LdapTestUtils.startEmbeddedServer(389, env.getProperty("ldap.base"), "sample");
		
		// For Demo purpose : populate LDAP from LDIF file
		LdapTestUtils.cleanAndSetup(contextSource(), LdapUtils.newLdapName(env.getProperty("ldap.base")), new ClassPathResource("annuaire.ldif"));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void destroy() throws Exception {
		// For Demo purpose : stop embedded LDAP Server
		LOGGER.info("Shutdown LDAP embedded server !");
		LdapTestUtils.shutdownEmbeddedServer();
	}
}
