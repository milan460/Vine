package com.techelevator.model;

public class UpdateStockDTO {
    private int favoriteId;
    private int requestQuantity;

    public UpdateStockDTO(){}

    public UpdateStockDTO(int favoriteId, int requestQuantity) {
        this.favoriteId = favoriteId;
        this.requestQuantity = requestQuantity;
    }

    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public int getRequestQuantity() {
        return requestQuantity;
    }

    public void setRequestQuantity(int requestQuantity) {
        this.requestQuantity = requestQuantity;
    }
}
