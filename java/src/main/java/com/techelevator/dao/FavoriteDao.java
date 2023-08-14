package com.techelevator.dao;

import com.techelevator.model.Favorite;
import com.techelevator.model.FavoriteDto;

import java.util.List;

public interface FavoriteDao {

 int addToFavorites(Favorite favorite);

 void deleteFromFavorites(int favoriteId);

 void updateFavoritesUsername(Favorite favorite);

 void updateFavoriteOwnedPlants(Favorite favorite);

 List<Favorite> getFavoritesList (String username);



}
