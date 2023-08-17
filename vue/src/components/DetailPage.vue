<template>
  <div class="main">
    <div id="backbtn">
      <img id="backbtn" src="../assets/arrow.png" @click="goBack" /> Back
    </div>
    <header id="header">
      <strong><h1>{{ plantObject.common_name }}</h1></strong>
    </header>
    <img
      id="pic"
      v-bind:src="checkThumbnail(plantObject.default_image)"
      alt="Image of Plant"
    />
    <div class="icon-details">
      <div id="icon">
        <img
        class="ratingStar"
        src="../assets/drop.png"
        v-bind:title="plantObject.watering"
        v-if="plantObject.watering == 'Minimum'"
      />
      <img id="water" src="../assets/water.png" v-else />  {{plantObject.watering}}
      </div>

     <div id="icon">
       <img
        class="ratingStar"
        src="../assets/sun.png"
        v-bind:title="plantObject.sunlight"
        v-if="plantObject.sunlight == 'full sun'"
        
      />
      
      <img id="water" src="../assets/partly-cloudy.png" v-else />
      {{plantObject.sunlight[0]}}
      </div>
      <div id="icon"><img
        id="water"
        src="../assets/salad.png"
        v-if="!edibleFilterOn"
        :title="plantObject.edible_leaf"
      /> Edible Leaf</div>
      <div id="icon"><img
        id="water"
        src="../assets/fruits.png"
        v-if="!edibleFilterOn"
        :title="plantObject.edible_fruit"
      /> Edible Fruit</div>

      <div id="icon"><img
        id="water"
        src="../assets/indoor-plants.png"
        v-if="!indoorFilterOn"
        :title="plantObject.indoor"
      /> Indoor</div>
    </div>
    <div id="description">
      <strong><h2>Description</h2></strong>
      <p>{{ plantObject.description }}</p>
    </div>

    <div id="tabs">
      <ul>
        <button
          class="button active"
          @click="showreviews = !showreviews"
          id="review"
        >
          Reviews
        </button>
        <button class="button active" id="rec">Recommendations</button>
        <button class="button active" id="nursery">Plant Shop</button>
        <button class="button active" @click="addToFavorites" id="garden">
          Add to Garden
        </button>
      </ul>
    </div>

    <div v-show="showreviews" id="review">
      <review-page :plantId="currentPlantId" />
      <button
        v-if="$store.state.token != ''"
        @click="showForm = !showForm"
        id="addReview"
      >
        Add Review
      </button>
      <create-review
        @form-submitted="handleFormSubmitted"
        :show-tag="showForm"
        v-show="showForm"
        id="createReview"
        :plantId="currentPlantId"
      ></create-review>
    </div>
  </div>
</template>

<script>
import PlantData from "../services/PlantData";
import ReviewPage from "../components/ReviewPage.vue";
import CreateReview from "./CreateReview.vue";
import FavoriteService from "../services/FavoriteService";

export default {
  name: "plant-detail",

  components: {
    ReviewPage,
    CreateReview,
  },

  data() {
    return {
      plantObject: "",
      currentPlantId: 0,
      imageURL: "",
      showreviews: false,
      showAddReviews: false,
      showForm: false,
      indoorFilterOn: false,
      ediblePlants: [],
      edibleFilterOn: false,
      flowers: false,
      sunIconDetail: "",

      filter: {
        common_name: "",
        cycle: "",
        watering: "",
        sunlight: "",
      },
    };
  },
  created() {
    this.currentPlantId = this.$store.state.activePlant;
    PlantData.getPlantDetails(this.currentPlantId).then((response) => {
      this.plantObject = response.data;
      // this.plantObject
    });
  },
  methods: {
    closeForm() {
      this.closeForm = false;
    },
    handleFormSubmitted() {
      this.showForm = false;
    },
    goBack() {
      window.history.back();
    },
    addToFavorites() {
      FavoriteService.addToFavorites(this.currentPlantId).then((response) => {
        if (response.status === 201) {
          //alert("Was added to your garden")
        }
      });
    },
    checkThumbnail(default_image) {
      if (default_image === null) {
        return "https://static.vecteezy.com/system/resources/previews/024/551/617/original/gardening-houseplant-error-404-flash-message-environmental-friendly-watering-plant-empty-state-ui-design-page-not-found-popup-cartoon-image-flat-illustration-concept-on-white-background-vector.jpg";
      }
      return default_image.regular_url;
    },
    // displaySunIconDetails() {
    //   console.log('im here')
    //   this.plantObject.forEach((item) => {
    //     PlantData.getPlantDetails(item.plantId)
    //       .then((response) => {
    //         console.log( response.data.sunlight[0])
    //         item.displayIconDetails =
    //           response.data.sunlight[0];
    //       })
    //       .catch((error) => {
    //         console.log(error);
    //       });
    //   });
    // },
  },
};
</script>

<style scoped>
#backbtn {
  grid-area: btn;
  height: 3vh;
  margin-left: 10%;
}
.main {
  display: grid;
  row-gap: 10px;
  grid-template-columns: 0.5fr 1fr 1fr 0.5fr 0.5fr;
  grid-template-areas:
    "btn header header ."
    ". pic icons ."
    ". description description ."
    ". tab tab ."
    ". review . ."
    ". addReview . .";

  grid-template-rows: 0.1fr 0.4fr 0.3fr 0.2fr 0.4fr;
  grid-template-areas:
    "btn header header ."
    ". pic icons ."
    ". description description ."
    ". tab tab ."
    ". review review ."
    ". addReview addReview .";
}

#pic {
  grid-area: pic;
  height: 60vh;
  width: 30vw;
  box-shadow:  5px 5px 5px gray;
  border: black solid 1px;
  
}


#tabs {
  grid-area: tab;
  justify-items: spaced-evenly;
  max-width: 95%;
  box-shadow: 5px 5px 5px gray;
  background-color: rgb(163, 231, 163);
}

#description {
  grid-area: description;
}

.icon-details {
  grid-area: icons;
  vertical-align: middle;
}

#header {
  grid-area: header;
}

#review {
  grid-area: review;
}

#addReview {
  grid-area: addReview;
  box-shadow: 5px 5px 5px gray;
}

#rec {
  grid-area: rec;
}

#nursery {
  grid-area: nursery;
}

#garden {
  grid-area: garden;
}

.nav-extended {
  grid-area: nav;
}

button {
  background-color: rgb(163, 231, 163);
  border: rgb(163, 231, 163);
  height: 40px;
  width: 25%;
  padding: 1%;
}

.button {
  padding-top: 2%;
}

#water {
  height: 5vh;
  /* margin: 3%;
  margin-left: 5%; */
}
#icon{
  margin-left: 10%;
  margin-bottom: 10%;
}
</style>