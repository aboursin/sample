package sample.springsecurity.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring security configuration.
 * @author angelo.boursin
 */
@Configuration
@Profile({ "MEM" })
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityMEMConfiguration extends WebSecurityConfigurerAdapter {
	
	private static Logger LOGGER = LoggerFactory.getLogger(SecurityMEMConfiguration.class);
	
	public SecurityMEMConfiguration(){
		super();
		LOGGER.info("Load...");
	}

	/**
	 * Configure 'in memory' authentication.
	 * @param auth {@link AuthenticationManagerBuilder}
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user1").password("123456").roles("USER");
		auth.inMemoryAuthentication().withUser("user2").password("123456").roles("USER", "ADMIN");
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
