package it.polito.ezgas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import exception.GPSDataException;
import exception.InvalidCarSharingException;
import exception.InvalidGasStationException;
import exception.InvalidGasTypeException;
import exception.InvalidLoginDataException;
import exception.InvalidUserException;
import exception.PriceException;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.GasStationRepository;
import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.service.impl.GasStationServiceimpl;
import it.polito.ezgas.service.impl.UserServiceimpl;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ResponseTimeTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GasStationRepository gasStationRepository;
	
	@Test
	public void testResponseTime() throws InvalidUserException, InvalidGasStationException, InvalidLoginDataException, PriceException, GPSDataException, InvalidGasTypeException, InvalidCarSharingException {
		Integer userId[] = new Integer[20];
		for(int i=1; i<21; i++) {
			String name = "User" + i;
			String pass = "pass" + i;
			String email = "user" + i + "@ezgas.com";
			User user = new User(name, pass, email, 0);
			user.setAdmin(false);
			entityManager.persist(user);
			userId[i-1] = user.getUserId();
		}
		
		GasStation gs0 =new GasStation("Canterino", "Via Fratelli Rosselli 102", true, true, false, false, true, false, "Enjoy", 45.551897, 8.0477697, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, null, null, 0);
		entityManager.persist(gs0);
		//GasStation nel raggio (distance = 0.78) 
		GasStation gs1 = new GasStation("EnerCoop", "Via Macchieraldo 2 Biella", false, false, true, true, true, false, "Enjoy", 45.5549032, 8.0569401, 5.0, 5.0, 1.3, 1.4, 1.5, 5.0, userId[6], new SimpleDateFormat("MM-dd-yyyy").format(new java.util.Date()), 25.0);
		gs1.setUser(userRepository.findByUserId(userId[6]));
		entityManager.persist(gs1);
		//GasStation in square, but not in radius (distance = 1.36)
		GasStation gs2 = new GasStation("Esso", "Viale Roma Biella", true, true, true, false, false, false, "Car2Go", 45.5560598,8.0642756, 1.1, 1.2, 1.3, 5.0, 5.0, 5.0, userId[7], new SimpleDateFormat("MM-dd-yyyy").format(new java.util.Date()), 25.0);
		gs2.setUser(userRepository.findByUserId(userId[7]));
		entityManager.persist(gs2);
		//GasStation outside alltogether (distance = 61.69)
		GasStation gs3 = new GasStation("Eni", "Via Magenta 52 Torino", false, true, true, true, true, false, null, 45.0671772,7.6639721, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, null, null, 0);
		entityManager.persist(gs3);
		GasStation gs4 = new GasStation("Super GasStation", "Via Asti 54 Torino", false, true, true, true, true, false, "Car2Go", 45.5569261, 8.0559956, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, null, null, 0);
		entityManager.persist(gs4);
		GasStation gs5 = new GasStation("LemonGasStation", "Via dei Limoni 4 Torino", true, true, true, true, true, true, "Enjoy", 45.5570985, 8.0474926, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, null, null, 0);
		entityManager.persist(gs5);
		GasStation gs6 = new GasStation("IvreaGasStation", "Via Ivrea 16 Torino", true, true, true, true, true, true, "Enjoy", 45.5574032, 8.0459052, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, null, null, 0);
		entityManager.persist(gs6);
		GasStation gs7 = new GasStation("BancaSella GasStation", "Piazza Gaudenzio 45 Torino", true, true, true, true, true, true, "Enjoy", 45.5577691, 8.0512994, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, null, null, 0);
		entityManager.persist(gs7);
		
		UserServiceimpl userServiceimpl = new UserServiceimpl(userRepository);
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, userRepository);
		
		String times[] = new String[18];
		Integer i = 0;
				
		long startTime = System.currentTimeMillis();
		userServiceimpl.getUserById(userId[2]);
		long endTime = System.currentTimeMillis();
		String duration = "getUserById() took " + (endTime - startTime) + " milliseconds";
		times[i++] = duration;
		
		UserDto userDto = new UserDto(null, "Mario", "rossi", "mario@ezgas.com", 0);
		startTime = System.currentTimeMillis();
		userServiceimpl.saveUser(userDto);
		endTime = System.currentTimeMillis();
		duration = "saveUser() took " + (endTime - startTime) + " milliseconds";
		times[i++] = duration;
		
		startTime = System.currentTimeMillis();
		userServiceimpl.getAllUsers();
		endTime = System.currentTimeMillis();
		duration = "getAllUsers() took " + (endTime - startTime) + " milliseconds";
		times[i++] = duration;
		
		startTime = System.currentTimeMillis();
		userServiceimpl.deleteUser(userId[18]);
		endTime = System.currentTimeMillis();
		duration = "deleteUser() took " + (endTime - startTime) + " milliseconds";
		times[i++] = duration;
		
		IdPw credentials = new IdPw("user5@ezgas.com", "pass5");
		startTime = System.currentTimeMillis();
		userServiceimpl.login(credentials);
		endTime = System.currentTimeMillis();
		duration = "login() took " + (endTime - startTime) + " milliseconds";
		times[i++] = duration;
		
		startTime = System.currentTimeMillis();
		userServiceimpl.increaseUserReputation(userId[5]);
		endTime = System.currentTimeMillis();
		duration = "increaseUserReputation() took " + (endTime - startTime) + " milliseconds";
		times[i++] = duration;
		
		startTime = System.currentTimeMillis();
		userServiceimpl.decreaseUserReputation(userId[10]);
		endTime = System.currentTimeMillis();
		duration = "decreaseUserReputation() took " + (endTime - startTime) + " milliseconds";
		times[i++] = duration;
		
		startTime = System.currentTimeMillis();
		gasStationServiceimpl.getGasStationById(gs2.getGasStationId());
		endTime = System.currentTimeMillis();
		duration = "getGasStationById() took " + (endTime - startTime) + " milliseconds";
		times[i++] = duration;
		
		GasStationDto gasStationDto = new GasStationDto(null, "Example1", "Address1", true, false, true, true, false, false, "Enjoy", 45.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		startTime = System.currentTimeMillis();
		gasStationServiceimpl.saveGasStation(gasStationDto);
		endTime = System.currentTimeMillis();
		duration = "saveGasStation() took " + (endTime - startTime) + " milliseconds";
		times[i++] = duration;
		
		startTime = System.currentTimeMillis();
		gasStationServiceimpl.getAllGasStations();
		endTime = System.currentTimeMillis();
		duration = "getAllGasStations() took " + (endTime - startTime) + " milliseconds";
		times[i++] = duration;
		
		startTime = System.currentTimeMillis();
		gasStationServiceimpl.deleteGasStation(gs4.getGasStationId());
		endTime = System.currentTimeMillis();
		duration = "deleteGasStation() took " + (endTime - startTime) + " milliseconds";
		times[i++] = duration;
		
		startTime = System.currentTimeMillis();
		gasStationServiceimpl.getGasStationsByGasolineType("Diesel");
		endTime = System.currentTimeMillis();
		duration = "getGasStationsByGasolineType() took " + (endTime - startTime) + " milliseconds";
		times[i++] = duration;
		
		startTime = System.currentTimeMillis();
		gasStationServiceimpl.getGasStationsByProximity(45.551897, 8.0477697, 1);
		endTime = System.currentTimeMillis();
		duration = "getGasStationsByProximity() took " + (endTime - startTime) + " milliseconds";
		times[i++] = duration;
		
		startTime = System.currentTimeMillis();
		gasStationServiceimpl.getGasStationsWithCoordinates(45.551897, 8.0477697, 1, "Gas", "Enjoy");
		endTime = System.currentTimeMillis();
		duration = "getGasStationsWithCoordinates() took " + (endTime - startTime) + " milliseconds";
		times[i++] = duration;
		
		startTime = System.currentTimeMillis();
		gasStationServiceimpl.getGasStationsWithoutCoordinates("Gas", "Enjoy");
		endTime = System.currentTimeMillis();
		duration = "getGasStationsWithoutCoordinates() took " + (endTime - startTime) + " milliseconds";
		times[i++] = duration;
		
		startTime = System.currentTimeMillis();
		gasStationServiceimpl.getGasStationByCarSharing("Car2Go");
		endTime = System.currentTimeMillis();
		duration = "getGasStationByCarSharing() took " + (endTime - startTime) + " milliseconds";
		times[i++] = duration;
		
		startTime = System.currentTimeMillis();
		gasStationServiceimpl.setReport(gs0.getGasStationId(), 1.4, 1.6, null, null, 0.95, null, userId[12]);
		endTime = System.currentTimeMillis();
		duration = "setReport() took " + (endTime - startTime) + " milliseconds";
		times[i++] = duration;
		
		List<GasStation> list = new ArrayList<GasStation>();
		list.add(gs1);
		list.add(gs2);
		list.add(gs3);
		startTime = System.currentTimeMillis();
		gasStationServiceimpl.updateDependabilities(list);
		endTime = System.currentTimeMillis();
		duration = "updateDependabilities() took " + (endTime - startTime) + " milliseconds";
		times[i++] = duration;
		
		for(int j=0; j<18; j++)
			System.out.println(times[j] + '\n');
	}

}
