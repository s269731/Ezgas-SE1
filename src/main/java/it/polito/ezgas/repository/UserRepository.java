package it.polito.ezgas.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.polito.ezgas.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByUserId(Integer userId);
	User findByAdminTrue();
	User findByUserNameAndPassword(String userName, String password);
	
}
