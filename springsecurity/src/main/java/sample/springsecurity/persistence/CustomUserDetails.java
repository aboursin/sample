package sample.springsecurity.persistence;

import java.util.Collection;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails, CredentialsContainer{

	private static final long serialVersionUID = 1178116163753207982L;
	
	private String username;
	private String name;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	public CustomUserDetails(String username, String name, String password, Collection<? extends GrantedAuthority> authorities){
		super();
		this.username = username;
		this.name = name;
		this.password = password;
		this.authorities = authorities;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public String getCompleteName() {
		return name;
	}
	
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public void eraseCredentials() {
		this.password = null;
	}

}
