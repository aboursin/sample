package sample.springldap.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import sample.springldap.annotation.LdapAttribute;
import sample.springldap.annotation.LdapEntry;

/**
 * Person LDAP bean.
 * It defines the mapping of :
 * <ul>
 *  <li>Designation attributes</li>
 *  <li>Contact attributes</li>
 *  <li>Organization attributes</li>
 *  <li>Localization attributes</li>
 * </ul>
 * @author angelo.boursin
 */
@LdapEntry(value = {"person"})
public class Person extends LdapEntity implements Serializable{
	
	private static final long serialVersionUID = -8984744681251906477L;
	
	/** Designation attribute(s) **/
	
	@LdapAttribute("uid")
	private String employeeid;
	
	@LdapAttribute(value = "sn", searchable = true)
	private String lastname;
	
	@LdapAttribute(value = "givenName", searchable = true)
	private String firstname;
	
	/** Contact attribute(s) **/
	
	@LdapAttribute("mail")
	private String email;
	
	@LdapAttribute("telephoneNumber")
	private String telephone;
	
	@LdapAttribute("facsimileTelephoneNumber")
	private String fax;
	
	@LdapAttribute(value = "mobile", updatable = true)
	private String mobile;
	
	/** Organization attribute(s) **/
	
	@LdapAttribute(value = "departmentNumber", searchable = true)
	private String department;
	
	/** Localization attribute(s) **/

	@LdapAttribute(value = "l", searchable = true, updatable = true)
	private String site;
	
	public Person(){
		super();
	}
	
	public Person(String cn){
		super(cn);
	}
	
	/**
	 * hashCode method is now based on 'emloyeeid' attribute.
	 */
	@Override
	public int hashCode(){
	    return new HashCodeBuilder().append(employeeid).toHashCode();
	}

	/**
	 * equals method is now based on 'employeeid' attribute.
	 */
	@Override
	public boolean equals(final Object obj){
	    if(obj instanceof Person){
	        final Person other = (Person) obj;
	        return new EqualsBuilder().append(employeeid, other.employeeid).isEquals();
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

	/** Designation **/
	
	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/** Contact **/
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/** Organization **/

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	/** Localization **/
	
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	
}
