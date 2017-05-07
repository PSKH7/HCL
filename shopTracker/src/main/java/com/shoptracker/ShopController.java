package com.shoptracker;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoptracker.Exception.RestServicesException;
import com.shoptracker.Exception.ShopException;
import com.shoptracker.models.Shop;
import com.shoptracker.service.ShopService;

/**
 * Create a controller class to handle all rest api operations on the Application
 */
@RestController
public class ShopController {

	@Autowired
	private ShopService service;

	/**
	 * Create a rest api method to  get  all the shops from Shop service 
	 * @throws ShopException 
	 * @returns Shop List
	 */
	@RequestMapping(value="/getShops",method = RequestMethod.GET)
	public List<Shop> getShopsList() throws ShopException{
		List<Shop> shop = service.getShopsListService();
		if(shop.isEmpty()){
			throw new ShopException("No shop found in the database");
		}
		return shop;
		
	}
	
	
	/**
	 * Create a rest api method to insert the shop into Shop service
	 * @param Shop shop: Shop instance
	 * @throws ShopException 
	 * @returns String
	 */
	@RequestMapping(value = "/shops", method = RequestMethod.POST, consumes = "application/json")
	public String insertShops(@RequestBody Shop shop ) throws ShopException{
		String result;
		if(shop.getShopName() == null){
			throw new ShopException("Shop Name is missing");
		}
		else if(shop.getShopAddress().getPostCode() == null ){
			throw new ShopException("Post Code for Shop is missing");
		}
		else if(shop.getShopAddress() == null){
			throw new ShopException("Shop Address is missing");
		}
		else{
			result = service.insertShopService(shop);
		}
		return result;
	}
	
	/**
	 * Create a rest api method to get the closest shop located based on customer coordinates
	 * @param Double latitude, longitude: Customer coordinates
	 * @throws ShopException 
	 * @returns Shop Object
	 */
	@RequestMapping(value = "/nearestShop",method = RequestMethod.GET) 
	public Shop getNearestShop(@RequestParam(value="latitude",required = true) double latitude, @RequestParam(value ="longitude",required = true) double longitude) throws ShopException {
		return service.getNearestShopService(latitude, longitude);
			
	}
	
}
