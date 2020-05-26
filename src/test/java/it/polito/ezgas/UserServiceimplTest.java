package it.polito.ezgas;


import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import exception.InvalidLoginDataException;
import exception.InvalidUserException;
import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.service.impl.UserServiceimpl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;


public class UserServiceimplTest {
	
	@Test
	public void testGetUserByIdNotNull() throws InvalidUserException {
		
		UserRepository URmock = mock (UserRepository.class);
		
		UserServiceimpl userServiceimpl = new UserServiceimpl(URmock);
		
		User user = new User("Alice", "HelloWorld", "alice@ezgas.com", 5);
		Integer userId=1;
		user.setUserId(userId);
		UserDto userDto = new UserDto (userId, "Alice", "HelloWorld", "alice@ezgas.com", 5);
		
		when(URmock.findByUserId(userId)).thenReturn(user);
		
		assert(userServiceimpl.getUserById(userId).equals(userDto));

	}
	
	@Test
	public void testGetUserByIdNull() throws InvalidUserException {
		
			UserRepository URmock = mock (UserRepository.class);
			
			UserServiceimpl userServiceimpl = new UserServiceimpl(URmock);
			
			Integer userId=1;
			User user = null;
			
			when(URmock.findByUserId(userId)).thenReturn(user);
			
			assert(userServiceimpl.getUserById(userId)==null);

	}
	
	@Test
	public void testGetUserByIdNeg()  {
		
		UserRepository URmock = mock (UserRepository.class);
		
		UserServiceimpl userServiceimpl = new UserServiceimpl(URmock);
		
		User user = new User("Alice", "HelloWorld", "alice@ezgas.com", 5);
		Integer userId=-1;
		user.setUserId(userId);
		
		when(URmock.findByUserId(userId)).thenReturn(user);
		
		Assertions.assertThrows(InvalidUserException.class, () -> {
			userServiceimpl.getUserById(userId);
		});
		
	}
	
	@Test
	public void testSaveUserNewUserEmailNotPresent() {
		
		//Iscrizione nuovo utente senza problematiche DONE
		UserRepository URmock = mock (UserRepository.class);
		
		UserDto userDtoIn = new UserDto(null, "Alice", "HelloWorld", "alice@ezgas.com", 0);
		
		User user = new User("Alice", "HelloWorld", "alice@ezgas.com", 0);
		user.setAdmin(false);
		user.setUserId(1);
		UserDto userDtoOut = new UserDto(1, "Alice", "HelloWorld", "alice@ezgas.com", 0);
		userDtoOut.setAdmin(false);

		when(URmock.findByEmail("alice@ezgas.com")).thenReturn(null);
		when(URmock.save(any(User.class))).thenReturn(user);
		
		UserServiceimpl userServiceimpl = new UserServiceimpl(URmock);
		UserDto result = userServiceimpl.saveUser(userDtoIn);
		
		assert(result.equals(userDtoOut));
		
	}
	
	@Test
	public void testSaveUserFails() {
		
		//Nuovo utente con e-mail di altro utente già iscritto -> return null DONE
		UserRepository URmock = mock (UserRepository.class);
		
		UserDto userDtoIn = new UserDto(null, "Shan", "pass1", "shan@ezgas.com", 0);
		User user = new User("Mario", "pass2", "shan@ezgas.com", 0);
		
		when(URmock.findByEmail("shan@ezgas.com")).thenReturn(user);
		
		UserServiceimpl userServiceimpl = new UserServiceimpl(URmock);
		UserDto result = userServiceimpl.saveUser(userDtoIn);
		assert(result == null);
	}
	
