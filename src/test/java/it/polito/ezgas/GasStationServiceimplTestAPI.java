package it.polito.ezgas;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import exception.GPSDataException;
import exception.InvalidCarSharingException;
import exception.InvalidGasStationException;
import exception.InvalidGasTypeException;
import exception.InvalidUserException;
import exception.PriceException;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.GasStationRepository;
import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.service.impl.GasStationServiceimpl;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GasStationServiceimplTestAPI {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private GasStationRepository gasStationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void TestGetGasStationByIdNotNull() throws InvalidGasStationException {
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		GasStation gasStation = new GasStation("ExpressGas", "Via Galimberti 6", true, true, true, true, true,true, "Enjoy", 45, 8, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0);
		entityManager.persist(gasStation);
		
		GasStationDto gasStationDto = new GasStationDto(gasStation.getGasStationId(), "ExpressGas", "Via Galimberti 6", true, true, true, true, true,true, "Enjoy", 45, 8, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0);	
		
		GasStationDto result=gasStationServiceimpl.getGasStationById(gasStation.getGasStationId());

		assert(gasStationDto.equals(result));
		
	}
	
	@Test
	public void TestGetGasStationByIdNull() throws InvalidGasStationException {
		
			GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
			
			Integer gasStationId = 50;
			assert(gasStationServiceimpl.getGasStationById(gasStationId)==null);
		
	}
	
	@Test
	public void TestGetGasStationByIdNeg()  {
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		Assertions.assertThrows(InvalidGasStationException.class, () -> { gasStationServiceimpl.getGasStationById(-50);
		} );
		
	}
	
	@Test
	public void testSaveGasStationNew() throws PriceException, GPSDataException { 
		
		//I create this gasStation to know what id number will be given to the following one that is added
		GasStation gs=new GasStation("gs","addressGs",true,true,false,false,false,false,"Enjoy",4.00,7.00,0.0,0.0,null,null,null,null,null,null,0.0);
		entityManager.persist(gs);
		
		//Insert new gasStation 
		GasStationDto gsDtoIn=new GasStationDto(null,"test0","testAddress0",true,false,false,false,false,false,"Car2Go",4.10,7.10,0.0,null,null,null,null,null,null,null,0.0);

	    GasStationDto gsDtoOut=new GasStationDto(gs.getGasStationId()+1,"test0","testAddress0",true,false,false,false,false,false,"Car2Go",4.10,7.10,0.0,null,null,null,null,null,null,null,0.0);

		GasStationServiceimpl gasStationService = new GasStationServiceimpl(gasStationRepository, null);
		GasStationDto result = gasStationService.saveGasStation(gsDtoIn);
		assert(result.equals(gsDtoOut));
	}
	
	@Test
	public void testSaveGasStationNewReturnNull() throws PriceException, GPSDataException {
		
		GasStation gs= new GasStation("test1","testAddress1",true,true,false,false,false,false,"Enjoy",4.20,7.20,0.0,0.0,null,null,null,null,null,null,0.0);
		entityManager.persist(gs);
		
		//Try to save a gas Station with the same coordinates and address -> returns null
		GasStationDto gsDtoIn=new GasStationDto(null,"test1","testAddress1",true,true,false,false,false,false,"Enjoy",4.20,7.20,0.0,0.0,null,null,null,null,null,null,0.0);

		GasStationServiceimpl gasStationService = new GasStationServiceimpl(gasStationRepository, null);
		Assertions.assertNull(gasStationService.saveGasStation(gsDtoIn));
	}
	
	@Test
	public void testSaveGasStationUpdate() throws PriceException, GPSDataException {
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);

		GasStation gasStation1 = new GasStation("Starita", "Via Monti 3 Biella", true, true, true, true, true, true, "Car2Go", 45.5549032, 8.0569401, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);	
		entityManager.persist(gasStation1);
		
		//GasStation di input con nuovo nome e carSharing (che sarà anche la gasStation che mi aspetto ritornata)
		GasStationDto gasStationDtoIn = new GasStationDto(gasStation1.getGasStationId(), "NewStarita", "Via Monti 3 Biella", true, true, true, true, true, true, "Enjoy", 45.5549040, 8.0569440, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);		
		GasStationDto result = gasStationServiceimpl.saveGasStation(gasStationDtoIn);

		assert(result.equals(gasStationDtoIn));
	
	}
	
	@Test
	public void testSaveGasStationUpdateReturnNull() throws PriceException, GPSDataException {
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);

		//I save two gas Stations in DB
		GasStation gasStation1 = new GasStation("EnerCoop", "Via Macchieraldo 2 Biella", false, true, false, true, true, true, "Enjoy", 45.7895214, 8.0568740, null, 0.0, null, 0.0, 0.0, 0.0, null, null, 0.0);	
		GasStation gasStation2 = new GasStation("Starita", "Via Monti 3 Biella", true, true, true, true, true, true, "Car2Go", 45.5549032, 8.0569401, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);	
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		
		//I try to modify gasStation2 by putting new type of car sharing and the same coordinates and address of gasStation1 -> should return null
		GasStationDto gasStationDtoIn = new GasStationDto(gasStation2.getGasStationId(), "Starita", "Via Macchieraldo 2 Biella", true, true, true, true, true, true, "Enjoy", 45.7895214, 8.0568740, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);	
		
		Assertions.assertNull(gasStationServiceimpl.saveGasStation(gasStationDtoIn));
		
	}
	
	@Test
	public void testSaveGasStationInvalidLat()  {
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		GasStationDto gasStationDto = new GasStationDto( null, "EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true,true, "Enjoy", 200, 8, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0);	
		Assertions.assertThrows(GPSDataException.class, () -> { gasStationServiceimpl.saveGasStation(gasStationDto);
		} ) ;
		
	}
	
	@Test
	public void testSaveGasStationInvalidLon()  {
		GasStation gasStation1 = new GasStation("EnerCoop", "Via Macchieraldo 2 Biella", false, true, false, true, true,true, "null", 45.7895214, 8.0568740, null, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0);	
		GasStation gasStation2 = new GasStation("Starita", "Via Monti 3 Biella", true, true, true, true, true, true, "Car2Go", 45.5549032, 8.0569401, 0.0, 0.0, 0.0, 0.0, 0.0,0.0, null, null, 0);	
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);

		GasStationDto gasStationDto = new GasStationDto(gasStation1.getGasStationId(), "EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true,true, "Enjoy", 45, 200, 0.0, 0.0, 0.0, 0.0, 0.0,0.0, null, null, 0);	
		Assertions.assertThrows(GPSDataException.class, () -> { gasStationServiceimpl.saveGasStation(gasStationDto);
		} ) ;
		
	}
	
	@Test
	public void testGetAllGasStationZero(){ // empty repository 
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(gasStationRepository, null);
		List<GasStationDto> result=gasStationService.getAllGasStations();
		assert(result.size()==0);
	}
	
	@Test
	public void testGetAllGasStation(){ //no empty repository 
		GasStation gs1 = new GasStation("Example1", "Address2", false, true, false, true, true,true, "Enjoy", 45.7895214, 8.0568740, null, 0.0, null, 0.0, 0.0, 0.0, null, null, 0);	
		GasStation gs2 = new GasStation("Example2", "Address2", false, true, false, true, true,true, "Car2Go", 45.9895214, 8.1568740, null, 0.0, null, 0.0, 0.0, 0.0, null, null, 0);	
		GasStation gs3 = new GasStation("Example3", "Address3", false, true, false, true, true,true, "Enjoy", 45.3895214, 8.2568740, null, 0.0, null, 0.0, 0.0, 0.0, null, null, 0);	
		
		entityManager.persist(gs1);
		entityManager.persist(gs2);
		entityManager.persist(gs3);
				
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(gasStationRepository, null);
		List<GasStationDto> result=gasStationService.getAllGasStations();
		assert(result.size()==3);
	}
	
	@Test
	public void testDeleteUnregisteredGasStation() throws InvalidGasStationException{ 
		GasStation gs1 = new GasStation("Example1", "Address1", true, false, true, true, false,true, "Enjoy", 45.55, 8.05, 0.0, null, 0.0, 0.0, null, 0.0, null, "null", 0);
		GasStation gs2 = new GasStation("Example2", "Address2", true, true, false, true, false,true, "Car2Go", 45.65, 8.25, 0.0, 0.0, null, 0.0, null, 0.0, null, "null", 0);
		GasStation gs3 = new GasStation("Example3", "Address3", false, false, false, true, true,true, null, 45.05, 8.15, null,null, null, 0.0, 0.0,0.0, null, "null", 0);
		
		entityManager.persist(gs1);
		entityManager.persist(gs2);
		entityManager.persist(gs3);
		
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(gasStationRepository, null);
		Assertions.assertFalse(gasStationService.deleteGasStation(2000));
	}
	
	@Test
	public void testDeleteGasStationInvalidId() throws InvalidGasStationException{ 
		int gsId=-4;
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(gasStationRepository, null);
		Assertions.assertThrows(InvalidGasStationException.class,()->{gasStationService.deleteGasStation(gsId);});
	}
	
	@Test
	public void testDeleteRegisteredGasStation() throws InvalidGasStationException{ 		
		GasStation gs1 = new GasStation("Example1", "Address1", true, false, true, true, false,true, "Enjoy", 45.55, 8.05, 0.0, null, 0.0, 0.0, null, 0.0, null, "null", 0);
		GasStation gs2 = new GasStation("Example2", "Address2", true, true, false, true, false, true, "Car2Go", 45.65, 8.25, 0.0, 0.0, null, 0.0, null, 0.0, null, "null", 0);
		GasStation gs3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, null, 45.05, 8.15, null, null, null, 0.0, 0.0, 0.0, null, "null", 0);
		
		entityManager.persist(gs1);
		entityManager.persist(gs2);
		entityManager.persist(gs3);
		
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(gasStationRepository, null);
		
		boolean deleted = gasStationService.deleteGasStation(gs2.getGasStationId());
		Assertions.assertTrue(deleted);
	}
	
	@Test
	public void testGetGasStationsByGasolineType() throws InvalidGasTypeException {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.55, 8.05, 0.0,null, 0.0, 0.0, null, 0.0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, true, "Car2Go", 45.65, 8.25, 0.0, 0.0, null, 0.0, null, 0.0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, true, true, true, false, true, "Car2Go", 45.65, 8.25, null, 0.0, 0.0, 0.0, null, 0.0, null, "null", 0);
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		GasStationDto gasStationDto1 = new GasStationDto(gasStation1.getGasStationId(), "Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.55, 8.05, 0.0, null, 0.0, 0.0, null,0.0, null, "null", 0);
		GasStationDto gasStationDto2 = new GasStationDto(gasStation2.getGasStationId(), "Example2", "Address2", true, true, false, true, false, true, "Car2Go", 45.55, 8.05, 0.0, 0.0,null, 0.0, null, 0.0, null, "null", 0);
			
		List<GasStationDto> gasStationDtos = new ArrayList<GasStationDto>();
		gasStationDtos.add(gasStationDto1);
		gasStationDtos.add(gasStationDto2);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		List<GasStationDto> results = new ArrayList<GasStationDto>();
		results = gasStationServiceimpl.getGasStationsByGasolineType("Diesel");
		
		assertTrue(results.equals(gasStationDtos));
	} 
	
	@Test
	public void testGetGasStationsByGasolineTypeNullList() throws InvalidGasTypeException {
		GasStation gasStation1 = new GasStation("Example1", "Address1", false, false, true, true, false, true, "Enjoy", 45.55, 8.05, null,null,0.0, 0.0, null, 0.0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", false, true, false, true, false, true, "Car2Go", 45.65, 8.25, null, 0.0, null, 0.0, null, 0.0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, true, true, true, false, true, "Car2Go", 45.65, 8.25, null, 0.0, 0.0, 0.0, null, 0.0, null, "null", 0);
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		List<GasStationDto> results = new ArrayList<GasStationDto>();
		results = gasStationServiceimpl.getGasStationsByGasolineType("Diesel");
		
		assertTrue(results.size() == 0);
	}
	
	@Test
	public void testGetGasStationsByGasolineTypeInvalid() throws InvalidGasTypeException {		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		Assertions.assertThrows(InvalidGasTypeException.class, () -> {
			gasStationServiceimpl.getGasStationsByGasolineType("LPG");
		});
	}
	
	@Test
	public void testGetGasStationByProximityLatException() {
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		Assertions.assertThrows(GPSDataException.class, () -> { gasStationServiceimpl.getGasStationsByProximity(-100, 0, 1);
			} );
	}
	
	@Test
	public void testGetGasStationByProximityLonException() {
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		Assertions.assertThrows(GPSDataException.class, () -> { gasStationServiceimpl.getGasStationsByProximity(0, 200, 1);
			} );
	}
	
	@Test
	public void testGetGasStationByProximityRadius() throws GPSDataException {
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		//Set lat, lon for position where search is performed
		double lat=45.551897;
		double lon=8.0477697;
		
		//GasStation nel raggio (distance = 0.78) 
		GasStation gs1 = new GasStation("EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true, true, "Enjoy", 45.5549032, 8.0569401, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);
		entityManager.persist(gs1);
		//GasStation in square, but not in radius (distance = 1.36)
		GasStation gs2 =new GasStation("Esso", "Viale Roma Biella", true, true, true, true, true, true, "Car2Go", 45.5560598, 8.0642756, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);
		entityManager.persist(gs2);
		//GasStation outside radius of search (distance = 61.69)
		GasStation gs3 =new GasStation("Eni", "Via Magenta 52 Torino", true, true, true, true, true, true, "null", 45.0671772,7.6639721, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);
		entityManager.persist(gs3);

		//Create gasStationDto I need
		GasStationDto gsDto1 = new GasStationDto(gs1.getGasStationId(),"EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true, true, "Enjoy", 45.5549032, 8.0569401, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);
		
		//Create List of correct results
		List<GasStationDto> gasStationsDto = new ArrayList<GasStationDto>();
		gasStationsDto.add(gsDto1);
		
		/*
		 * Note that in all gasStations useReport was set to null, so as to avoid having to pass though updateDependabilities, which we have already tested.
		 * 
		 */
		
		//In this search we insert negative radius to check that it will be set to default of 1 km
		List<GasStationDto> result = gasStationServiceimpl.getGasStationsByProximity(lat, lon, -1);

		assert(result.equals(gasStationsDto));
	}
	
	@Test
	public void testGetGasStationsWithoutCoordinates() throws InvalidGasTypeException, InvalidCarSharingException {
		
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.55, 8.05, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0.0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, true, "Car2Go", 45.65, 8.25, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0.0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, "null", 45.5549090, 8.0569411, null, null, null, 0.0, 0.0, 0.0, null, null, 0.0);
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		List<GasStationDto> gasStationDtos = new ArrayList<GasStationDto>();
		GasStationDto gasStationDto1 = new GasStationDto(gasStation1.getGasStationId(), "Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.55, 8.05, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0.0);
		gasStationDtos.add(gasStationDto1);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		List<GasStationDto> results = gasStationServiceimpl.getGasStationsWithoutCoordinates("Diesel", "Enjoy");
		assertTrue(results.equals(gasStationDtos));
	}
	
	@Test
	public void testGetGasStationsWithoutCoordinatesInvalidGasType() throws InvalidGasTypeException {
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		Assertions.assertThrows(InvalidGasTypeException.class, () -> {
			gasStationServiceimpl.getGasStationsWithoutCoordinates("InvalidGas", "Enjoy");
		});
	}
	
	@Test
	public void testGetGasStationsWithoutCoordinatesNullList() throws InvalidGasTypeException, InvalidCarSharingException {
		
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.55, 8.05, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0.0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, true, "Enjoy", 45.65, 8.25, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0.0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, "null", 45.5549090, 8.0569411, null, null, null, 0.0, 0.0, 0.0, null, null, 0.0);
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		//There are clearly no gas stations in the DB with gasType Diesel and carSharing Car2Go
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		List<GasStationDto> results = gasStationServiceimpl.getGasStationsWithoutCoordinates("Diesel", "Car2Go");
		assertTrue(results.size() == 0);
	}
	
	@Test
	public void testGetGasStationsWithCoordinates() throws InvalidGasTypeException, GPSDataException, InvalidCarSharingException {
		
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.5549032, 8.0569401, 0.0, null, 0.0 , 0.0, null, 0.0, null, null, 0.0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, true, "Car2Go", 45.5549100, 8.0569403, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0.0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, "null", 45.5549090, 8.0569411, null, null, null, 0.0, 0.0, 0.0, null, null, 0.0);
		GasStation gasStation4 = new GasStation("Example4", "Address4", true, true, false, true, false, true, "Car2Go", 45.0671772, 7.6639721, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0.0);
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		entityManager.persist(gasStation4);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		GasStationDto dto1 = new GasStationDto(gasStation1.getGasStationId(), "Example1", "Address1", true, false, true, true, false,  true, "Enjoy", 45.5549032, 8.0569401, 0.0, null, 0.0 , 0.0, null, 0.0, null, null, 0.0);
	
		List<GasStationDto> gasStationDtos = new ArrayList<GasStationDto>();
		gasStationDtos.add(dto1);

		List<GasStationDto> results = gasStationServiceimpl.getGasStationsWithCoordinates(45.551897, 8.0477697, 1, "Diesel", "Enjoy");
		assertTrue(results.equals(gasStationDtos));
	}
	
	@Test
	public void testGetGasStationsWithCoordinatesNullList() throws InvalidGasTypeException, GPSDataException, InvalidCarSharingException {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "null", 45.5549032, 8.0569401, 0.0, null, 0.0 , 0.0, null, 0.0, null, null, 0.0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, true, "Car2Go", 45.5549100, 8.0569403, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0.0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, "null", 45.5549090, 8.0569411, null, null, null, 0.0, 0.0, 0.0, null, null, 0.0);
		GasStation gasStation4 = new GasStation("Example4", "Address4", true, true, false, true, false, true, "Car2Go", 45.0671772, 7.6639721, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0.0);
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		entityManager.persist(gasStation4);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		//There are no gas stations in the area that have Diesel and car sharing enjoy -> result list should be empty
		List<GasStationDto> results = gasStationServiceimpl.getGasStationsWithCoordinates(45.551897, 8.0477697, 1, "Diesel", "Enjoy");
		assertTrue(results.size() == 0);
	}
	
	@Test
	public void testGetGasStationsWithCoordinatesOnlyLatLon() throws InvalidGasTypeException, GPSDataException, InvalidCarSharingException {
		
		//GasStation nel raggio di un km (distance = 0.78) 
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.5549032, 8.0569401, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0.0);
		//GasStation outside radius of search (distance = 61.69)
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, true, "Car2Go", 45.0671772, 7.6639721, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0.0);
		//GasStation in square, but not in radius (distance = 1.36)
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, "null", 45.5549090, 8.0569411, null, null, null, 0.0, 0.0, 0.0, null, null, 0.0);
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		GasStationDto dto1 = new GasStationDto(gasStation1.getGasStationId(), "Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.5549032, 8.0569401, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0.0);
		GasStationDto dto3 = new GasStationDto(gasStation3.getGasStationId(), "Example3", "Address3", false, false, false, true, true,  true, "null", 45.5549090, 8.0569411, null, null, null, 0.0, 0.0, 0.0, null, null, 0.0);
		
		//In the list of results I expect to find the gas stations in 1 km of radius (so I exclude gasStation2) with no particular requests in terms of carsharing or fuel type
		List<GasStationDto> gasStationDtos = new ArrayList<GasStationDto>();
		gasStationDtos.add(dto1);
		gasStationDtos.add(dto3);

		List<GasStationDto> results = gasStationServiceimpl.getGasStationsWithCoordinates(45.551897, 8.0477697, 1, "null", "null");

		assertTrue(results.equals(gasStationDtos));
	}
	
	@Test
	public void testGetGasStationsWithCoordinatesNoGasType() throws InvalidGasTypeException, GPSDataException, InvalidCarSharingException {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.5549032, 8.0569401, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, true, "Enjoy", 45.5549100, 8.0569403, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, "null", 45.5549090, 8.0569411, null, null, null, 0.0, 0.0, 0.0, null, null, 0.0);
		GasStation gasStation4 = new GasStation("Example4", "Address4", true, true, false, true, false, true, "Enjoy", 45.0671772, 7.6639721, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0.0);
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		entityManager.persist(gasStation4);

		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		//I won't create a dto for gasStation3 as it does not have Enjoy car sharing (that we are using in this test) and so it should not appear in results
		//I won't create a dto for gasStation4 as it outside the 3 km radius of search that we will use
		GasStationDto dto1 = new GasStationDto(gasStation1.getGasStationId(), "Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.5549032, 8.0569401, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0);
		GasStationDto dto2 = new GasStationDto(gasStation2.getGasStationId(), "Example2", "Address2", true, true, false, true, false,true, "Enjoy", 45.5549100, 8.0569403, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0);
		
		List<GasStationDto> gasStationDtos = new ArrayList<GasStationDto>();
		gasStationDtos.add(dto1);
		gasStationDtos.add(dto2);
		
		//We perform a search with radius 3 km (so we also verify that this feature works)
		List<GasStationDto> results = gasStationServiceimpl.getGasStationsWithCoordinates(45.551897, 8.0477697, 3, "null", "Enjoy");
		
		assertTrue(results.equals(gasStationDtos));
	}
	
	@Test
	public void testGetGasStationsWithCoordinatesNoCarSharing() throws InvalidGasTypeException, GPSDataException, InvalidCarSharingException {
		
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.5549032, 8.0569401, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, true, "Car2Go", 45.5549100, 8.0569403, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, "null", 45.5549090, 8.0569411, null, null, null, 0.0, 0.0, 0.0, null, null, 0.0);
		GasStation gasStation4 = new GasStation("Example4", "Address4", true, true, false, true, false, true, "Enjoy", 45.0671772, 7.6639721, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0.0);
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		entityManager.persist(gasStation4);

		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		//I won't create a dto for gasStation3 as it does not have Diesel fuel type
		//I won't create a dto for gasStation4 as it outside the 3 km radius of search that we will use
		GasStationDto dto1 = new GasStationDto(gasStation1.getGasStationId(), "Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.5549032, 8.0569401, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0);
		GasStationDto dto2 = new GasStationDto(gasStation2.getGasStationId(), "Example2", "Address2", true, true, false, true, false, true, "Car2Go", 45.5549100, 8.0569403, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0);

		List<GasStationDto> gasStationDtos = new ArrayList<GasStationDto>();
		gasStationDtos.add(dto1);
		gasStationDtos.add(dto2);

		List<GasStationDto> results = gasStationServiceimpl.getGasStationsWithCoordinates(45.551897, 8.0477697, 3, "Diesel", "null");

		assertTrue(results.equals(gasStationDtos));
	}
	
	@Test
	public void testGetGasStationsWithCoordinatesInvalidGPS() throws InvalidGasTypeException, GPSDataException {
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		Assertions.assertThrows(GPSDataException.class, () -> {
			gasStationServiceimpl.getGasStationsWithCoordinates(-100.1245, 54.541, 1, "Diesel", "Enjoy");
		});
	}
	
	@Test
	public void testGetGasStationsWithCoordinatesInvalidGasType() throws InvalidGasTypeException, GPSDataException {
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		Assertions.assertThrows(InvalidGasTypeException.class, () -> {
			gasStationServiceimpl.getGasStationsWithCoordinates(45.551897, 8.0477697, 1, "InvalidGasType", "Enjoy");
		});
	}
	
	@Test
	public void testSetReportNew() throws InvalidGasStationException, PriceException, InvalidUserException{
		User user1 = new User("TestName","123","test@ezgas.com",2);
		entityManager.persist(user1);
		User user2= new User("nome2","456","nome2@ezgas.com",1);
		entityManager.persist(user2);
		GasStation gs=new GasStation("test","testAddress",true,true,true,false,false,false,"Enjoy",45,8,0.0,0.0,0.0,null,null,null,null,null,0);	
		entityManager.persist(gs);
		
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(gasStationRepository, userRepository);
		gasStationService.setReport(gs.getGasStationId(), 1.22, 1.76, 1.78, null, null,null, user2.getUserId());
		assert(gs.getDieselPrice()==1.22 && gs.getSuperPrice()==1.76 && gs.getSuperPlusPrice()==1.78 && gs.getReportUser()==user2.getUserId());
	}
	
	@Test
	public void testSetReportOverwrittenUserTrust() throws InvalidGasStationException, PriceException, InvalidUserException{
		User user1 = new User("TestName","123","test@ezgas.com",1);
		entityManager.persist(user1);
		User user2= new User("nome2","456","nome2@ezgas.com",2);
		entityManager.persist(user2);
		GasStation gs=new GasStation("test","testAddress",true,true,true,false,false,false,"Enjoy",45,8,0.0,0.0,0.0,null,null,null,user1.getUserId(),new SimpleDateFormat("MM-dd-yyyy").format(new java.util.Date()),25.0);	
		gs.setUser(user1);
		entityManager.persist(gs);
		
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(gasStationRepository, userRepository);
		gasStationService.setReport(gs.getGasStationId(), 1.22, 1.76, 1.78, null, null,null, user2.getUserId());
		assert(gs.getDieselPrice()==1.22 && gs.getSuperPrice()==1.76 && gs.getSuperPlusPrice()==1.78 && gs.getReportUser()==user2.getUserId());
	}
	
	@Test
	public void testSetReportOverwrittenTimetag() throws InvalidGasStationException, PriceException, InvalidUserException{
		User user1 = new User("TestName","123","test@ezgas.com",3);
		entityManager.persist(user1);
		User user2= new User("nome2","456","nome2@ezgas.com",1);
		entityManager.persist(user2);
		GasStation gs=new GasStation("test","testAddress",true,true,true,false,false,false,"Enjoy",45,8,0.0,0.0,0.0,null,null,null,user1.getUserId(),"06-11-2020",25.0);	
		gs.setUser(user1);	
		entityManager.persist(gs);
		
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(gasStationRepository, userRepository);
		gasStationService.setReport(gs.getGasStationId(), 1.22, 1.76, 1.78, null, null,null, user2.getUserId());
		assert(gs.getDieselPrice()==1.22 && gs.getSuperPrice()==1.76 && gs.getSuperPlusPrice()==1.78 && gs.getReportUser()==user2.getUserId());
	}
	
	@Test
	public void testSetReportPriceException() throws InvalidGasStationException, PriceException, InvalidUserException {
		GasStationServiceimpl gasStationService=new GasStationServiceimpl(null, null);
		Assertions.assertThrows(PriceException.class,()->{gasStationService.setReport(1, -2.0, 1.0, 1.0, 1.0, 1.0,1.0, 1);});
	}
	
	@Test
	public void testSetReportInvalidUserException() throws InvalidGasStationException, PriceException, InvalidUserException {
		GasStationServiceimpl gasStationService=new GasStationServiceimpl(null, null);
		Assertions.assertThrows(InvalidUserException.class,()->{gasStationService.setReport(1, 1.0,1.0, 1.0, 1.0, 1.0, 1.0, -1);});
	}
	
	@Test
	public void testSetReportInvalidGasStationException() throws InvalidGasStationException, PriceException, InvalidUserException {
		GasStationServiceimpl gasStationService=new GasStationServiceimpl(null, null);
		Assertions.assertThrows(InvalidGasStationException.class,()->{gasStationService.setReport(-1, 1.0, 1.0, 1.0, 1.0,1.0, 1.0, 1);});
	}
	
	@Test
	public void testUpdateDependabilitiesReportUserNull(){
		ArrayList<GasStation> gsList=new ArrayList<GasStation>();
		GasStation gs=new GasStation();
		gs.setReportUser(null);
		gsList.add(gs);
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(null, userRepository);
		gasStationService.updateDependabilities(gsList);
		assert(gs.getReportDependability()==0);
	}
	
	@Test
	public void testUpdateDependabilitiesObsolescencePositive(){
		User user = new User("TestName","123","test@ezgas.com",3);
		entityManager.persist(user);
		 
		ArrayList<GasStation> gsList=new ArrayList<GasStation>();
		GasStation gs=new GasStation("test","testAddress",true,true,true,false,false,false,"Enjoy",4.32,7.89,1.0,1.0,1.0,null,null,null,user.getUserId(),new SimpleDateFormat("MM-dd-yyyy").format(new java.util.Date()),25.0);
		gs.setUser(user);
		gsList.add(gs);
				
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(gasStationRepository, userRepository);
		gasStationService.updateDependabilities(gsList);
		assert(gs.getReportDependability()==90);
	}
	
	@Test
	public void testGetGasStationByCarSharingNotNull() {
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		//Set CarSharing type
		String carSharing= "Enjoy";
		
		//I will set ReportUser == null as to not invoke updateUserDependabilities, as I am not concerned in the outcome of that function 
		GasStation gasStation = new GasStation("Q8", "Via Fratelli Rosselli 102", true, true, true, true, true, true, carSharing, 45.5549032, 8.04, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);
		entityManager.persist(gasStation);
		
		List<GasStationDto> output = new ArrayList<GasStationDto>();
		GasStationDto gasStationDto = new GasStationDto(gasStation.getGasStationId(),"Q8", "Via Fratelli Rosselli 102", true, true, true, true, true, true, carSharing, 45.5549032, 8.04, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);
		output.add(gasStationDto);
		
		List<GasStationDto> result = gasStationServiceimpl.getGasStationByCarSharing(carSharing);

		assert(output.equals(result));
	}
	
	@Test
	public void testGetGasStationByCarSharingNull() {
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.5549032, 8.0569401, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0.0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, true, "Car2Go", 45.5549100, 8.0569403, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0.0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, "null", 45.5549090, 8.0569411, null, null, null, 0.0, 0.0, 0.0, null, null, 0.0);
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		GasStationDto dto1 = new GasStationDto(gasStation1.getGasStationId(), "Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.5549032, 8.0569401, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0.0);
		GasStationDto dto2 = new GasStationDto(gasStation2.getGasStationId(), "Example2", "Address2", true, true, false, true, false, true, "Car2Go", 45.5549100, 8.0569403, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0.0);
		GasStationDto dto3 = new GasStationDto(gasStation3.getGasStationId(), "Example3", "Address3", false, false, false, true, true, true, "null", 45.5549090, 8.0569411, null, null, null, 0.0, 0.0, 0.0, null, null, 0.0);

		List<GasStationDto> gasStationDtos = new ArrayList<GasStationDto>();
		gasStationDtos.add(dto1);
		gasStationDtos.add(dto2);
		gasStationDtos.add(dto3);
		
		List<GasStationDto> results = gasStationServiceimpl.getGasStationByCarSharing("null");

		assert(gasStationDtos.equals(results));
	}
	
	@Test
	public void testGetGasStationByCarSharingIsEmpty() {
		
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.5549032, 8.0569401, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0.0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, true, "Enjoy", 45.5549100, 8.0569403, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0.0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, "null", 45.5549090, 8.0569411, null, null, null, 0.0, 0.0, 0.0, null, null, 0.0);
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		String carSharing= "Car2Go"; //there are no gasStations with car sharing Car2Go so list of results should be empty
		
		List<GasStationDto> result = gasStationServiceimpl.getGasStationByCarSharing(carSharing);
		List<GasStationDto> expected = new ArrayList<GasStationDto>();
		assert(expected.equals(result));
	}
	
	@Test
	public void testInvalidCarSharing() {
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		Assertions.assertThrows(InvalidCarSharingException.class, () -> {
			gasStationServiceimpl.getGasStationsWithoutCoordinates("Diesel", "InvalidCarSharing");
		});
	}
}
