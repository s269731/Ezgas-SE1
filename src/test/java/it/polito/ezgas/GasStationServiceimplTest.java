package it.polito.ezgas;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

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


public class GasStationServiceimplTest {
	
	@Test
	public void TestGetGasStationByIdNotNull() throws InvalidGasStationException {
		
		GasStationRepository GRmock = mock (GasStationRepository.class);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(GRmock, null);
		
		GasStation gasStation = new GasStation("EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true, true, "Enjoy", 45.5549032, 8.0569401, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0);
		Integer gasStationId = 1;
		gasStation.setGasStationId(gasStationId);
		GasStationDto gasStationDto = new GasStationDto( gasStationId, "EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true,true, "Enjoy", 45.5549032, 8.0569401, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0);	
		
		when(GRmock.findByGasStationId(gasStationId)).thenReturn(gasStation);
		
		GasStationDto result=gasStationServiceimpl.getGasStationById(gasStationId);
		assert(gasStationDto.equals(result)); 
		
	}
	
	@Test
	public void TestGetGasStationByIdNull() throws InvalidGasStationException {
		
			GasStationRepository GRmock = mock (GasStationRepository.class);
			
			GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(GRmock, null);
			
			Integer gasStationId = 1;
			GasStation gasStation = null;
			
			when(GRmock.findByGasStationId(gasStationId)).thenReturn(gasStation);
			
			assert(gasStationServiceimpl.getGasStationById(gasStationId)==null);
		
	}
	
	@Test
	public void TestGetGasStationByIdNeg()  {
		
		GasStationRepository GRmock = mock (GasStationRepository.class);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(GRmock, null);
		
		GasStation gasStation =  new GasStation("EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true, true, "Enjoy", 45.5549032, 8.0569401, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0);
		Integer gasStationId = -1;
		gasStation.setGasStationId(gasStationId);
		
		when(GRmock.findByGasStationId(gasStationId)).thenReturn(gasStation);
	
		Assertions.assertThrows(InvalidGasStationException.class, () -> { gasStationServiceimpl.getGasStationById(gasStationId);
		} );
		
	}
	
	@Test
	public void testSaveGasStationNew() throws PriceException, GPSDataException{ 
		
		GasStationRepository test= mock(GasStationRepository.class);
		when(test.findByGasStationAddressAndLatAndLon(anyString(), anyDouble(), anyDouble())).thenReturn(null);
		
		GasStationDto gasStationDtoIn = new GasStationDto(null, "EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true, true, "Enjoy", 45.5549032, 8.0569401, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);	
		GasStationDto gsDtoOut=new GasStationDto( 1, "EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true, true, "Enjoy", 45.5549032, 8.0569401,  0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);
		GasStation gs=new GasStation("EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true, true, "Enjoy", 45.5549032, 8.0569401, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);
		gs.setGasStationId(1);
		when(test.save(any(GasStation.class))).thenReturn(gs);
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(test, null);
		GasStationDto result= gasStationService.saveGasStation(gasStationDtoIn);
		assert(result.equals(gsDtoOut));
	}
 
	@Test
	public void testSaveGasStationNewReturnNull() throws PriceException, GPSDataException{ 
	
		GasStationRepository test= mock(GasStationRepository.class);
		when(test.findByGasStationAddressAndLatAndLon(anyString(), anyDouble(), anyDouble())).thenReturn(new GasStation()); //We are supposing that there is already a gs in the DB with passed coordinates and address
		GasStationDto gsDtoIn=new GasStationDto(null,"test","testAddress", true, true, true, true, true, true, "Enjoy", 4.32, 7.89, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);

		GasStationServiceimpl gasStationService = new GasStationServiceimpl(test, null);
		Assertions.assertNull(gasStationService.saveGasStation(gsDtoIn));
	}
	
	@Test
	public void testSaveGasStationUpdate() throws PriceException, GPSDataException {
		
		/*
		 * Note that we have already done UnitTests on entity and dtos plus
		 * verified that getGasStationById(userId) works, so testing here will 
		 * assume that those methods work. 
		 */
		
		GasStationRepository GRmock = mock (GasStationRepository.class);
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(GRmock, null);
		
		//GasStation di input con nuovi dati (che sarà anche la gasStation che mi aspetto ritornata)
		GasStationDto gasStationDtoIn = new GasStationDto(1, "EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true, true, "Enjoy", 45.5549032, 8.0569401, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);	
		//GasStation nel db con stesso Id
		GasStation gasStation = new GasStation("EnerCoop", "Via Asti 47 Biella", true, true, true, true, true, true, "Car2Go", 45.605412, 8.0570489, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);	
		gasStation.setGasStationId(1);
		//GasStation post save
		GasStation gasStationPost = new GasStation("EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true, true, "Enjoy", 45.605412, 45.605412, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);
		gasStationPost.setGasStationId(1);
		
		when(GRmock.findByGasStationId(anyInt())).thenReturn(gasStation);
		when(GRmock.findByGasStationAddressAndLatAndLon(anyString(), anyDouble(), anyDouble())).thenReturn(null);
		when(GRmock.save(any(GasStation.class))).thenReturn(gasStationPost);
		GasStationDto result = gasStationServiceimpl.saveGasStation(gasStationDtoIn);
		assert(gasStationDtoIn.equals(result));
	}
	
	@Test
	public void testSaveGasStationUpdateReturnNull() throws PriceException, GPSDataException {
		
		GasStationRepository GRmock = mock (GasStationRepository.class);
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(GRmock, null);
		
		//GasStation di input con nuovi dati
		GasStationDto gasStationDtoIn = new GasStationDto(1, "EnerCoop", "Via Asti 47 Biella", true, true, true, true, true, true, "Enjoy", 45.5549032, 8.0570489, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);
		//GasStation nel db con stesso Id (ossia la gas station che per cui voglio fare l'update)
		GasStation gasStation = new GasStation("EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true, true, "Enjoy", 45.605412, 8.06, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);
		gasStation.setGasStationId(1);
		//GasStation already found in the db with same address/&lat&lon (update must fail -> return null)
		GasStation gasStationFound = new GasStation("Eni", "Via Asti 47 Biella", true, false, false, true, true, true, "CarSharing", 45.5549032, 8.0570489, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);	
		gasStationFound.setGasStationId(7);
		
		when(GRmock.findByGasStationId(anyInt())).thenReturn(gasStation);
		when(GRmock.findByGasStationAddressAndLatAndLon(anyString(), anyDouble(), anyDouble())).thenReturn(gasStationFound);

		GasStationDto result = gasStationServiceimpl.saveGasStation(gasStationDtoIn);
		assert(result == null);
	}
	
	@Test
	public void testSaveGasStationInvalidLat()  {
		
		GasStationRepository GRmock = mock (GasStationRepository.class);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(GRmock, null);
		
		GasStation gasStation = new GasStation("EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true,true, "Enjoy", -100, 8.0569401, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null,0);
		Integer gasStationId = 1;
		gasStation.setGasStationId(gasStationId);
		GasStationDto gasStationDto = new GasStationDto( gasStationId, "EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true,true, "Enjoy", -100, 8.0569401, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0);
		
		when(GRmock.findByGasStationId(gasStationId)).thenReturn(gasStation);
		
		Assertions.assertThrows(GPSDataException.class, () -> { gasStationServiceimpl.saveGasStation(gasStationDto);
		} ) ;
		
	}
	
	@Test
	public void testSaveGasStationInvalidLon()  {
		
		GasStationRepository GRmock = mock (GasStationRepository.class);
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(GRmock, null);

		GasStationDto gasStationDto =  new GasStationDto( 1, "EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true,true, "Enjoy", 24, 200, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0);	
		
		Assertions.assertThrows(GPSDataException.class, () -> { gasStationServiceimpl.saveGasStation(gasStationDto);
		} ) ;
		
	}
	
	@Test
	public void testGetAllGasStationZero(){ //empty repository
		GasStationRepository test= mock(GasStationRepository.class);
		when(test.findAll()).thenReturn(new ArrayList<GasStation>());
		
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(test, null);
		ArrayList<GasStationDto> gasStationArray= (ArrayList<GasStationDto>) gasStationService.getAllGasStations();
		assert(gasStationArray.size()==0);
	}
		
	@Test
	public void testGetAllGasStation(){ //no empty repository 
		ArrayList<GasStation> gsList=new ArrayList<GasStation>();
		for(int i=0; i<10; i++) {
			gsList.add(new GasStation());
		}
		GasStationRepository test= mock(GasStationRepository.class);
		when(test.findAll()).thenReturn(gsList);
		
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(test, null);
		ArrayList<GasStationDto> gasStationArray= (ArrayList<GasStationDto>) gasStationService.getAllGasStations();
		assert(gasStationArray.size()==10);
	}
	
	@Test
	public void testDeleteUnregisteredGasStation() throws InvalidGasStationException{ 
		GasStationRepository test= mock(GasStationRepository.class);
		when(test.findByGasStationId(any(Integer.class))).thenReturn(null); //gs not found
		
		int gsId=4;
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(test,null);
		Assertions.assertFalse(gasStationService.deleteGasStation(gsId));
		
	}
	
	@Test
	public void testDeleteGasStationInvalidId() throws InvalidGasStationException{ 
		GasStationRepository test= mock(GasStationRepository.class);
		when(test.findByGasStationId(any(Integer.class))).thenReturn(new GasStation()); 
		
		int gsId=-4;
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(test, null);
		Assertions.assertThrows(InvalidGasStationException.class,()->{gasStationService.deleteGasStation(gsId);});
	
	}
	
	@Test
	public void testDeleteRegisteredGasStation() throws InvalidGasStationException{ 
		GasStationRepository test= mock(GasStationRepository.class);
		when(test.findByGasStationId(any(Integer.class))).thenReturn(new GasStation()); 
		
		int gsId=1;
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(test, null);
		boolean deleted = gasStationService.deleteGasStation(gsId);
		Assertions.assertTrue(deleted);
	}
	
	@Test
	public void testGetGasStationsByGasolineType() throws InvalidGasTypeException {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false,true, "Enjoy", 45.55, 8.05, 0.0, null, 0.0, 0.0, null,0.0, null, "null", 0);
		gasStation1.setGasStationId(5);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false,true, "Car2Go", 45.65, 8.25, 0.0, 0.0, null, 0.0, null, 0.0, null, "null", 0);
		gasStation2.setGasStationId(6);
		List<GasStation> gasStations = new ArrayList<GasStation>();
		gasStations.add(gasStation1);
		gasStations.add(gasStation2);
		GasStationDto gasStationDto1 = new GasStationDto(gasStation1.getGasStationId(), "Example1", "Address1", true, false, true, true, false,true, "Enjoy", 45.55, 8.05, 0.0, null, 0.0, 0.0, null,0.0, null, "null", 0);
		GasStationDto gasStationDto2 = new GasStationDto(gasStation2.getGasStationId(), "Example2", "Address2", true, true, false, true, false,true, "Car2Go", 45.65, 8.25, 0.0, 0.0, null, 0.0, null, 0.0, null, "null", 0);
		
		GasStationRepository gasStationRepository = mock(GasStationRepository.class);

		when(gasStationRepository.findByHasDieselTrueOrderByDieselPriceAsc()).thenReturn(gasStations);
		
		List<GasStationDto> gasStationDtos = new ArrayList<GasStationDto>();
		gasStationDtos.add(gasStationDto1);
		gasStationDtos.add(gasStationDto2);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		List<GasStationDto> results = new ArrayList<GasStationDto>();
		results = gasStationServiceimpl.getGasStationsByGasolineType("Diesel");
		
		assertTrue(results.equals(gasStationDtos));
	}

	@Test
	public void testGetGasStationsByGasolineTypeInvalid() throws InvalidGasTypeException {
		GasStationRepository gasStationRepository = mock(GasStationRepository.class);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		Assertions.assertThrows(InvalidGasTypeException.class, () -> {gasStationServiceimpl.getGasStationsByGasolineType("LPG");});
	}
	
	@Test
	public void testGetGasStationsByGasolineTypeNullList() throws InvalidGasTypeException {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false,true, "Enjoy", 45.55, 8.05, 0.0, null, 0.0, 0.0, null,0.0, null, "null", 0);
		gasStation1.setGasStationId(5);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false,true, "Car2Go", 45.65, 8.25, 0.0, 0.0, null, 0.0, null, 0.0, null, "null", 0);
		gasStation2.setGasStationId(6);
		List<GasStation> gasStations = new ArrayList<GasStation>();
		
		GasStationRepository gasStationRepository = mock(GasStationRepository.class);
		when(gasStationRepository.findByHasDieselTrueOrderByDieselPriceAsc()).thenReturn(gasStations);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		List<GasStationDto> results = new ArrayList<GasStationDto>();
		results = gasStationServiceimpl.getGasStationsByGasolineType("Diesel");
		
		assertTrue(results.size() == 0);
	}
	
	@Test
	public void testGetGasStationByProximityLatException() {
		
		GasStationRepository GRmock = mock (GasStationRepository.class);
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(GRmock, null);
		
		Assertions.assertThrows(GPSDataException.class, () -> { gasStationServiceimpl.getGasStationsByProximity(-100, 0, 1);});
	}
	
	@Test
	public void testGetGasStationByProximityLonException() {
		
		GasStationRepository GRmock = mock (GasStationRepository.class);
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(GRmock, null);
		
		Assertions.assertThrows(GPSDataException.class, () -> { gasStationServiceimpl.getGasStationsByProximity(0, 200, 1);});
	}
	
	@Test
	public void testGetGasStationByProximityRadius() throws GPSDataException {
		
		GasStationRepository GRmock = mock (GasStationRepository.class);
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(GRmock, null);
		
		//Set lat, lon per Canterino
		double lat=45.551897;
		double lon=8.0477697;
		
		//Set values for square
		
		double minLat = 45.54290379805665;
		double maxLat = 45.560890201943344;
		double minLon = 8.034927092528488;
		double maxLon = 8.06061230747151;
		
		//Create gasStations List
		List<GasStation> gasStations = new ArrayList<GasStation>();
		//GasStation nel raggio (distance = 0.78)
		GasStation gs1 = new GasStation("EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true, true, "Enjoy", 45.5549032, 8.0569401, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);
		gs1.setGasStationId(1);
		//GasStation in square, but not in radius (distance = 1.36)
		GasStation gs2 =new GasStation("Esso", "Viale Roma Biella", true, true, true, true, true, true, "Car2Go", 45.5560598, 8.0642756, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);
		gs2.setGasStationId(2);
		//GasStation outside all together (distance = 61.69)
		GasStation gs3 =new GasStation("Eni", "Via Magenta 52 Torino", true, true, true, true, true, true, "null", 45.0671772,7.6639721, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);
		gs3.setGasStationId(3);
		
		gasStations.add(gs1);
		gasStations.add(gs2);
		gasStations.add(gs3);
		
		//Create gasStationDtos
		GasStationDto gsDto1 = new GasStationDto(1,"EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true, true, "Enjoy", 45.5549032, 8.0569401, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0.0);
		
		//Create List of correct results
		List<GasStationDto> gasStationsDto = new ArrayList<GasStationDto>();
		gasStationsDto.add(gsDto1);
		
		when(GRmock.findByLatBetweenAndLonBetween(minLat, maxLat, minLon, maxLon)).thenReturn(gasStations);
		
		/*
		 * Note that in all gasStations useReport was set to null, so as to avoid having to pass though updateDependabilities, which we have already tested.
		 * 
		 */
		
		List<GasStationDto> result = gasStationServiceimpl.getGasStationsByProximity(lat, lon, 1);
		assert(result.equals(gasStationsDto));
	}
	
	@Test
	public void testGetGasStationsWithoutCoordinates() throws InvalidGasTypeException, InvalidCarSharingException {
		
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.55, 8.05, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0.0);
		gasStation1.setGasStationId(5);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, true, "Car2Go", 45.65, 8.25, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0.0);
		gasStation2.setGasStationId(6);
		List<GasStation> gasStations = new ArrayList<GasStation>();
		gasStations.add(gasStation1);
		gasStations.add(gasStation2);
		GasStationDto gasStationDto1 = new GasStationDto(gasStation1.getGasStationId(), "Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.55, 8.05, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0.0);
		
		GasStationRepository gasStationRepository = mock(GasStationRepository.class);
		when(gasStationRepository.findByHasDieselTrueOrderByDieselPriceAsc()).thenReturn(gasStations);
		
		List<GasStationDto> gasStationDtos = new ArrayList<GasStationDto>();
		gasStationDtos.add(gasStationDto1);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		List<GasStationDto> results = gasStationServiceimpl.getGasStationsWithoutCoordinates("Diesel", "Enjoy");
		
		assertTrue(results.equals(gasStationDtos));
	}
	
	@Test
	public void testGetGasStationsWithoutCoordinatesInvalidGasType() throws InvalidGasTypeException {
		
		GasStationRepository gasStationRepository = mock(GasStationRepository.class);
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		Assertions.assertThrows(InvalidGasTypeException.class, () -> {
			gasStationServiceimpl.getGasStationsWithoutCoordinates("InvalidGasType", "Enjoy");
		});
	}
	
	@Test
	public void testGetGasStationsWithoutCoordinatesNullList() throws InvalidGasTypeException, InvalidCarSharingException {
		
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true,"Enjoy", 45.55, 8.05, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0.0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, true, "Enjoy", 45.65, 8.25, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0.0);
		gasStation1.setGasStationId(5);
		gasStation2.setGasStationId(6);
		List<GasStation> gasStations = new ArrayList<GasStation>();
		gasStations.add(gasStation1);
		gasStations.add(gasStation2);
		
		GasStationRepository gasStationRepository = mock(GasStationRepository.class);
		when(gasStationRepository.findByHasDieselTrueOrderByDieselPriceAsc()).thenReturn(gasStations);
		
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		List<GasStationDto> results = gasStationServiceimpl.getGasStationsWithoutCoordinates("Diesel", "Car2Go");
		assertTrue(results.isEmpty());
	}
	
	@Test
	public void testGetGasStationsWithCoordinates() throws InvalidGasTypeException, GPSDataException, InvalidCarSharingException {
		
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.5549032, 8.0569401, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0.0);
		gasStation1.setGasStationId(1);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, true, "Car2Go", 45.5549100, 8.0569403, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0.0);
		gasStation2.setGasStationId(2);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, false, "null", 45.5549090, 8.0569411, null, null, null, 0.0, 0.0, null, null, null, 0.0);
		gasStation3.setGasStationId(3);
		List<GasStation> gasStationsLatLon = new ArrayList<GasStation>();
		gasStationsLatLon.add(gasStation1);
		gasStationsLatLon.add(gasStation2);
		gasStationsLatLon.add(gasStation3);
		
		GasStationRepository gasStationRepository = mock(GasStationRepository.class);
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		GasStationDto dto1 = new GasStationDto(gasStation1.getGasStationId(), "Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.5549032, 8.0569401, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0.0);
		
		when(gasStationRepository.findByLatBetweenAndLonBetween(anyDouble(), anyDouble(), anyDouble(), anyDouble())).thenReturn(gasStationsLatLon);
		
		List<GasStation> gasStationsGasType = new ArrayList<GasStation>();
		gasStationsGasType.add(gasStation1);
		gasStationsGasType.add(gasStation2);
		when(gasStationRepository.findByHasDieselTrueOrderByDieselPriceAsc()).thenReturn(gasStationsGasType);
		
		List<GasStationDto> gasStationDtos = new ArrayList<GasStationDto>();
		gasStationDtos.add(dto1);
		
		List<GasStationDto> results = gasStationServiceimpl.getGasStationsWithCoordinates(45.551897, 8.0477697, 1, "Diesel", "Enjoy");
		
		assertTrue(results.equals(gasStationDtos));
	}
	
	@Test
	public void testGetGasStationsWithCoordinatesNullList() throws InvalidGasTypeException, GPSDataException, InvalidCarSharingException {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Car2Go", 45.5549032, 8.0569401, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0.0);
		gasStation1.setGasStationId(1);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, true, "Car2Go", 45.5549100, 8.0569403, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0.0);
		gasStation2.setGasStationId(2);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, false, "null", 45.5549090, 8.0569411, null, null, null, 0.0, 0.0, null, null, null, 0.0);
		gasStation3.setGasStationId(3);
		List<GasStation> gasStationsLatLon = new ArrayList<GasStation>();
		gasStationsLatLon.add(gasStation1);
		gasStationsLatLon.add(gasStation2);
		gasStationsLatLon.add(gasStation3);
		
		GasStationRepository gasStationRepository = mock(GasStationRepository.class);
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		when(gasStationRepository.findByLatBetweenAndLonBetween(anyDouble(), anyDouble(), anyDouble(), anyDouble())).thenReturn(gasStationsLatLon);
		
		List<GasStation> gasStationsGasType = new ArrayList<GasStation>();
		gasStationsGasType.add(gasStation1);
		gasStationsGasType.add(gasStation2);
		when(gasStationRepository.findByHasDieselTrueOrderByDieselPriceAsc()).thenReturn(gasStationsGasType);
		
		List<GasStationDto> results = gasStationServiceimpl.getGasStationsWithCoordinates(45.551897, 8.0477697, 1, "Diesel", "Enjoy");
		
		assertTrue(results.size() == 0);
	}
	
	@Test
	public void testGetGasStationsWithCoordinatesOnlyLatLon() throws InvalidGasTypeException, GPSDataException, InvalidCarSharingException {
		
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.5549032, 8.0569401, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0.0);
		gasStation1.setGasStationId(1);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, true, "Car2Go", 45.0671772, 7.6639721, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0.0);
		gasStation2.setGasStationId(2);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, "null", 45.5549090, 8.0569411, null, null, null, 0.0, 0.0, 0.0, null, null, 0.0);
		gasStation3.setGasStationId(3);
		List<GasStation> gasStationsLatLon = new ArrayList<GasStation>();
		gasStationsLatLon.add(gasStation1);
		gasStationsLatLon.add(gasStation3);
		
		GasStationRepository gasStationRepository = mock(GasStationRepository.class);
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		GasStationDto dto1 = new GasStationDto(gasStation1.getGasStationId(), "Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.5549032, 8.0569401, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0.0);
		GasStationDto dto3 = new GasStationDto(gasStation3.getGasStationId(), "Example3", "Address3", false, false, false, true, true, true, "null", 45.5549090, 8.0569411, null, null, null, 0.0, 0.0, 0.0, null, null, 0.0);

		when(gasStationRepository.findByLatBetweenAndLonBetween(anyDouble(), anyDouble(), anyDouble(), anyDouble())).thenReturn(gasStationsLatLon);
		
		List<GasStationDto> gasStationDtos = new ArrayList<GasStationDto>();
		gasStationDtos.add(dto1);
		gasStationDtos.add(dto3);

		List<GasStationDto> results = gasStationServiceimpl.getGasStationsWithCoordinates(45.551897, 8.0477697, 1, "null", "null");

		assertTrue(results.equals(gasStationDtos));
	}
	
	@Test
	public void testGetGasStationsWithCoordinatesNoGasType() throws InvalidGasTypeException, GPSDataException, InvalidCarSharingException {
			
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.5549032, 8.0569401, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0.0);
		gasStation1.setGasStationId(1);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, true, "Enjoy", 45.5549100, 8.0569403, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0.0);
		gasStation2.setGasStationId(2);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, "null", 45.5549090, 8.0569411, null, null, null, 0.0, 0.0, 0.0, null, null, 0.0);
		gasStation3.setGasStationId(3);
		List<GasStation> gasStationsLatLon = new ArrayList<GasStation>();
		gasStationsLatLon.add(gasStation1);
		gasStationsLatLon.add(gasStation2);
		gasStationsLatLon.add(gasStation3);
		
