<template>
  <div id="main">
    <div id="filters">
      <!-- <input type="text" id="common name" placeholder="Search"  /> -->
      <b-form-input
        size="sm"
        class="mr-sm-2"
        placeholder="Search"
        v-model="filter.common_name"
      ></b-form-input>

      <button id="button" @click="filterToggle">Filter</button>

      <div id="filterCheckbox" v-if="filterToggleOn">
        <label for="indoorPlants">
          <button id="btn" @click="checkIndoorPlants">Indoor</button>
        </label>

        <label for="outdoorPlants">
          <button id="btn"  @click="checkOutdoorPlants">Outdoor</button>
        </label>

        <label for="ediblePlants">
          <button id="btn"  @click="checkEdiblePlants">Edible</button>
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
            v-bind:img-src="plant.thumbnail"
            alt="Plant Image"
            img-top
            tag="article"
            style="max-width: 20rem"
            class="mb-2 flex1"
          >
            <b-card-text>
              <img id="water"
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
              <img id="water" src="../assets/water.png" v-else>
                  <img
                    class="ratingStar"
                    src="../assets/sun.png"
                    v-bind:title="plant.sunlight" v-if="plant.sunlight == 'full sun'"
                  />
                  <img id="water" src="../assets/partly-cloudy.png" v-else>
            </b-card-text>
            
            <b-button href="#"  @click="addToFavorites(plant.id)" variant="primary">Add to Garden</b-button>
          </b-card>
        </router-link>
      </div>
    </div>

    <div id="page arrows">
      <button id="pageDown" @click="decrementPage()">Previous Page</button>
      <button id="pageUp" @click="incrementPage()">Next Page</button>
    </div>
  </div>
</template>

<script>
import FavoriteService from '../services/FavoriteService.js';
import plantData from "../services/PlantData.js";
export default {
  data() {
    return {
      filterToggleOn: false,
      plants: [],
      indoorPlants: [],
      indoorFilterOn: false,
      outdoorPlants: [],
      outdoorFilterOn: false,
      ediblePlants: [],
      edibleFilterOn: false,
      pagecounter: 1,
      sortAlphabetically: false,
      filter: {
        common_name: "",
        cycle: "",
        watering: "",
        sunlight: "",
      },
    };
  },
  created() {
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
              : plantData.default_image.medium_url,
        };
      });
    });
  },
  mounted() {},
  methods: {
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
              thumbnail:
                plantData.default_image === null
                  ? this.checkThumbnail(plantData.default_image)
                  : plantData.default_image.thumbnail,
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
                thumbnail:
                  plantData.default_image === null
                    ? this.checkThumbnail(plantData.default_image)
                    : plantData.default_image.thumbnail,
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
                thumbnail:
                  plantData.default_image === null
                    ? this.checkThumbnail(plantData.default_image)
                    : plantData.default_image.thumbnail,
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
              thumbnail:
                plantData.default_image === null
                  ? this.checkThumbnail(plantData.default_image)
                  : plantData.default_image.thumbnail,
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
                thumbnail:
                  plantData.default_image === null
                    ? this.checkThumbnail(plantData.default_image)
                    : plantData.default_image.thumbnail,
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
                  thumbnail:
                    plantData.default_image === null
                      ? this.checkThumbnail(plantData.default_image)
                      : plantData.default_image.thumbnail,
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
                      : plantData.default_image.thumbnail,
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
                    : plantData.default_image.thumbnail,
              };
            });
          });
        }
      }
    },
    checkIndoorPlants() {
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
                  : plantData.default_image.thumbnail,
            };
          });
        });
    },

    checkOutdoorPlants() {
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
                  : plantData.default_image.thumbnail,
            };
          });
        });
    },
    checkEdiblePlants() {
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
                : plantData.default_image.thumbnail,
          };
        });
      });
    },
    addToFavorites(plantId){
    console.log("this is the plant Id")
    console.log(plantId)
    FavoriteService.addToFavorites(plantId).then(response =>{
      if (response.status === 201){
        //alert("Was added to your garden")
      }
    })
    }
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
}
#water{
  height: 5vh;
  margin: 2%;
}

#page-arrows {
  margin-top: 20px;
}

#btn{
  width: 98%;
  height: 5vh;
  box-shadow: 5px 5px 5px gray;
  margin-right: 50px;
  }

  .mr-sm-2{
    width: 10vw

  }
  #pageDown{
  width: 17%;
  height: 2vh;
  box-shadow: 5px 5px 5px gray;
  text-align: center;
  padding: 1.5%;
  margin-left: 5%;
 


  }

  #pageUp{
  width: 17%;
  height: 2vh;
  box-shadow: 5px 5px 5px gray;
  margin-left: 55%;
  text-align: center;
  padding: 1.5%


  }

</style>