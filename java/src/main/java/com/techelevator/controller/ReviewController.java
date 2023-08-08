package com.techelevator.controller;

import com.techelevator.dao.ReviewDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
public class ReviewController {
    @Autowired
    private final ReviewDao reviewDao;

    @Autowired
    private final UserDao userDao;

    public ReviewController(ReviewDao reviewDao, UserDao userDao) {
        this.reviewDao = reviewDao;
        this.userDao = userDao;
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/review/{id}", method = RequestMethod.GET)
    public Review getReview(@PathVariable int id){
        Review review = reviewDao.getReviewByPlantId(id);

        return review;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/review", method = RequestMethod.POST)
    public Review addReview(@RequestBody Review review, Principal principal){
        review.setUsername(principal.getName());

            reviewDao.addReview(review);

        return review;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path="/review/{id}", method = RequestMethod.PUT)
    public Review deleteReview (@PathVariable int reviewId, Principal principal){
        Review review = new Review();

        review.setUsername(principal.getName());
        reviewDao.deleteReview(reviewId);
        return review;
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/review", method = RequestMethod.GET)
    public List<Review> listOfReviews(){
        return reviewDao.listOfReview();
    }


}
