package com.techelevator.dao;

import com.techelevator.model.Review;

import java.util.List;

public interface ReviewDao {
    Review getReviewByPlantId (int plantId);
    void addReview (Review review);

//    Review getRating (Review review);
    List<Review> listOfReview();
    void deleteReview (int reviewId);





}
