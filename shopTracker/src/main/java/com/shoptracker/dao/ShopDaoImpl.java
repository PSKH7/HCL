package com.shoptracker.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoptracker.Exception.ShopException;
import com.shoptracker.geocodeapi.Geocode;
import com.shoptracker.models.Location;
import com.shoptracker.models.Shop;
import com.shoptracker.models.ShopAddress;

/**
 * Create a repository class to handle interaction between Data Access Object and Database
 */
@Repository
public class ShopDaoImpl implements ShopDao {
	private static final Logger logger = LoggerFactory
			.getLogger(ShopDaoImpl.class);
	@Autowired
	private SessionFactory factory;
	
	@Autowired
	Geocode api;

	@PostConstruct
	public void init() {
		
	}
	public ShopDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	

	/**
	 * Create a  method to insert the shop into database 
	 * @param Shop shop: Shop instance
	 * @throws ShopException 
	 * @returns String
	 */
	@Override
	public String insertShopsDao(Shop shop) throws ShopException {
		// TODO Auto-generated method stub
		String output = "No Update";
        Location loc = api.getLocation(shop);
        if(loc == null){
        	throw new ShopException("No results found for the shop. Please verify the shop name and address provided as input");
        }
        else{
        	  shop.setLocation(loc);
              //Save the employee in database
              List<Shop> shopsList = getShopsList();
              //First time insertion
              ObjectMapper mapperObj = new ObjectMapper();
              if(shopsList.isEmpty()){
              	insertShop(shop);
          		try {
      				output = mapperObj.writeValueAsString(shop);
      				logger.info("Shop inserted successfully");
      			} catch (JsonProcessingException e) {
      				// TODO Auto-generated catch block
      				e.printStackTrace();
      			}
              }
              else{	
              	int count = 0;
              	for(Shop shopItr :shopsList){
              		if(shopItr.getShopName().equalsIgnoreCase(shop.getShopName())){
              			count++;
              			
              			try {
      						output = "previousVersion:"+mapperObj.writeValueAsString(shopItr);
      					} catch (JsonProcessingException e) {
      						// TODO Auto-generated catch block
      						e.printStackTrace();
      					}
              		}
              	}
                  	if(count > 0){
                  		//update
                  		try {
      						output = output+",  newVersion:"+mapperObj.writeValueAsString(shop);
      					} catch (JsonProcessingException e) {
      						// TODO Auto-generated catch block
      						e.printStackTrace();
      					}
                  		updateShop(shop);
                  		logger.info("Shop updated successfully");
                  	}
                  	else{
                  		insertShop(shop);
                  		logger.info("Shop inserted successfully");
                  		try {
      						output = mapperObj.writeValueAsString(shop);
      					} catch (JsonProcessingException e) {
      						// TODO Auto-generated catch block
      						e.printStackTrace();
      					}
                  	}
                  }
        }
      
      
        //Commit the transaction
		return output;
	}
	
	/**
	 * Create a method to  get  all the shops from database
	 * @returns Shop List
	 */
	@Override
	public List<Shop> getShopsList() {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		List<Shop> list  =  session.createQuery(" SELECT j FROM Shop j ")
				.list();
		session.close();
		return list;
		
		
	}
	
	
	/**
	 * Create a  method to  update the shop in database 
	 * @returns Shop 
	 */
	public void updateShop(Shop shop) {
		Session session = factory.openSession();
        session.beginTransaction();
        
        
        Shop shopObject = session.get(Shop.class, shop.getShopName());
        shopObject.setShopAddress(new ShopAddress(shop.getShopAddress().getNumber(), shop.getShopAddress().getPostCode()));
        shopObject.setLocation(new Location(shop.getLocation().getLatitude(), shop.getLocation().getLongitude()));
        session.update(shopObject);
 
        //Commit the transaction
        session.getTransaction().commit();
        session.close();
        
				
	}
	
	public void insertShop(Shop shop){
		Session session = factory.openSession();
		session.beginTransaction();
		session.save(shop);
		session.getTransaction().commit();
		session.close();
	}
	/**
	 * Create a method to get the closest shop located based on customer coordinates
	 * @param Double latitude, longitude: Customer coordinates
	 * @throws ShopException 
	 * @returns Shop Object
	 */
	@Override
	public Shop getNearestServiceDao(double latitude, double longitude) throws ShopException {
		// TODO Auto-generated method stub
		double distance;
		Shop shopObject = null;
		List<Shop> shopsList = getShopsList();
		if(shopsList.isEmpty()){
			throw new ShopException("No Shops found in database");
		}
		else{
			ListIterator<Shop> itr=shopsList.listIterator();
			HashMap<Shop, Double> hm = new HashMap<>();
			while(itr.hasNext()){
				Shop shopItr = itr.next();
				//method to calculate distance
				distance = api.distance(latitude, longitude, Double.parseDouble(shopItr.getLocation().getLatitude()), Double.parseDouble(shopItr.getLocation().getLongitude()));
				hm.put(shopItr, distance);
			}
			
			Double min = Collections.min(hm.values());
			for(Shop shopItr : hm.keySet()){
				if(hm.get(shopItr).compareTo(min) == 0){
					 shopObject = shopItr;
					 logger.info("Nearest Shop Found-->"+shopObject.toString());
				}
			}
		}
		
		return shopObject;
	}
	
}
