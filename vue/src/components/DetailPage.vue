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
      <ul>
        <button id="review">Reviews</button>
        <button id="rec">Recommendations</button>
        <button id="nursery">Nursery</button>
        <button id="garden">Add to Garden</button>
      </ul>
    </div>
  </div>
</template>

<script>
import PlantData from "../services/PlantData";

export default {
  name: "plant-detail",
  data() {
    return {
      plantObject: "",
      currentPlantId: 0,
      imageURL: "",
    };
  },
  created() {
    this.currentPlantId = this.$store.state.activePlant;
    PlantData.getPlantDetails(this.currentPlantId).then((response) => {
      this.plantObject = response.data;
    });
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
    ". tab tab .";
    grid-template-rows: .3fr .3fr .3fr .3fr;
    grid-template-areas: 
    ". header header ."
    ". pic icons ."
    ". description description ."
    ". tab tab ."
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
</style>