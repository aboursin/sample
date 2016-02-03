package sample.springldap.repository;

import java.util.List;

import org.springframework.ldap.SizeLimitExceededException;

import sample.springldap.annotation.LdapAttribute;
import sample.springldap.annotation.LdapEntry;
import sample.springldap.bean.Person;

/**
 * Person repository.
 * @see Repository
 * @see Person
 * @see LdapEntry
 * @see LdapAttribute
 * @author angelo.boursin
 */
public interface PersonRepository extends Repository<Person, String> {

	/**
	 * Find one person by CN.
	 * @param cn Person Common Name
	 * @return {@link Person}
	 */
	@Override
	Person findOne(String cn);
	
	/**
	 * Find all persons.
	 * Will throw a {@link SizeLimitExceededException} if there is too many results.
	 * @return List of {@link Person}
	 */
	@Override
	List<Person> findAll();
	
	@Override
	void save(Person person);
	
	/**
	 * Update a person.
	 * Only editable fields from {@link LdapAttribute} will be updated.
	 * @param person {@link Person} to update.
	 */
	@Override
	void update(Person person);
	
	@Override
	void delete(Person person);
	
	/**
	 * Find persons by criteria.
	 * Will throw a {@link SizeLimitExceededException} if there is too many results.
	 * @param criteria {@link Person} Criteria to use in the search
	 * @return List of {@link Person}
	 */
	List<Person> findByCriteria(Person criteria);

}