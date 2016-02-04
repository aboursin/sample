package sample.springldap.repository;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import sample.springldap.bean.Person;
import sample.springldap.configuration.GlobalConfiguration;

/**
 * Test class for {@link PersonRepository}.
 * This test is closely linked to {@link Person} entity and ldif file.
 * @author angelo.boursin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = GlobalConfiguration.class)
@ActiveProfiles("demo")
public class PersonRepositoryTest extends TestCase{

	@Autowired
	private PersonRepository personRepository;

	@Test
	public void findOne(){
		Person person = personRepository.findOne("Robert Browning");
		assertNotNull(person); // Fields mapping will not be checked here
	}
	
	@Test(expected = IncorrectResultSizeDataAccessException.class)
	public void findOne_exception(){
		personRepository.findOne("Peter May");
	}
	
	@Test
	public void findAll(){
		List<Person> persons = personRepository.findAll();
		assertEquals(4, persons.size());
	}
	
	@Test
	public void findByCriteria(){
		Person criteria = new Person();
		criteria.setSite("London");
		List<Person> persons = personRepository.findByCriteria(criteria);
		assertEquals(3, persons.size()); // Filter on one filed
		
		criteria.setDepartment("Research and Development");
		persons = personRepository.findByCriteria(criteria);
		assertEquals(2, persons.size()); // Filter on multiple fields
		
		criteria.setTelephone("+44 20 7946 0738");
		persons = personRepository.findByCriteria(criteria);
		assertEquals(2, persons.size()); // Filter will not include telephone (searchable = false)
		
		criteria.setFirstname("Peter");
		persons = personRepository.findByCriteria(criteria);
		assertEquals(0, persons.size()); // No entry matching given criteria
	}
	
	public void save(){
		// TODO
	}
	
	@Test
	public void update(){
		Person person = personRepository.findOne("Robert Browning");
		assertEquals("London", person.getSite());
		assertEquals("Human Resources", person.getDepartment());
		
		person.setSite("Paris");
		person.setDepartment("IT");
		personRepository.update(person);
		
		person = personRepository.findOne("Robert Browning");
		assertEquals("Paris", person.getSite()); // Site has been updated (updatable = true)
		assertEquals("Human Resources", person.getDepartment()); // Department has not been updated (updatable = false)
		
		// Back to former state
		person.setSite("London");
		personRepository.update(person);
	}
	
	public void delete(){
		// TODO
	}
	
}
