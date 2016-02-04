package sample.springldap.repository;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.Filter;

import sample.springldap.LdapHelper;
import sample.springldap.bean.LdapEntity;

/**
 * Abstract Generic LDAP Repository.
 * @param <T> Type extends {@link LdapEntity}
 * @author angelo.boursin
 */
public abstract class LdapRepository<T extends LdapEntity> {
	
	@Autowired
	private LdapTemplate ldapTemplate;
	
	/**
	 * Derived class type.
	 */
	private Class<T> type;
	
	/**
	 * Derived class context mapper.
	 */
	private ContextMapper<T> contextMapper;
	
	/**
	 * Derived class entity root.
	 * @return Root as String
	 */
	protected abstract String getBase();
	
	/**
	 * Derived class logger.
	 * @return {@link Logger}
	 */
	protected abstract Logger getLogger();
	
	public LdapRepository(Class<T> type){
		this.type = type;
		this.contextMapper = LdapHelper.buildContextMapper(type);	
	}
	
	/**
	* Find one entity by id.
	* Will throw a {@link IncorrectResultSizeDataAccessException} if no result or more than one result found.
	* @param id ID of entity to find
	* @return Matching entity or null
	*/
	public T findOne(String id){
		
		// Build a criteria based on entity ID
		T criteria = null;
		try{
			criteria = type.newInstance();
			criteria.setId(id);
		} catch (InstantiationException | IllegalAccessException e){
			e.printStackTrace();
		}
		
		// Search for result(s) 
		List<T> result = find(LdapHelper.buildFilter(criteria));
		
		// If no result or more than one result found : throw a exception
		if(result.size() != 1){
			throw new IncorrectResultSizeDataAccessException(1, result.size());
		}
		
		// Return found result !
		return result.get(0);
	}
	
	/**
	* Find all entities.
	* @return List of all entities
	*/
	public List<T> findAll(){
		
		// Build an empty criteria
		T criteria = null;
		try{
			criteria = type.newInstance();
		} catch (InstantiationException | IllegalAccessException e){
			e.printStackTrace();
		}
		
		// Search for result(s) and return
		return find(LdapHelper.buildFilter(criteria));
	}
	
	/**
	* Find entities by criteria.
	* Will throw a {@link SizeLimitExceededException} if there is too many results.
	* @param criteria Criteria to use
	* @return List of all matching entities
	*/
	public List<T> findByCriteria(T criteria){
		
		// Search for result(s) using given criteria and return
		return find(LdapHelper.buildFilter(criteria));
	}
	
	/**
	* Save given entity/
	* @param object Entity to save
	*/
	public void save(T object){
		// TODO
	}
	
	/**
	* Update given entity.
	* @param object Entity to update
	*/
	public void update(T object){
		
		// Modify attributes
		ldapTemplate.modifyAttributes(object.getDn(), LdapHelper.buildModificationItems(object));
	}
	
	/**
	* Delete given entity.
	* @param object Entity to delete
	*/
	public void delete(T object){
		// TODO
	}
	
	/**
	* Find entities by filter.
	* @param filter {@link Filter} to use
	* @return List of all matching entities
	*/
	private List<T> find(Filter filter){
		getLogger().debug("Find with filter {}", filter.encode());
		return ldapTemplate.search(getBase(), filter.encode(), contextMapper);
	}

}
