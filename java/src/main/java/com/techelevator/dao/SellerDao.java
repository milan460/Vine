package com.techelevator.dao;

import com.techelevator.model.Seller;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface SellerDao {
    List<Seller> getAllSellersPlants();

    void addListing(Seller seller);
}
