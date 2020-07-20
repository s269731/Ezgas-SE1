package it.polito.ezgas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polito.ezgas.dto.GasStationDto;

public class GasStationDtoTest {
	
	@Test
	public void testSetGasStationId() {
		
		GasStationDto gs = new GasStationDto();
		Integer gasStationId = 1;
		gs.setGasStationId(gasStationId);
		
		assertTrue("setGasStationId() fails", gasStationId.equals(gs.getGasStationId()));
	}
	
	@Test
	public void testGetGasStationId() {
		GasStationDto gs = new GasStationDto();
		Integer gasStationId = 1;
		gs.setGasStationId(gasStationId);
		Integer result = gs.getGasStationId();
		
		assertTrue("getGasStationId() fails", result.equals(gasStationId));
	}
	
	@Test
	public void testSetGasStationName() {
		
		GasStationDto gs = new GasStationDto();
		String gasStationName = "nameTest";
		gs.setGasStationName(gasStationName);
		
		assertEquals("setGasStationName() fails", gasStationName, gs.getGasStationName());
	}
	
	@Test
	public void testGetGasStationName() {
		
		GasStationDto gs = new GasStationDto();
		String gasStationName = "nameTest";
		gs.setGasStationName(gasStationName);
		String result = gs.getGasStationName();
		
		assertEquals("getGasStationName() fails", gasStationName, result);
		
	}
	
	@Test
	public void testSetGasStationAddress() {
		
		GasStationDto gs = new GasStationDto();
		String gasStationAddress = "addressTest";
		gs.setGasStationAddress(gasStationAddress);
		
		assertEquals("setGasStationAddress() fails", gasStationAddress, gs.getGasStationAddress());
	}
	
	@Test
	public void testGetGasStationAddress() {
		
		GasStationDto gs = new GasStationDto();
		String gasStationAddress = "addressTest";
		gs.setGasStationName(gasStationAddress);
		String result = gs.getGasStationName();
		
		assertEquals("getGasStationAddress() fails", gasStationAddress, result);
		
	}
	
	@Test
	public void testSetHasDiesel() {
		
		GasStationDto gs = new GasStationDto();
		boolean hasDiesel = true;
		gs.setHasDiesel(hasDiesel);
		
		assertEquals("setHasDiesel() fails", hasDiesel,gs.getHasDiesel());
	}
	
	@Test
	public void testGetHasDiesel() {
		GasStationDto gs = new GasStationDto();
		boolean hasDiesel = true;
		gs.setHasDiesel(hasDiesel);
		boolean result = gs.getHasDiesel();
		
		assertEquals("getHasDiesel() fails", hasDiesel, result);
	}
	
	@Test
	public void testSetHasSuper() {
		
		GasStationDto gs = new GasStationDto();
		boolean hasSuper = true;
		gs.setHasSuper(hasSuper);
		
		assertEquals("setHasSuper() fails", hasSuper,gs.getHasSuper());
	}
	
	@Test
	public void testGetHasSuper() {
		GasStationDto gs = new GasStationDto();
		boolean hasSuper = true;
		gs.setHasSuper(hasSuper);
		boolean result = gs.getHasSuper();
		
		assertEquals("getHasSuper() fails", hasSuper, result);
	}
	
	@Test
	public void testSetHasSuperPlus() {
		
		GasStationDto gs = new GasStationDto();
		boolean hasSuperPlus = true;
		gs.setHasSuperPlus(hasSuperPlus);
		
		assertEquals("setHasSuperPlus() fails", hasSuperPlus,gs.getHasSuperPlus());
	}
	
	@Test
	public void testGetHasSuperPLus() {
		GasStationDto gs = new GasStationDto();
		boolean hasSuperPlus = true;
		gs.setHasSuperPlus(hasSuperPlus);
		boolean result = gs.getHasSuperPlus();
		
		assertEquals("getHasSuperPlus() fails", hasSuperPlus, result);
	}
	
	@Test
	public void testSetHasGas() {
		
		GasStationDto gs = new GasStationDto();
		boolean hasGas = true;
		gs.setHasGas(hasGas);
		
		assertEquals("setHasGas() fails", hasGas,gs.getHasGas());
	}
	
	@Test
	public void testGetHasGas() {
		GasStationDto gs = new GasStationDto();
		boolean hasGas = true;
		gs.setHasGas(hasGas);
		boolean result = gs.getHasGas();
		
		assertEquals("getHasGas() fails", hasGas, result);
	}
	
