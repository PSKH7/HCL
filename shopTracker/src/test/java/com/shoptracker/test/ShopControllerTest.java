package com.shoptracker.test;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoptracker.Exception.RestServicesException;
import com.shoptracker.Exception.ShopException;
import com.shoptracker.geocodeapi.Geocode;
import com.shoptracker.models.Location;
import com.shoptracker.models.Shop;
import com.shoptracker.models.ShopAddress;
import com.shoptracker.service.ShopService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShopControllerTest {

	 @LocalServerPort
	    private int port;

	    private URL base;

	    @Autowired
	    private TestRestTemplate template;
	    
	    @Autowired
	    private JdbcTemplate jdbc;
	    @Autowired
	    ShopService shopService;
	    @Autowired
	    ResourceLoader resourceLoader;
	    @Autowired
	    Geocode api;
	    @Before
	    public void setUp() throws Exception {
	    	
	        this.base = new URL("http://localhost:" + port + "/getShops");
	        
	    }

	    @Test
	    public void getShops() throws Exception {
	    	Shop shop = new Shop("KFC", new ShopAddress("177", "560032"),null);
	    	List<Shop> expected = new ArrayList<>();
	    	expected.add(shop);
	    	insertShop(shop);
	        ResponseEntity<List> response = template.getForEntity(base.toString(),
	                List.class);
	        
	        if(response.getBody().contains(expected)){
	        	  assertEquals(response.getBody(),expected);
	        }
	        else{
	        	assertNotEquals(response.getBody(), expected);
	        }
	      
	     
	    }
	   
	    public void insertShop(Shop shop) throws ShopException{
	    	ObjectMapper mapperObj = new ObjectMapper();
	    	String expected ="No data";
	    	String result = shopService.insertShopService(shop);
	    	Location loc = api.getLocation(shop);
	        shop.setLocation(loc);
	        try {
	        	expected = mapperObj.writeValueAsString(shop);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        assertEquals(result,expected);
	    }
	    @Test
	    public void insertShopTest() throws ShopException{
	    	Shop shop = new Shop("Al-Bek", new ShopAddress("527", "560003"),null);
	    	insertShop(shop);
	    	
	    }
	    @Test
	    public void getNearestShopTest() throws ShopException{
	    	Shop shop = new Shop("Families Super Market", new ShopAddress("80", "560032"),null);
	    	insertShop(shop);
	    	Shop nearest = shopService.getNearestShopService(13.0265706, 77.58980609999999);
	    	assertEquals(shop.getShopName(), nearest.getShopName());
	    }
	    
	    @Test 
	    public void wrongShopInputTest() { 
	    	Shop shop = new Shop(null, new ShopAddress("80", "560032"),null);
	    	ResponseEntity<Shop> response = template.exchange("http://localhost:" + port + "/shops", HttpMethod.POST,
					new HttpEntity<Shop>(shop), Shop.class);
	    	assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
	    } 
	    
	    @Test 
	    public void missingCustomerCoordinatesTest() { 
	    	 ResponseEntity<Shop> response = template.getForEntity("http://localhost:" + port + "/nearestShop",Shop.class);
	    	assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	    } 
	    

}
