<template>
  <div id="main">
    <div id="filters">
      <b-form-input
        id="src"
        size="sm"
        class="mr-sm-2"
        placeholder="Search"
        v-model="filter.common_name"
      ></b-form-input>

      <button id="button" @click="filterToggle">Filter</button>

      <div id="filterCheckbox" v-if="filterToggleOn">
        <label for="indoorPlants">
          <button
            id="btn"
            :class="{ clicked: indoorButtonClicked }"
            @click="
              checkIndoorPlants();
              toggleIndoorButton();
            "
          >
            Indoor
          </button>
        </label>

        <label for="outdoorPlants">
          <button
            id="btn"
            :class="{ clicked: outdoorButtonClicked }"
            @click="checkOutdoorPlants"
          >
            Outdoor
          </button>
        </label>

        <label for="ediblePlants">
          <button
            id="btn"
            :class="{ clicked: edibleButtonClicked }"
            @click="checkEdiblePlants"
          >
            Edible
          </button>
        </label>
      </div>
    </div>

    <div id="indoorPlants">
      <div id="cards" v-for="plant in filteredList" v-bind:key="plant.id">
        <router-link
          class="link"
          :to="{ name: 'plant-detail', params: { id: plant.id } }"
        >
          <b-card
            :title="plant.common_name"
            v-bind:img-src="plant.medium_url"
            alt="Plant Image"
            img-top
            tag="article"
            style="max-width: 20rem"
            class="mb-2 flex1"
          >
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
              @click="addToFavorites(plant.id)"
              :disabled="plant.itemAlreadyfavorited"
              variant="primary"
              >Add to Garden</b-button
            >
          </b-card>
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
      edibleFilterOn: false,
      pageCounter: 1,
      sortAlphabetically: false,

      filter: {
        common_name: "",
        cycle: "",
        watering: "",
        sunlight: "",
        imageURL: "",
        scientific_name: "",
      },
    };
  },
  created() {
    plantData
      .getPlantSearch(this.pagecounter, this.filter.common_name)
      .then((response) => {
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
            itemAlreadyfavorited: this.checkFavoriteItems(plantData.id),
          };
        });
      });
  },
  methods: {
    async checkFavoriteItems(plantId) {
      console.log("this ran");
      try {
        const response = await FavoriteService.getFavoritesList();
        if (response.status === 200) {
          const favoriteObj = response.data.find(
            (favoriteItem) => favoriteItem.plantId === plantId
          );
          console.log("this is the plantId in the checkFavoriteItems function");
          console.log(plantId);
          if (typeof favoriteObj.plantId === "undefined") {
            return false;
          } else if (favoriteObj.plantId === plantId) {
            return true;
          } else {
            return false;
          }
          // for (let i = 0; i < response.data.length; i++) {
          //   // console.log("this is the favorites items");
          //   // console.log(response.data[i]);
          //   // console.log(response.data[i].plantId);
          //   if (response.data[i].plantId === plantId) {
          //     return true;
          //   }
          // }
        }
      } catch (error) {
        console.error("Error fetching favorite items:", error);
        return false;
      }
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
            };
          });
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
              };
            });
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
              };
            });
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
            };
          });
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
              };
            });
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
                };
              });
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
                  thumbnail:
                    plantData.default_image === null
                      ? this.checkThumbnail(plantData.default_image)
                      : plantData.default_image.medium_url,
                };
              });
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
                thumbnail:
                  plantData.default_image === null
                    ? this.checkThumbnail(plantData.default_image)
                    : plantData.default_image.medium_url,
              };
            });
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
              thumbnail:
                plantData.default_image === null
                  ? this.checkThumbnail(plantData.default_image)
                  : plantData.default_image.medium_url,
            };
          });
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
              thumbnail:
                plantData.default_image === null
                  ? this.checkThumbnail(plantData.default_image)
                  : plantData.default_image.medium_url,
            };
          });
        });
    },
    checkEdiblePlants() {
      this.edibleButtonClicked = !this.outdoorButtonClicked;
      this.edibleFilterOn = !this.edibleFilterOn;
      plantData.getEdiblePlants(this.pagecounter, 1).then((response) => {
        this.ediblePlants = response.data.data.map((plantData) => {
          return {
            id: plantData.id,
            common_name: plantData.common_name,
            cycle: plantData.cycle,
            watering: plantData.watering,
            sunlight: plantData.sunlight,
            thumbnail:
              plantData.default_image === null
                ? this.checkThumbnail(plantData.default_image)
                : plantData.default_image.medium_url,
          };
        });
      });
    },
    toggleIndoorButton() {
      this.indoorButtonClicked = !this.indoorButtonClicked;
    },

    toggleOutdoorButton() {
      this.outdoorButtonClicked = !this.outdoorButtonClicked;
    },

    toggleEdibleButton() {
      this.edibleButtonClicked = !this.edibleButtonClicked;
    },
    addToFavorites(plantId) {
      let alreadyAddedPlantId = 0;
      FavoriteService.getFavoritesList().then((response) => {
        if (response.status === 200) {
          for (let i = 0; i < response.data.length; i++) {
            console.log("this is the favorites items");
            console.log(response.data[i]);
            console.log(response.data[i].plantId);
            if (response.data[i].plantId === plantId) {
              alreadyAddedPlantId = response.data[i].plantId;
            }
          }
          console.log("this is the plant Id");
          console.log(plantId);

          console.log("this is the alreadyAddedPlantId");
          console.log(alreadyAddedPlantId);
          if (plantId !== alreadyAddedPlantId) {
            FavoriteService.addToFavorites(plantId)
              .then((response) => {
                if (response.status === 201) {
                  //alert("Was added to your garden")
                }
              })
              .catch((error) => {
                alert(error.response.data.message);
              });
          }
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
  watch: {
    sortAlphabetically(newValue) {
      if (newValue) {
        this.filteredList.sort((a, b) =>
          a.common_name.localeCompare(b.common_name, undefined, {
            sensitivity: "base",
          })
        );
      } else {
        this.sortAlphabetically = false;
        plantData.getPlantData(this.pagecounter).then((response) => {
          this.plants = response.data.data.map((plantData) => {
            return {
              id: plantData.id,
              common_name: plantData.common_name,
              cycle: plantData.cycle,
              watering: plantData.watering,
              sunlight: plantData.sunlight,
              thumbnail:
                plantData.default_image === null
                  ? this.checkThumbnail(plantData.default_image)
                  : plantData.default_image.thumbnail,
              indoor: "",
            };
          });
        });
      }
    },
  },
};
</script>

<style scoped>
#main {
  height: 100%;
  background-color: rgb(206, 245, 206);
}
.link {
  display: inline-block;
}

#indoorPlants {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}

#cards {
  display: flex;
  flex-basis: 30%;
  padding: 3%;
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
  box-shadow: 5px 5px 5px gray;
  margin-right: 50px;
  background-color: rgb(206, 245, 206);
}

#src {
  width: 55vw;
  box-shadow: 5px 5px 5px gray;
}

.click {
  background-color: darkgreen;
}
</style>