package com.techelevator.model;

public class FavoriteDto {
    private String username;
    private int plantId;



    public FavoriteDto(String username, int plantId) {
        this.username = username;
        this.plantId = plantId;
    }
    public FavoriteDto(){

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
}
