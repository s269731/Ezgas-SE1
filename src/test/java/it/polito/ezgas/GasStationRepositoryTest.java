package it.polito.ezgas;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.repository.GasStationRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GasStationRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private GasStationRepository gasStationRepository;
	
	@Test
	public void testFindByGasStationId() {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, false, "Car2Go", 45.65, 8.25, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, null, 45.05, 8.15, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		GasStation result = gasStationRepository.findByGasStationId(gasStation2.getGasStationId());
		
		assertTrue(result.getGasStationId().equals(gasStation2.getGasStationId()));
	}
	
	@Test
	public void testFindByGasStationIdNull() {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, false, "Car2Go", 45.65, 8.25, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, null, 45.05, 8.15, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		GasStation result = gasStationRepository.findByGasStationId(245);
		
		assertTrue(result == null);
	}
	
	@Test
	public void testFindByLatBetweenAndLonBetween() {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 42.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, false, "Car2Go", 43.65, 9.25, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, null, 45.05, 10.15, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		List<GasStation> results = new ArrayList<GasStation>();
		results = gasStationRepository.findByLatBetweenAndLonBetween(41.0, 44.0, 7.0, 12.0);
		
		assertTrue(results.size() == 2);
	}
	
	@Test
	public void testFindByLatBetweenAndLonBetweenNull() {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 42.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, false, "Car2Go", 43.65, 9.25, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, null, 45.05, 10.15, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);

		
		List<GasStation> results = new ArrayList<GasStation>();
		results = gasStationRepository.findByLatBetweenAndLonBetween(37.0, 39.0, 7.0, 10.0);
		
		assertTrue(results.size() == 0);
	}
	
	@Test
	public void testFindByHasDieselTrueOrderByDieselPriceAsc() {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 42.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, false, "Car2Go", 43.65, 9.25, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, null, 45.05, 10.15, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		List<GasStation> results = new ArrayList<GasStation>();
		results = gasStationRepository.findByHasDieselTrueOrderByDieselPriceAsc();
		
		assertTrue(results.size() == 2);
	}
	
	@Test
	public void testFindByHasDieselTrueOrderByDieselPriceAscNull() {
		GasStation gasStation1 = new GasStation("Example1", "Address1", false, false, true, true, false, true, "Enjoy", 42.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", false, true, false, true, false, false, "Car2Go", 43.65, 9.25, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, null, 45.05, 10.15, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		List<GasStation> results = new ArrayList<GasStation>();
		results = gasStationRepository.findByHasDieselTrueOrderByDieselPriceAsc();
		
		assertTrue(results.size() == 0);
	}
	
	@Test
	public void testFindByHasSuperTrueOrderBySuperPriceAsc() {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 42.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, false, "Car2Go", 43.65, 9.25, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, null, 45.05, 10.15, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		List<GasStation> results = new ArrayList<GasStation>();
		results = gasStationRepository.findByHasSuperTrueOrderBySuperPriceAsc();
		
		assertTrue(results.size() == 1);
	}
	
	@Test
	public void testFindByHasSuperTrueOrderBySuperPriceAscNull() {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 42.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, false, false, true, false, false, "Car2Go", 43.65, 9.25, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, null, 45.05, 10.15, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		List<GasStation> results = new ArrayList<GasStation>();
		results = gasStationRepository.findByHasSuperTrueOrderBySuperPriceAsc();
		
		assertTrue(results.size() == 0);
	}
	
	@Test
	public void testFindByHasSuperPlusTrueOrderBySuperPlusPriceAsc() {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 42.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, true, true, false, false, "Car2Go", 43.65, 9.25, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, null, 45.05, 10.15, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		List<GasStation> results = new ArrayList<GasStation>();
		results = gasStationRepository.findByHasSuperPlusTrueOrderBySuperPlusPriceAsc();
		
		assertTrue(results.size() == 2);
	}
	
	@Test
	public void testFindByHasSuperPlusTrueOrderBySuperPlusPriceAscNull() {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, false, true, false, true, "Enjoy", 42.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, false, "Car2Go", 43.65, 9.25, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, null, 45.05, 10.15, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		List<GasStation> results = new ArrayList<GasStation>();
		results = gasStationRepository.findByHasSuperPlusTrueOrderBySuperPlusPriceAsc();
		
		assertTrue(results.size() == 0);
	}
	
	public void testFindByHasGasTrueOrderByGasPriceAsc() {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 42.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, false, "Car2Go", 43.65, 9.25, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, null, 45.05, 10.15, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		List<GasStation> results = new ArrayList<GasStation>();
		results = gasStationRepository.findByHasGasTrueOrderByGasPriceAsc();
		
		assertTrue(results.size() == 3);
	}
	
	@Test
	public void testFindByHasGasTrueOrderByGasPriceAscNull() {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, false, false, true, "Enjoy", 42.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, false, false, false, "Car2Go", 43.65, 9.25, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, false, true, true, null, 45.05, 10.15, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		List<GasStation> results = new ArrayList<GasStation>();
		results = gasStationRepository.findByHasGasTrueOrderByGasPriceAsc();
		
		assertTrue(results.size() == 0);
	}
	
	public void testFindByHasMethaneTrueOrderByMethanePriceAsc() {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 42.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, false, "Car2Go", 43.65, 9.25, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, null, 45.05, 10.15, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		List<GasStation> results = new ArrayList<GasStation>();
		results = gasStationRepository.findByHasMethaneTrueOrderByMethanePriceAsc();
		
		assertTrue(results.size() == 1);
	}
	
	@Test
	public void testFindByHasMethaneTrueOrderByMethanePriceAscNull() {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 42.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, false, "Car2Go", 43.65, 9.25, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, false, true, null, 45.05, 10.15, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		List<GasStation> results = new ArrayList<GasStation>();
		results = gasStationRepository.findByHasMethaneTrueOrderByMethanePriceAsc();
		
		assertTrue(results.size() == 0);
	}
	
	public void testFindByHasPremiumDieselTrueOrderByPremiumDieselPriceAsc() {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 42.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, false, "Car2Go", 43.65, 9.25, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, null, 45.05, 10.15, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		List<GasStation> results = new ArrayList<GasStation>();
		results = gasStationRepository.findByHasPremiumDieselTrueOrderByPremiumDieselPriceAsc();
		
		assertTrue(results.size() == 2);
	}
	
	@Test
	public void testFindByHasPremiumDieselTrueOrderByPremiumDieselPriceAscNull() {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, false, "Enjoy", 42.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, false, "Car2Go", 43.65, 9.25, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, false, null, 45.05, 10.15, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		List<GasStation> results = new ArrayList<GasStation>();
		results = gasStationRepository.findByHasPremiumDieselTrueOrderByPremiumDieselPriceAsc();
		
		assertTrue(results.size() == 0);
	}
	
	@Test
	public void testFindByCarSharing() {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 42.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, false, "Car2Go", 43.65, 9.25, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, null, 45.05, 10.15, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		List<GasStation> results = new ArrayList<GasStation>();
		results = gasStationRepository.findByCarSharing(gasStation2.getCarSharing());
		
		assertTrue(results.size() == 1);
	}
	
	@Test
	public void testFindByCarSharingNull() {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 42.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, false, "Enjoy", 43.65, 9.25, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, null, 45.05, 10.15, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		List<GasStation> results = new ArrayList<GasStation>();
		results = gasStationRepository.findByCarSharing("Car2Go");
		
		assertTrue(results.size() == 0);
	}
	
	@Test
	public void testFindByGasStationAddressAndLatAndLon() {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, false, "Car2Go", 45.65, 8.25, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, null, 45.05, 8.15, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		GasStation result = gasStationRepository.findByGasStationAddressAndLatAndLon("Address2", 45.65, 8.25);
		assertTrue(result.getGasStationId().equals(gasStation2.getGasStationId()));
	}
	
	@Test
	public void testFindByGasStationAddressAndLatAndLonNull() {
		GasStation gasStation1 = new GasStation("Example1", "Address1", true, false, true, true, false, true, "Enjoy", 45.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation2 = new GasStation("Example2", "Address2", true, true, false, true, false, false, "Car2Go", 45.65, 8.25, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStation gasStation3 = new GasStation("Example3", "Address3", false, false, false, true, true, true, null, 45.05, 8.15, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		
		entityManager.persist(gasStation1);
		entityManager.persist(gasStation2);
		entityManager.persist(gasStation3);
		
		GasStation result = gasStationRepository.findByGasStationAddressAndLatAndLon("Address2", 45.555, 8.15);
		assertTrue(result == null);
	}

}
