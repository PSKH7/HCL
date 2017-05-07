package com.shoptracker.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.shoptracker.Exception.ShopException;
import com.shoptracker.models.Shop;

/**
 * Create a service interface to handle interaction between Data Access Object and Controller
 */
public interface ShopService {
	List<Shop> getShopsListService();
	String insertShopService(Shop shop) throws ShopException;
	Shop getNearestShopService(double latitude,double longitude) throws ShopException;
	
}
