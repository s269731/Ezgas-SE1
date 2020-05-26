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
		
		GasStation gasStation = new GasStation("ExpressGas", "Via Galimberti 6", true, true, true, true, true, "Enjoy", 45, 8, 1.1, 1.2, 1.3, 1.4, 1.5, 5, "2020/05/17-18:30:17", 3.3);
		entityManager.persist(gasStation);
		
		GasStationDto gasStationDto = new GasStationDto(gasStation.getGasStationId(), "ExpressGas", "Via Galimberti 6", true, true, true, true, true, "Enjoy", 45, 8, 1.1, 1.2, 1.3, 1.4, 1.5, 5, "2020/05/17-18:30:17", 3.3);	
		
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
	public void testSaveGasStationNew() throws PriceException, GPSDataException{ 
		GasStation gs=new GasStation("test","testAddressGs",false,false,false,false,false,"enjoy",4.32,7.89,5.0,5.0,5.0,5.0,5.0,0,"",0.0);
		entityManager.persist(gs);
		//equal coordinates but different address 
		GasStationDto gsDtoIn=new GasStationDto(null,"test","testAddress",true,true,true,true,true,"enjoy",4.32,7.89,1.0,3.0,2.0,4.0,5.0,0,"2020/05/26-09:00",4.0);

	    GasStationDto gsDtoOut=new GasStationDto(gs.getGasStationId()+1,"test","testAddress",false,false,false,false,false,"enjoy",4.32,7.89,5.0,5.0,5.0,5.0,5.0,0,"",0.0);
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(gasStationRepository, null);
		GasStationDto result= gasStationService.saveGasStation(gsDtoIn);
		assert(result.equals(gsDtoOut));
	}
	
	@Test
	public void testSaveGasStationNewReturnNull() throws PriceException, GPSDataException{ 
		GasStation gs=new GasStation("test","testAddressGs",false,false,false,false,false,"enjoy",4.32,7.89,5.0,5.0,5.0,5.0,5.0,0,"",0.0);
		entityManager.persist(gs);
		//equal coordinates and address 
		GasStationDto gsDtoIn=new GasStationDto(null,"test","testAddressGs",true,true,true,true,true,"enjoy",4.32,7.89,1.0,3.0,2.0,4.0,5.0,0,"2020/05/26-09:00",4.0);

		GasStationServiceimpl gasStationService = new GasStationServiceimpl(gasStationRepository, null);
		Assertions.assertNull(gasStationService.saveGasStation(gsDtoIn));
	}
	
	@Test
	public void testSaveGasStationUpdate() throws PriceException, GPSDataException {
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);

		//GasStation nel db con stesso Id
		GasStation gasStation1 = new GasStation("EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true, "Enjoy", 45.7895214, 8.0568740, 1.1, 1.2, 1.3, 1.4, 1.5, 5, "2020/05/17-18:30:17", 3.3);	
		GasStation gasStation2 = new GasStation("Starita", "Via Monti 3 Biella", true, true, true, true, true, "Car2Go", 45.5549032, 8.0569401, 1.1, 1.2, 1.3, 1.4, 1.5, 5, "2020/05/17-18:30:17", 3.3);	
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		
		//GasStation di input con nuovi dati (che sarà anche la gasStation che mi aspetto ritornata)
		GasStationDto gasStationDtoIn = new GasStationDto(gasStation2.getGasStationId(), "Starita", "Via Croce 3 Biella", true, true, true, true, true, "Enjoy", 45.5549040, 8.0569440, 1.1, 1.2, 1.3, 1.4, 1.5, 5, "2020/05/17-18:30:17", 3.3);	
		
		GasStationDto result = gasStationServiceimpl.saveGasStation(gasStationDtoIn);

		assert(result.equals(gasStationDtoIn));
	
	}
	
	@Test
	public void testSaveGasStationUpdateReturnNull() throws PriceException, GPSDataException {
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);

		//GasStation nel db con stesso Id
		GasStation gasStation1 = new GasStation("EnerCoop", "Via Macchieraldo 2 Biella", false, true, false, true, true, "null", 45.7895214, 8.0568740, 1.1, 1.2, 1.3, 1.4, 1.5, 5, "2020/05/17-18:30:17", 3.3);	
		GasStation gasStation2 = new GasStation("Starita", "Via Monti 3 Biella", true, true, true, true, true, "Car2Go", 45.5549032, 8.0569401, 1.1, 1.2, 1.3, 1.4, 1.5, 5, "2020/05/17-18:30:17", 3.3);	
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		
		//GasStation di input con nuovi dati (GasStation already found in the db with same address/&lat&lon)
		GasStationDto gasStationDtoIn = new GasStationDto(gasStation2.getGasStationId(), "Starita", "Via Macchieraldo 2 Biella", true, true, true, true, true, "Enjoy", 45.7895214, 8.0568740, 1.1, 1.2, 1.3, 1.4, 1.5, 5, "2020/05/17-18:30:17", 3.3);	
		
		GasStationDto result = gasStationServiceimpl.saveGasStation(gasStationDtoIn);

		assert(result == null);
		
	}
	
	@Test
	public void testSaveGasStationInvalidLat()  {
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		GasStationDto gasStationDto = new GasStationDto( null, "EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true, "Enjoy", -100, 8.0569401, 1.1, 1.2, 1.3, 1.4, 1.5, 5, "2020/05/17-18:30:17", 3.3);	
		Assertions.assertThrows(GPSDataException.class, () -> { gasStationServiceimpl.saveGasStation(gasStationDto);
		} ) ;
		
	}
	
	@Test
	public void testSaveGasStationInvalidLon()  {
		GasStation gasStation1 = new GasStation("EnerCoop", "Via Macchieraldo 2 Biella", false, true, false, true, true, "null", 45.7895214, 8.0568740, 1.1, 1.2, 1.3, 1.4, 1.5, 5, "2020/05/17-18:30:17", 3.3);	
		GasStation gasStation2 = new GasStation("Starita", "Via Monti 3 Biella", true, true, true, true, true, "Car2Go", 45.5549032, 8.0569401, 1.1, 1.2, 1.3, 1.4, 1.5, 5, "2020/05/17-18:30:17", 3.3);	
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);

		GasStationDto gasStationDto = new GasStationDto(gasStation1.getGasStationId(), "EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true, "Enjoy", 45, 200, 1.1, 1.2, 1.3, 1.4, 1.5, 5, "2020/05/17-18:30:17", 3.3);	
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
		GasStation gs1 = new GasStation("Example1", "Address1", true, false, true, true, false, "Enjoy", 45.55, 8.05, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gs2 = new GasStation("Example2", "Address2", true, true, false, true, false, "Car2Go", 45.65, 8.25, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gs3 = new GasStation("Example3", "Address3", false, false, false, true, true, null, 45.05, 8.15, 0, 0, 0.0, 0, 0, null, "null", 0);
		
		entityManager.persist(gs1);
		entityManager.persist(gs2);
		entityManager.persist(gs3);
				
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(gasStationRepository, null);
		List<GasStationDto> result=gasStationService.getAllGasStations();
		assert(result.size()==3);
	}
	
  
	
	@Test
	public void testDeleteUnregisteredGasStation() throws InvalidGasStationException{ 
		GasStation gs1 = new GasStation("Example1", "Address1", true, false, true, true, false, "Enjoy", 45.55, 8.05, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gs2 = new GasStation("Example2", "Address2", true, true, false, true, false, "Car2Go", 45.65, 8.25, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gs3 = new GasStation("Example3", "Address3", false, false, false, true, true, null, 45.05, 8.15, 0, 0, 0.0, 0, 0, null, "null", 0);
		
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
		GasStation gs1 = new GasStation("Example1", "Address1", true, false, true, true, false, "Enjoy", 45.55, 8.05, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gs2 = new GasStation("Example2", "Address2", true, true, false, true, false, "Car2Go", 45.65, 8.25, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gs3 = new GasStation("Example3", "Address3", false, false, false, true, true, null, 45.05, 8.15, 0, 0, 0.0, 0, 0, null, "null", 0);
		
		entityManager.persist(gs1);
		entityManager.persist(gs2);
		entityManager.persist(gs3);
		
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(gasStationRepository, null);
		
		boolean deleted = gasStationService.deleteGasStation(gs2.getGasStationId());
		Assertions.assertTrue(deleted);
	}
	
	@Test
	public void testGetGasStationsByGasolineType() throws InvalidGasTypeException {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, "Enjoy", 45.55, 8.05, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, "Car2Go", 45.65, 8.25, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, true, true, true, false, "Car2Go", 45.65, 8.25, 0, 0, 0.0, 0, 0, null, "null", 0);
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		GasStationDto gasStationDto1 = new GasStationDto(gasStation1.getGasStationId(), "Example1", "Address1", true, false, true, true, false, "Enjoy", 45.55, 8.05, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStationDto gasStationDto2 = new GasStationDto(gasStation2.getGasStationId(), "Example2", "Address2", true, true, false, true, false, "Car2Go", 45.55, 8.05, 0, 0, 0.0, 0, 0, null, "null", 0);
			
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
		GasStation gasStation1 = new GasStation("Example1", "Address1", false, false, true, true, false, "Enjoy", 45.55, 8.05, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", false, true, false, true, false, "Car2Go", 45.65, 8.25, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, true, true, true, false, "Car2Go", 45.65, 8.25, 0, 0, 0.0, 0, 0, null, "null", 0);
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
		
		Assertions.assertThrows(GPSDataException.class, () -> { gasStationServiceimpl.getGasStationsByProximity(-100, 0);
			} );
	}
	
	@Test
	public void testGetGasStationByProximityLonException() {
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		Assertions.assertThrows(GPSDataException.class, () -> { gasStationServiceimpl.getGasStationsByProximity(0, 200);
			} );
	}
	
	@Test
	public void testGetGasStationByProximityRadius() throws GPSDataException {
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		//Set lat, lon per Canterino
		double lat=45.551897;
		double lon=8.0477697;
		
		//GasStation nel raggio (distance = 0.78) 
		GasStation gs1 = new GasStation("EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true, "Enjoy", 45.5549032, 8.0569401, 1.1, 1.2, 1.3, 1.4, 1.5, null, "2020/05/17-18:30:17", 3.3);
		entityManager.persist(gs1);
		//GasStation in square, but not in radius (distance = 1.36)
		GasStation gs2 =new GasStation("Esso", "Viale Roma Biella", true, true, true, true, true, "Car2Go",45.5560598,8.0642756, 1.1, 1.2, 1.3, 1.4, 1.5, null, "2020/05/18-18:30:17", 3.3);
		entityManager.persist(gs2);
		//GasStation outside alltogether (distance = 61.69)
		GasStation gs3 =new GasStation("Eni", "Via Magenta 52 Torino", true, true, true, true, true, "null", 45.0671772,7.6639721, 1.1, 1.2, 1.3, 1.4, 1.5, null, "2020/05/19-19:30:17", 3.3);
		entityManager.persist(gs3);

		//Create gasStationDto I need
		GasStationDto gsDto1 = new GasStationDto(gs1.getGasStationId(),"EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true, "Enjoy", 45.5549032, 8.0569401, 1.1, 1.2, 1.3, 1.4, 1.5, null, "2020/05/17-18:30:17", 3.3);
	
		//Create List of correct results
		List<GasStationDto> gasStationsDto = new ArrayList<GasStationDto>();
		gasStationsDto.add(gsDto1);
		
		/*
		 * Note that in all gasStations useReport was set to null, so as to avoid having to pass though updateDependabilities, which we have already tested.
		 * 
		 */
		
		List<GasStationDto> result = gasStationServiceimpl.getGasStationsByProximity(lat, lon);

		assert(result.equals(gasStationsDto));
	}
	
	@Test
	public void testGetGasStationsWithoutCoordinates() throws InvalidGasTypeException {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, "Enjoy", 45.55, 8.05, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, "Car2Go", 45.65, 8.25, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, "null", 45.5549090, 8.0569411, 0, 0, 0.0, 0, 0, null, "null", 0);
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		List<GasStationDto> gasStationDtos = new ArrayList<GasStationDto>();
		GasStationDto gasStationDto1 = new GasStationDto(gasStation1.getGasStationId(), "Example1", "Address1", true, false, true, true, false, "Enjoy", 45.55, 8.05, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStationDto gasStationDto2 = new GasStationDto(gasStation2.getGasStationId(), "Example2", "Address2", true, true, false, true, false, "Car2Go", 45.55, 8.05, 0, 0, 0.0, 0, 0, null, "null", 0);
		gasStationDtos.add(gasStationDto1);
		gasStationDtos.add(gasStationDto2);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		List<GasStationDto> results = gasStationServiceimpl.getGasStationsWithoutCoordinates("Diesel", "Enjoy");
		gasStationDtos.remove(gasStationDtos.size() - 1);
		assertTrue(results.equals(gasStationDtos));
	}
	
	@Test
	public void testGetGasStationsWithoutCoordinatesInvalidGasType() throws InvalidGasTypeException {
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		Assertions.assertThrows(InvalidGasTypeException.class, () -> {
			gasStationServiceimpl.getGasStationsWithoutCoordinates("LPG", "Enjoy");
		});
	}
	
	@Test
	public void testGetGasStationsWithoutCoordinatesNullList() throws InvalidGasTypeException {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, "Enjoy", 45.55, 8.05, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, "Enjoy", 45.65, 8.25, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, "null", 45.5549090, 8.0569411, 0, 0, 0.0, 0, 0, null, "null", 0);
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		GasStationDto gasStationDto1 = new GasStationDto(gasStation1.getGasStationId(), "Example1", "Address1", true, false, true, true, false, "Enjoy", 45.55, 8.05, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStationDto gasStationDto2 = new GasStationDto(gasStation2.getGasStationId(), "Example2", "Address2", true, true, false, true, false, "Enjoy", 45.55, 8.05, 0, 0, 0.0, 0, 0, null, "null", 0);
		
		List<GasStationDto> gasStationDtos = new ArrayList<GasStationDto>();
		gasStationDtos.add(gasStationDto1);
		gasStationDtos.add(gasStationDto2);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		List<GasStationDto> results = gasStationServiceimpl.getGasStationsWithoutCoordinates("Diesel", "Car2Go");
		assertTrue(results.size() == 0);
	}
	
	@Test
	public void testGetGasStationsWithCoordinates() throws InvalidGasTypeException, GPSDataException {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, "Enjoy", 45.5549032, 8.0569401, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, "Car2Go", 45.5549100, 8.0569403, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, "null", 45.5549090, 8.0569411, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation4 = new GasStation("Example4", "Address4", true, true, false, true, false, "Car2Go", 45.0671772, 7.6639721, 0, 0, 0.0, 0, 0, null, "null", 0);
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		entityManager.persist(gasStation4);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		GasStationDto dto1 = new GasStationDto(gasStation1.getGasStationId(), "Example1", "Address1", true, false, true, true, false, "Enjoy", 45.5549032, 8.0569401, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStationDto dto2 = new GasStationDto(gasStation2.getGasStationId(), "Example2", "Address2", true, true, false, true, false, "Car2Go", 45.5549100, 8.0569403, 0, 0, 0.0, 0, 0, null, "null", 0);
		//GasStationDto dto3 = new GasStationDto(gasStation3.getGasStationId(), "Example3", "Address3", false, false, false, true, true, null, 45.5549090, 8.0569411, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStationDto dto4 = new GasStationDto(gasStation4.getGasStationId(), "Example4", "Address4", true, true, false, true, false, "Car2Go", 45.0671772, 7.6639721, 0, 0, 0.0, 0, 0, null, "null", 0);
		
		List<GasStationDto> gasStationDtos = new ArrayList<GasStationDto>();
		gasStationDtos.add(dto1);
		gasStationDtos.add(dto2);
		gasStationDtos.add(dto4);

		List<GasStationDto> results = gasStationServiceimpl.getGasStationsWithCoordinates(45.551897, 8.0477697, "Diesel", "Enjoy");
		gasStationDtos.remove(gasStationDtos.size() - 1);
		gasStationDtos.remove(gasStationDtos.size() - 1);
		
		assertTrue(results.equals(gasStationDtos));
	}
	
	@Test
	public void testGetGasStationsWithCoordinatesNullList() throws InvalidGasTypeException, GPSDataException {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, "Car2Go", 45.5549032, 8.0569401, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, "Car2Go", 45.5549100, 8.0569403, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, "null", 45.5549090, 8.0569411, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation4 = new GasStation("Example4", "Address4", true, true, false, true, false, "Car2Go", 45.0671772, 7.6639721, 0, 0, 0.0, 0, 0, null, "null", 0);
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		entityManager.persist(gasStation4);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		GasStationDto dto1 = new GasStationDto(gasStation1.getGasStationId(), "Example1", "Address1", true, false, true, true, false, "Car2Go", 45.5549032, 8.0569401, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStationDto dto2 = new GasStationDto(gasStation2.getGasStationId(), "Example2", "Address2", true, true, false, true, false, "Car2Go", 45.5549100, 8.0569403, 0, 0, 0.0, 0, 0, null, "null", 0);
		//GasStationDto dto3 = new GasStationDto(gasStation3.getGasStationId(), "Example3", "Address3", false, false, false, true, true, null, 45.5549090, 8.0569411, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStationDto dto4 = new GasStationDto(gasStation4.getGasStationId(), "Example4", "Address4", true, true, false, true, false, "Car2Go", 45.0671772, 7.6639721, 0, 0, 0.0, 0, 0, null, "null", 0);
		
		List<GasStationDto> gasStationDtos = new ArrayList<GasStationDto>();
		gasStationDtos.add(dto1);
		gasStationDtos.add(dto2);
		gasStationDtos.add(dto4);

		List<GasStationDto> results = gasStationServiceimpl.getGasStationsWithCoordinates(45.551897, 8.0477697, "Diesel", "Enjoy");
		
		assertTrue(results.size() == 0);
	}
	
	@Test
	public void testGetGasStationsWithCoordinatesOnlyLatLon() throws InvalidGasTypeException, GPSDataException {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, "Enjoy", 45.5549032, 8.0569401, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, "Car2Go", 45.0671772, 7.6639721, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, "null", 45.5549090, 8.0569411, 0, 0, 0.0, 0, 0, null, "null", 0);
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		GasStationDto dto1 = new GasStationDto(gasStation1.getGasStationId(), "Example1", "Address1", true, false, true, true, false, "Enjoy", 45.5549032, 8.0569401, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStationDto dto3 = new GasStationDto(gasStation3.getGasStationId(), "Example3", "Address3", false, false, false, true, true, "null", 45.5549090, 8.0569411, 0, 0, 0.0, 0, 0, null, "null", 0);
		
		List<GasStationDto> gasStationDtos = new ArrayList<GasStationDto>();
		gasStationDtos.add(dto1);
		gasStationDtos.add(dto3);

		List<GasStationDto> results = gasStationServiceimpl.getGasStationsWithCoordinates(45.551897, 8.0477697, "null", "null");

		assertTrue(results.equals(gasStationDtos));
	}
	
	@Test
	public void testGetGasStationsWithCoordinatesNoGasType() throws InvalidGasTypeException, GPSDataException {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, "Enjoy", 45.5549032, 8.0569401, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, "Enjoy", 45.5549100, 8.0569403, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, "null", 45.5549090, 8.0569411, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation4 = new GasStation("Example4", "Address4", true, true, false, true, false, "Enjoy", 45.0671772, 7.6639721, 0, 0, 0.0, 0, 0, null, "null", 0);
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		entityManager.persist(gasStation4);

		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		GasStationDto dto1 = new GasStationDto(gasStation1.getGasStationId(), "Example1", "Address1", true, false, true, true, false, "Enjoy", 45.5549032, 8.0569401, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStationDto dto2 = new GasStationDto(gasStation2.getGasStationId(), "Example2", "Address2", true, true, false, true, false, "Car2Go", 45.5549100, 8.0569403, 0, 0, 0.0, 0, 0, null, "null", 0);
		//GasStationDto dto3 = new GasStationDto(gasStation3.getGasStationId(), "Example3", "Address3", false, false, false, true, true, null, 45.5549090, 8.0569411, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStationDto dto4 = new GasStationDto(gasStation4.getGasStationId(), "Example4", "Address4", true, true, false, true, false, "Enjoy", 45.0671772, 7.6639721, 0, 0, 0.0, 0, 0, null, "null", 0);

		List<GasStationDto> gasStationDtos = new ArrayList<GasStationDto>();
		gasStationDtos.add(dto1);
		gasStationDtos.add(dto2);
		gasStationDtos.add(dto4);

		List<GasStationDto> results = gasStationServiceimpl.getGasStationsWithCoordinates(45.551897, 8.0477697, "null", "Enjoy");
		gasStationDtos.remove(gasStationDtos.size() - 1);
		
		assertTrue(results.equals(gasStationDtos));
	}
	
	@Test
	public void testGetGasStationsWithCoordinatesNoCarSharing() throws InvalidGasTypeException, GPSDataException {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, "Enjoy", 45.5549032, 8.0569401, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, "Car2Go", 45.5549100, 8.0569403, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, "null", 45.5549090, 8.0569411, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation4 = new GasStation("Example4", "Address4", false, true, false, true, false, "Enjoy", 45.0671772, 7.6639721, 0, 0, 0.0, 0, 0, null, "null", 0);
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		entityManager.persist(gasStation4);

		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		GasStationDto dto1 = new GasStationDto(gasStation1.getGasStationId(), "Example1", "Address1", true, false, true, true, false, "Enjoy", 45.5549032, 8.0569401, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStationDto dto2 = new GasStationDto(gasStation2.getGasStationId(), "Example2", "Address2", true, true, false, true, false, "Car2Go", 45.5549100, 8.0569403, 0, 0, 0.0, 0, 0, null, "null", 0);
		//GasStationDto dto3 = new GasStationDto(gasStation3.getGasStationId(), "Example3", "Address3", false, false, false, true, true, null, 45.5549090, 8.0569411, 0, 0, 0.0, 0, 0, null, "null", 0);
		//GasStationDto dto4 = new GasStationDto(gasStation4.getGasStationId(), "Example4", "Address4", false, true, false, true, false, "Enjoy", 45.0671772, 7.6639721, 0, 0, 0.0, 0, 0, null, "null", 0);

		List<GasStationDto> gasStationDtos = new ArrayList<GasStationDto>();
		gasStationDtos.add(dto1);
		gasStationDtos.add(dto2);

		List<GasStationDto> results = gasStationServiceimpl.getGasStationsWithCoordinates(45.551897, 8.0477697, "Diesel", "null");
		
		assertTrue(results.equals(gasStationDtos));
	}
	
	@Test
	public void testGetGasStationsWithCoordinatesInvalidGPS() throws InvalidGasTypeException, GPSDataException {
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		Assertions.assertThrows(GPSDataException.class, () -> {
			gasStationServiceimpl.getGasStationsWithCoordinates(-100.1245, 54.541, "Diesel", "Enjoy");
		});
	}
	
	@Test
	public void testGetGasStationsWithCoordinatesInvalidGasType() throws InvalidGasTypeException, GPSDataException {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, "Enjoy", 45.5549032, 8.0569401, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, "Car2Go", 45.0671772, 7.6639721, 0, 0, 0.0, 0, 0, null, "null", 0);
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		Assertions.assertThrows(InvalidGasTypeException.class, () -> {
			gasStationServiceimpl.getGasStationsWithCoordinates(45.551897, 8.0477697, "LPG", "Enjoy");
		});
	}
	
	@Test
	public void testSetReportNew() throws InvalidGasStationException, PriceException, InvalidUserException{
		User user1 = new User("TestName","123","test@ezgas.com",3);
		entityManager.persist(user1);
		User user2= new User("nome2","456","nome2@ezgas.com",2);
		entityManager.persist(user2);
		GasStation gs=new GasStation("test","testAddress",true,true,true,false,false,"enjoy",0.0,0.0,0.0,0.0,0.0,0.0,0.0,null,new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss").format(new java.util.Date()),25.0);	
		entityManager.persist(gs);
		
		
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(gasStationRepository, userRepository);
		gasStationService.setReport(gs.getGasStationId(), 1.22, 1.76, 1.78, 0.1, 0.1, user2.getUserId());
		assert(gs.getDieselPrice()==1.22 && gs.getSuperPrice()==1.76 && gs.getSuperPlusPrice()==1.78 && gs.getReportUser()==user2.getUserId());
	}
	
	@Test
	public void testSetReportOverwritten() throws InvalidGasStationException, PriceException, InvalidUserException{
		User user1 = new User("TestName","123","test@ezgas.com",3);
		entityManager.persist(user1);
		User user2= new User("nome2","456","nome2@ezgas.com",2);
		entityManager.persist(user2);
		GasStation gs=new GasStation("test","testAddress",true,true,true,false,false,"enjoy",4.32,7.89,1.0,1.0,1.0,0.0,0.0,user1.getUserId(),new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss").format(new java.util.Date()),25.0);	
		entityManager.persist(gs);
		
		
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(gasStationRepository, userRepository);
		gasStationService.setReport(gs.getGasStationId(), 1.22, 1.76, 1.78, 0.1, 0.1, user2.getUserId());
		assert(gs.getDieselPrice()==1.22 && gs.getSuperPrice()==1.76 && gs.getSuperPlusPrice()==1.78 && gs.getReportUser()==user2.getUserId());
	}
	
	@Test
	public void testSetReportPriceException() throws InvalidGasStationException, PriceException, InvalidUserException {
		GasStationServiceimpl gasStationService=new GasStationServiceimpl(null, null);
		Assertions.assertThrows(PriceException.class,()->{gasStationService.setReport(1, -2.0, 1.0, 1.0, 1.0, 1.0, 1);});
	}
	
	@Test
	public void testSetReportInvalidUserException() throws InvalidGasStationException, PriceException, InvalidUserException {
		GasStationServiceimpl gasStationService=new GasStationServiceimpl(null, null);
		Assertions.assertThrows(InvalidUserException.class,()->{gasStationService.setReport(1, -1.0, 1.0, -1.0, 1.0, 1.0, -1);});
	}
	
	@Test
	public void testSetReportInvalidGasStationException() throws InvalidGasStationException, PriceException, InvalidUserException {
		GasStationServiceimpl gasStationService=new GasStationServiceimpl(null, null);
		Assertions.assertThrows(InvalidGasStationException.class,()->{gasStationService.setReport(-1, 1.0, -1.0, -1.0, 1.0, 1.0, 1);});
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
		GasStation gs=new GasStation("test","testAddress",true,true,true,false,false,"enjoy",4.32,7.89,1.0,1.0,1.0,0.0,0.0,user.getUserId(),new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss").format(new java.util.Date()),25.0);
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
		GasStation gasStation = new GasStation("Q8", "Via Fratelli Rosselli 102", true, true, true, true, true, carSharing, 45.5549032, 8.04, -1, -1, -1, -1, -1, null, null, 0);

		//Save in db
		entityManager.persist(gasStation);
		
		List<GasStationDto> output = new ArrayList<GasStationDto>();
		GasStationDto gasStationDto = new GasStationDto(gasStation.getGasStationId(),"Q8", "Via Fratelli Rosselli 102", true, true, true, true, true, carSharing, 45.5549032, 8.04, -1, -1, -1, -1, -1, null, null, 0);
		output.add(gasStationDto);
		
		List<GasStationDto> result = gasStationServiceimpl.getGasStationByCarSharing(carSharing);

		assert(output.equals(result));
	}
	
	@Test
	public void testGetGasStationByCarSharingNull() {
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, "Enjoy", 45.5549032, 8.0569401, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, "Car2Go", 45.5549100, 8.0569403, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, "null", 45.5549090, 8.0569411, 0, 0, 0.0, 0, 0, null, "null", 0);
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		GasStationDto dto1 = new GasStationDto(gasStation1.getGasStationId(), "Example1", "Address1", true, false, true, true, false, "Enjoy", 45.5549032, 8.0569401, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStationDto dto2 = new GasStationDto(gasStation2.getGasStationId(), "Example2", "Address2", true, true, false, true, false, "Car2Go", 45.5549100, 8.0569403, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStationDto dto3 = new GasStationDto(gasStation3.getGasStationId(), "Example3", "Address3", false, false, false, true, true, "null", 45.5549090, 8.0569411, 0, 0, 0.0, 0, 0, null, "null", 0);

		List<GasStationDto> gasStationDtos = new ArrayList<GasStationDto>();
		gasStationDtos.add(dto1);
		gasStationDtos.add(dto2);
		gasStationDtos.add(dto3);
		
		List<GasStationDto> results = gasStationServiceimpl.getGasStationByCarSharing("null");

		assert(gasStationDtos.equals(results));
	}
	
	@Test
	public void testGetGasStationByCarSharingIsEmpty() {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, "Enjoy", 45.5549032, 8.0569401, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, "Enjoy", 45.5549100, 8.0569403, 0, 0, 0.0, 0, 0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, "null", 45.5549090, 8.0569411, 0, 0, 0.0, 0, 0, null, "null", 0);
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		String carSharing= "Car2Go";
		
		List<GasStationDto> result = gasStationServiceimpl.getGasStationByCarSharing(carSharing);
		List<GasStationDto> expected = new ArrayList<GasStationDto>();
		assert(expected.equals(result));
	}
}
