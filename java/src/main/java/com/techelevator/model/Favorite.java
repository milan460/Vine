package com.techelevator.model;

import java.util.Objects;

public class Favorite {
    private int favoriteId;
    private int plantId;
    private String username;


    public Favorite(int favoriteId, int plantId, String username) {
        this.favoriteId = favoriteId;
        this.plantId = plantId;
        this.username = username;
    }

    public Favorite(){

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

    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "favoriteId=" + favoriteId +
                ", plantId=" + plantId +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorite favorite = (Favorite) o;
        return favoriteId == favorite.favoriteId && plantId == favorite.plantId && username.equals(favorite.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(favoriteId, plantId, username);
    }
}
