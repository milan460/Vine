<template>
  <div>
      <div v-for="plant in plants" v-bind:key="plant.id">
        <img :src="plant.thumbnail" alt="Plant Image">
        <h2>{{plant.common_name}}</h2>
        <p>{{plant.cycle}}</p>
        <p>{{plant.watering}}</p>
        <ul v-for="sunlight in plant.sunlight" v-bind:key="sunlight">
            <ol>{{sunlight}}</ol>
        </ul>
      </div>
  </div>
</template>

<script>
import plantData from '../services/PlantData.js'
export default {
    data(){
        return{
            plants: []
        }
    },
    created(){
            plantData.getPlantData(2).then(response => {
                console.log('this is the response data')
                console.log(response.data.data)
                 this.plants = response.data.data.map(plantData => {
                   return{
                        id: plantData.id,
                        common_name: plantData.common_name,
                        cycle: plantData.cycle,
                        watering: plantData.watering,
                        sunlight: plantData.sunlight,
                        thumbnail: plantData.default_image === null ? this.checkThumbnail(plantData.default_image) : plantData.default_image.thumbnail
                   }
                })
                console.log('new object plant')
                console.log(this.plants)
                
               


               
            })
    },
    methods:{
        checkThumbnail(default_image){
            if(default_image === null){
                return "https://perenual.com/storage/species_image/16_acer_griseum/thumbnail/5158906371_ed08a86876_b.jpg"
            }
            
            return default_image;
            
        }
    },
}
</script>

<style>

</style>