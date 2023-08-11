package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Favorite;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcFavoriteDao implements FavoriteDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcFavoriteDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int addToFavorites(Favorite favorite) {
        int newFavorite = 0;
        String sql = "INSERT INTO favorites (plant_id, username) VALUES (?, ?) RETURNING favorites_id;";
        try {
            newFavorite = jdbcTemplate.queryForObject(sql, int.class, favorite.getPlantId(), favorite.getUsername());
            favorite.setFavoriteId(newFavorite);
        } catch (NullPointerException e) {
            throw new NullPointerException("Login to add to the garden");
        }
        return newFavorite;
    }

    @Override
    public void deleteFromFavorites(int favoriteId) {
        String sql = "DELETE FROM favorites WHERE favorites_id = ?;";
        try {
            jdbcTemplate.update(sql, favoriteId);
        } catch (Exception e) {
            throw new DaoException("Unable to delete the favorites", e);
        }
    }

    @Override
    public List<Favorite> getFavoritesList(String username) {
        List<Favorite> favorites = new ArrayList<>();
        String sql = "SELECT favorites_id, plant_id, username FROM favorites WHERE username = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
            while (results.next()) {
                Favorite newFavorite = mapToRowFavorite(results);
                favorites.add(newFavorite);
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new DaoException("Cannot Add to favorites", e);


        }
        return favorites;
    }


    private Favorite mapToRowFavorite(SqlRowSet sqlRowSet){
        Favorite favorite = new Favorite();

        favorite.setUsername(sqlRowSet.getString("username"));
        favorite.setFavoriteId(sqlRowSet.getInt("favorites_id"));
        favorite.setPlantId(sqlRowSet.getInt("plant_id"));

        return favorite;
    }


}
