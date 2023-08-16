package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Favorite;
import com.techelevator.model.Seller;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcSellerDao implements SellerDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcSellerDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Seller> getAllSellersPlants() {
        List<Seller> listOfAllPlantsBeingSold = new ArrayList<>();
        String sql = "SELECT sellers.favorites_id, description, price, is_available, stock_quantity, plant_id, username\n" +
                "FROM sellers\n" +
                "JOIN favorites ON sellers.favorites_id = favorites.favorites_id\n" +
                "WHERE owned_plant = true";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

            while(results.next()){
                Seller seller = mapToRowSeller(results);
                listOfAllPlantsBeingSold.add(seller);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DaoException("Cannot find seller plants", e);
        }
        return  listOfAllPlantsBeingSold;
    }


    @Override
    public void addListing(Seller seller) {

        String sql = "INSERT INTO sellers (favorites_id, description, price, is_available, stock_quantity)\n" +
                "VALUES (?, ?, ?, ?, ?) ";

        try{
           jdbcTemplate.update(sql, seller.getFavoritesId(), seller.getDescription(), seller.getPrice(), seller.getAvailable(),seller.getStockQuantity());

        }
        catch (Exception e){
            e.printStackTrace();
            throw new DaoException("Cannot find seller plants", e);
        }
    }

    @Override
    public void deleteListing(int favoriteId) {
        String sql = "DELETE FROM sellers\n" +
                "WHERE favorites_id = ?";

        try{
            jdbcTemplate.update(sql, favoriteId);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DaoException("Cannot delete listing", e);
        }
    }

    @Override
    public void updateQuantity(int favoriteId, int requestQty) {
        String sql ="UPDATE sellers " +
                "SET stock_quantity = stock_quantity - (SELECT request_qty " +
                "FROM buyers " +
                "WHERE favorites_id = ?) " +
                " WHERE favorites_id = ?";

        jdbcTemplate.update(sql, favoriteId, favoriteId);
    }


    private Seller mapToRowSeller(SqlRowSet sqlRowSet){
        Seller seller = new Seller();

        seller.setFavoritesId(sqlRowSet.getInt("favorites_id"));
        seller.setDescription(sqlRowSet.getString("description"));
        seller.setPrice(sqlRowSet.getBigDecimal("price"));
        seller.setAvailable(sqlRowSet.getBoolean("is_available"));
        seller.setStockQuantity(sqlRowSet.getInt("stock_quantity"));
        seller.setPlantId(sqlRowSet.getInt("plant_id"));
        seller.setUsername(sqlRowSet.getString("username"));

        return seller;
    }
}
