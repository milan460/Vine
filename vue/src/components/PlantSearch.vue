<template>
  <div id="main">
    <div id="filters">
      <div class="search-container">
        
        <b-form-input
          id="src"
          size="sm"
          class="mr-sm-2"
          placeholder="Search"
          v-model="filter.common_name"
        > </b-form-input>
        
          <button
          id="plant-query"
          @click="findPlantByCommonName(filter.common_name)"
        >
          Search
        </button>
        
        
      </div>
      <button id="button" @click="filterToggle">Filter</button>

      <div id="filterCheckbox" v-if="filterToggleOn">

        <label for="indoorPlants">
          <button
            id="btn"
            :class=" {'active': indoorButtonClicked} "
            :disabled="outdoorButtonClicked === true || edibleButtonClicked === true"
            @click="
              checkIndoorPlants();
            "
          >
            Indoor
          </button>
        </label>

        <label for="outdoorPlants">
          <button
            id="btn"
            :class=" {'active': outdoorButtonClicked} "
            :disabled="indoorButtonClicked === true || edibleButtonClicked === true"
            @click="checkOutdoorPlants"
          >
            Outdoor
          </button>
        </label>

        <label for="ediblePlants">
          <button
            id="btn"
            :class=" {'active': edibleButtonClicked} "
            :disabled="indoorButtonClicked === true || outdoorButtonClicked === true"
            @click="checkEdiblePlants"
          >
            Edible
          </button>
        </label>
      </div>
    </div>
    <!-- <div class="loading" v-if="isLoading">
      <img src="../assets/ping_pong_loader.gif" />
    </div> -->
    <div id="indoorPlants">
      <div id="cards" v-for="plant in filteredList" v-bind:key="plant.id">
        <router-link
          class="cardLink"
          :to="{ name: 'plant-detail', params: { id: plant.id } }"
        >
          <b-card id="card"
            :title="plant.common_name"
            v-bind:img-src="plant.medium_url"
            alt="Plant Image"
            img-top
            tag="article"
            style="max-width: 20rem"
            class="mb-2 flex1"
          >
            <!-- <b-card-img
              :src="plant.medium_url"
              :alt="plant.common_name"
              top
              style="max-width: 100%; max-height: 20rem"
            /> -->
            <b-card-text>
              <img
                id="water"
                src="../assets/indoor-plants.png"
                v-if="indoorFilterOn"
                :title="plant.indoor"
              />
              <img
                class="ratingStar"
                src="../assets/drop.png"
                v-bind:title="plant.watering"
                v-if="plant.watering == 'Minimum'"
              />
              <img id="water" src="../assets/water.png" v-else />
              <img
                class="ratingStar"
                src="../assets/sun.png"
                v-bind:title="plant.sunlight"
                v-if="plant.sunlight == 'full sun'"
              />
              <img id="water" src="../assets/partly-cloudy.png" v-else />
            </b-card-text>

            <b-button
              id="favoriteBtn"
              href="#"
              v-if="$store.state.token != ''"
              @click="addToFavorites(plant.id, index); checkbuttonPress();"
              :disabled="plant.itemAlreadyfavorited"
              variant="primary"
              > {{plant.itemAlreadyfavorited === false ? 'Add to Favorites' : 'Added'}}</b-button
            >
          </b-card>

          <b-alert
            v-model="plant.showAlert"
            dismissible
            fade
            variant="success"
            class="mt-3"
          >
            Added to Favorites!
          </b-alert>
        </router-link>
      </div>
    </div>

    <div id="page-arrows">
      <button id="pageDown" @click="decrementPage()">Previous Page</button>
      <button id="pageUp" @click="incrementPage()">Next Page</button>
    </div>
  </div>
</template>

<script>
import FavoriteService from "../services/FavoriteService.js";
import plantData from "../services/PlantData.js";

