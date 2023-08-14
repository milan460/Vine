package com.techelevator.controller;

import com.techelevator.dao.FavoriteDao;
import com.techelevator.dao.JdbcFavoriteDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class FavoriteController {

    @Autowired
    private final FavoriteDao favoriteDao;
    @Autowired
    private final UserDao userDao;

    public FavoriteController(FavoriteDao favoriteDao, UserDao userDao) {
        this.favoriteDao = favoriteDao;
        this.userDao = userDao;
    }
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path="/favorites", method = RequestMethod.GET)
    public List<Favorite> getFavoritesList(Principal principal){
        return favoriteDao.getFavoritesList(principal.getName());
    }
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path="/deleteFavorites/{favoriteId}", method = RequestMethod.DELETE)
    public void deleteFavorite(@PathVariable int favoriteId){
        favoriteDao.deleteFromFavorites(favoriteId);
    }
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path= "/favorites-add/", method = RequestMethod.POST)
    public int addToFavorites(@RequestBody Favorite favorite, Principal principal){
        favorite.setUsername(principal.getName());
       return favoriteDao.addToFavorites(favorite);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path= "/updateFavorite", method = RequestMethod.PUT)
    public void updateFavorites(@RequestBody Favorite favorite, Principal principal){
        favorite.setUsername(principal.getName());
        favoriteDao.updateFavoritesUsername(favorite);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path= "/updateOwned", method = RequestMethod.PUT)
    public void updateFavoritesOwnedPlant(@RequestBody Favorite favorite){
        favoriteDao.updateFavoriteOwnedPlants(favorite);
    }








}
