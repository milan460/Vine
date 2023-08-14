package com.techelevator.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Seller {

    private int favorites_id;
    private String description;
    private BigDecimal price;
    private boolean isAvailable;
    private int stockQuantity;
    private String username;

    private int plantId;

    public Seller(int favorites_id, String description, BigDecimal price, boolean isAvailable, int stockQuantity, String username, int plantId) {
        this.favorites_id = favorites_id;
        this.description = description;
        this.price = price;
        this.isAvailable = isAvailable;
        this.stockQuantity = stockQuantity;
        this.username = username;
        this.plantId = plantId;
    }

    public Seller(int favorites_id, String description, BigDecimal price, boolean isAvailable, int stockQuantity) {
        this.favorites_id = favorites_id;
        this.description = description;
        this.price = price;
        this.isAvailable = isAvailable;
        this.stockQuantity = stockQuantity;
    }

    public Seller() {

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

    public int getFavorites_id() {
        return favorites_id;
    }

    public void setFavorites_id(int favorites_id) {
        this.favorites_id = favorites_id;
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

    @Override
    public String toString() {
        return "Seller{" +
                "favorites_id=" + favorites_id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                ", stockQuantity=" + stockQuantity +
                ", username=" + username +
                ", plantId=" + plantId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seller)) return false;
        Seller seller = (Seller) o;
        return favorites_id == seller.favorites_id && isAvailable == seller.isAvailable && stockQuantity == seller.stockQuantity && username == seller.username && plantId == seller.plantId && description.equals(seller.description) && price.equals(seller.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(favorites_id, description, price, isAvailable, stockQuantity, username, plantId);
    }
}

