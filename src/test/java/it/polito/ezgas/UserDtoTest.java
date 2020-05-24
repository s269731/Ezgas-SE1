package it.polito.ezgas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polito.ezgas.dto.UserDto;

public class UserDtoTest {
	
	@Test
	public void testSetUserId() {
		UserDto user = new UserDto();
		Integer id = 5;
		user.setUserId(id);
		
		assertEquals("setUserId() fails", id, user.getUserId());
	}
	
	@Test
	public void testGetUserId() {
		UserDto user = new UserDto();
		int id = 5;
		user.setUserId(id);
		
		int result = user.getUserId();
		
		assertEquals("getUserId() fails", 5, result);
	}	
	
	@Test
	public void testSetUserName() {
		UserDto user = new UserDto();
		String name = "Mario";
		user.setUserName(name);
		
		assertEquals("setUserName() fails", name, user.getUserName());
	}
	
	@Test
	public void testGetUserName() {
		UserDto user = new UserDto();
		String name = "Mario";
		user.setUserName(name);
		
		String result = user.getUserName();
		
		assertEquals("getUserName() fails", name, result);
	}
	
	@Test
	public void testSetPassword() {
		UserDto user = new UserDto();
		String pass = "rossi";
		user.setPassword(pass);
		
		assertEquals("setPassword() fails", pass, user.getPassword());
	}
	
	@Test
	public void testGetPassword() {
		UserDto user = new UserDto();
		String pass = "rossi";
		user.setPassword(pass);
		
		String result = user.getPassword();
		
		assertEquals("getPassword() fails", pass, result);
	}
	
	@Test
	public void testSetEmail() {
		UserDto user = new UserDto();
		String email = "mario@ezgas.com";
		user.setEmail(email);
		
		assertEquals("setEmail() fails", email, user.getEmail());
	}
	
	@Test
	public void testGetEmail() {
		UserDto user = new UserDto();
		String email = "mario@ezgas.com";
		user.setEmail(email);
		
		String result = user.getEmail();
		
		assertEquals("getEmail() fails", email, result);
	}
	
	@Test
	public void testSetReputation() {
		UserDto user = new UserDto();
		Integer rep = 5;
		user.setReputation(rep);
		
		assertEquals("setReputation() fails", rep, user.getReputation());
	}
	
	@Test
	public void testGetReputation() {
		UserDto user = new UserDto();
		int rep = 5;
		user.setReputation(rep);
		
		int result = user.getReputation();
		
		assertEquals("getReputation() fails", rep, result);
	}	
	
	@Test
	public void testSetAdmin() {
		UserDto user = new UserDto();
		Boolean adm = true;
		user.setAdmin(adm);
		
		assertEquals("setAdmin() fails", adm, user.getAdmin());
	}
	
	@Test
	public void testGetAdmin() {
		UserDto user = new UserDto();
		Boolean adm = true;
		user.setAdmin(adm);
		
		Boolean result = user.getAdmin();
		
		assertEquals("getAdmin() fails", adm, result);
	}	
	
	@Test
	public void testUserDtoConstructor() {
		String username = "Mario";
		String password = "rossi";
		String email = "mario@ezgas.com";
		Integer rep = 5;
		
		UserDto user = new UserDto(5, username, password, email, rep);
		
		assert(user.getUserId() == 5);
		assertEquals(user.getUserName(),username);
		assertEquals(user.getPassword(), password);
		assertEquals(user.getEmail(), email);
		assertEquals(user.getReputation(), rep);
	}
	
	@Test
	public void testEquals() {
		UserDto user1 = new UserDto(5, "Mario", "rossi", "mario@ezgas.com", 0);
		UserDto user2 = new UserDto(5, "Mario", "rossi", "mario@ezgas.com", 0);
		
		assertTrue(user1.equals(user2));
	}

	@Test
	public void testEqualsNo() {
		UserDto user1 = new UserDto(5, "Mario", "rossi", "mario@ezgas.com", 0);
		UserDto user2 = new UserDto(7, "Mario", "rossi", "mario@ezgas.com", 0);
		
		assertFalse(user1.equals(user2));
	}
	
	@Test
	public void testEqualsSelf() {
		UserDto user1 = new UserDto(5, "Mario", "rossi", "mario@ezgas.com", 0);
		
		assertTrue(user1.equals(user1));
	}
	
	@Test
	public void testEqualsNull() {
		UserDto user1 = new UserDto(5, "Mario", "rossi", "mario@ezgas.com", 0);
		
		assertFalse(user1.equals(null));
	}
}
