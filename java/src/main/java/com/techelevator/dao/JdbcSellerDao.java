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
        String sql = "SELECT * FROM sellers";

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
        int newsellerId = 0;
        String sql = "INSERT INTO sellers (plant_id, username, price)\n" +
                "VALUES (?, ?, ?) RETURNING seller_id";

        try{
            newsellerId = jdbcTemplate.queryForObject(sql, int.class, seller.getPlantId(), seller.getUsername(), seller.getPrice());
            seller.setSellerId(newsellerId);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DaoException("Cannot find seller plants", e);
        }
        return newsellerId;
    }

    private Seller mapToRowSeller(SqlRowSet sqlRowSet){
        Seller seller = new Seller();

        seller.setUsername(sqlRowSet.getString("username"));
        seller.setPlantId(sqlRowSet.getInt("plant_id"));
        seller.setPrice(sqlRowSet.getBigDecimal("price"));

        return seller;
    }
}
