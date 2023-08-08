package com.techelevator.dao;

import com.techelevator.model.Review;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdbcReviewDao implements ReviewDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcReviewDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Review getReview(int reviewId) {
        String sql = "SELECT username, title, review_detail, rating FROM reviews WHERE review_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, reviewId);
        Review review = null;
        if(results.next()){
            review = mapToRowReview(results);
        }


        return review;
    }

    @Override
    public void addReview(Review review) {

    }

    @Override
    public void updateReview(Review review) {

    }

    @Override
    public Review getRating(Review review) {
        return null;
    }

    @Override
    public List<Review> listOfReview() {
        return null;
    }

    @Override
    public void deleteReview(int reviewId) {

    }

    private Review mapToRowReview(SqlRowSet sqlRowSet){
        Review review = new Review();

        review.setUsername(sqlRowSet.getString("username"));
        review.setTitle(sqlRowSet.getString("title"));
        review.setReviewDetail(sqlRowSet.getString("review_detail"));
        review.setRating(sqlRowSet.getInt("rating"));
        return review;
    }
}
