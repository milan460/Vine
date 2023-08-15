package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Favorite {
    private int favoriteId;
    private int plantId;
    private String username;
    @JsonProperty(value = "ownedPlant")
    private boolean ownedPlant;


    public Favorite(int favoriteId, int plantId, String username, boolean ownedPlant) {
        this.favoriteId = favoriteId;
        this.plantId = plantId;
        this.username = username;
        this.ownedPlant = ownedPlant;
    }

    public Favorite() {
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

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isOwnedPlant() {
        return ownedPlant;
    }

    public void setOwnedPlant(boolean ownedPlants) {
        this.ownedPlant = ownedPlants;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "favoriteId=" + favoriteId +
                ", plantId=" + plantId +
                ", username='" + username + '\'' +
                ", ownedPlants=" + ownedPlant +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Favorite)) return false;
        Favorite favorite = (Favorite) o;
        return favoriteId == favorite.favoriteId && plantId == favorite.plantId && ownedPlant == favorite.ownedPlant && username.equals(favorite.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(favoriteId, plantId, username, ownedPlant);
    }
}
