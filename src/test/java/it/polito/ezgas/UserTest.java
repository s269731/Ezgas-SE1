package it.polito.ezgas;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import it.polito.ezgas.entity.User;

public class UserTest {
	
	@Test
	public void testSetUserId() {
		User user = new User();
		Integer id = 5;
		user.setUserId(id);
		
		assertEquals("setUserId() fails", id, user.getUserId());
	}
	
	@Test
	public void testGetUserId() {
		User user = new User();
		int id = 5;
		user.setUserId(id);
		
		int result = user.getUserId();
		
		assertEquals("getUserId() fails", 5, result);
	}	
	
	@Test
	public void testSetUserName() {
		User user = new User();
		String name = "Mario";
		user.setUserName(name);
		
		assertEquals("setUserName() fails", name, user.getUserName());
	}
	
	@Test
	public void testGetUserName() {
		User user = new User();
		String name = "Mario";
		user.setUserName(name);
		
		String result = user.getUserName();
		
		assertEquals("getUserName() fails", name, result);
	}
	
	@Test
	public void testSetPassword() {
		User user = new User();
		String pass = "rossi";
		user.setPassword(pass);
		
		assertEquals("setPassword() fails", pass, user.getPassword());
	}
	
	@Test
	public void testGetPassword() {
		User user = new User();
		String pass = "rossi";
		user.setPassword(pass);
		
		String result = user.getPassword();
		
		assertEquals("getPassword() fails", pass, result);
	}
	
	@Test
	public void testSetEmail() {
		User user = new User();
		String email = "mario@ezgas.com";
		user.setEmail(email);
		
		assertEquals("setEmail() fails", email, user.getEmail());
	}
	
	@Test
	public void testGetEmail() {
		User user = new User();
		String email = "mario@ezgas.com";
		user.setEmail(email);
		
		String result = user.getEmail();
		
		assertEquals("getEmail() fails", email, result);
	}
	
	@Test
	public void testSetReputation() {
		User user = new User();
		Integer rep = 5;
		user.setReputation(rep);
		
		assertEquals("setReputation() fails", rep, user.getReputation());
	}
	
	@Test
	public void testGetReputation() {
		User user = new User();
		int rep = 5;
		user.setReputation(rep);
		
		int result = user.getReputation();
		
		assertEquals("getReputation() fails", rep, result);
	}	
	
	@Test
	public void testSetAdmin() {
		User user = new User();
		Boolean adm = true;
		user.setAdmin(adm);
		
		assertEquals("setAdmin() fails", adm, user.getAdmin());
	}
	
	@Test
	public void testGetAdmin() {
		User user = new User();
		Boolean adm = true;
		user.setAdmin(adm);
		
		Boolean result = user.getAdmin();
		
		assertEquals("getAdmin() fails", adm, result);
	}	
	
	@Test
	public void testUserConstructor() {
		String username = "Mario";
		String password = "rossi";
		String email = "mario@ezgas.com";
		Integer rep = 5;
		
		User user = new User(username, password, email, rep);
		
		assertEquals(username, user.getUserName());
		assertEquals(password, user.getPassword());
		assertEquals(email, user.getEmail());
		assertEquals(rep, user.getReputation());
	}
}
