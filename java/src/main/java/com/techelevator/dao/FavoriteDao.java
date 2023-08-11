package com.techelevator.dao;

import com.techelevator.model.Favorite;
import com.techelevator.model.FavoriteDto;

import java.util.List;

public interface FavoriteDao {

 int addToFavorites(Favorite favorite);

 void deleteFromFavorites(int reviewId);
 List<Favorite> getFavoritesList (String username);



}
