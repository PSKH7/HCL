package com.shoptracker.geocodeapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoptracker.Exception.ShopException;
import com.shoptracker.models.Location;
import com.shoptracker.models.Shop;

/**
 * Create a Utils class to handle interaction with the Geocoding Api
 */
@Component
public class Geocode {


	public Geocode() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Create a  method to get the location(latitude and longitude) of the Shop 
	 * @param Shop shop: Shop instance
	 * @throws ShopException 
	 * @returns Location object
	 */
	public Location getLocation(Shop shop)  {
		//I have used rest template because it is thread safe.
		String API_KEY = readProperties();
		RestTemplate restTemplate = new RestTemplate();
		Location loc = null;
		Map<String, String> vars = new HashMap<String, String>(); 
		vars.put("address", shop.getShopAddress().getNumber()+" "+shop.getShopName());
		vars.put("postal_code", shop.getShopAddress().getPostCode());
		
		String result  =  restTemplate
		.getForObject(
		"https://maps.googleapis.com/maps/api/geocode/json?address={address}&components=postal_code:{postal_code}&key="+API_KEY,
		 
		String.class, vars);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root,results = null;
		try {
			root = mapper.readTree(result);
			results = root.path("results");
			for(JsonNode geometry :results){
				for(JsonNode latitude :geometry.get("geometry") ){
					if(latitude.has("lat") && latitude.has("lng") ){
						loc = new Location(latitude.get("lat").toString(), latitude.get("lng").toString());
						break;
					}
					
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		return loc;
		
           
        
	}
	
	/**
	 * Calculate distance between two points in latitude and longitude
	 * lat1, lon1 Start point lat2, lon2 End point 
	 * @returns Distance in KM
	 */
	public  double distance(double lat1,  double lon1,double lat2,
	        double lon2) {

	    final int R = 6371; // Radius of the earth

	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c ; // convert to meter
	
	    return distance;
	}
	
	/**
	 * Create a  method to read spandana.properties file
	 */
	 public String readProperties()  {
	        File configFile = new File("src/main/java/geoapikey.properties");
	       

	        FileInputStream fis = null;
	        Properties properties = new Properties();
	        try {
	            
	        	try {
	            	  fis = new FileInputStream(configFile);
	                  properties.load(fis);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            System.out.println(properties.getProperty("API_KEY"));
	            
	        } finally {
	            if (fis != null) {
	                try {
						fis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        }
	        return properties.getProperty("API_KEY");
	    }
}
