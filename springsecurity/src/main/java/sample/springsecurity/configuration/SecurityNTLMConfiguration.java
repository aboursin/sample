package sample.springsecurity.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import waffle.servlet.spi.BasicSecurityFilterProvider;
import waffle.servlet.spi.NegotiateSecurityFilterProvider;
import waffle.servlet.spi.SecurityFilterProvider;
import waffle.servlet.spi.SecurityFilterProviderCollection;
import waffle.spring.NegotiateSecurityFilter;
import waffle.spring.NegotiateSecurityFilterEntryPoint;
import waffle.windows.auth.impl.WindowsAuthProviderImpl;

/**
 * Spring security NTLM configuration.
 * @author angelo.boursin
 */
@Configuration
@Profile("NTLM")
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityNTLMConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private NegotiateSecurityFilterEntryPoint authenticationEntryPoint;

	@Autowired
	private NegotiateSecurityFilter waffleNegotiateSecurityFilter;
	
	public SecurityNTLMConfiguration(){
		super();
		System.out.println("Security NTLM Configuration...");
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
		.and().addFilterBefore(waffleNegotiateSecurityFilter, BasicAuthenticationFilter.class)
		.httpBasic().authenticationEntryPoint(authenticationEntryPoint);
	}
	
	@Bean
	public WindowsAuthProviderImpl waffleWindowsAuthProvider() {
		return new WindowsAuthProviderImpl();
	}

	@Bean
	public NegotiateSecurityFilterProvider negotiateSecurityFilterProvider(WindowsAuthProviderImpl waffleWindowsAuthProvider) {
		return new NegotiateSecurityFilterProvider(waffleWindowsAuthProvider);
	}

	@Bean
	public BasicSecurityFilterProvider basicSecurityFilterProvider(WindowsAuthProviderImpl windowsAuthProvider) {
		return new BasicSecurityFilterProvider(windowsAuthProvider);
	}

	@Bean
	public SecurityFilterProviderCollection waffleSecurityFilterProviderCollection(NegotiateSecurityFilterProvider negotiateSecurityFilterProvider, 
			BasicSecurityFilterProvider basicSecurityFilterProvider){
		return new SecurityFilterProviderCollection(new SecurityFilterProvider[]{negotiateSecurityFilterProvider, basicSecurityFilterProvider});
	}

	@Bean
	public NegotiateSecurityFilterEntryPoint negotiateSecurityFilterEntryPoint(SecurityFilterProviderCollection waffleSecurityFilterProviderCollection) {
		final NegotiateSecurityFilterEntryPoint negotiateSecurityFilterEntryPoint = new NegotiateSecurityFilterEntryPoint();
		negotiateSecurityFilterEntryPoint.setProvider(waffleSecurityFilterProviderCollection);
		return negotiateSecurityFilterEntryPoint;
	}

	@Bean
	public NegotiateSecurityFilter waffleNegotiateSecurityFilter(SecurityFilterProviderCollection waffleSecurityFilterProviderCollection) {
		NegotiateSecurityFilter negotiateSecurityFilter = new NegotiateSecurityFilter();
		negotiateSecurityFilter.setProvider(waffleSecurityFilterProviderCollection);
		return negotiateSecurityFilter;
	}

}
