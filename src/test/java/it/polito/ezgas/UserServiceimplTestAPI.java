package it.polito.ezgas;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import exception.InvalidLoginDataException;
import exception.InvalidUserException;
import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.service.impl.UserServiceimpl;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserServiceimplTestAPI {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testGetUserByIdNotNull() throws InvalidUserException {
		
		UserServiceimpl userServiceimpl = new UserServiceimpl(userRepository);
		
		User user = new User("Alice", "HelloWorld", "alice@ezgas.com", 5);
		entityManager.persist(user);
		UserDto userDto = new UserDto (user.getUserId(), "Alice", "HelloWorld", "alice@ezgas.com", 5);
		
		assert(userServiceimpl.getUserById(user.getUserId()).equals(userDto));
	}
	
	@Test
	public void testGetUserByIdNull() throws InvalidUserException {
		
			UserServiceimpl userServiceimpl = new UserServiceimpl(userRepository);
			
			assert(userServiceimpl.getUserById(50)==null);

	}
	
	@Test
	public void testGetUserByIdNeg()  {
		
		UserServiceimpl userServiceimpl = new UserServiceimpl(userRepository);
		
		Assertions.assertThrows(InvalidUserException.class, () -> { userServiceimpl.getUserById(-10);
		} );
		
	}
	
	@Test
	public void testSaveUserNewUserEmailNotPresent() {
		//Iscrizione nuovo utente senza problematiche
		User user = new User("Alex", "password", "alex@ezgas.com", 0);
		entityManager.persist(user);
		Integer size1 = userRepository.findAll().size();
		UserDto userDtoIn = new UserDto(null,"Luigi","password", "luigi@ezgas.com", 0);
		UserServiceimpl userServiceimpl = new UserServiceimpl(userRepository);
		userServiceimpl.saveUser(userDtoIn);
		Integer size2 = userRepository.findAll().size();
				
		assert(size2==size1+1);
		
	}
	
	@Test
	public void testSaveUserFails() {
		//Nuovo utente con e-mail di altro utente già iscritto -> return null DONE
		User user = new User("Alex", "password", "alex@ezgas.com", 0);
		entityManager.persist(user);
		UserDto userDtoIn = new UserDto(null, "Mario", "password", "alex@ezgas.com", 0);
		
		UserServiceimpl userServiceimpl = new UserServiceimpl(userRepository);
		UserDto result = userServiceimpl.saveUser(userDtoIn);
		assert(result ==null);
	}
	
	@Test
	public void testSaveUserUpdate() {
		//Aggiornamento di un utente (esempio in cui aggiorna password) DONE
		User user = new User("Maria", "password","maria@ezgas.com", 0);
		entityManager.persist(user);
		UserDto userDto = new UserDto(user.getUserId(), "Maria", "newPassword", "maria@ezgas.com", 0);

		UserServiceimpl userServiceimpl = new UserServiceimpl(userRepository);
		UserDto result = userServiceimpl.saveUser(userDto);
		
		assert(result.equals(userDto));
	}
	
	@Test
	public void testSaveUserUpdateReturnNull() {
		//Aggiornamento di un utente con inserimento di e-mail di altro utente già iscritto -> return null DONE
		
		User user1 = new User("Viviana", "password", "vivana@ezgas.com",0);
		entityManager.persist(user1);
		User user2 = new User("Simone", "password", "simone@ezgas.com",0);
		entityManager.persist(user2);
		UserDto userDto = new UserDto(user1.getUserId(), "Viviana", "password", "simone@ezgas.com", 0);
		
		UserServiceimpl userServiceimpl = new UserServiceimpl(userRepository);
		UserDto result = userServiceimpl.saveUser(userDto);

		assert(result == null);
	}
	
	@Test
	public void testGetAllUsersZero(){ //empty Repository 
		UserServiceimpl userService = new UserServiceimpl(userRepository);
		List<UserDto> result= userService.getAllUsers();
		assert(result.size()==1); //only admin
	}
	
	@Test
	public void testGetAllUsers(){ 
		User user1=new User("nome1","123","nome1@ezgas.com",3);
		User user2=new User("nome2","456","nome2@ezgas.com",2);
		User user3=new User("nome3","789","nome3@ezgas.com",5);
		entityManager.persist(user1);
		entityManager.persist(user2);
		entityManager.persist(user3);
         
		UserServiceimpl userService = new UserServiceimpl(userRepository);
		List<UserDto> result= userService.getAllUsers();
		assert(result.size()==4);
	}
	
	@Test
	public void testDeleteUnregisteredUser() throws InvalidUserException{ 
		User user1=new User("nome1","123","nome1@ezgas.com",3);
		User user2=new User("nome2","456","nome2@ezgas.com",2);
		User user3=new User("nome3","789","nome3@ezgas.com",5);
		entityManager.persist(user1);
		entityManager.persist(user2);
		entityManager.persist(user3);
		
		UserServiceimpl userService = new UserServiceimpl(userRepository);
		boolean deleted= userService.deleteUser(2000);
		assert(deleted==false);
	}
	
	@Test
	public void testDeleteUserInvalidId() throws InvalidUserException{ //negative userId
		int userId=-1;
		UserServiceimpl userService = new UserServiceimpl(userRepository);
		Assertions.assertThrows(InvalidUserException.class,()->{userService.deleteUser(userId);});
	}
	
	@Test
	public void testDeleteRegisteredUser() throws InvalidUserException{ 
		User user1=new User("nome1","123","nome1@ezgas.com",3);
		User user2=new User("nome2","456","nome2@ezgas.com",2);
		User user3=new User("nome3","789","nome3@ezgas.com",5);
		entityManager.persist(user1);
		entityManager.persist(user2);
		entityManager.persist(user3);
        
		UserServiceimpl userService = new UserServiceimpl(userRepository);
		boolean deleted= userService.deleteUser(user2.getUserId());
		assert(deleted==true);
	}
	
	@Test
	public void testLogin() throws InvalidLoginDataException {
		User user = new User("Mario", "rossi", "mario@ezgas.com", 3);
		user.setAdmin(false);
		entityManager.persist(user);

		LoginDto login = new LoginDto();
		login.setUserId(user.getUserId());
		login.setUserName(user.getUserName());
		login.setEmail(user.getEmail());
		login.setReputation(user.getReputation());
		login.setAdmin(user.getAdmin());
		IdPw credentials = new IdPw(user.getEmail(), user.getPassword());
		UserServiceimpl userServiceimpl = new UserServiceimpl(userRepository);
		LoginDto result = userServiceimpl.login(credentials);
		
		assertTrue(result.equals(login));
	}
	
	@Test
	public void testLoginInvalid() throws InvalidLoginDataException {
		User user = new User("Mario", "rossi", "mario@ezgas.com", 3);
		user.setAdmin(false);
		entityManager.persist(user);

		IdPw credentials = new IdPw(user.getEmail(), "ciaooo");			// password is wrong
		UserServiceimpl userServiceimpl = new UserServiceimpl(userRepository);
		
		Assertions.assertThrows(InvalidLoginDataException.class, () -> {
			userServiceimpl.login(credentials);
		});
	}
	
	@Test
	public void testIncreaseUserReputation() throws InvalidUserException {
		User user = new User("Mario", "rossi", "mario@ezgas.com", 3);
		entityManager.persist(user);

		UserServiceimpl userServiceimpl = new UserServiceimpl(userRepository);
		Integer rep = userServiceimpl.increaseUserReputation(user.getUserId());
		Integer res = user.getReputation();

		assertTrue(rep.equals(res));
	}
	
	@Test
	public void testIncreaseUserReputationNegativeUserId() throws InvalidUserException {
		UserServiceimpl userServiceimpl = new UserServiceimpl(userRepository);
		
		Assertions.assertThrows(InvalidUserException.class, () -> {
			userServiceimpl.increaseUserReputation(-5);
		});
	}
	
	@Test
	public void testIncreaseUserReputationNotFound() throws InvalidUserException {
		UserServiceimpl userServiceimpl = new UserServiceimpl(userRepository);
		Integer rep = userServiceimpl.increaseUserReputation(5);
		
		assertTrue(rep == null);
	}
	
	@Test
	public void testDecreaseUserReputation() throws InvalidUserException {
		User user = new User("Mario", "rossi", "mario@ezgas.com", 3);
		entityManager.persist(user);

		UserServiceimpl userServiceimpl = new UserServiceimpl(userRepository);
		Integer rep = userServiceimpl.decreaseUserReputation(user.getUserId());
		Integer res = user.getReputation();

		assertTrue(rep.equals(res));
	}
	
	@Test
	public void testDecreaseUserReputationNegativeUserId() throws InvalidUserException {
		UserServiceimpl userServiceimpl = new UserServiceimpl(userRepository);
		
		Assertions.assertThrows(InvalidUserException.class, () -> {
			userServiceimpl.decreaseUserReputation(-5);
		});
	}
	
	@Test
	public void testDecreaseUserReputationNotFound() throws InvalidUserException {
		UserServiceimpl userServiceimpl = new UserServiceimpl(userRepository);
		Integer rep = userServiceimpl.decreaseUserReputation(5);
		
		assertTrue(rep == null);
	}
}