	@Test
	public void testSaveUserUpdate() {
		//Aggiornamento di un utente (esempio in cui aggiorna password) DONE
		UserRepository URmock = mock (UserRepository.class);
		
		UserDto userDtoIn = new UserDto(1, "Alice", "pass2", "alice@ezgas.com", 0);
		User user = new User(userDtoIn.getUserName(), userDtoIn.getPassword(), userDtoIn.getEmail(), 0);
		user.setUserId(1);
		user.setAdmin(false);
		
		User userOriginal = new User("Alice", "pass1", "alice@ezgas.com", 0);
		userOriginal.setUserId(1);
		userOriginal.setAdmin(false);

		//when(URmock.findByEmail("alice@ezgas.com")).thenReturn(null);
		when(URmock.findByUserId(1)).thenReturn(userOriginal);
		when(URmock.save(any(User.class))).thenReturn(user);
		
		UserServiceimpl userServiceimpl = new UserServiceimpl(URmock);
		UserDto result = userServiceimpl.saveUser(userDtoIn);
		
		assert(result.equals(userDtoIn));
	}
	
	@Test
	public void testSaveUserUpdateReturnNull() {
		//Aggiornamento di un utente con inserimento di e-mail di altro utente già iscritto -> return null DONE
		UserRepository URmock = mock (UserRepository.class);
		
		//Alice è gia Iscritta, vuole aggiornare la sua mail con quella di un altro user
		UserDto userDtoIn = new UserDto(1, "Alice", "HelloWorld", "camilla@ezgas.com", 0);
		//I dati vecchi di Alice sono:
		User user= new User ("Alice", "HelloWorld", "alice@ezgas.com", 0);
		user.setUserId(1);
		//Camilla è la user che possiede già quella mail
		User u = new User("Camilla", "password", "camilla@ezgas.com", 0);
		u.setUserId(5);

		when(URmock.findByUserId(1)).thenReturn(user);
		when(URmock.findByEmail("camilla@ezgas.com")).thenReturn(u);
		
		UserServiceimpl userServiceimpl = new UserServiceimpl(URmock);
		UserDto result = userServiceimpl.saveUser(userDtoIn);
		
		assert(result == null);
	}
	
	@Test
	public void testGetAllUsersZero(){ //empty repository
		UserRepository test= mock(UserRepository.class);
		when(test.findAll()).thenReturn(new ArrayList<User>());
		UserServiceimpl userService = new UserServiceimpl(test);
		ArrayList<UserDto> userArray= (ArrayList<UserDto>) userService.getAllUsers();
		assert(userArray.size()==0);
	}
	

	@Test
	public void testGetAllUsers(){ //no empty repository 
		List<User> testList = new ArrayList<User>();
		List<UserDto> expectedList = new ArrayList<UserDto>();
		for(int i=0; i<10; i++) {
			testList.add(new User());
			expectedList.add(new UserDto());
		}
		
		UserRepository test= mock(UserRepository.class);
		when(test.findAll()).thenReturn(testList);
		
		UserServiceimpl userService = new UserServiceimpl(test);
		List<UserDto> userArray= userService.getAllUsers();
		assert(userArray.equals(expectedList));
	}
	

	@Test
	public void testDeleteUnregisteredUser() throws InvalidUserException{ 
		UserRepository test= mock(UserRepository.class);
		when(test.findByUserId(any(Integer.class))).thenReturn(null); //user not found
		
		int userId=4;
		UserServiceimpl userService = new UserServiceimpl(test);
		boolean deleted= userService.deleteUser(userId);
		assert(deleted==false);
	}
	
	@Test
	public void testDeleteUserInvalidId() throws InvalidUserException{ //negative userId
		UserRepository test= mock(UserRepository.class);
		when(test.findByUserId(any(Integer.class))).thenReturn(new User());

		int userId=-1;
		UserServiceimpl userService = new UserServiceimpl(test);
		Assertions.assertThrows(InvalidUserException.class,()->{userService.deleteUser(userId);});
	}
	
	@Test
	public void testDeleteRegisteredUser() throws InvalidUserException{ 
		UserRepository test= mock(UserRepository.class);
		when(test.findByUserId(any(Integer.class))).thenReturn(new User()); //user not found

		int userId=1;
		UserServiceimpl userService = new UserServiceimpl(test);
		boolean deleted= userService.deleteUser(userId);
		assert(deleted==true);
	}
	
