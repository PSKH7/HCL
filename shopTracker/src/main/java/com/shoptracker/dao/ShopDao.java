package com.shoptracker.dao;

import java.util.List;

import com.shoptracker.Exception.ShopException;
import com.shoptracker.models.Shop;
/**
 * Create a repository interface to handle interaction between Data Access Object and Database
 */
public interface ShopDao {
	String insertShopsDao(Shop shop) throws ShopException;
	List<Shop> getShopsList();
	Shop  getNearestServiceDao(double latitude, double longitude) throws ShopException;
	
	
}
