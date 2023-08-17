package com.techelevator.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcBuyersDao implements BuyersDao{
    JdbcTemplate jdbcTemplate;

    JdbcBuyersDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addBuyerTable( int listingId, int favoritesId, int user_id, int requestQty){
    String sql ="INSERT INTO buyers (listing_id, request_qty, user_id, favorites_id)\n" +
            "VALUES (?, ?, ?, ?)";

    jdbcTemplate.update(sql, listingId, requestQty, user_id, favoritesId);

}
}
