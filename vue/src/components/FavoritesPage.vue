<template>
  <div>
    <div v-for="favoriteItem in favoriteList" v-bind:key="favoriteItem.favoriteId" >
      <div>
        {{this.currentPlantId}}
      <!-- <b-card
            :title="favoriteItem.plantObj.common_name"
            v-bind:img-src="favoriteItem.plantObj.default_image.medium_url"
            alt="Plant Image"
            img-top
            tag="article"
            style="max-width: 20rem"
            class="mb-2 flex1"
          >
            <b-card-text>
              <img src="../assets/indoor-plants.png" v-if="checkIndoorPlants === true" :title="plant.indoor"> //indoor
              <img
          class="ratingStar"
          src="../assets/drop.png"
          v-bind:title="favoriteItem.plantObj.watering"
        /> //watering
              <ul v-for="sunlight in favoriteItem.plantObj.sunlight" v-bind:key="sunlight">
                <ol>
                  <img
          class="ratingStar"
          src="../assets/sun.png"
          v-bind:title="favoriteItem.plantObj.sunlight"
        /> //sunlight
                </ol>

              </ul>
            </b-card-text>

            <b-button href="#" @click="removeFromfavoritesDatabase(favoriteItem.favoriteId)" variant="primary">delete From Favorites</b-button>
          </b-card> -->
      </div>
      <plant-care :plantId="currentPlantId" />
    </div>
    
  </div>
</template>

<script>
import PlantData from "../services/PlantData"
import FavoriteService from "../services/FavoriteService";
import PlantCare from "../components/PlantCare.vue"

export default {
  components: {
    PlantCare
  },
  data() {
    return {
      favoriteList: [
        {
          favoriteId: '',
          plantId: '',
          username: '',
          plantObj: {}
          
          }
      ],
      currentPlantId: 0
    };
  },

  methods: {
    showFavoritesList() {
      FavoriteService.getFavoritesList().then((response) => {
        if (response.status === 200) {
          console.log('this is the response error')
          console.log(response)
          this.favoriteList = response.data.map( (favoriteItem) => {
            return {
              favoriteId: favoriteItem.favoriteId,
              plantId: favoriteItem.plantId,
              username: favoriteItem.username,
              plantObj: favoriteItem.plantObj
            }
          });
          this.getPlantData()
        }
      });
    },

    getPlantData(){
      this.favoriteList.forEach(favorite => {
        PlantData.getPlantDetails(favorite.plantId).then(response =>{
          if(response.status === 200){
            favorite.plantObj = response.data
          }
        })
      })
    },
    removeFromfavoritesDatabase(favoriteId){
      FavoriteService.deleteFromFavoritesWithPlantId(favoriteId).then((response) => {
        if (response.status === 200){
        this.deleteFromFavoritesDisplay(favoriteId);
        }
      })
    },

    deleteFromFavoritesDisplay(deletedFavoriteId){
     // Remove the entry with the deleted reviewId from favoriteList
    this.favoriteList = this.favoriteList.filter(item => item.favoriteId !== deletedFavoriteId);
  },
    testMethod(){
    
    }
    
     
  },
  created() {
     this.showFavoritesList(),
     this.currentPlantId = this.$store.state.active;
  },
};
</script>

<style>
</style>