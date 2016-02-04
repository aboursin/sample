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
	private String id;
	
	private String dn;
	
	public LdapEntity(){
		super();
	}
	
	public LdapEntity(String id){
		super();
		this.id = id;
	}
	
	/**
	 * hashCode method is now based on 'id' attribute.
	 */
	@Override
	public int hashCode(){
	    return new HashCodeBuilder().append(id).toHashCode();
	}

	/**
	 * equals method is now based on 'id' attribute.
	 */
	@Override
	public boolean equals(final Object obj){
	    if(obj instanceof LdapEntity){
	        final LdapEntity other = (LdapEntity) obj;
	        return new EqualsBuilder().append(id, other.id).isEquals();
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}
	
}
