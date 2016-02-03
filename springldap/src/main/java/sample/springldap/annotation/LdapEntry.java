package sample.springldap.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation for a LDAP type.
 * Its attributes will be annotated with {@link LdapAttribute}.
 * Its ID attribute will be set with {@link LdapId}.
 * @author angelo.boursin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LdapEntry {
	
	/**
	 * Array of matching LDAP objectClass
	 */
	String[] value() default "*";
}
