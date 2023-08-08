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
        String sql = "INSERT INTO reviews (username, title, review_detail, rating) VALUES ('admin', 'Awful', 'idk', 1) RETURNING review_id ";
        int newReview = jdbcTemplate.queryForObject(sql, int.class, review.getUsername(), review.getTitle(), review.getReviewDetail(), review.getRating());
        review.setReviewId(newReview);
    }

    @Override
    public void updateReview(Review review) {
        String sql = "UPDATE reviews SET username = ?, title = ?, review_detail = ?, rating = ? WHERE review_id = ?";
        jdbcTemplate.update(sql, review.getUsername(), review.getTitle(), review.getReviewDetail(), review.getRating(), review.getReviewId());

    }

    @Override
    public Review getRating(Review review) {
      //Don't know if it's necessary or not
        return null;
    }

    @Override
    public List<Review> listOfReview() {
        List<Review> review = new ArrayList<>();
        String sql = "SELECT username, title, review_detail, rating FROM reviews";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            Review review1 = mapToRowReview(results);
            review.add(review1);
        }
        return review;
    }

    @Override
    public void deleteReview(int reviewId) {
        String sql = "DELETE FROM reviews WHERE review_id = ?";
        jdbcTemplate.update(sql, reviewId);

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
