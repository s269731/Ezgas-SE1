package it.polito.ezgas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.dto.LoginDto;

public class LoginDtoAndIdPwTest {
	
	@Test
	public void testSetUserId() {
		LoginDto user = new LoginDto();
		Integer id = 5;
		user.setUserId(id);
		
		assertEquals("setUserId() fails", id, user.getUserId());
	}
	
	@Test
	public void testGetUserId() {
		LoginDto user = new LoginDto();
		int id = 5;
		user.setUserId(id);
		
		int result = user.getUserId();
		
		assertEquals("getUserId() fails", 5, result);
	}	
	
	@Test
	public void testSetUserName() {
		LoginDto user = new LoginDto();
		String name = "Mario";
		user.setUserName(name);
		
		assertEquals("setUserName() fails", name, user.getUserName());
	}
	
	@Test
	public void testGetUserName() {
		LoginDto user = new LoginDto();
		String name = "Mario";
		user.setUserName(name);
		
		String result = user.getUserName();
		
		assertEquals("getUserName() fails", name, result);
	}
	
	@Test
	public void testSetToken() {
		LoginDto user = new LoginDto();
		String tok = "ABC1234";
		user.setToken(tok);
		
		assertEquals("setPassword() fails", tok, user.getToken());
	}
	
	@Test
	public void testGetToken() {
		LoginDto user = new LoginDto();
		String tok = "ABC1234";
		user.setToken(tok);
		
		String result = user.getToken();
		
		assertEquals("getPassword() fails", tok, result);
	}
	
	@Test
	public void testSetEmail() {
		LoginDto user = new LoginDto();
		String email = "mario@ezgas.com";
		user.setEmail(email);
		
		assertEquals("setEmail() fails", email, user.getEmail());
	}
	
	@Test
	public void testGetEmail() {
		LoginDto user = new LoginDto();
		String email = "mario@ezgas.com";
		user.setEmail(email);
		
		String result = user.getEmail();
		
		assertEquals("getEmail() fails", email, result);
	}
	
	@Test
	public void testSetReputation() {
		LoginDto user = new LoginDto();
		Integer rep = 5;
		user.setReputation(rep);
		
		assertEquals("setReputation() fails", rep, user.getReputation());
	}
	
	@Test
	public void testGetReputation() {
		LoginDto user = new LoginDto();
		int rep = 5;
		user.setReputation(rep);
		
		int result = user.getReputation();
		
		assertEquals("getReputation() fails", rep, result);
	}	
	
	@Test
	public void testSetAdmin() {
		LoginDto user = new LoginDto();
		Boolean adm = true;
		user.setAdmin(adm);
		
		assertEquals("setAdmin() fails", adm, user.getAdmin());
	}
	
	@Test
	public void testGetAdmin() {
		LoginDto user = new LoginDto();
		Boolean adm = true;
		user.setAdmin(adm);
		
		Boolean result = user.getAdmin();
		
		assertEquals("getAdmin() fails", adm, result);
	}	
	
	@Test
	public void testLoginDtoConstructor() {
		String username = "Mario";
		String tok = "ABC1234";
		String email = "mario@ezgas.com";
		Integer rep = 5;
		
		LoginDto user = new LoginDto(5, username, tok, email, rep);
		
		assert(user.getUserId() == 5);
		assertEquals(user.getUserName(),username);
		assertEquals(user.getToken(), tok);
		assertEquals(user.getEmail(), email);
		assertEquals(user.getReputation(), rep);
	}
	
	@Test
	public void testEquals() {
		LoginDto user1 = new LoginDto(5, "Mario", "ABC1234", "mario@ezgas.com", 0);
		LoginDto user2 = new LoginDto(5, "Mario", "ABC1234", "mario@ezgas.com", 0);
		
		assertTrue(user1.equals(user2));
	}

	@Test
	public void testEqualsNo() {
		LoginDto user1 = new LoginDto(5, "Mario", "ABC1234", "mario@ezgas.com", 0);
		LoginDto user2 = new LoginDto(7, "Mario", "ABC1234", "mario@ezgas.com", 0);
		
		assertFalse(user1.equals(user2));
	}
	
	@Test
	public void testEqualsSelf() {
		LoginDto user1 = new LoginDto(5, "Mario", "ABC1234", "mario@ezgas.com", 0);
		
		assertTrue(user1.equals(user1));
	}
	
	@Test
	public void testEqualsNull() {
		LoginDto user1 = new LoginDto(5, "Mario", "ABC1234", "mario@ezgas.com", 0);
		
		assertFalse(user1.equals(null));
	}
	
	@Test
	public void testSetUser() {
		IdPw credentials = new IdPw();
		String email = "mario@ezgas.com";
		credentials.setUser(email);
		
		assertEquals("setUser() fails", email, credentials.getUser());
	}
	
	@Test
	public void testGetUser() {
		IdPw credentials = new IdPw();
		String email = "mario@ezgas.com";
		credentials.setUser(email);
		
		String result = credentials.getUser();
		
		assertEquals("getUser() fails", email, result);
	}
	
	@Test
	public void testSetPw() {
		IdPw credentials = new IdPw();
		String pass = "rossi";
		credentials.setPw(pass);
		
		assertEquals("setPw() fails", pass, credentials.getPw());
	}
	
	@Test
	public void testGetPw() {
		IdPw credentials = new IdPw();
		String pass = "rossi";
		credentials.setPw(pass);
		
		String result = credentials.getPw();
		
		assertEquals("getPw() fails", pass, result);
	}

	@Test
	public void testIdPwConstructor() {
		String user = "mario@ezgas.com";
		String pass = "rossi";
		
		IdPw credentials = new IdPw(user, pass);
		
		assertEquals(credentials.getUser(), user);
		assertEquals(credentials.getPw(), pass);
	}
}
