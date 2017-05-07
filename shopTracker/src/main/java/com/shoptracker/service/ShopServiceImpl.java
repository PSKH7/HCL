package com.shoptracker.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoptracker.Exception.ShopException;
import com.shoptracker.dao.ShopDao;
import com.shoptracker.models.Shop;
/**
 * Create a service class to handle interaction between Data Access Object and Controller
 */
@Service
@Transactional
public class ShopServiceImpl implements  ShopService {
	
	@Autowired
	private ShopDao dao;
	
	
	public ShopServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Create a method to  get  all the shops from Shop Data Access Object 
	 * @returns Shop List
	 */
	@Override
	@Transactional
	public List<Shop> getShopsListService() {
		// TODO Auto-generated method stub
		List<Shop> shops  = dao.getShopsList();
		return shops;
	}

	/**
	 * Create a  method to insert the shop into Shop Data Access Object 
	 * @param Shop shop: Shop instance
	 * @throws ShopException 
	 * @returns String
	 */
	@Override
	public String insertShopService(Shop shop) throws ShopException {
		// TODO Auto-generated method stub
		//api.getLocation(shop);
		String result =  dao.insertShopsDao(shop);
		return result;
	}

	/**
	 * Create a method to get the closest shop located based on customer coordinates
	 * @param Double latitude, longitude: Customer coordinates
	 * @throws ShopException 
	 * @returns Shop Object
	 */
	@Override
	public Shop getNearestShopService(double latitude, double longitude) throws ShopException {
		// TODO Auto-generated method stub
		
		return dao.getNearestServiceDao(latitude, longitude);
	}


	

}
