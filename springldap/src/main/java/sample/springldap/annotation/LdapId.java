package sample.springldap.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation for a LDAP type ID.
 * In addition to {@link LdapAttribute} it sets the ID of an {@link LdapClass}.
 * @author angelo.boursin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LdapId {

}