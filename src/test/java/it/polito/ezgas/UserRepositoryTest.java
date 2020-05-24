package it.polito.ezgas;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testFindByUserId() {
		User user1 = new User("Mario", "rossi", "mario@ezgas.com", 3);
		User user2 = new User("Maria", "rossa", "maria@ezgas.com", 4);
		User user3 = new User("Martina", "roseto", "marti@ezgas.com", 0);
		
		entityManager.persist(user1);
		entityManager.persist(user2);
		entityManager.persist(user3);
		
		User result = userRepository.findByUserId(user2.getUserId());
		
		assertTrue(result.getUserId().equals(user2.getUserId()));
	}
	
	@Test
	public void testFindByUserIdNull() {
		User user1 = new User("Mario", "rossi", "mario@ezgas.com", 3);
		User user2 = new User("Maria", "rossa", "maria@ezgas.com", 4);
		User user3 = new User("Martina", "roseto", "marti@ezgas.com", 0);
		
		entityManager.persist(user1);
		entityManager.persist(user2);
		entityManager.persist(user3);
		
		User result = userRepository.findByUserId(245);	// result will be the same if passing null or a negative value for userId
		
		assertTrue(result == null);
	}	
	
	@Test
	public void testFindByAdminTrue() {
		User user1 = new User("Mario", "rossi", "mario@ezgas.com", 3);
		User user2 = new User("Maria", "rossa", "maria@ezgas.com", 4);
		User user3 = new User("Martina", "roseto", "marti@ezgas.com", 0);
		
		entityManager.persist(user1);
		entityManager.persist(user2);
		entityManager.persist(user3);
		
		User result = userRepository.findByAdminTrue();
		
		assertTrue(result.getAdmin().equals(true));
	}
	
	@Test
	public void testFindByEmailAndPassword() {
		User user1 = new User("Mario", "rossi", "mario@ezgas.com", 3);
		User user2 = new User("Maria", "rossa", "maria@ezgas.com", 4);
		User user3 = new User("Martina", "roseto", "marti@ezgas.com", 0);
		
		entityManager.persist(user1);
		entityManager.persist(user2);
		entityManager.persist(user3);
		
		User result = userRepository.findByEmailAndPassword(user2.getEmail(), user2.getPassword());
		
		assertTrue(result.getUserId().equals(user2.getUserId()));
	}
	
	@Test
	public void testFindByEmailAndPasswordNull() {
		User user1 = new User("Mario", "rossi", "mario@ezgas.com", 3);
		User user2 = new User("Maria", "rossa", "maria@ezgas.com", 4);
		User user3 = new User("Martina", "roseto", "marti@ezgas.com", 0);
		
		entityManager.persist(user1);
		entityManager.persist(user2);
		entityManager.persist(user3);
		
		User result = userRepository.findByEmailAndPassword(user2.getEmail(), user1.getPassword());
		
		assertTrue(result == null);
	}
	
	@Test
	public void testFindByEmail() {
		User user1 = new User("Mario", "rossi", "mario@ezgas.com", 3);
		User user2 = new User("Maria", "rossa", "maria@ezgas.com", 4);
		User user3 = new User("Martina", "roseto", "marti@ezgas.com", 0);
		
		entityManager.persist(user1);
		entityManager.persist(user2);
		entityManager.persist(user3);
		
		User result = userRepository.findByEmail(user2.getEmail());
		
		assertTrue(result.getUserId().equals(user2.getUserId()));
	}
	
	@Test
	public void testFindByEmailNull() {
		User user1 = new User("Mario", "rossi", "mario@ezgas.com", 3);
		User user2 = new User("Maria", "rossa", "maria@ezgas.com", 4);
		User user3 = new User("Martina", "roseto", "marti@ezgas.com", 0);
		
		entityManager.persist(user1);
		entityManager.persist(user2);
		entityManager.persist(user3);
		
		User result = userRepository.findByEmail("ciaociao@ezgas.com");
		
		assertTrue(result == null);
	}

}
