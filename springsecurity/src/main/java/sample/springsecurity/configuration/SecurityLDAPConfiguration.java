package sample.springsecurity.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring security LDAP configuration.
 * @author angelo.boursin
 */
@Configuration
@Profile("LDAP")
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@PropertySource("classpath:environment.properties")
public class SecurityLDAPConfiguration extends WebSecurityConfigurerAdapter{
	
	private static Logger LOGGER = LoggerFactory.getLogger(SecurityLDAPConfiguration.class);
	
	@Autowired
	private Environment env;
	
	public SecurityLDAPConfiguration(){
		super();
		LOGGER.info("Load...");
	}

	/**
	 * Configure 'in memory' authentication.
	 * @param auth {@link AuthenticationManagerBuilder}
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth, LdapContextSource contextSource) throws Exception {
		
		if(env.acceptsProfiles("TEST")){
			LOGGER.info("TEST Profile > Embedded LDAP server based on LDIF file !");
			// Example : LDIF (login : uid / Base64 password : '123456')
			auth.ldapAuthentication()
				.userSearchBase(env.getRequiredProperty("ldif.usersearchbase"))
	            .userSearchFilter(env.getRequiredProperty("ldif.usersearchfilter"))
				.groupSearchBase(env.getRequiredProperty("ldif.groupsearchbase"))
				.groupSearchFilter(env.getRequiredProperty("ldif.groupsearchfilter"))
				.contextSource().root(env.getRequiredProperty("ldif.rootbase")).ldif("classpath:sample.ldif");
		} else {
			// Example : LDAP
			auth.ldapAuthentication()
				.userSearchBase(env.getRequiredProperty("ldap.usersearchbase"))
				.userSearchFilter(env.getRequiredProperty("ldap.usersearchfilter"))
				.groupSearchBase(env.getRequiredProperty("ldap.groupsearchbase"))
				.groupSearchFilter(env.getRequiredProperty("ldap.groupsearchfilter"))
				.contextSource(contextSource);
		}
	}
	
	/**
	 * LDAP Context source bean.
	 * @return New instance of {@link LdapContextSource}
	 * @throws Exception
	 */
	@Bean
	public LdapContextSource contextSource() throws Exception{
		LdapContextSource contextSource = new LdapContextSource();
		contextSource.setUrl(env.getRequiredProperty("ldap.url"));
		contextSource.setBase(env.getRequiredProperty("ldap.rootbase"));
		contextSource.setUserDn(env.getRequiredProperty("ldap.userdn"));
		contextSource.setPassword(env.getRequiredProperty("ldap.userpwd"));
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
	 * Authorize only authenticated users.
	 * Describe login & logout actions.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			.antMatchers("/css/**", "/images/**", "/js/**").permitAll()
		.and().authorizeRequests()
			.anyRequest().authenticated()
		.and().formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/login")
			.failureUrl("/login?error")
			.usernameParameter("username")
			.passwordParameter("password")
			.defaultSuccessUrl("/")
			.permitAll()
		.and().logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/login?logout")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.permitAll()
		.and().csrf()
			.disable();
	}

}
