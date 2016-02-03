package sample.springldap.repository;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.test.LdapTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import junit.framework.TestCase;
import sample.springldap.bean.Person;
import sample.springldap.configuration.GlobalConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = GlobalConfiguration.class)
@ActiveProfiles("demo")
public class PersonRepositoryTest extends TestCase{

	private static Logger LOGGER = LoggerFactory.getLogger(PersonRepositoryTest.class);
	
	@Autowired
	private PersonRepository personRepository;
	
	@AfterClass
	public static void clean() throws Exception{
		LOGGER.info("Shutdown LDAP embedded server !");
		LdapTestUtils.shutdownEmbeddedServer();
	}
	
	@Test
	public void findOne(){
		Person person = personRepository.findOne("Robert Browning");
		assertNotNull(person);
		person = personRepository.findOne("Peter May");
		assertNull(person);
	}
	
	@Test
	public void search(){
		List<Person> persons = personRepository.findAll();
		assertEquals(4, persons.size());
	}
	
	@Test
	public void save(){
		// TODO
	}
	
	@Test
	public void update(){
		// TODO
	}
	
	@Test
	public void delete(){
		// TODO
	}
	
}