		GasStationRepository gasStationRepository = mock(GasStationRepository.class);
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		GasStationDto dto1 = new GasStationDto(gasStation1.getGasStationId(), "Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.5549032, 8.0569401, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0.0);
		GasStationDto dto2 = new GasStationDto(gasStation2.getGasStationId(), "Example2", "Address2", true, true, false, true, false, true, "Enjoy", 45.5549100, 8.0569403, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0.0);
		
		when(gasStationRepository.findByLatBetweenAndLonBetween(anyDouble(), anyDouble(), anyDouble(), anyDouble())).thenReturn(gasStationsLatLon);
		
		List<GasStation> gasStationsCarSh = new ArrayList<GasStation>();
		gasStationsCarSh.add(gasStation1);
		gasStationsCarSh.add(gasStation2);
		
		when(gasStationRepository.findByCarSharing(anyString())).thenReturn(gasStationsCarSh);
		List<GasStationDto> gasStationDtos = new ArrayList<GasStationDto>();
		gasStationDtos.add(dto1);
		gasStationDtos.add(dto2);
		
		List<GasStationDto> results = gasStationServiceimpl.getGasStationsWithCoordinates(45.551897, 8.0477697, 1, "null", "Enjoy");

		assertTrue(results.equals(gasStationDtos));
	}
	
