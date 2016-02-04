package sample.springldap.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import sample.springldap.bean.Person;

/**
 * LDAP Person Repository.
 * @see Person
 * @see LdapRepository
 * @author angelo.boursin
 */
@Component
public class PersonRepository extends LdapRepository<Person> {

	private static Logger LOGGER = LoggerFactory.getLogger(PersonRepository.class);
	
	public PersonRepository(){
		super(Person.class);
	}

	/**
	 * {@link Person} base.
	 */
	@Override
	protected String getBase() {
		return "";
	}

	/**
	 * {@link PersonRepository} logger.
	 */
	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	
}