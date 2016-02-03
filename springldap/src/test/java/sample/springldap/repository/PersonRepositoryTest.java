package sample.springldap.repository;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import sample.springldap.configuration.GlobalConfiguration;
import sample.springldap.repository.PersonRepository;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = GlobalConfiguration.class)
public class PersonRepositoryTest extends TestCase{

	@Autowired
	private PersonRepository personRepository;
	
	@Test
	public void search(){
		// TODO
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
