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
        String sql = "SELECT * FROM plant_sellers JOIN favorites ON favorites.favorite_id = sellers.favorite" +
                "WHERE owned_plants = true";

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
    public int addListing(Seller seller) {
        int newPlantSellerId = 0;
        String sql = "INSERT INTO plant_sellers (username, plant_id, description, price, stock_quantity)\n" +
                "VALUES (?, ?, ?, ?, ?) \n" +
                "RETURNING plant_seller_id";

        try{
            newPlantSellerId = jdbcTemplate.queryForObject(sql, int.class, seller.getUsername(), seller.getPlantId(), seller.getDescription(), seller.getPrice(), seller.getStockQuantity());
            seller.setPlantSellerId(newPlantSellerId);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DaoException("Cannot find seller plants", e);
        }
        return newPlantSellerId;
    }

    private Seller mapToRowSeller(SqlRowSet sqlRowSet){
        Seller seller = new Seller();

        seller.setPlantSellerId(sqlRowSet.getInt("plant_seller_id"));
        seller.setPlantId(sqlRowSet.getInt("plant_id"));
        seller.setUsername(sqlRowSet.getString("username"));
        seller.setDescription(sqlRowSet.getString("description"));
        seller.setPrice(sqlRowSet.getBigDecimal("price"));
        seller.setStockQuantity(sqlRowSet.getInt("stock_quantity"));

        return seller;
    }
}
