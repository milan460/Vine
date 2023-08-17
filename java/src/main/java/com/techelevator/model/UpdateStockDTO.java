package com.techelevator.model;

public class UpdateStockDTO {
    private int listingId;
    private int favoriteId;
    private int requestQty;

    public UpdateStockDTO(){

    }

    public UpdateStockDTO(int listingId, int requestQuantity, int favoriteId) {
        this.listingId = listingId;
        this.requestQty = requestQuantity;
        this.favoriteId = favoriteId;
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

    public int getListingId() {
        return listingId;
    }

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }
}
