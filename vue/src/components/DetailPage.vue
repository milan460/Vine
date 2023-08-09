<template>
  <div class="main">
    <header id="header">
      <h1>{{ plantObject.common_name }}</h1>
    </header>
    <img
      id="pic"
      v-bind:src="plantObject.default_image.medium_url"
      alt="Image of Plant"
    />
    <div class="icon-details">
      <p>{{ plantObject.watering }}</p>
      <p>{{ plantObject.sunlight }}</p>
      <!-- <p>figure out edible</p> -->
      <p>{{ plantObject.flowers }}</p>
      <p>{{ plantObject.indoor }}</p>
      <!-- v-if statements for edible and indoor/outdoor -->
    </div>
    <div id="description">
      <h2>Description</h2>
      <p>{{ plantObject.description }}</p>
    </div>

    <div id="tabs">
        <!-- <div class="row">
    <div class="col s12">
      <ul class="tabs">
        <li class="tab col s3"><a href="#test1">Test 1</a></li>
        <li class="tab col s3"><a class="active" href="#test2">Test 2</a></li>
        <li class="tab col s3 disabled"><a href="#test3">Disabled Tab</a></li>
        <li class="tab col s3"><a href="#test4">Test 4</a></li>
      </ul>
    </div>
    <div id="test1" class="col s12">Test 1</div>
    <div id="test2" class="col s12">Test 2</div>
    <div id="test3" class="col s12">Test 3</div>
    <div id="test4" class="col s12">Test 4</div>
  </div> -->
        
        
      <ul>
        <button @click="showreviews = !showreviews" id="review">Reviews</button>
        <button id="rec">Recommendations</button>
        <button id="nursery">Nursery</button>
        <button id="garden">Add to Garden</button>
      </ul>
    </div> 
    <review-page v-show ="showreviews" id="review" :plantId="currentPlantId"  />
  </div>
</template>

<script>
import PlantData from "../services/PlantData";
import ReviewPage from '../components/ReviewPage.vue'
import M from 'materialize-css'
export default {
  name: "plant-detail",
  components: {
    ReviewPage
  },
  data() {
    return {
      plantObject: "",
      currentPlantId: 0,
      imageURL: "",
      showreviews: false,
    };
  },
  created() {
    this.currentPlantId = this.$store.state.activePlant;
    PlantData.getPlantDetails(this.currentPlantId).then((response) => {
      this.plantObject = response.data;
    });
  },
  mounted () {
    M.AutoInit()
},
};
</script>

<style>
.main {
  display: grid;
  grid-template-columns: 0.5fr 1fr 1fr 0.5fr;
  grid-template-areas:
    ". header header ."
    ". pic icons ."
    ". description description ."
    ". tab tab ."
    ". review . .";

    grid-template-rows: .3fr .3fr .3fr .3fr;
    grid-template-areas: 
    ". header header ."
    ". pic icons ."
    ". description description ."
    ". tab tab ."
    ". review review ."
    ;
}

#pic {
  grid-area: pic;
}

#tabs {
  grid-area: tab;
}

#description {
  grid-area: description;
}

.icon-details {
  grid-area: icons;
}

#header {
  grid-area: header;
}

#review {
  grid-area: review;
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

.nav-extended{
  grid-area: nav;
}
</style>