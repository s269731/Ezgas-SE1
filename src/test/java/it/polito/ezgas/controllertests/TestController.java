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

//For the Controller tests, we used a database file that you can find on GitLab, in the GUI folder.
//Note that the following tests may fail if you try to run them without using our database file.

public class TestController {
    
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
    public void testDeleteGasStation() throws ClientProtocolException, IOException {
    	HttpUriRequest request = new HttpDelete("http://localhost:8080/gasstation/deleteGasStation/6");
    	HttpResponse response = HttpClientBuilder.create().build().execute(request);
    	
    	assert(response.getStatusLine().getStatusCode() == 200);
    }
  
    @Test
    public void testGetGasStationsByProximity() throws ClientProtocolException, IOException {
    	HttpUriRequest request = new HttpGet("http://localhost:8080/gasstation/searchGasStationByProximity/45.0672093/7.6638629");
    	HttpResponse response = HttpClientBuilder.create().build().execute(request);
    	
    	assert(response.getStatusLine().getStatusCode() == 200);
    }
}
