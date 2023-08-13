package com.techelevator.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Seller {

    private int sellerId;
    private int favoriteId;
    private int plantId;
    private String username;

    private BigDecimal price;

    private boolean isAvailable;

    public Seller(int sellerId,int favoriteId, int plantId, String userName, BigDecimal price, boolean isAvailable){
        this.sellerId = sellerId;
        this.favoriteId = favoriteId;
        this.plantId = plantId;
        this.username = userName;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public Seller(){

    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "sellerId=" + sellerId +
                ", favoriteId=" + favoriteId +
                ", plantId=" + plantId +
                ", username='" + username + '\'' +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seller)) return false;
        Seller seller = (Seller) o;
        return sellerId == seller.sellerId && favoriteId == seller.favoriteId && plantId == seller.plantId && isAvailable == seller.isAvailable && username.equals(seller.username) && price.equals(seller.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sellerId, favoriteId, plantId, username, price, isAvailable);
    }
}
