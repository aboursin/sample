package sample.springldap.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation for a LDAP attribute.
 * Attributes are defined under a {@link LdapClass}.
 * The ID attribute will be set with {@link LdapId}.
 * @author angelo.boursin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LdapAttribute {
	
	/**
	 * LDAP matching attribute name.
	 */
	String value() default "";
	
	/**
	 * Defines if attribute is update-able.
	 */
	boolean updatable() default false;
	
	/**
	 * Defines if attribute is search-able.
	 */
	boolean searchable() default false;
}