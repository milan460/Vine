package com.techelevator.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Seller {

    private int favoritesId;
    private String description;
    private BigDecimal price;
    private boolean isAvailable;
    private int stockQuantity;
    private String username;
    private int plantId;

    public Seller(int favoritesId, String description, BigDecimal price, boolean isAvailable, int stockQuantity, String username, int plantId) {
        this.favoritesId = favoritesId;
        this.description = description;
        this.price = price;
        this.isAvailable = isAvailable;
        this.stockQuantity = stockQuantity;
        this.username = username;
        this.plantId = plantId;
    }

    public Seller(int favorites_id, String description, BigDecimal price, boolean isAvailable, int stockQuantity) {
        this.favoritesId = favorites_id;
        this.description = description;
        this.price = price;
        this.isAvailable = isAvailable;
        this.stockQuantity = stockQuantity;
    }

    public Seller() {
    }

    public int getFavoritesId() {
        return favoritesId;
    }

    public void setFavoritesId(int favoritesId) {
        this.favoritesId = favoritesId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "favoritesId=" + favoritesId +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                ", stockQuantity=" + stockQuantity +
                ", username='" + username + '\'' +
                ", plantId=" + plantId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seller)) return false;
        Seller seller = (Seller) o;
        return favoritesId == seller.favoritesId && isAvailable == seller.isAvailable && stockQuantity == seller.stockQuantity && plantId == seller.plantId && description.equals(seller.description) && price.equals(seller.price) && username.equals(seller.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(favoritesId, description, price, isAvailable, stockQuantity, username, plantId);
    }
}

