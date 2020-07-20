package it.polito.ezgas.controllertests;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.dto.UserDto;

//For the Controller tests, we used a database file that you can find on GitLab, in the GUITests folder.
//Note that the following tests may fail if you try to run them without using our database file.

public class TestController {
	
    @Test
    public void testGetUserById() throws ClientProtocolException, IOException {
    	Integer userId = 3;
    	
    	HttpUriRequest request = new HttpGet("http://localhost:8080/user/getUser/"+userId);
    	HttpResponse response = HttpClientBuilder.create().build().execute(request);
    	String jsonFromResponse =EntityUtils.toString(response.getEntity());
    	ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	UserDto result = mapper.readValue(jsonFromResponse, UserDto.class);
    
    	assert(result.getUserId()==userId);
    }
    
    @Test
	public void testGetAllUsers() throws ClientProtocolException, IOException {
		HttpUriRequest request = new HttpGet("http://localhost:8080/user/getAllUsers");
		HttpResponse response =HttpClientBuilder.create().build().execute(request);
		String jsonFromResponse = EntityUtils.toString(response.getEntity());
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		UserDto[] userArray= mapper.readValue(jsonFromResponse, UserDto[].class);
		
		assert(userArray.length==7);
	}
    
	@Test
	public void testSaveUser() throws ClientProtocolException, IOException {
		HttpPost request = new HttpPost("http://localhost:8080/user/saveUser");
		String json = "{\"userId\":null,\"userName\":\"Luigi\",\"password\":\"luigi\",\"email\":\"luigi@ezgas.com\",\"reputation\":0,\"admin\":false}";
		StringEntity entity = new StringEntity(json);
		request.setEntity(entity);
		request.setHeader("Accept", "application/json, text/plain, */*");
		request.setHeader("Content-type", "application/json;charset=UTF-8");
		
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		assert(response.getStatusLine().getStatusCode() == 200); 
	}
    
	@Test
    public void testDeleteUser() throws ClientProtocolException, IOException {
		String userId="5";
		HttpUriRequest request = new HttpDelete("http://localhost:8080/user/deleteUser/" + userId);
    	HttpResponse response = HttpClientBuilder.create().build().execute(request);
    	
    	assert(response.getStatusLine().getStatusCode() == 200);
    }
	
	@Test
	public void testIncreaseUserReputation() throws ClientProtocolException, IOException {
		HttpPost request = new HttpPost("http://localhost:8080/user/increaseUserReputation/3");
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		
		assert(response.getStatusLine().getStatusCode() == 200);
	}
	
	@Test
	public void testDecreaseUserReputation() throws ClientProtocolException, IOException {
		HttpPost request = new HttpPost("http://localhost:8080/user/decreaseUserReputation/3");
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		
		assert(response.getStatusLine().getStatusCode() == 200);
	}
	
	@Test
	public void testLogin() throws ClientProtocolException, IOException {
		HttpPost request = new HttpPost("http://localhost:8080/user/login");
		String json = "{\"user\":\"admin@ezgas.com\",\"pw\":\"admin\"}";
		StringEntity entity = new StringEntity(json);
		request.setEntity(entity);
		request.setHeader("Accept", "application/json, text/plain, */*");
		request.setHeader("Content-type", "application/json;charset=UTF-8");
		
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		assert(response.getStatusLine().getStatusCode() == 200);
	}
	
	@Test
    public void testGetGasStationById() throws ClientProtocolException, IOException {
    	HttpUriRequest request = new HttpGet("http://localhost:8080/gasstation/getGasStation/2");
    	HttpResponse response = HttpClientBuilder.create().build().execute(request);
    	
    	String jsonFromResponse = EntityUtils.toString(response.getEntity());
    	ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	GasStationDto gasStation = mapper.readValue(jsonFromResponse, GasStationDto.class);
    	
    	assert(gasStation.getGasStationId() == 2);
    }
    
    @Test
    public void testGetAllGasStations() throws ClientProtocolException, IOException {
    	HttpUriRequest request = new HttpGet("http://localhost:8080/gasstation/getAllGasStations");
    	HttpResponse response = HttpClientBuilder.create().build().execute(request); 
    	
    	String jsonFromResponse = EntityUtils.toString(response.getEntity());
    	ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	GasStationDto[] gasStationArray = mapper.readValue(jsonFromResponse, GasStationDto[].class);
    	
    	assert(gasStationArray.length == 6);
    }
    
