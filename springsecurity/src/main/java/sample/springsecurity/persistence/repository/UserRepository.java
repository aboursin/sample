package sample.springsecurity.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sample.springsecurity.persistence.bean.SecUser;

public interface UserRepository extends JpaRepository<SecUser, String>{
	
}
