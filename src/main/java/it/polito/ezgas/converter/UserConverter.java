package it.polito.ezgas.converter;

import org.springframework.stereotype.Component;

import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;

@Component
public class UserConverter {
	
	public static UserDto toUserDto(User user) {
		
		UserDto userDto = new UserDto();
		userDto.setUserId(user.getUserId());
		userDto.setUserName(user.getUserName());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setReputation(user.getReputation());
		userDto.setAdmin(user.getAdmin());
		
		return userDto;
	}
}
