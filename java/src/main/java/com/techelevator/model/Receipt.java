package com.techelevator.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Receipt {
    private int receiptId;
    private String fromUsername;
    private String toUsername;
    private BigDecimal price;
    private int plantId;

    public Receipt(int receiptId, String fromUsername, String toUsername, BigDecimal price, int plantId) {
        this.receiptId = receiptId;
        this.fromUsername = fromUsername;
        this.toUsername = toUsername;
        this.price = price;
        this.plantId = plantId;
    }

    public Receipt() {
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "receiptId=" + receiptId +
                ", fromUsername='" + fromUsername + '\'' +
                ", toUsername='" + toUsername + '\'' +
                ", price=" + price +
                ", plantId=" + plantId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Receipt)) return false;
        Receipt receipt = (Receipt) o;
        return receiptId == receipt.receiptId && plantId == receipt.plantId && fromUsername.equals(receipt.fromUsername) && toUsername.equals(receipt.toUsername) && price.equals(receipt.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiptId, fromUsername, toUsername, price, plantId);
    }
}
