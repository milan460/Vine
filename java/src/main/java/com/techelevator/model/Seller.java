package com.techelevator.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Seller {

    private int plantSellerId;
    private String username;
    private int plantId;
    private String description;

    private BigDecimal price;

    private int stockQuantity;

    public Seller(int plantSellerId, String username, int plantId, String description, BigDecimal price, int stockQuantity) {
        this.plantSellerId = plantSellerId;
        this.username = username;
        this.plantId = plantId;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Seller() {

    }

    public int getPlantSellerId() {
        return plantSellerId;
    }

    public void setPlantSellerId(int plantSellerId) {
        this.plantSellerId = plantSellerId;
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

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "plantSellerId=" + plantSellerId +
                ", username='" + username + '\'' +
                ", plantId=" + plantId +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seller)) return false;
        Seller seller = (Seller) o;
        return plantSellerId == seller.plantSellerId && plantId == seller.plantId && stockQuantity == seller.stockQuantity && username.equals(seller.username) && description.equals(seller.description) && price.equals(seller.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plantSellerId, username, plantId, description, price, stockQuantity);
    }
}
