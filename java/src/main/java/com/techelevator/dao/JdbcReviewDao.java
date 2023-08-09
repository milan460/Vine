package com.techelevator.dao;

import com.techelevator.model.Review;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcReviewDao implements ReviewDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcReviewDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


//    @Override
//    public Review getReviewByPlantId(int plantId) {
//        Review review = null;
//        String sql = "SELECT username, title, review_detail, rating, plant_id FROM reviews WHERE plant_id = ?";
//        try{
//            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, plantId);
//
//            if(results.next()){
//                review = mapToRowReview(results);
//            }
//        } catch(NullPointerException e) {
//            throw new NullPointerException("Review not found");
//        }
//
//        return review;
//    }

    @Override
    public void addReview(Review review) {
        String sql = "INSERT INTO reviews (username, title, review_detail, rating, plant_id) VALUES (?, ?, ?, ?, ?) RETURNING review_id ";
        try{
            int newReview = jdbcTemplate.queryForObject(sql, int.class, review.getUsername(), review.getTitle(), review.getReviewDetail(), review.getRating(), review.getPlantID());
            review.setReviewId(newReview);
        } catch (NullPointerException e){
            throw new NullPointerException("Login to add a Review");
        }
    }

//    @Override
//    public Review getRating(Review review) {
//      //figure it out
//        return null;
//    }

    @Override
    public List<Review> listOfReview(int plantId) {
        List<Review> review = new ArrayList<>();
        String sql = "SELECT username, title, review_detail, rating, plant_id, review_id FROM reviews WHERE plant_id= ?";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, plantId);
            while(results.next()){
            Review review1 = mapToRowReview(results);
            review.add(review1);
            }
        } catch (NullPointerException e){
            throw new NullPointerException("Reviews not found");
        }
        return review;
    }

    @Override
    public void deleteReview(int reviewId) {
        String sql = "DELETE FROM reviews WHERE review_id = ?";
        try{
            jdbcTemplate.update(sql, reviewId);
        } catch (NullPointerException e) {
            throw new NullPointerException("Unable to delete the review");
        }

    }

    private Review mapToRowReview(SqlRowSet sqlRowSet){
        Review review = new Review();

        review.setUsername(sqlRowSet.getString("username"));
        review.setTitle(sqlRowSet.getString("title"));
        review.setReviewDetail(sqlRowSet.getString("review_detail"));
        review.setRating(sqlRowSet.getInt("rating"));
        review.setPlantID(sqlRowSet.getInt("plant_id"));
        review.setReviewId(sqlRowSet.getInt("review_id"));
        return review;
    }
}
