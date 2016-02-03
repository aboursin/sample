package sample.springldap.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import sample.springldap.annotation.LdapAttribute;
import sample.springldap.annotation.LdapId;

/**
 * Abstract LDAP entity.
 * It defines mapping of CN and DN attributes.
 * All others LDAP entities will extends this entity.
 * @author angelo.boursin
 */
public abstract class LdapEntity implements Serializable {

	private static final long serialVersionUID = -8397167650030890907L;
	
	@LdapId
	@LdapAttribute(value = "cn", searchable = true)
	private String cn;
	
	@LdapAttribute(value = "dn")
	private String dn;
	
	/**
	 * hashCode method is now based on 'cn' attribute.
	 */
	@Override
	public int hashCode(){
	    return new HashCodeBuilder().append(cn).toHashCode();
	}

	/**
	 * equals method is now based on 'cn' attribute.
	 */
	@Override
	public boolean equals(final Object obj){
	    if(obj instanceof LdapEntity){
	        final LdapEntity other = (LdapEntity) obj;
	        return new EqualsBuilder().append(cn, other.cn).isEquals();
	    } else{
	        return false;
	    }
	}
	
	/**
	 * New toString implementation.
	 */
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	public LdapEntity(){
		super();
	}
	
	public LdapEntity(String cn){
		super();
		this.cn = cn;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}
	
	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}
	
}
