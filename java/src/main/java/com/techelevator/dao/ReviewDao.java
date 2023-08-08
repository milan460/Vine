package com.techelevator.dao;

import com.techelevator.model.Review;

import java.util.List;

public interface ReviewDao {
    Review getReview (int reviewId);
    void addReview (Review review);
    void updateReview (Review review);

    Review getRating (Review review);
    List<Review> listOfReview();
    void deleteReview (int reviewId);





}