	@Test
	public void testSetHasMethane() {
		
		GasStationDto gs = new GasStationDto();
		boolean hasMethane = true;
		gs.setHasMethane(hasMethane);
		
		assertEquals("setHasMethane() fails", hasMethane,gs.getHasMethane());
	}
	
	@Test
	public void testGetHasMethane() {
		GasStationDto gs = new GasStationDto();
		boolean hasMethane = true;
		gs.setHasMethane(hasMethane);
		boolean result = gs.getHasMethane();
		
		assertEquals("getHasMethane() fails", hasMethane, result);
	}
	
	@Test
	public void testSetHasPremiumDiesel() {
		
		GasStationDto gs = new GasStationDto();
		boolean hasPremiumDiesel = true;
		gs.setHasPremiumDiesel(hasPremiumDiesel);
		
		assertEquals("setHasPremiumDiesel() fails", hasPremiumDiesel, gs.getHasPremiumDiesel());
	}
	
	@Test
	public void testGetHasPremiumDiesel() {
		GasStationDto gs = new GasStationDto();
		boolean hasPremiumDiesel = true;
		gs.setHasPremiumDiesel(hasPremiumDiesel);
		boolean result = gs.getHasPremiumDiesel();
		
		assertEquals("getHasPremiumDiesel() fails", hasPremiumDiesel, result);
	}
	
	@Test
	public void testSetCarSharing() {
		
		GasStationDto gs = new GasStationDto();
		String carSharing = "Enjoy";
		gs.setCarSharing(carSharing);
		
		assertEquals("setCarSharing() fails", carSharing, gs.getCarSharing());
	}
	
	@Test
	public void testGetCarSharing() {
		
		GasStationDto gs = new GasStationDto();
		String carSharing = "Enjoy";
		gs.setCarSharing(carSharing);
		String result = gs.getCarSharing();
		
		assertEquals("getCarSharing() fails", carSharing, result);
		
	}
	
	@Test
	public void testSetLat() {
		
		GasStationDto gs = new GasStationDto();
		 double lat = 45.5517866;
		 gs.setLat(lat);
		 
		 assertEquals("setLat() fails", lat, gs.getLat(), 0);

	}
	
	@Test
	public void testGetLat() {
		
		GasStationDto gs = new GasStationDto();
		double lat = 45.5517866;
		gs.setLat(lat);
		double result = gs.getLat();
		
		assertEquals("getLat() fails", lat, result, 0);
	}
	
	@Test
	public void testSetLon() {
		
		GasStationDto gs = new GasStationDto();
		double lon = 8.050702;
		gs.setLon(lon);
		 
		assertEquals("setLon() fails", lon, gs.getLon(), 0);
		
	}
	
	@Test
	public void testGetLon() {
		
		GasStationDto gs = new GasStationDto();
		double lon = 8.050702;
		gs.setLon(lon);
		double result = gs.getLon();
		
		assertEquals("getLon() fails", lon, result, 0);
	}
	
	@Test
	public void testSetDieselPrice() {
		
		GasStationDto gs = new GasStationDto();
		double dieselPrice = 1.50;
		gs.setDieselPrice(dieselPrice);
		 
		assertEquals("setDieselPrice() fails", dieselPrice, gs.getDieselPrice(), 0);
		
	}
	
	@Test
	public void testGetDieselPrice() {
		
		GasStationDto gs = new GasStationDto();
		double dieselPrice = 1.50;
		gs.setDieselPrice(dieselPrice);;
		double result = gs.getDieselPrice();
		
		assertEquals("getDieselPrice() fails", dieselPrice, result, 0);
	}
	
	@Test
	public void testSetSuperPrice() {
		
		GasStationDto gs = new GasStationDto();
		double superPrice = 1.50;
		gs.setSuperPrice(superPrice);
		 
		assertEquals("setSuperPrice() fails", superPrice, gs.getSuperPrice(), 0);
		
	}
	
	@Test
	public void testGetSuperPrice() {
		
		GasStationDto gs = new GasStationDto();
		double superPrice = 1.50;
		gs.setSuperPrice(superPrice);;
		double result = gs.getSuperPrice();
		
		assertEquals("getSuperPrice() fails", superPrice, result, 0);
	}
	
