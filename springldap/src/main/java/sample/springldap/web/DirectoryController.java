package sample.springldap.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.SizeLimitExceededException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sample.springldap.LdapHelper;
import sample.springldap.bean.Person;
import sample.springldap.repository.PersonRepository;

/**
 * Directory controller.
 * @author angelo.boursin
 */
@Controller
public class DirectoryController {

	private static Logger LOGGER = LoggerFactory.getLogger(DirectoryController.class);
	
	@Autowired
	private PersonRepository personDao;
	
	@Autowired
	private MessageSource messageSource;

	/**
	 * Default view rendering.
	 * @return Search view
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String view(ModelMap model) {
		model.addAttribute("criteria", new Person());
		return "search";
	}
	
	/**
	 * Search persons from linked directory.
	 * @param criteria Search criteria
	 * @return List of matching {@link Person}
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> search(@ModelAttribute Person criteria) {
		
		LOGGER.debug("Criteria : {}", criteria);
		
		List<Person> result = new ArrayList<Person>();
		
		if(!LdapHelper.isCriteriaEmpty(criteria)){
			try {
				result = personDao.findByCriteria(criteria);
			} catch (Exception e){
				if(e instanceof SizeLimitExceededException){
					String msg = messageSource.getMessage("search.sizelimitexceeded", new Object[0], LocaleContextHolder.getLocale());
					return new ResponseEntity<String>(msg, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
		}
		
		return new ResponseEntity<List<Person>>(result, HttpStatus.OK);
	}
	
	/**
	 * Available departments list.
	 * @return List of department
	 */
	@ModelAttribute("departments")
	public List<String> departements(){
		return Arrays.asList("Human Resources", "Research and Development", "Sales");
	}
	
	/**
	 * Available sites list.
	 * @return List of site
	 */
	@ModelAttribute("sites")
	public List<String> sites(){
		return Arrays.asList("London", "New York");
	}

}
