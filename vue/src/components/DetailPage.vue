<template>
  <div>
      <h2>{{plantObject.common_name}}</h2>
      <img v-bind:src="plantObject.default_image.medium_url" alt="Image of Plant">
  </div>
</template>

<script>
import PlantData from '../services/PlantData'

export default {
    name: 'plant-detail',
    data(){
        return {
            plantObject: '',
            currentPlantId: 0,
            imageURL: '',
        }
    },
    created(){
        this.currentPlantId = this.$store.state.activePlant
        PlantData.getPlantDetails(this.currentPlantId).then(response => {
            this.plantObject = response.data;
        })
    }
    

}
</script>

<style>

</style>