export default {
  data() {
    return {
      isBtnPressed: false,
      isLoading: true,
      showAlert: false,
      indoorButtonClicked: false,
      outdoorButtonClicked: false,
      edibleButtonClicked: false,
      filterToggleOn: false,
      plants: [],
      indoorPlants: [],
      indoorFilterOn: false,
      outdoorPlants: [],
      outdoorFilterOn: false,
      ediblePlants: [],
      plantSearch: [],
      specificPlantFilter: false,
      edibleFilterOn: false,
      pagecounter: 1,
      sortAlphabetically: false,

      filter: {
        common_name: "",
        cycle: "",
        watering: "",
        sunlight: "",
        imageURL: "",
        scientific_name: "",
        itemAlreadyfavorited: false,
        showAlert: false
        
      },
    };
  },
  async created() {
    plantData.getPlantData(this.pagecounter).then((response) => {
      this.plants = response.data.data.map((plantData) => {
        return {
          id: plantData.id,
          common_name: plantData.common_name,
          cycle: plantData.cycle,
          watering: plantData.watering,
          sunlight: plantData.sunlight,
          medium_url:
            plantData.default_image === null
              ? this.checkThumbnail(plantData.default_image)
              : plantData.default_image.medium_url,
          itemAlreadyfavorited: false,
          showAlert: false
        };
      });
      this.updateFavoritesStatus();
      this.updateIndoorFavorites();
      this.isLoading = false;
    });
  },
  methods: {
    checkbuttonPress(){
      this.isBtnPressed = true;
    },
    async updateFavoritesStatus() {
      const favoritesResponse = await FavoriteService.getFavoritesList();
      if (favoritesResponse.status === 200) {
        const favoritesData = favoritesResponse.data;

        if (this.plants.length !== 0) {
          this.plants.forEach((plant) => {
            const favoriteObj = favoritesData.find(
              (favoriteItem) => favoriteItem.plantId === plant.id
            );

            if (favoriteObj) {
              plant.itemAlreadyfavorited = true;
            }
          });
        }
      }
    },
    updateIndoorFavorites() {
      FavoriteService.getFavoritesList().then((favoritesResponse) => {
        if (favoritesResponse.status === 200) {
          const favoritesData = favoritesResponse.data;

          this.indoorPlants.forEach((plant) => {
            const favoriteObj = favoritesData.find(
              (favoriteItem) => favoriteItem.plantId === plant.id
            );

            if (favoriteObj) {
              plant.itemAlreadyfavorited = true;
            } else {
              plant.itemAlreadyfavorited = false;
            }
          });
        }
      });
    },
    updateOutdoorFavorites() {
      FavoriteService.getFavoritesList().then((favoritesResponse) => {
        if (favoritesResponse.status === 200) {
          const favoritesData = favoritesResponse.data;

          this.outdoorPlants.forEach((plant) => {
            const favoriteObj = favoritesData.find(
              (favoriteItem) => favoriteItem.plantId === plant.id
            );

            if (favoriteObj) {
              plant.itemAlreadyfavorited = true;
            } else {
              plant.itemAlreadyfavorited = false;
            }
          });
        }
      });
    },
    updateEdibleFavorites() {
      FavoriteService.getFavoritesList().then((favoritesResponse) => {
        if (favoritesResponse.status === 200) {
          const favoritesData = favoritesResponse.data;

          this.ediblePlants.forEach((plant) => {
            const favoriteObj = favoritesData.find(
              (favoriteItem) => favoriteItem.plantId === plant.id
            );

            if (favoriteObj) {
              plant.itemAlreadyfavorited = true;
            } else {
              plant.itemAlreadyfavorited = false;
            }
          });
        }
      });
    },
    updatedPlantSearchFavorites() {
      FavoriteService.getFavoritesList().then((favoritesResponse) => {
        if (favoritesResponse.status === 200) {
          const favoritesData = favoritesResponse.data;

          this.plantSearch.forEach((plant) => {
            const favoriteObj = favoritesData.find(
              (favoriteItem) => favoriteItem.plantId === plant.id
            );

            if (favoriteObj) {
              plant.itemAlreadyfavorited = true;
            } else {
              plant.itemAlreadyfavorited = false;
            }
          });
        }
      });
    },
    filterToggle() {
      this.filterToggleOn = !this.filterToggleOn;
    },
    checkThumbnail(default_image) {
      if (default_image === null) {
        return "https://static.vecteezy.com/system/resources/previews/024/551/617/original/gardening-houseplant-error-404-flash-message-environmental-friendly-watering-plant-empty-state-ui-design-page-not-found-popup-cartoon-image-flat-illustration-concept-on-white-background-vector.jpg";
      }

      return default_image;
    },
    incrementPage() {
      this.pagecounter++;
      if (!this.indoorFilterOn && !this.outdoorFilterOn) {
        plantData.getPlantData(this.pagecounter).then((response) => {
          this.plants = response.data.data.map((plantData) => {
            return {
              id: plantData.id,
              common_name: plantData.common_name,
              cycle: plantData.cycle,
              watering: plantData.watering,
              sunlight: plantData.sunlight,
              medium_url:
                plantData.default_image === null
                  ? this.checkThumbnail(plantData.default_image)
                  : plantData.default_image.medium_url,
              indoor: "",
              itemAlreadyfavorited: false,
              showAlert: false
            };
          });
          this.updateFavoritesStatus();
        });
      } else if (this.indoorFilterOn && !this.outdoorFilterOn) {
        plantData
          .getIndoorOrOutdoorPlants(this.pagecounter, 1)
          .then((response) => {
            this.indoorPlants = response.data.data.map((plantData) => {
              return {
                id: plantData.id,
                common_name: plantData.common_name,
                cycle: plantData.cycle,
                watering: plantData.watering,
                sunlight: plantData.sunlight,
                medium_url:
                  plantData.default_image === null
                    ? this.checkThumbnail(plantData.default_image)
                    : plantData.default_image.medium_url,
                itemAlreadyfavorited: false,
                showAlert: false
              };
            });
            this.updateIndoorFavorites();
          });
      } else if (!this.indoorFilterOn && this.outdoorFilterOn) {
        plantData
          .getIndoorOrOutdoorPlants(this.pagecounter, 0)
          .then((response) => {
            this.outdoorPlants = response.data.data.map((plantData) => {
              return {
                id: plantData.id,
                common_name: plantData.common_name,
                cycle: plantData.cycle,
                watering: plantData.watering,
                sunlight: plantData.sunlight,
                medium_url:
                  plantData.default_image === null
                    ? this.checkThumbnail(plantData.default_image)
                    : plantData.default_image.medium_url,
                itemAlreadyfavorited: false,
                showAlert: false
              };
            });
            this.updateOutdoorFavorites();
          });
      } else if (
        this.edibleFilterOn &&
        !this.indoorFilterOn &&
        !this.indoorFilterOn
      ) {
        plantData.getEdiblePlants(this.pagecounter, 1).then((response) => {
          this.ediblePlants = response.data.data.map((plantData) => {
            return {
              id: plantData.id,
              common_name: plantData.common_name,
              cycle: plantData.cycle,
              watering: plantData.watering,
              sunlight: plantData.sunlight,
              medium_url:
                plantData.default_image === null
                  ? this.checkThumbnail(plantData.default_image)
                  : plantData.default_image.medium_url,
              itemAlreadyfavorited: false,
              showAlert: false
            };
          });
          this.updateEdibleFavorites();
        });
      }
    },
    decrementPage() {
      if (this.pagecounter > 1) {
        this.pagecounter--;
        if (
          !this.indoorFilterOn &&
          !this.outdoorFilterOn &&
          !this.edibleFilterOn
        ) {
          plantData.getPlantData(this.pagecounter).then((response) => {
            this.plants = response.data.data.map((plantData) => {
              return {
                id: plantData.id,
                common_name: plantData.common_name,
                cycle: plantData.cycle,
                watering: plantData.watering,
                sunlight: plantData.sunlight,
                medium_url:
                  plantData.default_image === null
                    ? this.checkThumbnail(plantData.default_image)
                    : plantData.default_image.medium_url,
                indoor: "",
                itemAlreadyfavorited: false,
                showAlert: false
              };
            });
            this.updateFavoritesStatus();
          });
        } else if (
          this.indoorFilterOn &&
          !this.outdoorFilterOn &&
          !this.edibleFilterOn
        ) {
          plantData
            .getIndoorOrOutdoorPlants(this.pagecounter, 1)
            .then((response) => {
              this.indoorPlants = response.data.data.map((plantData) => {
                return {
                  id: plantData.id,
                  common_name: plantData.common_name,
                  cycle: plantData.cycle,
                  watering: plantData.watering,
                  sunlight: plantData.sunlight,
                  medium_url:
                    plantData.default_image === null
                      ? this.checkThumbnail(plantData.default_image)
                      : plantData.default_image.medium_url,
                  itemAlreadyfavorited: false,
                  showAlert: false
                };
              });
              this.updateIndoorFavorites();
            });
        } else if (
          !this.indoorFilterOn &&
          this.outdoorFilterOn &&
          !this.edibleFilterOn
        ) {
          plantData
            .getIndoorOrOutdoorPlants(this.pagecounter, 0)
            .then((response) => {
              this.outdoorPlants = response.data.data.map((plantData) => {
                return {
                  id: plantData.id,
                  common_name: plantData.common_name,
                  cycle: plantData.cycle,
                  watering: plantData.watering,
                  sunlight: plantData.sunlight,
                  medium_url:
                    plantData.default_image === null
                      ? this.checkThumbnail(plantData.default_image)
                      : plantData.default_image.medium_url,
                  itemAlreadyfavorited: false,
                  showAlert: false
                };
              });
              this.updateOutdoorFavorites();
            });
        } else if (
          !this.indoorFilterOn &&
          !this.indoorFilterOn &&
          this.edibleFilterOn
        ) {
          plantData.getEdiblePlants(this.pagecounter, 1).then((response) => {
            this.ediblePlants = response.data.data.map((plantData) => {
              return {
                id: plantData.id,
                common_name: plantData.common_name,
                cycle: plantData.cycle,
                watering: plantData.watering,
                sunlight: plantData.sunlight,
                medium_url:
                  plantData.default_image === null
                    ? this.checkThumbnail(plantData.default_image)
                    : plantData.default_image.medium_url,
                itemAlreadyfavorited: false,
                showAlert: false
              };
            });
            this.updateEdibleFavorites();
          });
        }
      }
    },
    checkIndoorPlants() {
      this.indoorButtonClicked = !this.indoorButtonClicked;
      this.indoorFilterOn = !this.indoorFilterOn;
      plantData
        .getIndoorOrOutdoorPlants(this.pagecounter, 1)
        .then((response) => {
          this.indoorPlants = response.data.data.map((plantData) => {
            return {
              id: plantData.id,
              common_name: plantData.common_name,
              cycle: plantData.cycle,
              watering: plantData.watering,
              sunlight: plantData.sunlight,
              medium_url:
                plantData.default_image === null
                  ? this.checkThumbnail(plantData.default_image)
                  : plantData.default_image.medium_url,
              itemAlreadyfavorited: false,
              showAlert: false
            };
          });
          this.updateIndoorFavorites();
        });
    },

    checkOutdoorPlants() {
      this.outdoorButtonClicked = !this.outdoorButtonClicked;
      this.outdoorFilterOn = !this.outdoorFilterOn;
      plantData
        .getIndoorOrOutdoorPlants(this.pagecounter, 0)
        .then((response) => {
          this.outdoorPlants = response.data.data.map((plantData) => {
            return {
              id: plantData.id,
              common_name: plantData.common_name,
              cycle: plantData.cycle,
              watering: plantData.watering,
              sunlight: plantData.sunlight,
              medium_url:
                plantData.default_image === null
                  ? this.checkThumbnail(plantData.default_image)
                  : plantData.default_image.medium_url,
              itemAlreadyfavorited: false,
              showAlert: false
            };
          });
          this.updateOutdoorFavorites();
        });
    },
    checkEdiblePlants() {
      this.edibleButtonClicked = !this.edibleButtonClicked;
      this.edibleFilterOn = !this.edibleFilterOn;
      plantData.getEdiblePlants(this.pagecounter, 1).then((response) => {
        this.ediblePlants = response.data.data.map((plantData) => {
          return {
            id: plantData.id,
            common_name: plantData.common_name,
            cycle: plantData.cycle,
            watering: plantData.watering,
            sunlight: plantData.sunlight,
            medium_url:
              plantData.default_image === null
                ? this.checkThumbnail(plantData.default_image)
                : plantData.default_image.medium_url,
            itemAlreadyfavorited: false,
            showAlert: false
          };
        });
        this.updateEdibleFavorites();
      });
    },
    // toggleIndoorButton() {
    //   this.indoorButtonClicked = !this.indoorButtonClicked;
    // },

    toggleOutdoorButton() {
      this.outdoorButtonClicked = !this.outdoorButtonClicked;
    },

    toggleEdibleButton() {
      this.edibleButtonClicked = !this.edibleButtonClicked;
    },
    addToFavorites(plantId, index) {
      let alreadyAddedPlantId = 0;
      FavoriteService.getFavoritesList().then((response) => {
        if (response.status === 200) {
          for (let i = 0; i < response.data.length; i++) {
            if (response.data[i].plantId === plantId) {
              alreadyAddedPlantId = response.data[i].plantId;
            }
          }

          if (plantId !== alreadyAddedPlantId) {
            FavoriteService.addToFavorites(plantId)
              .then((response) => {
                if (response.status === 200) {
                  this.filteredList[index].showAlert = true
                  this.updateFavoritesStatus()
                  this.updateIndoorFavorites()
                  this.updateOutdoorFavorites()
                  this.updateEdibleFavorites()
                  this.updatedPlantSearchFavorites()
                  //alert("Was added to your garden")
                  setTimeout(() => {
                    this.filteredList[index].showAlert = false; // Hide the alert after a certain time
                  }, 3000);
                }
              })
              .catch((error) => {
                alert(error.response.data.message);
              });
          }
        }
      });
    },
    findPlantByCommonName(sCommonName) {
      this.specificPlantFilter = !this.specificPlantFilter;
      plantData.getPlantSearch(sCommonName).then((response) => {
        this.plantSearch = response.data.data.map((plantData) => {
          return {
            id: plantData.id,
            common_name: plantData.common_name,
            cycle: plantData.cycle,
            watering: plantData.watering,
            sunlight: plantData.sunlight,
            medium_url:
              plantData.default_image === null
                ? this.checkThumbnail(plantData.default_image)
                : plantData.default_image.medium_url,
            itemAlreadyfavorited: false,
            showAlert: false
          };
        });
        this.updatedPlantSearchFavorites();
      });
    },
    updateFavoritesStatusForCategory(categoryPlants) {
      FavoriteService.getFavoritesList().then((favoritesResponse) => {
        if (favoritesResponse.status === 200) {
          const favoritesData = favoritesResponse.data;

          categoryPlants.forEach((plant) => {
            const favoriteObj = favoritesData.find(
              (favoriteItem) => favoriteItem.plantId === plant.id
            );

            if (favoriteObj) {
              plant.itemAlreadyfavorited = true;
            } else {
              plant.itemAlreadyfavorited = false;
            }
          });
        }
      });
    },
  },
  computed: {
    filteredList() {
      let filteredPlants;

      if (
        !this.edibleFilterOn &&
        !this.indoorFilterOn &&
        this.outdoorFilterOn
      ) {
        filteredPlants = this.outdoorPlants;
      } else if (
        !this.edibleFilterOn &&
        !this.outdoorFilterOn &&
        this.indoorFilterOn
      ) {
        filteredPlants = this.indoorPlants;
      } else if (
        this.edibleFilterOn &&
        !this.outdoorFilterOn &&
        !this.indoorFilterOn
      ) {
        filteredPlants = this.ediblePlants;
      } else if (
        this.specificPlantFilter &&
        !this.edibleFilterOn &&
        !this.outdoorFilterOn &&
        !this.indoorFilterOn
      ) {
        filteredPlants = this.plantSearch;
      } else {
        filteredPlants = this.plants;
      }

      if (this.filter.common_name != "") {
        filteredPlants = filteredPlants.filter((plant) =>
          plant.common_name
            .toLowerCase()
            .includes(this.filter.common_name.toLocaleLowerCase())
        );
      }
      if (this.filter.cycle != "") {
        filteredPlants = filteredPlants.filter((plant) =>
          plant.cycle
            .toLowerCase()
            .includes(this.filter.cycle.toLocaleLowerCase())
        );
      }
      if (this.filter.watering != "") {
        filteredPlants = filteredPlants.filter((plant) =>
          plant.watering
            .toLowerCase()
            .includes(this.filter.watering.toLocaleLowerCase())
        );
      }
      if (this.sortAlphabetically) {
        filteredPlants.sort((a, b) =>
          a.common_name.localeCompare(b.common_name, undefined, {
            sensitivity: "base",
          })
        );
      }
      if (this.filter.scientific_name != "") {
        filteredPlants = filteredPlants.filter((plant) =>
          plant.scientific_name
            .toLowerCase()
            .includes(this.filter.scientific_name.toLocaleLowerCase())
        );
      }

      return filteredPlants;
    },
  },
  watch: {},
};
</script>

