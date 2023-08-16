<template>
  <div>
    <div>
      <h1>Favorites</h1>
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
          <div class="accordion" role="tablist">
            <b-card no-body class="mb-1">
              <b-card-header header-tag="header" class="p-1" role="tab">
                <b-button block v-b-toggle.accordion-1 variant="info"
                  >Watering</b-button
                >
              </b-card-header>
              <b-collapse
                id="accordion-1"
                visible
                accordion="my-accordion"
                role="tabpanel"
              >
                <b-card-body>
                  <b-card-text>{{
                    favoriteItem.wateringInstructions
                  }}</b-card-text>
                </b-card-body>
              </b-collapse>
            </b-card>

            <b-card no-body class="mb-1">
              <b-card-header header-tag="header" class="p-1" role="tab">
                <b-button block v-b-toggle.accordion-2 variant="info"
                  >Sunlight</b-button
                >
              </b-card-header>
              <b-collapse
                id="accordion-2"
                accordion="my-accordion"
                role="tabpanel"
              >
                <b-card-body>
                  <b-card-text>{{ favoriteItem.sunInstructions }}</b-card-text>
                </b-card-body>
              </b-collapse>
            </b-card>

            <b-card no-body class="mb-1">
              <b-card-header header-tag="header" class="p-1" role="tab">
                <b-button block v-b-toggle.accordion-3 variant="info"
                  >Pruning</b-button
                >
              </b-card-header>
              <b-collapse
                id="accordion-3"
                accordion="my-accordion"
                role="tabpanel"
              >
                <b-card-body>
                  <b-card-text>{{
                    favoriteItem.pruningInstructions
                  }}</b-card-text>
                </b-card-body>
              </b-collapse>
            </b-card>
          </div>
        </b-card-text>

        <b-button
          class="btns"
          id="own"
          href="#"
          @click="updateOwned(favoriteItem.favoriteId)"
          variant="secondary"
          >Own?</b-button
        >
        <img
          id="delete"
          src="../assets/trash-can.png"
          @click="removeFromfavoritesDatabase(favoriteItem.favoriteId)"
        />
      </b-card>
    </div>

    <!--the div and b-card containers below are the filterFavorite Plants that the user does own -->

    <div>
      <h1>Personal Garden</h1>
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
          <div class="accordion" role="tablist">
            <b-card no-body class="mb-1">
              <b-card-header header-tag="header" class="p-1" role="tab">
                <b-button block v-b-toggle.accordion-1 variant="info"
                  >Watering</b-button
                >
              </b-card-header>
              <b-collapse
                id="accordion-1"
                visible
                accordion="my-accordion"
                role="tabpanel"
              >
                <b-card-body>
                  <b-card-text>{{
                    favoriteItem.wateringInstructions
                  }}</b-card-text>
                </b-card-body>
              </b-collapse>
            </b-card>

            <b-card no-body class="mb-1">
              <b-card-header header-tag="header" class="p-1" role="tab">
                <b-button block v-b-toggle.accordion-2 variant="info"
                  >Sunlight</b-button
                >
              </b-card-header>
              <b-collapse
                id="accordion-2"
                accordion="my-accordion"
                role="tabpanel"
              >
                <b-card-body>
                  <b-card-text>{{ favoriteItem.sunInstructions }}</b-card-text>
                </b-card-body>
              </b-collapse>
            </b-card>

            <b-card no-body class="mb-1">
              <b-card-header header-tag="header" class="p-1" role="tab">
                <b-button block v-b-toggle.accordion-3 variant="info"
                  >Pruning</b-button
                >
              </b-card-header>
              <b-collapse
                id="accordion-3"
                accordion="my-accordion"
                role="tabpanel"
              >
                <b-card-body>
                  <b-card-text>{{
                    favoriteItem.pruningInstructions
                  }}</b-card-text>
                </b-card-body>
              </b-collapse>
            </b-card>
          </div>
        </b-card-text>

        <b-button
          id="sell"
          href="#"
          v-if="favoriteItem.ownedPlant === true"
          @click="sendToSellerForm(favoriteItem.favoriteId)"
          variant="secondary"
          >Sell?</b-button
        >
        <img
          id="delete"
          src="../assets/trash-can.png"
          @click="removeFromfavoritesDatabase(favoriteItem.favoriteId)"
        />
      </b-card>
    </div>
  </div>
</template>

<script>
import PlantData from "../services/PlantData.js";
import FavoriteService from "../services/FavoriteService";
import SellerService from "../services/SellerService.js";

export default {
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
  computed: {
    filteredOwnedPlants() {
      return this.favoriteList.filter(
        (favoriteItem) => favoriteItem.ownedPlant === true
      );
    },
    filteredWishListPlants() {
      return this.favoriteList.filter(
        (favoriteItem) => favoriteItem.ownedPlant === false
      );
    },
  },
  methods: {
    updateOwned(favoriteId, ownedPlant) {
      ownedPlant = !ownedPlant;
      FavoriteService.updateFavoriteOwnedPlant(favoriteId, ownedPlant).then(
        (response) => {
          
          if (response.status === 200) {
            window.location.reload();
          }
        }
      );
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
.mb-3 {
  box-shadow: 3px 3px 3px 5px gray;
  width: 80vw;
  /* margin: 8%; */
  margin-left: 8%;
  margin-right: 8%;
  margin-top: 4%;
}

#delete {
  height: 6vh;
  margin-left: 79%;
  margin-top: 3%;
}
h1 {
  margin-top: 3%;
  margin-left: 8%;
}
#own {
  margin-top: 3%;
  margin-right: 5%;
}
</style> 