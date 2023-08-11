<template>
  <div>
    <div v-for="plant in plantObject" v-bind:key="plant.id">
      <b-card
            :title="plant.common_name"
            v-bind:img-src="plant.default_image.original_url"
            alt="Plant Image"
            img-top
            tag="article"
            style="max-width: 20rem"
            class="mb-2 flex1"
          >
            <b-card-text>
              <img src="../assets/indoor-plants.png" v-if="checkIndoorPlants == true" :title="plant.indoor"> //indoor
              <img
          class="ratingStar"
          src="../assets/drop.png"
          v-bind:title="plant.watering"
        /> //watering
              <ul v-for="sunlight in plant.sunlight" v-bind:key="sunlight">
                <ol>
                  <img
          class="ratingStar"
          src="../assets/sun.png"
          v-bind:title="plant.sunlight"
        /> //sunlight
                </ol>

              </ul>
            </b-card-text>

            <b-button href="#" @click="removeFromfavoritesDatabase" variant="primary">delete From Favorites</b-button>
          </b-card>


    </div>
  </div>
</template>

<script>
import PlantData from "../services/PlantData"
import FavoriteService from "../services/FavoriteService";
export default {
  data() {
    return {
      favoriteList: [],
      plantObject: [],
    };
  },

  methods: {
    showFavoritesList() {
      FavoriteService.getFavoritesList().then((response) => {
        if (response.status === 200) {
          this.favoriteList = response.data;
          this.getPlantData()
        }
      });
    },

    getPlantData(){
      this.favoriteList.forEach(favorite => {
        console.log("this is the plant Id")
        console.log(favorite.plantId)
        console.log("this is the favoriteID")
        console.log(favorite.favoriteId)
        PlantData.getPlantDetails(favorite.plantId).then(response =>{
          if(response.status === 200){
            console.log("Plant data")
            console.log(response.data)
            this.plantObject.push(response.data)
          }
        })
      })
    },
    removeFromfavoritesDatabase(){
      console.log("this is favoriteID")
        console.log(this.favoriteList.favoriteId)
      const deletedReviewId = FavoriteService.deleteFromFavorites(this.favoriteList.favoriteId).then((response) => {
        if (response.status === 200){
          console.log("this is the deleted reviewId")
          console.log(deletedReviewId)
        this.deleteFromFavoritesDisplay(deletedReviewId);
        }
      })
    },

    deleteFromFavoritesDisplay(deletedReviewId){
     // Remove the entry with the deleted reviewId from favoriteList
    this.favoriteList = this.favoriteList.filter(item => item.reviewId !== deletedReviewId);

    // Remove the corresponding entry from plantObject based on the deleted reviewId
    this.plantObject = this.plantObject.filter(plant => {
      const matchingFavorite = this.favoriteList.find(favorite => favorite.plantId === plant.id);
      return matchingFavorite !== undefined;
    });
  }
    
     
  },
  created() {
     this.showFavoritesList()
     
     
  },
};
</script>

<style>
</style>