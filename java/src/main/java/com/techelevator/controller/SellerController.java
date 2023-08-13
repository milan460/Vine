package com.techelevator.controller;

import com.techelevator.dao.SellerDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class SellerController {
    @Autowired
    private final SellerDao sellerDao;
    @Autowired
    private final UserDao userDao;

    public SellerController(SellerDao sellerDao, UserDao userDao){
        this.sellerDao = sellerDao;
        this.userDao = userDao;
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path = "/market", method = RequestMethod.GET)
    public List<Seller> getAllSellersPlants(){
        return sellerDao.getAllSellersPlants();
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path = "/addListing", method = RequestMethod.POST)
    public int addNewListing(@RequestBody Seller seller, Principal principal){
        seller.setUsername(principal.getName());
        return sellerDao.addListing(seller);
    }
}