	@Test
	public void testSetSuperPlusPrice() {
		
		GasStationDto gs = new GasStationDto();
		double superPlusPrice = 1.50;
		gs.setSuperPlusPrice(superPlusPrice);
		 
		assertEquals("setSuperPlusPrice() fails", superPlusPrice, gs.getSuperPlusPrice(), 0);
		
	}
	
	@Test
	public void testGetSuperPlusPrice() {
		
		GasStationDto gs = new GasStationDto();
		double superPlusPrice = 1.50;
		gs.setSuperPlusPrice(superPlusPrice);;
		double result = gs.getSuperPlusPrice();
		
		assertEquals("getSuperPlusPrice() fails", superPlusPrice, result, 0);
	}
	
	@Test
	public void testSetGasPrice() {
		
		GasStationDto gs = new GasStationDto();
		double gasPrice = 1.50;
		gs.setGasPrice(gasPrice);
		 
		assertEquals("setGasPrice() fails", gasPrice, gs.getGasPrice(), 0);
		
	}
	
	@Test
	public void testGetGasPrice() {
		
		GasStationDto gs = new GasStationDto();
		double gasPrice = 1.50;
		gs.setGasPrice(gasPrice);;
		double result = gs.getGasPrice();
		
		assertEquals("getGasPrice() fails", gasPrice, result, 0);
	}
	
	@Test
	public void testSetMethanePrice() {
		
		GasStationDto gs = new GasStationDto();
		double methanePrice = 1.50;
		gs.setMethanePrice(methanePrice);
		 
		assertEquals("setMethanePrice() fails", methanePrice, gs.getMethanePrice(), 0);
		
	}
	
	@Test
	public void testGetMethanePrice() {
		
		GasStationDto gs = new GasStationDto();
		double methanePrice = 1.50;
		gs.setMethanePrice(methanePrice);;
		double result = gs.getMethanePrice();
		
		assertEquals("getMethanePrice() fails", methanePrice, result, 0);
	}
	
	@Test
	public void testSetPremiumDieselPrice() {
		
		GasStationDto gs = new GasStationDto();
		double premiumDieselPrice = 1.50;
		gs.setPremiumDieselPrice(premiumDieselPrice);
		 
		assertEquals("setPremiumDieselPrice() fails", premiumDieselPrice, gs.getPremiumDieselPrice(), 0);
		
	}
	
	@Test
	public void testGetPremiumDieselPrice() {
		
		GasStationDto gs = new GasStationDto();
		double premiumDieselPrice = 1.50;
		gs.setPremiumDieselPrice(premiumDieselPrice);;
		double result = gs.getPremiumDieselPrice();
		
		assertEquals("getPremiumDieselPrice() fails", premiumDieselPrice, result, 0);
	}
	
	@Test
	public void testSetReportUser() {
		
		GasStationDto gs = new GasStationDto();
		Integer reportUser = 10;
		gs.setReportUser(reportUser);
		
		assertTrue("setReportUser() fails", reportUser.equals(gs.getReportUser()));
	}
	
	@Test
	public void testGetReportUser() {
		GasStationDto gs = new GasStationDto();
		Integer reportUser = 10;
		gs.setReportUser(reportUser);
		Integer result = gs.getReportUser();
		
		assertTrue("getReportUser() fails", result.equals(reportUser));
	}
	
	@Test
	public void testSetReportTimestamp() {
		
		GasStationDto gs = new GasStationDto();
		String reportTimestamp = "2020/05/17-18:30:17";
		gs.setReportTimestamp(reportTimestamp);
		
		assertEquals("setRepotTimestamp() fails", reportTimestamp, gs.getReportTimestamp());
	}
	
	@Test
	public void testGetReportTimestamp() {
		
		GasStationDto gs = new GasStationDto();
		String reportTimestamp = "2020/05/17-18:30:17";
		gs.setReportTimestamp(reportTimestamp);
		String result = gs.getReportTimestamp();
		
		assertEquals("getReportTimestamp() fails", reportTimestamp, result);
		
	}
	
	@Test
	public void testSetReportDependality() {
		
		GasStationDto gs = new GasStationDto();
		double reportDependability = 3.0;
		gs.setReportDependability(reportDependability);
		 
		assertEquals("setReportDependability() fails", reportDependability, gs.getReportDependability(), 0);
		
	}
	