<style scoped>

#main {
  height: 100%;
  /* background-color: rgb(206, 245, 206); */
  background-image: url(../assets/rotated.jpg);
  background-blend-mode: lighten;
  background-repeat: round;
  background-size:unset;
}


#indoorPlants {
  display: inline-flex;
  flex-wrap: wrap;
  margin-left: 9%;
}

#cards {
  display: flex;
  flex-basis: 30%;
  padding: 3%;
}

#cards:hover {
  background-color: rgb(206, 245, 206);
  border: rgb(206,245, 206) solid 2px;
  border-radius: 6px;
}

#card {
  box-shadow: 2px 2px 3px 3px rgb(172, 170, 170);
  border: black solid 1px;
}

.cardLink{
  text-decoration: none;
  color: black;
}

#card > img{
  width: 20.75vw;
  height: 43vh;
}

#card > img:hover {
  background-color: white;
  border: black solid 1px;
}

#main {
  background-color: rgb(206, 245, 206);
}

.flex1 {
  display: flex;
  flex-direction: column;
}

#filters {
  margin-bottom: 20px;
  margin-left: 20px;
  grid-area: filt;
}


#button {
  width: 100px;
  height: 34px;
  background-color: rgb(206, 245, 206);
  box-shadow: 5px 5px 5px gray;
  margin: 0.5%;
  margin-right: 10px;
}

#water {
  height: 5vh;
  margin: 2%;  
}

#pageDown,
#pageUp {
  margin: 1%;
  background-color: rgb(174, 230, 174);
  box-shadow: 3px 3px 3px rgb(167, 166, 166);
  justify-content: space-between;
}

#pageUp {
  margin-left: 83%;
}

#btn {
  width: 98%;
  height: 4vh;
  box-shadow: 2px 2px 2px gray;
  margin-right: 50px;
  background-color: rgb(206, 245, 206);
}

#src {
  width: 55vw;
  box-shadow: 5px 5px 5px gray;
  margin-left: 0.5%;
}

.click {
  background-color: darkgreen;
}
.search-container{
  display:flex;
  align-items: center;
}
#plant-query{
  border-radius: 3px;
  margin-left: 1%;
  width: 7vw;
  height: 4vh;
  border: none;
  box-shadow: 5px 5px 5px gray;
}
#btn.active {
  background-color: rgb(143, 194, 143);

}
</style>