    @Test
	public void testSaveGasStation() throws ClientProtocolException, IOException {
		HttpPost request = new HttpPost("http://localhost:8080/gasstation/saveGasStation");
		String json = "{\"gasStationId\":null,\"gasStationName\":\"Ip\",\"gasStationAddress\":\"Corso Trapani 79 Turin Piemont Italy\",\"hasDiesel\":true,\"hasSuper\":true,\"hasSuperPlus\":false,\"hasGas\":false,\"hasMethane\":false,\"hasPremiumDiesel\":false, \"carSharing\":\"Enjoy\",\"lat\":45.0692404,\"lon\": 7.6391213,\"dieselPrice\": 1.457, \"superPrice\": 1.768, \"superPlusPrice\": null,\"gasPrice\": null,\"methanePrice\": null,\"premiumDieselPrice\": null,\"reportUser\": null,\"reportTimestamp\":null,\"reportDependability\":null}";
		StringEntity entity = new StringEntity(json);
		request.setEntity(entity); 
		request.setHeader("Accept", "application/json, text/plain, */*");
		request.setHeader("Content-type", "application/json;charset=UTF-8");
		
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		assert(response.getStatusLine().getStatusCode() == 200);
	}
    
    @Test
    public void testDeleteGasStation() throws ClientProtocolException, IOException {
    	HttpUriRequest request = new HttpDelete("http://localhost:8080/gasstation/deleteGasStation/6");
    	HttpResponse response = HttpClientBuilder.create().build().execute(request);
    	
    	assert(response.getStatusLine().getStatusCode() == 200);
    }
    
    @Test
	public void testGetGasStationsByGasolineType() throws ClientProtocolException, IOException {
		HttpUriRequest request = new HttpGet("http://localhost:8080/gasstation/searchGasStationByGasolineType/Diesel");
		HttpResponse response =HttpClientBuilder.create().build().execute(request);
		String jsonFromResponse = EntityUtils.toString(response.getEntity());
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		GasStationDto[] gs= mapper.readValue(jsonFromResponse, GasStationDto[].class);
		
		assert(gs.length==5);
	}
  
    @Test
    public void testGetGasStationsByProximity() throws ClientProtocolException, IOException {
    	HttpUriRequest request = new HttpGet("http://localhost:8080/gasstation/searchGasStationByProximity/45.0672093/7.6638629/1");
    	HttpResponse response = HttpClientBuilder.create().build().execute(request);
    	
    	String jsonFromResponse = EntityUtils.toString(response.getEntity());
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		GasStationDto[] gs= mapper.readValue(jsonFromResponse, GasStationDto[].class);
		
		assert(gs.length==3);
    }
    
    @Test
    public void testGetGasStationsWithCoordinates() throws ClientProtocolException, IOException {
    	/*Consider starting location of Via Magenta 52/D Turin Piedmont*/
    	String myLat="/45.0672093";
    	String myLon="/7.6638629";
    	String myRadius="/1";
    	String gasolineType="/Diesel";
    	String carSharing="/Enjoy";
    	HttpUriRequest request = new HttpGet("http://localhost:8080/gasstation/getGasStationsWithCoordinates"+myLat+myLon+myRadius+gasolineType+carSharing);
    	HttpResponse response = HttpClientBuilder.create().build().execute(request);
    	String jsonFromResponse =EntityUtils.toString(response.getEntity());
    	ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	GasStationDto[] gasStationArray = mapper.readValue(jsonFromResponse, GasStationDto[].class);
    	
    	/* Should return Q8 */
    	assert(gasStationArray.length==1);
    }
    
	@Test
	public void testSetGasStationReport() throws IOException {
	    HttpPost request = new HttpPost("http://localhost:8080/gasstation/setGasStationReport");	
	    String json = "{\"gasStationId\":3,\"dieselPrice\":\"1.256\",\"superPrice\":null,\"superPlusPrice\":\"1.3\",\"gasPrice\":null,\"methanePrice\":\"0.987\",\"premiumDieselPrice\":null,\"userId\":\"3\"}";
		StringEntity entity = new StringEntity(json);
		request.setEntity(entity); 
		request.setHeader("Accept", "application/json, text/plain, */*");
		request.setHeader("Content-type", "application/json;charset=UTF-8");
		
	    HttpResponse response = HttpClientBuilder.create().build().execute(request);
		assert(response.getStatusLine().getStatusCode() == 200);
	}
}
