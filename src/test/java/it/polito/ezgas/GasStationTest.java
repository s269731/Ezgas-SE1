package it.polito.ezgas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import it.polito.ezgas.entity.GasStation;

public class GasStationTest {
	
	@Test
	public void testSetGasStationId() {
		
		GasStation gs = new GasStation();
		Integer gasStationId = 1;
		gs.setGasStationId(gasStationId);
		
		assertTrue("setGasStationId() fails", gasStationId.equals(gs.getGasStationId()));
	}
	
	@Test
	public void testGetGasStationId() {
		GasStation gs = new GasStation();
		Integer gasStationId = 1;
		gs.setGasStationId(gasStationId);
		Integer result = gs.getGasStationId();
		
		assertTrue("getGasStationId() fails", result.equals(gasStationId));
	}
	
	@Test
	public void testSetGasStationName() {
		
		GasStation gs = new GasStation();
		String gasStationName = "nameTest";
		gs.setGasStationName(gasStationName);
		
		assertEquals("setGasStationName() fails", gasStationName, gs.getGasStationName());
	}
	
	@Test
	public void testGetGasStationName() {
		
		GasStation gs = new GasStation();
		String gasStationName = "nameTest";
		gs.setGasStationName(gasStationName);
		String result = gs.getGasStationName();
		
		assertEquals("getGasStationName() fails", gasStationName, result);
		
	}
	
	@Test
	public void testSetGasStationAddress() {
		
		GasStation gs = new GasStation();
		String gasStationAddress = "addressTest";
		gs.setGasStationAddress(gasStationAddress);
		
		assertEquals("setGasStationAddress() fails", gasStationAddress, gs.getGasStationAddress());
	}
	
	@Test
	public void testGetGasStationAddress() {
		
		GasStation gs = new GasStation();
		String gasStationAddress = "addressTest";
		gs.setGasStationName(gasStationAddress);
		String result = gs.getGasStationName();
		
		assertEquals("getGasStationAddress() fails", gasStationAddress, result);
		
	}
	
	@Test
	public void testSetHasDiesel() {
		
		GasStation gs = new GasStation();
		boolean hasDiesel = true;
		gs.setHasDiesel(hasDiesel);
		
		assertEquals("setHasDiesel() fails", hasDiesel,gs.getHasDiesel());
	}
	
	@Test
	public void testGetHasDiesel() {
		GasStation gs = new GasStation();
		boolean hasDiesel = true;
		gs.setHasDiesel(hasDiesel);
		boolean result = gs.getHasDiesel();
		
		assertEquals("getHasDiesel() fails", hasDiesel, result);
	}
	
	@Test
	public void testSetHasSuper() {
		
		GasStation gs = new GasStation();
		boolean hasSuper = true;
		gs.setHasSuper(hasSuper);
		
		assertEquals("setHasSuper() fails", hasSuper,gs.getHasSuper());
	}
	
	@Test
	public void testGetHasSuper() {
		GasStation gs = new GasStation();
		boolean hasSuper = true;
		gs.setHasSuper(hasSuper);
		boolean result = gs.getHasSuper();
		
		assertEquals("getHasSuper() fails", hasSuper, result);
	}
	
	@Test
	public void testSetHasSuperPlus() {
		
		GasStation gs = new GasStation();
		boolean hasSuperPlus = true;
		gs.setHasSuperPlus(hasSuperPlus);
		
		assertEquals("setHasSuperPlus() fails", hasSuperPlus,gs.getHasSuperPlus());
	}
	
	@Test
	public void testGetHasSuperPLus() {
		GasStation gs = new GasStation();
		boolean hasSuperPlus = true;
		gs.setHasSuperPlus(hasSuperPlus);
		boolean result = gs.getHasSuperPlus();
		
		assertEquals("getHasSuperPlus() fails", hasSuperPlus, result);
	}
	
	@Test
	public void testSetHasGas() {
		
		GasStation gs = new GasStation();
		boolean hasGas = true;
		gs.setHasGas(hasGas);
		
		assertEquals("setHasGas() fails", hasGas,gs.getHasGas());
	}
	
	@Test
	public void testGetHasGas() {
		GasStation gs = new GasStation();
		boolean hasGas = true;
		gs.setHasGas(hasGas);
		boolean result = gs.getHasGas();
		
		assertEquals("getHasGas() fails", hasGas, result);
	}
	
	@Test
	public void testSetHasMethane() {
		
		GasStation gs = new GasStation();
		boolean hasMethane = true;
		gs.setHasMethane(hasMethane);
		
		assertEquals("setHasMethane() fails", hasMethane,gs.getHasMethane());
	}
	
	@Test
	public void testGetHasMethane() {
		GasStation gs = new GasStation();
		boolean hasMethane = true;
		gs.setHasMethane(hasMethane);
		boolean result = gs.getHasMethane();
		
		assertEquals("getHasMethane() fails", hasMethane, result);
	}
	
}