	@Test
	public void testGetGasStationsWithCoordinatesNoCarSharing() throws InvalidGasTypeException, GPSDataException, InvalidCarSharingException {
		
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.5549032, 8.0569401, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0.0);
		gasStation1.setGasStationId(1);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, true, "Car2Go", 45.5549100, 8.0569403, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0.0);
		gasStation2.setGasStationId(2);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, "null", 45.5549090, 8.0569411, null, null, null, 0.0, 0.0, 0.0, null, null, 0.0);
		gasStation3.setGasStationId(3);gasStation3.setGasStationId(3);
		List<GasStation> gasStationsLatLon = new ArrayList<GasStation>();
		gasStationsLatLon.add(gasStation1);
		gasStationsLatLon.add(gasStation2);
		gasStationsLatLon.add(gasStation3);
		
		GasStationRepository gasStationRepository = mock(GasStationRepository.class);
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		GasStationDto dto1 = new GasStationDto(gasStation1.getGasStationId(), "Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.5549032, 8.0569401, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0.0);
		GasStationDto dto2 = new GasStationDto(gasStation2.getGasStationId(), "Example2", "Address2", true, true, false, true, false, true, "Car2Go", 45.5549100, 8.0569403, 0.0, 0.0, null, 0.0, null, 0.0, null, null, 0.0);
	
		when(gasStationRepository.findByLatBetweenAndLonBetween(anyDouble(), anyDouble(), anyDouble(), anyDouble())).thenReturn(gasStationsLatLon);
		
		List<GasStation> gasStationsGasType = new ArrayList<GasStation>();
		gasStationsGasType.add(gasStation1);
		gasStationsGasType.add(gasStation2);
		when(gasStationRepository.findByHasDieselTrueOrderByDieselPriceAsc()).thenReturn(gasStationsGasType);
		
		List<GasStationDto> gasStationDtos = new ArrayList<GasStationDto>();
		gasStationDtos.add(dto1);
		gasStationDtos.add(dto2);

		List<GasStationDto> results = gasStationServiceimpl.getGasStationsWithCoordinates(45.551897, 8.0477697, 1, "Diesel", "null");

		assertTrue(results.equals(gasStationDtos));
	}
	
	@Test
	public void testGetGasStationsWithCoordinatesInvalidGPS() throws InvalidGasTypeException, GPSDataException {
		GasStationRepository gasStationRepository = mock(GasStationRepository.class);
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		Assertions.assertThrows(GPSDataException.class, () -> {
			gasStationServiceimpl.getGasStationsWithCoordinates(-100.1245, 54.541, 1, "Diesel", "Enjoy");
		});
	}
	
	@Test
	public void testGetGasStationsWithCoordinatesInvalidGasType() throws InvalidGasTypeException, GPSDataException {
		
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.5549032, 8.0569401, 0.0, null, 0.0, 0.0, null, 0.0, null, null, 0.0);
		gasStation1.setGasStationId(5);
		GasStationRepository gasStationRepository = mock(GasStationRepository.class);
		List<GasStation> gasStations = new ArrayList<GasStation>();
		gasStations.add(gasStation1);
		when(gasStationRepository.findByLatBetweenAndLonBetween(anyDouble(), anyDouble(), anyDouble(), anyDouble())).thenReturn(gasStations);

		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(gasStationRepository, null);
		
		Assertions.assertThrows(InvalidGasTypeException.class, () -> {
			gasStationServiceimpl.getGasStationsWithCoordinates(45.551897, 8.0477697, 1, "InvalidGasType", "Enjoy");
		});
	}
	
	@Test
	public void testSetReportNew() throws InvalidGasStationException, PriceException, InvalidUserException{ 
		GasStationRepository testGsRepo=mock(GasStationRepository.class);
		UserRepository testUserRepo=mock(UserRepository.class);
		GasStation gs=new GasStation("test","testAddress",true,true,true,false,false,false,"Enjoy",0.0,0.0,0.0,0.0,0.0,null,null,null,null,new SimpleDateFormat("MM-dd-yyyy").format(new java.util.Date()),25.0);	
		when(testGsRepo.findByGasStationId(any(Integer.class))).thenReturn(gs);
		when(testGsRepo.save(any(GasStation.class))).thenReturn(gs);
		
		User newUser = new User("Mario", "Rossi", "mario@ezgas.com", 3);
		newUser.setUserId(1);
		
		when(testUserRepo.findByUserId(any(Integer.class))).thenReturn(newUser);
		
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(testGsRepo, testUserRepo);
		gasStationService.setReport(1, 1.22, 1.76, 1.78, null, null, null, 1);
		assert(gs.getDieselPrice()==1.22 && gs.getSuperPrice()==1.76 && gs.getSuperPlusPrice()==1.78 && gs.getReportUser()==1);
	}
	
	@Test
	public void testSetReportOverwrittenUserTrust() throws InvalidGasStationException, PriceException, InvalidUserException{ //U>=U.2
		GasStationRepository testGsRepo=mock(GasStationRepository.class);
		UserRepository testUserRepo=mock(UserRepository.class);
		GasStation gs=new GasStation("test","testAddress",true,true,true,false,false,false,"Enjoy",0.0,0.0,0.0,0.0,0.0,null,null,null,1,new SimpleDateFormat("MM-dd-yyyy").format(new java.util.Date()),25.0);	
		when(testGsRepo.findByGasStationId(any(Integer.class))).thenReturn(gs);
		when(testGsRepo.save(any(GasStation.class))).thenReturn(gs);
		
		User currentUser = new User("Alice", "HelloWorld", "alice@ezgas.com", 2);
		currentUser.setUserId(1);
		gs.setUser(currentUser);
		
		User newUser = new User("Mario", "Rossi", "mario@ezgas.com", 3);
		newUser.setUserId(2);
		
		when(testUserRepo.findByUserId(any(Integer.class))).thenReturn(newUser);
		
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(testGsRepo, testUserRepo);
		gasStationService.setReport(1, 1.22, 1.76, 1.78, null, null, null, 2);
		assert(gs.getDieselPrice()==1.22 && gs.getSuperPrice()==1.76 && gs.getSuperPlusPrice()==1.78 && gs.getReportUser()==2);
	}
	
	@Test
	public void testSetReportOverwrittenTimeTag() throws InvalidGasStationException, PriceException, InvalidUserException{ //U<U.2 but (today - P.time_tag ) > 4 days
		GasStationRepository testGsRepo=mock(GasStationRepository.class);
		UserRepository testUserRepo=mock(UserRepository.class);
		GasStation gs=new GasStation("test","testAddress",true,true,true,false,false,false,"Enjoy",0.0,0.0,0.0,0.0,0.0,null,null,null,1,"06-11-2020",25.0);	 //5 days
		when(testGsRepo.findByGasStationId(any(Integer.class))).thenReturn(gs);
		when(testGsRepo.save(any(GasStation.class))).thenReturn(gs);
		
		User currentUser = new User("Alice", "HelloWorld", "alice@ezgas.com", 3);
		currentUser.setUserId(1);
		gs.setUser(currentUser);
		
		User newUser = new User("Mario", "Rossi", "mario@ezgas.com", 2);
		newUser.setUserId(2);
		
		when(testUserRepo.findByUserId(any(Integer.class))).thenReturn(newUser);
		
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(testGsRepo, testUserRepo);
		gasStationService.setReport(1, 1.22, 1.76, 1.78, null, null, null, 2);
		assert(gs.getDieselPrice()==1.22 && gs.getSuperPrice()==1.76 && gs.getSuperPlusPrice()==1.78 && gs.getReportUser()==2);
	}
	
	@Test
	public void testSetReportPriceException() throws InvalidGasStationException, PriceException, InvalidUserException {
		GasStationServiceimpl gasStationService=new GasStationServiceimpl(null, null);
		Assertions.assertThrows(PriceException.class,()->{gasStationService.setReport(1, -2.0, 1.2, 1.3, 1.0, 1.0,1.3, 1);});
	}
	
	@Test
	public void testSetReportInvalidUserException() throws InvalidGasStationException, PriceException, InvalidUserException {
		GasStationServiceimpl gasStationService=new GasStationServiceimpl(null,null);
		Assertions.assertThrows(InvalidUserException.class,()->{gasStationService.setReport(1, 1.0, 1.0, 1.0, 1.0, 1.0,1.0, -1);});
	}
	
	@Test
	public void testSetReportInvalidGasStationException() throws InvalidGasStationException, PriceException, InvalidUserException {
		GasStationServiceimpl gasStationService=new GasStationServiceimpl(null,null);
		Assertions.assertThrows(InvalidGasStationException.class,()->{gasStationService.setReport(-1, 1.0, 1.0, 1.0, 1.0,1.0, 1.0, 1);});
	}
	
	@Test
	public void testUpdateDependabilitiesReportUserNull(){
		UserRepository test=mock(UserRepository.class);
		ArrayList<GasStation> gsList=new ArrayList<GasStation>();
		GasStation gs=new GasStation();
		gs.setReportUser(null);
		gsList.add(gs);
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(null, test);
		gasStationService.updateDependabilities(gsList);
		assert(gs.getReportDependability()==0);
	}
	
	@Test
	public void testUpdateDependabilitiesObsolescencePositive(){
		User user = new User("TestName","123","test@ezgas.com", 3);
		user.setUserId(1);
		UserRepository test=mock(UserRepository.class);
		
		ArrayList<GasStation> gsList=new ArrayList<GasStation>();
		GasStation gs=new GasStation("test","testAddress",true,true,true,false,false,false,"Enjoy",4.32,7.89,0.0,0.0,0.0,null,null,null,1,new SimpleDateFormat("MM-dd-yyyy").format(new java.util.Date()),25.0);
		gs.setUser(user);
		gsList.add(gs);
		
		GasStationRepository testGsRepo=mock(GasStationRepository.class);
		when(testGsRepo.save(any(GasStation.class))).thenReturn(gs); 
		
		GasStationServiceimpl gasStationService = new GasStationServiceimpl(testGsRepo, test);
		gasStationService.updateDependabilities(gsList);
		assert(gs.getReportDependability()==90);
	}
		
	@Test
	public void testGetGasStationByCarSharingNotNull() {
		
		GasStationRepository GRmock = mock (GasStationRepository.class);
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(GRmock, null);
		
		//Set CarSharing type
		String carSharing= "Enjoy";
		
		//Create gasStations List
		List<GasStation> gasStations = new ArrayList<GasStation>();
		//I will set ReportUser == null as to not invoke updateUserDependabilities, as I am not concerned in the outcome of that function 
		GasStation gs1 =new GasStation("EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true, true, carSharing, 45.5549032, 8.0569401, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0);
		gs1.setGasStationId(1);
		GasStation gs2 =new GasStation("Esso", "Via Ivrea 16 Biella", true, true, true, true, true, true, carSharing, 45.55, 8.03, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0);
		gs2.setGasStationId(2);
		gasStations.add(gs1);
		gasStations.add(gs2);
		
		//Create gasStationDtos List
		List<GasStationDto> gasStationsDto = new ArrayList<GasStationDto>();
		GasStationDto gsDto1 = new GasStationDto(1,"EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true, true, carSharing, 45.5549032, 8.0569401, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0);
		GasStationDto gsDto2 = new GasStationDto(2, "Esso", "Via Ivrea 16 Biella", true, true, true, true, true, true, carSharing, 45.55, 8.03, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0);
		gasStationsDto.add(gsDto1);
		gasStationsDto.add(gsDto2);
		
		when(GRmock.findByCarSharing(carSharing)).thenReturn(gasStations);
	
		List<GasStationDto> result = gasStationServiceimpl.getGasStationByCarSharing(carSharing);
		assert(result.equals(gasStationsDto));
	}
	
	@Test
	public void testGetGasStationByCarSharingNull() {
		
		GasStationRepository GRmock = mock (GasStationRepository.class);
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(GRmock, null);
		
		//Create gasStations List
		List<GasStation> gasStations = new ArrayList<GasStation>();
		//I will set ReportUser == null as to not invoke updateUserDependabilities, as I am not concerned in the outcome of that function 
		GasStation gs1 =new GasStation("EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true, true, "Enjoy", 45.5549032, 8.0569401, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0);
		gs1.setGasStationId(1);
		GasStation gs2 =new GasStation("Esso", "Via Ivrea 16 Biella", true, true, true, true, true, true, "null", 45.55,8.03, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0);
		gs2.setGasStationId(2);
		gasStations.add(gs1);
		gasStations.add(gs2);
		
		//Create gasStationDtos List
		List<GasStationDto> gasStationsDto = new ArrayList<GasStationDto>();
		GasStationDto gsDto1 = new GasStationDto(1, "EnerCoop", "Via Macchieraldo 2 Biella", true, true, true, true, true, true, "Enjoy", 45.5549032, 8.0569401, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0);
		GasStationDto gsDto2 = new GasStationDto(2, "Esso", "Via Ivrea 16 Biella", true, true, true, true, true, true, null, 45.55,8.03, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, 0);
		gasStationsDto.add(gsDto1);
		gasStationsDto.add(gsDto2);
		
		when(GRmock.findAll()).thenReturn(gasStations);
	
		List<GasStationDto> results = gasStationServiceimpl.getGasStationByCarSharing("null");
		assert(results.equals(gasStationsDto));
	}
	
	@Test
	public void testGetGasStationByCarSharingIsEmpty() {
		
		GasStationRepository GRmock = mock (GasStationRepository.class);
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(GRmock, null);
		
		//Set CarSharing type
		String carSharing= "Car2Go";
		
		//Create gasStations Lists
		List<GasStation> gasStations = new ArrayList<GasStation>();
		List<GasStationDto> gasStationsDto = new ArrayList<GasStationDto>();

		//Assuming updatDependabilities functions correctly (it has already been tested) so I can ignore this call
		when(GRmock.findByCarSharing(carSharing)).thenReturn(gasStations); //we suppose that there are no gas stations in the DB with this type of carSharing
		List<GasStationDto> result = gasStationServiceimpl.getGasStationByCarSharing(carSharing);
		assert(result.equals(gasStationsDto));
	}
	
	
	@Test
	public void testInvalidCarSharing() {
	
		GasStationRepository GRmock = mock (GasStationRepository.class);
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl(GRmock, null);
		
		Assertions.assertThrows(InvalidCarSharingException.class, () -> {
			gasStationServiceimpl.getGasStationsWithoutCoordinates("Diesel", "FreeToGo");
		});
	}
}