	@Test
	public void testLogin() throws InvalidLoginDataException {
		User user = new User("Mario", "rossi", "mario@ezgas.com", 3);
		user.setUserId(5);
		user.setAdmin(false);
		UserRepository userRepository = mock(UserRepository.class);
		when(userRepository.findByEmailAndPassword(anyString(), anyString())).thenReturn(user);
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
		user.setUserId(5);
		user.setAdmin(false);
		UserRepository userRepository = mock(UserRepository.class);
		when(userRepository.findByEmailAndPassword(anyString(), anyString())).thenReturn(null);

		IdPw credentials = new IdPw(user.getEmail(), user.getPassword());
		UserServiceimpl userServiceimpl = new UserServiceimpl(userRepository);
		
		Assertions.assertThrows(InvalidLoginDataException.class, () -> {
			userServiceimpl.login(credentials);
		});
	}
	
	@Test
	public void testIncreaseUserReputation() throws InvalidUserException {
		User user = new User("Mario", "rossi", "mario@ezgas.com", 3);
		user.setUserId(5);
		UserRepository userRepository = mock(UserRepository.class);
		when(userRepository.findByUserId(anyInt())).thenReturn(user);
		when(userRepository.save(any(User.class))).thenReturn(user);
		UserServiceimpl userServiceimpl = new UserServiceimpl(userRepository);
		Integer rep = userServiceimpl.increaseUserReputation(user.getUserId());
		
		Integer res = user.getReputation();
		assertTrue(rep.equals(res));
	}
	
	@Test
	public void testIncreaseUserReputationNegativeUserId() throws InvalidUserException {
		User user = new User("Mario", "rossi", "mario@ezgas.com", 3);
		user.setUserId(-5);
		UserRepository userRepository = mock(UserRepository.class);
		UserServiceimpl userServiceimpl = new UserServiceimpl(userRepository);
		
		Assertions.assertThrows(InvalidUserException.class, () -> {
			userServiceimpl.increaseUserReputation(user.getUserId());
		});
	}
	
	@Test
	public void testIncreaseUserReputationNotFound() throws InvalidUserException {
		User user = new User("Mario", "rossi", "mario@ezgas.com", 3);
		user.setUserId(5);
		UserRepository userRepository = mock(UserRepository.class);
		when(userRepository.findByUserId(anyInt())).thenReturn(null);

		UserServiceimpl userServiceimpl = new UserServiceimpl(userRepository);
		Integer rep = userServiceimpl.increaseUserReputation(user.getUserId());
		assertTrue(rep == null);
	}
	
	@Test
	public void testDecreaseUserReputation() throws InvalidUserException {
		User user = new User("Mario", "rossi", "mario@ezgas.com", 3);
		user.setUserId(5);
		UserRepository userRepository = mock(UserRepository.class);
		when(userRepository.findByUserId(anyInt())).thenReturn(user);
		when(userRepository.save(any(User.class))).thenReturn(user);
		UserServiceimpl userServiceimpl = new UserServiceimpl(userRepository);
		Integer rep = userServiceimpl.decreaseUserReputation(user.getUserId());
		
		Integer res = user.getReputation();
		assertTrue(rep.equals(res));
	}
	
	@Test
	public void testDecreaseUserReputationNegativeUserId() throws InvalidUserException {
		User user = new User("Mario", "rossi", "mario@ezgas.com", 3);
		user.setUserId(-5);
		UserRepository userRepository = mock(UserRepository.class);
		UserServiceimpl userServiceimpl = new UserServiceimpl(userRepository);
		
		Assertions.assertThrows(InvalidUserException.class, () -> {
			userServiceimpl.decreaseUserReputation(user.getUserId());
		});
	}
	
	@Test
	public void testDecreaseUserReputationNotFound() throws InvalidUserException {
		User user = new User("Mario", "rossi", "mario@ezgas.com", 3);
		user.setUserId(5);
		UserRepository userRepository = mock(UserRepository.class);
		when(userRepository.findByUserId(anyInt())).thenReturn(null);

		UserServiceimpl userServiceimpl = new UserServiceimpl(userRepository);
		Integer rep = userServiceimpl.decreaseUserReputation(user.getUserId());
		assertTrue(rep == null);
	}
}
