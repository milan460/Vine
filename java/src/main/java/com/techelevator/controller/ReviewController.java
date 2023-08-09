package com.techelevator.controller;

import com.techelevator.dao.ReviewDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class ReviewController {
    @Autowired
    private final ReviewDao reviewDao;

    @Autowired
    private final UserDao userDao;

    public ReviewController(ReviewDao reviewDao, UserDao userDao) {
        this.reviewDao = reviewDao;
        this.userDao = userDao;
    }
    @RequestMapping(path = "/review/{id}", method = RequestMethod.GET)
    public Review getReview(@PathVariable int reviewId){
        Review review = reviewDao.getReview(reviewId);

        return review;
    }
    @RequestMapping(path = "/review", method = RequestMethod.POST)
    public Review addReview(@RequestBody Review review, Principal principal){
        review.setUsername(principal.getName());


    }
}