	@Test
	public void testGetReportDependability() {
		
		GasStationDto gs = new GasStationDto();
		double reportDependability = 3.0;
		gs.setReportDependability(reportDependability);;
		double result = gs.getReportDependability();
		
		assertEquals("getReportDependability() fails", reportDependability, result, 0);
	}
	
	@Test
	public void testGasStationDtoConstructor() {
		final String gasStationName = "testName";
		final String gasStationAddress= "testAddress";
		final boolean hasDiesel = true;
		final boolean hasSuper=false;
		final boolean hasSuperPlus=false;
		final boolean hasGas=true;
		final boolean hasMethane=true;
		final boolean hasPremiumDiesel=false;
		final String carSharing="Enjoy"; 
		final double lat=1.23;
		final double lon=3.34; 
		final double dieselPrice=1.23; 
		final double superPrice=1.34; 
		final double superPlusPrice=1.45; 
		final double gasPrice=1.56; 
		final double methanePrice=1.67;
		final double premiumDieselPrice=1.51;
		final Integer reportUser=4; 
		final String reportTimestamp="05-17-2020"; 
		final double reportDependability=4.0;
		
		GasStationDto gs = new GasStationDto(5, gasStationName, gasStationAddress, hasDiesel, hasSuper, hasSuperPlus, hasGas, hasMethane, hasPremiumDiesel, carSharing,  lat,  lon,  dieselPrice,  superPrice,  superPlusPrice,  gasPrice,  methanePrice, premiumDieselPrice,  reportUser,  reportTimestamp,  reportDependability);
		
		assert(gs.getGasStationId() == 5);
		assertEquals(gasStationName, gs.getGasStationName());
		assertEquals(gasStationAddress, gs.getGasStationAddress());
		assertEquals(hasDiesel, gs.getHasDiesel());
		assertEquals(hasSuper, gs.getHasSuper());
		assertEquals(hasSuperPlus, gs.getHasSuperPlus());
		assertEquals(hasGas, gs.getHasGas());
		assertEquals(hasMethane, gs.getHasMethane());
		assertEquals(hasPremiumDiesel, gs.getHasPremiumDiesel());
		assertEquals(carSharing, gs.getCarSharing());
		assertEquals(lat, gs.getLat(),0);
		assertEquals(lon, gs.getLon(),0);
		assertEquals(dieselPrice, gs.getDieselPrice(),0);
		assertEquals(superPrice, gs.getSuperPrice(),0);
		assertEquals(superPlusPrice, gs.getSuperPlusPrice(),0);
		assertEquals(gasPrice, gs.getGasPrice(),0);
		assertEquals(methanePrice, gs.getMethanePrice(),0);
		assertEquals(premiumDieselPrice, gs.getPremiumDieselPrice(),0);
		assertTrue(reportUser.equals(gs.getReportUser()));
		assertEquals(reportTimestamp, gs.getReportTimestamp());
		assertEquals(reportDependability, gs.getReportDependability(),0);
	}

	@Test
	public void testEquals() {
		GasStationDto gasStationDto1 = new GasStationDto(5, "Example1", "Address1", true, false, true, true, false, false, "Enjoy", 45.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStationDto gasStationDto2 = new GasStationDto(5, "Example1", "Address1", true, false, true, true, false, false, "Enjoy", 45.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);

		assertTrue(gasStationDto1.equals(gasStationDto2));
	}
	
	@Test
	public void testEqualsNo() {
		GasStationDto gasStationDto1 = new GasStationDto(5, "Example1", "Address1", true, false, true, true, false, false, "Enjoy", 45.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);
		GasStationDto gasStationDto2 = new GasStationDto(7, "Example1", "Address1", true, false, true, true, false, false, "Enjoy", 45.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);

		assertFalse(gasStationDto1.equals(gasStationDto2));
	}
	
	@Test
	public void testEqualsSelf() {
		GasStationDto gasStationDto1 = new GasStationDto(5, "Example1", "Address1", true, false, true, true, false, false, "Enjoy", 45.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);

		assertTrue(gasStationDto1.equals(gasStationDto1));
	}
	
	@Test
	public void testEqualsNull() {
		GasStationDto gasStationDto1 = new GasStationDto(5, "Example1", "Address1", true, false, true, true, false, false, "Enjoy", 45.55, 8.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, "null", 0);

		assertFalse(gasStationDto1.equals(null));
	}
}
