package sample.springldap.repository;

import java.util.Collection;

/**
 * Basic repository interface.
 * @author angelo.boursin
 * @param <T> Entity type
 * @param <ID> Entity ID type
 */
public interface Repository<T, ID> {

	/**
	 * Find one entity by id.
	 * @param id ID of entity to find
	 * @return Matching entity or null
	 */
	T findOne(ID id);
	
	/**
	 * Find all entities.
	 * @return List of all entities
	 */
	Collection<T> findAll();
	
	/**
	 * Save given entity/
	 * @param object Entity to save
	 */
	void save(T object);
	
	/**
	 * Update given entity.
	 * @param object Entity to update
	 */
	void update(T object);
	
	/**
	 * Delete given entity.
	 * @param object Entity to delete
	 */
	void delete(T object);
	
}
