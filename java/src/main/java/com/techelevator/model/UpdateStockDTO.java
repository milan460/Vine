package com.techelevator.model;

public class UpdateStockDTO {
    private int favoriteId;
    private int requestQty;

    public UpdateStockDTO(){

    }

    public UpdateStockDTO(int favoriteId, int requestQuantity) {
        this.favoriteId = favoriteId;
        this.requestQty = requestQuantity;
    }

    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public int getRequestQuantity() {
        return requestQty;
    }

    public void setRequestQuantity(int requestQuantity) {
        this.requestQty = requestQuantity;
    }
}
