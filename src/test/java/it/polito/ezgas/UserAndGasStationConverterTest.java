package it.polito.ezgas;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;

public class UserAndGasStationConverterTest {
	
	@Test
	public void testUserConverter() {
		User user=new User("Test","123","test@ezgas.com",3);
		user.setUserId(4);
		user.setAdmin(false);
		UserDto expectedDto=new UserDto(4,"Test","123","test@ezgas.com",3,false);
		UserDto userDto= UserConverter.toUserDto(user);
		assert(userDto.equals(expectedDto));
	}
	
	@Test
	public void testToGasStationDtoObject() {
		
		GasStation gasStation = new GasStation("EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true, true, "Enjoy", 45.5549032, 8.0569401, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 5, "05-17-2020", 3.3);
		Integer gasStationId = 5;
		gasStation.setGasStationId(gasStationId);
		GasStationDto gasStationDto = new GasStationDto(gasStationId,"EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true, true, "Enjoy", 45.5549032, 8.0569401, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 5, "05-17-2020", 3.3);

		GasStationDto result = GasStationConverter.toGasStationDto(gasStation);
		assert(result.equals(gasStationDto));
	}
	
	@Test
	public void testToGasStationDtoList() {
		
		List<GasStation> gasStations = new ArrayList<GasStation> ();
		
		GasStation gs1 = new GasStation("EnerCoop", "Via Falletti 15 Biella", true, true, true, true, true, true, "Enjoy", 45.542, 8.0569401, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 5, "05-17-2020", 3.3);
		GasStation gs2 = new GasStation("Eni", "Via Macchieraldo 2 Biella", true, true, true, true, true, true, "Enjoy", 45.55, 8.0569401, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 5, "05-17-2020", 3.3);
		GasStation gs3 = new GasStation("Esso", "Via Ivrea 16 Biella", true, true, true, true, true, true, "Enjoy", 45.56, 8.0569401, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 5, "05-17-2020", 3.3);
		gs1.setGasStationId(1);
		gs2.setGasStationId(2);
		gs3.setGasStationId(3);
		
		gasStations.add(gs1);
		gasStations.add(gs2);
		gasStations.add(gs3);
		
		List<GasStationDto> gasStationsDto = new ArrayList<GasStationDto> ();
		GasStationDto gsDto1 = new GasStationDto(1,"EnerCoop", "Via Falletti 15 Biella", true, true, true, true, true, true, "Enjoy", 45.542, 8.0569401, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 5, "05-17-2020", 3.3);
		GasStationDto gsDto2 = new GasStationDto(2,"Eni", "Via Macchieraldo 2 Biella", true, true, true, true, true, true, "Enjoy", 45.55, 8.0569401, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 5, "05-17-2020", 3.3);
		GasStationDto gsDto3 = new GasStationDto(3,"Esso", "Via Ivrea 16 Biella", true, true, true, true, true, true, "Enjoy", 45.56, 8.0569401, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 5, "05-17-2020", 3.3);
		
		gasStationsDto.add(gsDto1);
		gasStationsDto.add(gsDto2);
		gasStationsDto.add(gsDto3);
		
		List<GasStationDto> result = GasStationConverter.toGasStationDto(gasStations);
		assert(result.equals(gasStationsDto));
		
	}

}
