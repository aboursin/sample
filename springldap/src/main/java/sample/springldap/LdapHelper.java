package sample.springldap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.util.StringUtils;

import sample.springldap.annotation.LdapAttribute;
import sample.springldap.annotation.LdapClass;
import sample.springldap.annotation.LdapId;
import sample.springldap.bean.LdapEntity;

/**
 * Custom LDAP helper.
 * It exposes generic methods for {@link ContextMapper}, {@link ModificationItem} and {@link Filter}.
 * @see LdapEntity
 * @see LdapClass
 * @see LdapAttribute
 * @author angelo.boursin
 */
public class LdapHelper {
	
	/**
	 * Build an LDAP {@link ContextMapper} using reflection.
	 * Only {@link LdapAttribute} valued fields will be added to the mapper.
	 * @param type Type of object (must be a type assignable from {@link LdapEntity} & annotated with {@link LdapClass})
	 * @return {@link ContextMapper} for given type of object
	 */
	public static <T> ContextMapper<T> buildContextMapper(final Class<T> type) {
		
		// If object is null : throw exception !
		if(type == null){
			throw new IllegalArgumentException("Given object is null !");
		}
		
		// If object does not extends LdapBean : throw exception !
		if(type.isAssignableFrom(LdapEntity.class)){
			throw new IllegalArgumentException("Given object does not extends LdapBean !");
		}
	
		// If object is not annotated with LdapClass  : throw exception !
		if(!type.isAnnotationPresent(LdapClass.class)){
			throw new IllegalArgumentException("Given object is not annotated with LdapClass annotation !");
		}
		
		// Our context mapper implementation
		return new ContextMapper<T>() {
			
			public T mapFromContext(Object ctx) {

				T object = null;
				DirContextOperations adapter = (DirContextOperations) ctx;
				
				try{
						
					// Create a new instance of destination object
					object = type.newInstance();
					
					// Browse all fields
					for(Field field : getAllFields(type)){
						
						// If field if annotated with LdapAttribute annotation : set field with LDAP attribute value
						LdapAttribute annotation = field.getAnnotation(LdapAttribute.class);
						if(annotation != null && !StringUtils.isEmpty(annotation.value())){
							field.setAccessible(true);
							field.set(object, adapter.getObjectAttribute(annotation.value()));
						}
					}
				
				} catch (Exception e){
					e.printStackTrace();
				}
				
				return object;
			}
			
		};
	}
	
