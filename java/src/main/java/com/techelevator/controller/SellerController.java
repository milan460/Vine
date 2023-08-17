package com.techelevator.controller;

import com.techelevator.dao.BuyersDao;
import com.techelevator.dao.SellerDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Seller;
import com.techelevator.model.UpdateStockDTO;
import com.techelevator.model.User;
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

    @Autowired
    private final BuyersDao buyersDao;


    public SellerController(SellerDao sellerDao, UserDao userDao, BuyersDao buyersDao){
        this.sellerDao = sellerDao;
        this.userDao = userDao;
        this.buyersDao = buyersDao;
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path = "/seller-listings", method = RequestMethod.GET)
    public List<Seller> getAllSellersPlants(){
        return sellerDao.getAllSellersPlants();
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path = "/addListing", method = RequestMethod.POST)
    public void addNewListing(@RequestBody Seller seller, Principal principal){
        seller.setUsername(principal.getName());
        sellerDao.addListing(seller);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path = "/deleteListing/{favoriteId}", method = RequestMethod.DELETE)
    public void deleteListing(@PathVariable int favoriteId){
        sellerDao.deleteListing(favoriteId);
    }

    @RequestMapping(path = "/updateStock", method = RequestMethod.PUT)
    public void updateQty(@RequestBody UpdateStockDTO updateStock){
        sellerDao.updateQuantity(updateStock.getListingId());
    }

    @RequestMapping(path= "/insertBuyer", method = RequestMethod.POST)
    public void addNewBuyer(@RequestBody UpdateStockDTO updateStock, Principal principal){
        User user = userDao.getUserByUsername(principal.getName());
        buyersDao.addBuyerTable(updateStock.getListingId(), updateStock.getFavoriteId(), user.getId() , updateStock.getRequestQuantity());
   }
}










