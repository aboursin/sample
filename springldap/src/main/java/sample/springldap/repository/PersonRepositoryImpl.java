package sample.springldap.repository;

import java.util.List;

import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.Filter;
import org.springframework.stereotype.Component;

import sample.springldap.LdapHelper;
import sample.springldap.bean.Person;

/**
 * {@inheritDoc}
 */
@Component
public class PersonRepositoryImpl implements PersonRepository {

	private static Logger LOGGER = LoggerFactory.getLogger(PersonRepositoryImpl.class);
	
	@Autowired
	private LdapTemplate ldapTemplate;
	
	private static ContextMapper<Person> contextMapper = LdapHelper.buildContextMapper(Person.class);
	
	private static String PERSON_ROOT = "";
	
	public PersonRepositoryImpl(){
		super();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Person findOne(String cn){
		Filter filter = LdapHelper.buildFilter(new Person(cn));
		return (Person) ldapTemplate.searchForObject(PERSON_ROOT, filter.encode(), contextMapper);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<Person> findAll() {
		Filter filter = LdapHelper.buildFilter(new Person());
		return find(filter);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Person> findByCriteria(Person criteria) {
		Filter filter = LdapHelper.buildFilter(criteria);
		LOGGER.debug("Filter : {}", filter.encode());
		return find(filter);
	}
	
	@Override
	public void save(Person object) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void update(Person person) {
		
		try{
			DirContext context = ldapTemplate.getContextSource().getReadWriteContext();
			String cn = String.format("cn=%s", person.getCn());
			Attributes attributes = context.getAttributes(cn);
			ModificationItem[] modifications = LdapHelper.buildModificationItems(person, attributes);
			for (ModificationItem modificationItem : modifications) {
				LOGGER.debug("Modification : {}", modificationItem);
			}
			ldapTemplate.modifyAttributes(cn, modifications);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void delete(Person object) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Find persons by filter.
	 * @param filter {@link Filter} to use
	 * @return List of {@link Person}
	 */
	private List<Person> find(Filter filter){
		return ldapTemplate.search(PERSON_ROOT, filter.encode(), contextMapper);
	}

}