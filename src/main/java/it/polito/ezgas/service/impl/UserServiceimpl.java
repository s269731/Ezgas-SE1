package it.polito.ezgas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import exception.InvalidLoginDataException;
import exception.InvalidUserException;
import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.service.UserService;

/**
 * Created by softeng on 27/4/2020.
 */
@Service
public class UserServiceimpl implements UserService {
	
	//@Autowired
	private UserRepository userRepository;
	
	public UserServiceimpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDto getUserById(Integer userId) throws InvalidUserException {
		if (userId > 0) {
			User user = userRepository.findByUserId(userId);
			if (user != null)
				return UserConverter.toUserDto(user);
			else
				return null;
		}
		else
			throw new InvalidUserException("UserId cannot be negative"); 
	}

	@Override
	public UserDto saveUser(UserDto userDto) {
		User user;
		if (userDto.getUserId() != null && userRepository.findByUserId(userDto.getUserId()) != null) {	// user already enrolled --> update
			user = userRepository.findByUserId(userDto.getUserId());
			if (user.getEmail().compareTo(userDto.getEmail()) != 0) {
				User u = userRepository.findByEmail(userDto.getEmail());
				if (u != null)
					return null;
			}
			user.setUserName(userDto.getUserName());
			user.setEmail(userDto.getEmail());
			user.setPassword(userDto.getPassword());
		} else {	// new user --> insert
			User u = userRepository.findByEmail(userDto.getEmail());
			if (u == null) {	// email not present in the database
				user = new User(userDto.getUserName(), userDto.getPassword(), userDto.getEmail(), 0);
				user.setAdmin(userDto.getAdmin());				
			} else
				return null;
		}
		User u = userRepository.save(user);
		
		return UserConverter.toUserDto(u);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDto> usersDto = new ArrayList<UserDto>();
		for(User user:users)
			usersDto.add(UserConverter.toUserDto(user));
		return usersDto;
	}

	@Override
	public Boolean deleteUser(Integer userId) throws InvalidUserException {
		if (userId > 0) {
			User user = userRepository.findByUserId(userId);
	        if (user != null) {
	        	userRepository.delete(user);
	        	return true;
	        } else
	        	return false;
		} else
			throw new InvalidUserException("UserId cannot be negative");
	}

	@Override
	public LoginDto login(IdPw credentials) throws InvalidLoginDataException {
		User user = userRepository.findByEmailAndPassword(credentials.getUser(), credentials.getPw());
		if (user != null) {
			LoginDto login = new LoginDto();
			login.setUserId(user.getUserId());
			login.setUserName(user.getUserName());
			login.setEmail(user.getEmail());
			login.setReputation(user.getReputation());
			login.setAdmin(user.getAdmin());
			return login;
		} else 	
			throw new InvalidLoginDataException("Invalid login");
	}

	@Override
	public Integer increaseUserReputation(Integer userId) throws InvalidUserException {
		if (userId > 0) {
			User user = userRepository.findByUserId(userId);
			if (user != null) {
				if(user.getReputation() < 5) {
					user.setReputation(user.getReputation()+1);
					userRepository.save(user);
				}
				return user.getReputation();
			} else
				return null;
		} else
			throw new InvalidUserException("UserId cannot be negative");
	}

	@Override
	public Integer decreaseUserReputation(Integer userId) throws InvalidUserException {
		if (userId > 0) {
			User user = userRepository.findByUserId(userId);
			if (user != null) {
				if(user.getReputation() > -5) {
					user.setReputation(user.getReputation()-1);
					userRepository.save(user);
				}
				return user.getReputation();
			} else
				return null;
		} else
			throw new InvalidUserException("UserId cannot be negative");
	}
	
}
