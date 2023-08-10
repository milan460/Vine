<template>
  <div id="main">
    <div id="filters">
      <!-- <input type="text" id="common name" placeholder="Search"  /> -->
      <b-form-input size="sm" class="mr-sm-2" placeholder="Search" v-model="filter.common_name"></b-form-input>

      <button id="button" @click="filterToggle">Filter</button>

      <div id="filterCheckbox" v-if="filterToggleOn">
        <label for="indoorPlants">
          Indoor Plants
          <input
            type="checkbox"
            class="filled-in"
            checked="checked"
            id="indoorPlants"
            @change="checkIndoorPlants"
          />
          <span></span>
        </label>

        <label for="outdoorPlants">
          Outdoor Plants
          <input
            type="checkbox"
            class="filled-in"
            checked="checked"
            id="outdoorPlants"
            @change="checkOutdoorPlants"
          />
          <span></span>
        </label>

        <label for="ediblePlants">
          Edible Plants
          <input
            type="checkbox"
            class="filled-in"
            checked="checked"
            id="ediblePlants"
            @change="checkEdiblePlants"
          />
          <span></span>
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

            <b-button href="#" variant="primary">Go somewhere</b-button>
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

<style>
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

#button{
  width: 100px;
  height: 34px;
}

/* 
  #indoorPlants {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-template-areas: 
    "filt filt filt"
    "cards cards cards"
    "cards cards cards"
    "cards cards cards"
    "cards cards cards"
    "cards cards cards"
    ;
  } */

/* .plant-card {
    border: 1px solid black;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  } */

/* .plant-card img {
    max-width: 100%;
    height: auto;
  }

  .plant-card h2 {
    font-size: 1.2em;
    margin-top: 10px;
  }

  .plant-card p {
    margin: 5px 0;
  }

  .plant-card ul {
    margin: 5px 0;
  }

  .plant-card li {
    margin: 5px 0;
  } */

#page-arrows {
  margin-top: 20px;
}
</style>