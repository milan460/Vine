<template>
<div class="mt-4">
    <b-card :img-src="plantObject.default_image.thumbnail" img-alt="Plant image" img-left class="mb-3">
      <b-card-text>
        Some quick example text to build on the card and make up the bulk of the card's content.{{plantObject}}
      </b-card-text>
    </b-card>
  </div>
</template>

// <script>
// import PlantData from '../services/PlantData'

export default {
    name: 'plant-care',
    props: ['plantId'],
    data(){
        return {
            plantObject: "",
            currentPlantId: 0,
            imageURL: ""
        }
    },
    // created() {
    // this.currentPlantId = this.$store.state.activePlant;
    // PlantData.getPlantCareGuide(this.currentPlantId).then((response) => {
    //   this.plantObject = response.data;
    // });
    
//   },
}
</script>

<style>

</style>