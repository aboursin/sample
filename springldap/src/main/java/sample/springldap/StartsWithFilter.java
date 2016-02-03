package sample.springldap;

import org.springframework.ldap.filter.CompareFilter;
import org.springframework.ldap.support.LdapEncoder;

/**
 * Custom 'starts with' filter.
 * This filter has been created due to the lack of this type of comparison under {@link CompareFilter}.
 * @author angelo.boursin
 */
public class StartsWithFilter extends CompareFilter {

	private static final String EQUALS_SIGN = "=";

	public StartsWithFilter(String attribute, String value) {
		super(attribute, value);
	}

	public StartsWithFilter(String attribute, int value) {
		super(attribute, value);
	}

	/**
	 * encodeValue method now add an asterisk after the encoded value.
	 * @param value Value to encode
	 * @return Encoded value
	 */
	@Override
	protected String encodeValue(String value) {
		return LdapEncoder.filterEncode(value) + "*";
	}

	protected String getCompareString() {
		return EQUALS_SIGN;
	}

}
