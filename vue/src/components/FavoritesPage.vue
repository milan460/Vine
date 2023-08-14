<template>
  <div>
    <div v-for="favoriteItem in favoriteList" v-bind:key="favoriteItem.favoriteId" >
       <b-card
            :title="favoriteItem.plantObj.common_name"
            v-bind:img-src="favoriteItem.plantObj.default_image.medium_url"
            alt="Plant Image"
           img-left class="mb-3"
            style="max-width: 20rem"
         
          >
            <b-card-text>
              
            </b-card-text>

            <b-button href="#" @click="removeFromfavoritesDatabase(favoriteItem.favoriteId)" variant="primary">delete From Favorites</b-button>
            <b-button href="#" @click="updateOwned(favoriteItem.favoriteId)" variant="secondary">Own this Plant?</b-button>
            <b-button href="#" v-if="favoriteItem.ownedPlant === true" @click="sendToSellerForm" variant="secondary">Sell this Plant?</b-button>
          </b-card>
    </div>
    
  </div>
</template>

<script>
import PlantData from "../services/PlantData"
import FavoriteService from "../services/FavoriteService";
// import PlantCare from "../components/PlantCare.vue"

export default {
  components: {
    // PlantCare
  },
  data() {
    return {
      favoriteList: [
        {
          favoriteId: '',
          plantId: '',
          username: '',
          ownedPlant: '',
          plantObj: {}
        }
      ],
      currentPlantId: 0
    };
  },

  methods: {
    updateOwned(favoriteId){
      FavoriteService.updateFavoriteOwnedPlant(favoriteId).then( (response) => {
        console.log(response)
        if(response.status === 200){
          console.log('this went thru')
        }
      })
    },
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
              ownedPlant: favoriteItem.ownedPlant,
              plantObj: favoriteItem.plantObj
            }
          });
          this.getPlantData()
          console.log('this is the favorite list')
          console.log(this.favoriteList)
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
  checkThumbnail(default_image) {
      if (default_image === null) {
        return "https://static.vecteezy.com/system/resources/previews/024/551/617/original/gardening-houseplant-error-404-flash-message-environmental-friendly-watering-plant-empty-state-ui-design-page-not-found-popup-cartoon-image-flat-illustration-concept-on-white-background-vector.jpg";
      }

      return default_image;
    },

    sendToSellerForm(){
      this.$router.push()
    }
    
     
  },
  created() {
     this.showFavoritesList(),
     this.currentPlantId = this.$store.state.active;
  },
};
</script>

<style scoped>
</style> 