	/**
	 * Build a {@link ModificationItem} array from given object using reflection.
	 * Only {@link LdapAttribute} editable fields will be added to the array.
	 * @param object Object to process (must be an instance of {@link LdapEntity} & annotated with {@link LdapClass})
	 * @param attributes {@link Attributes} of existing object in order to determine if it's a ADD_ATTRIBUTE or a REPLACE_ATTRIBUTE.
	 * @return Array of {@link ModificationItem}
	 */
	public static <T> ModificationItem[] buildModificationItems(T object, Attributes attributes){
		
		List<ModificationItem> items = new ArrayList<ModificationItem>();
		
		// If object is null : throw exception !
		if(object == null){
			throw new IllegalArgumentException("Given object is null !");
		}
		
		// If object does not extends LdapBean : throw exception !
		if(!(object instanceof LdapEntity)){
			throw new IllegalArgumentException("Given object does not extends LdapBean !");
		}
	
		// If object is not annotated with LdapClass  : throw exception !
		if(!object.getClass().isAnnotationPresent(LdapClass.class)){
			throw new IllegalArgumentException("Given object is not annotated with LdapClass annotation !");
		}
		
		try{

			// Browse all fields
			for(Field field : object.getClass().getDeclaredFields()){

				field.setAccessible(true);
				Object value = field.get(object);
				
				// If field if annotated with LdapAttribute annotation & editable is true : add attribute
				LdapAttribute ldapAttribute = field.getAnnotation(LdapAttribute.class);
				if(ldapAttribute != null && ldapAttribute.updatable() && !StringUtils.isEmpty(value)){
					
					int mod = DirContext.REPLACE_ATTRIBUTE;
					if(attributes.get(ldapAttribute.value()) == null){
						mod = DirContext.ADD_ATTRIBUTE;
					}
					items.add(new ModificationItem(mod, new BasicAttribute(ldapAttribute.value(), value)));
				}
				
			}
		
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return items.toArray(new ModificationItem[0]);
	}
	
	/**
	 * Build a LDAP {@link Filter} from given criteria using reflection.
	 * Only {@link LdapAttribute} search-able fields will be added to the filter.
	 * @param criteria Criteria object (must be an object annotated with {@link LdapClass})
	 * @return {@link Filter}
	 */
	public static <T> Filter buildFilter(T criteria){
		
		AndFilter filter = new AndFilter();
	      
		// If object is null : throw exception !
		if(criteria == null){
			throw new IllegalArgumentException("Given object is null !");
		}
		
		// If object is not annotated with LdapClass : throw exception !
		if(!criteria.getClass().isAnnotationPresent(LdapClass.class)){
			throw new IllegalArgumentException("Given object is not annotated with LdapClass annotation !");
		}
		
		try{
			
			// Add filter for object classes
			LdapClass ldapClass = criteria.getClass().getAnnotation(LdapClass.class);
			String[] objectClasses = ldapClass.value();
			for (String objectClass : objectClasses) {
				filter.and(new EqualsFilter("objectclass", objectClass));
			}
					
			// Browse all fields
			for(Field field : getAllFields(criteria.getClass())){

				field.setAccessible(true);
				Object value = field.get(criteria);
				
				// If field is valued
				if(!StringUtils.isEmpty(value)){
	
					// If field if annotated with LdapAttribute annotation & search-able : add filter
					LdapAttribute ldapAttribute = field.getAnnotation(LdapAttribute.class);
					if(ldapAttribute != null && !StringUtils.isEmpty(ldapAttribute.value()) && ldapAttribute.searchable()){
						if(field.isAnnotationPresent(LdapId.class)){
							filter.and(new EqualsFilter(ldapAttribute.value(), value.toString()));
						} else {
							filter.and(new StartsWithFilter(ldapAttribute.value(), value.toString()));
						}
					}
				}
			}

		
		} catch (Exception e){
			e.printStackTrace();
		}

		return filter;
	}
	
	public static <T> boolean isCriteriaEmpty(T criteria){
		
		// If object is null : throw exception !
		if(criteria == null){
			throw new IllegalArgumentException("Given object is null !");
		}
		
		// If object is not annotated with LdapClass : throw exception !
		if(!criteria.getClass().isAnnotationPresent(LdapClass.class)){
			throw new IllegalArgumentException("Given object is not annotated with LdapClass annotation !");
		}
		
		try {
			
			// Browse all fields
			for(Field field : getAllFields(criteria.getClass())){
				field.setAccessible(true);
				Object value = field.get(criteria);
				
				// If field if annotated with LdapAttribute annotation & search-able & valued : current object has one search criteria at least !
				LdapAttribute ldapAttribute = field.getAnnotation(LdapAttribute.class);
				if(ldapAttribute != null && ldapAttribute.searchable() && !StringUtils.isEmpty(value)){
					return false;
				}
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
		// Current object hasn't any search criteria
		return true;
	}
	
	/**
	 * Retrieve all fields (including super class) for a given type.
	 * (This code uses reflection)
	 * @param type Type to process
	 * @return List of {@link Field}
	 */
	public static List<Field> getAllFields(Class<?> type) {
		
		List<Field> fields = new ArrayList<Field>();
		
		// Build stack and add given type as first element
		Stack<Class<?>> stack = new Stack<Class<?>>();
		stack.push(type);
		
		// Browse stack
		while (!stack.isEmpty()) {
			
			// Add fields from current type
			Class<?> current = stack.pop();
			fields.addAll(Arrays.asList(current.getDeclaredFields()));
			
			// If current type has a super class : add it to the stack
			if(!current.getSuperclass().equals(Object.class)){
				stack.push(current.getSuperclass());
			}
		}
		
	    return fields;
	}
	
}
