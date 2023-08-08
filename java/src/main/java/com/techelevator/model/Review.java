package com.techelevator.model;

public class Review {

    private String username;
    private String title;
    private String reviewDetail;
    private int rating;


    public Review (String username, String title, String reviewDetail, int rating){
        this.username = username;
        this.title = title;
        this.reviewDetail = reviewDetail;
        this.rating = rating;
    }

    public Review(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReviewDetail() {
        return reviewDetail;
    }

    public void setReviewDetail(String reviewDetail) {
        this.reviewDetail = reviewDetail;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
