<template>
  <div>
    <div>
      <h1>Your Wish List</h1>
    </div>
       <div
      v-for="favoriteItem in filteredWishListPlants"
      v-bind:key="favoriteItem.favoriteId"
    >
      <b-card
        :title="favoriteItem.plantObj.common_name"
        v-bind:img-src="favoriteItem.plantObj.default_image.medium_url"
        alt="Plant Image"
        img-left
        class="mb-3"
      >
        <b-card-text>
          <strong>Watering Instructions: </strong>
          {{ favoriteItem.wateringInstructions }}
          <strong> Sun Instructions: </strong>
          {{ favoriteItem.sunInstructions }}
          <strong> Pruning Instructions: </strong>
          {{ favoriteItem.pruningInstructions }}
        </b-card-text>

        <b-button
          href="#"
          @click="removeFromfavoritesDatabase(favoriteItem.favoriteId)"
          variant="primary"
          >Delete</b-button
        >
        <b-button
          href="#"
          @click="updateOwned(favoriteItem.favoriteId)"
          variant="secondary"
          >Owned?</b-button
        >
          <b-button
            href="#"
            v-if="favoriteItem.ownedPlant === true"
            @click="sendToSellerForm(favoriteItem.favoriteId)"
            variant="secondary"
            >Sell</b-button
          >
      </b-card>
    </div>

    <!--the div and b-card containers below are the filterFavorite Plants that the user does own -->

    <div>
      <h1>Your Favorites</h1>
    </div>
       <div
      v-for="favoriteItem in filteredOwnedPlants"
      v-bind:key="favoriteItem.favoriteId"
    >
      <b-card
        :title="favoriteItem.plantObj.common_name"
        v-bind:img-src="favoriteItem.plantObj.default_image.medium_url"
        alt="Plant Image"
        img-left
        class="mb-3"
      >
        <b-card-text>
          <strong>Watering Instructions: </strong>
          {{ favoriteItem.wateringInstructions }}
          <strong> Sun Instructions: </strong>
          {{ favoriteItem.sunInstructions }}
          <strong> Pruning Instructions: </strong>
          {{ favoriteItem.pruningInstructions }}
        </b-card-text>

        <b-button
          href="#"
          @click="removeFromfavoritesDatabase(favoriteItem.favoriteId)"
          variant="primary"
          >Delete</b-button
        >
        <b-button
          href="#"
          @click="updateOwned(favoriteItem.favoriteId, favoriteItem.ownedPlant)"
          variant="secondary"
          >Owned?</b-button
        >
          <b-button
            href="#"
            v-if="favoriteItem.ownedPlant === true"
            @click="sendToSellerForm(favoriteItem.favoriteId)"
            variant="secondary"
            >Sell</b-button
          >
      </b-card>
    </div>




    
  </div>
</template>

<script>
import PlantData from "../services/PlantData.js";
import FavoriteService from "../services/FavoriteService";
import SellerService from "../services/SellerService.js";
// import PlantCare from "../components/PlantCare.vue"

export default {
  components: {
    // PlantCare
  },
  data() {
    return {
      favoriteList: [
        {
          favoriteId: "",
          plantId: "",
          username: "",
          ownedPlant: false,
          plantObj: {},
        },
      ],
      currentPlantId: 0,
    };
  },
  computed:{
    filteredOwnedPlants() {
      return this.favoriteList.filter((favoriteItem) => favoriteItem.ownedPlant === true);
    },
    filteredWishListPlants() {
      return this.favoriteList.filter((favoriteItem) => favoriteItem.ownedPlant === false);
    },
  },
  methods: {
    updateOwned(favoriteId, ownedPlant) {
      ownedPlant = !ownedPlant
      FavoriteService.updateFavoriteOwnedPlant(favoriteId, ownedPlant).then((response) => {
        console.log(response);
        if (response.status === 200) {
          console.log("this went thru");
        }
      });
    },
    showFavoritesList() {
      FavoriteService.getFavoritesList().then((response) => {
        if (response.status === 200) {
          this.favoriteList = response.data.map((favoriteItem) => {
            return {
              favoriteId: favoriteItem.favoriteId,
              plantId: favoriteItem.plantId,
              username: favoriteItem.username,
              ownedPlant: favoriteItem.ownedPlant,
              plantObj: favoriteItem.plantObj,
              wateringInstructions: "",
              sunInstructions: "",
              pruningInstructions: "",
            };
          });
          this.getPlantData();
          this.getWateringInstructions();
          this.getSunInstructions();
          this.getPruningInstructions();
        }
      });
    },

    getWateringInstructions() {
      this.favoriteList.forEach((favorite) => {
        PlantData.getPlantCareData(favorite.plantId)
          .then((response) => {
            favorite.wateringInstructions =
              response.data.data[0].section[0].description;
          })
          .catch((error) => {
            console.log(error);
          });
      });
    },
    getSunInstructions() {
      this.favoriteList.forEach((favorite) => {
        PlantData.getPlantCareData(favorite.plantId)
          .then((response) => {
            favorite.sunInstructions =
              response.data.data[0].section[1].description;
          })
          .catch((error) => {
            console.log(error);
          });
      });
    },
    getPruningInstructions() {
      this.favoriteList.forEach((favorite) => {
        PlantData.getPlantCareData(favorite.plantId)
          .then((response) => {
            favorite.pruningInstructions =
              response.data.data[0].section[2].description;
          })
          .catch((error) => {
            console.log(error);
          });
      });
    },

    getPlantData() {
      this.favoriteList.forEach((favorite) => {
        
        PlantData.getPlantDetails(favorite.plantId).then((response) => {
          if (response.status === 200) {
            favorite.plantObj = response.data;
          }
        });
      });
    },

    
    sendToSellerForm(favoriteId) {
      this.$router.push({ name: "listing", params: { id: favoriteId } });
    },
    removeFromfavoritesDatabase(favoriteId) {
      SellerService.deleteListing(favoriteId).then((response) => {
        if (response.status === 200) {
          FavoriteService.deleteFromFavoritesWithPlantId(favoriteId).then(
            (response) => {
              if (response.status === 200) {
                this.deleteFromFavoritesDisplay(favoriteId);
              }
            }
          );
        }
      });
    },

    deleteFromFavoritesDisplay(deletedFavoriteId) {
      // Remove the entry with the deleted reviewId from favoriteList
      this.favoriteList = this.favoriteList.filter(
        (item) => item.favoriteId !== deletedFavoriteId
      );
    },
    checkThumbnail(default_image) {
      if (default_image === null) {
        return "https://static.vecteezy.com/system/resources/previews/024/551/617/original/gardening-houseplant-error-404-flash-message-environmental-friendly-watering-plant-empty-state-ui-design-page-not-found-popup-cartoon-image-flat-illustration-concept-on-white-background-vector.jpg";
      }

      return default_image;
    },
  },
  created() {
    this.showFavoritesList(), (this.currentPlantId = this.$store.state.active);
  },
};
</script>

<style scoped>
</style> 