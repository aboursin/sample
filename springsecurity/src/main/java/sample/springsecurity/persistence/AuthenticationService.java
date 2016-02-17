package sample.springsecurity.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import sample.springsecurity.persistence.bean.SecRole;
import sample.springsecurity.persistence.bean.SecUser;
import sample.springsecurity.persistence.repository.UserRepository;

@Component
@Profile("DB")
public class AuthenticationService implements UserDetailsService, InitializingBean {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		SecUser user = userRepository.findOne(username);
		
		if(user == null){
			throw new UsernameNotFoundException("User does not exists");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(SecRole role : user.getRoles()){
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}

		//String completeName = String.format("%s %s", user.getFirstname(), user.getLastname());
		//return new CustomUserDetails(username, completeName, user.getPassword(), authorities);
		return new User(username, user.getPassword(), authorities);
	}

	@Override
	@Profile("TEST")
	public void afterPropertiesSet() throws Exception {
		// SHA1 password : '123456'
		userRepository.save(new SecUser("user3", "John", "Keats", "7c4a8d09ca3762af61e59520943dc26494f8941b", "ROLE_USER"));
		userRepository.save(new SecUser("user4", "John", "Milton", "7c4a8d09ca3762af61e59520943dc26494f8941b", "ROLE_USER", "ROLE_ADMIN"));
	}

}
