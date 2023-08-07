<template>
  <div>
      <input type="text" id="common name" v-model="filter.common_name"/>
      <label for="common name"> common name</label>
      <input type="text" id="cycle" v-model="filter.cycle"/>
      <label for="cycle">cycle</label>
      <input type="text" name="watering conditions" id="watering" v-model="filter.watering"/>
      <label for="watering">watering conditions</label>
      <input type="checkbox" v-on:change="checkIndoorPlants" v-model="compareIndoorPlantArray">


      <div v-for="plant in filteredList" v-bind:key="plant.id">
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
            plants: [],
            indoorPlants:[],
            filter: {
                common_name: "",
                cycle: "",
                watering: "",
                sunlight: "",
            }
        }
    },
    created(){
            plantData.getPlantData(2).then(response => {
                 this.plants = response.data.data.map(plantData => {
                   return{
                        id: plantData.id,
                        common_name: plantData.common_name,
                        cycle: plantData.cycle,
                        watering: plantData.watering,
                        sunlight: plantData.sunlight,
                        thumbnail: plantData.default_image === null ? this.checkThumbnail(plantData.default_image) : plantData.default_image.thumbnail,
                        indoor: ''
                   }
                })
            
            })
    },
    methods:{
        checkThumbnail(default_image){
            if(default_image === null){
                return "https://perenual.com/storage/species_image/16_acer_griseum/thumbnail/5158906371_ed08a86876_b.jpg"
            }
            
            return default_image;
            
        },
        checkIndoorPlants(){
            plantData.getIndoorPlants(1).then(response => {
                 this.indoorPlants = response.data.data.map(plantData => {
                   return{
                        id: plantData.id,
                        common_name: plantData.common_name,
                        cycle: plantData.cycle,
                        watering: plantData.watering,
                        sunlight: plantData.sunlight,
                        thumbnail: plantData.default_image === null ? this.checkThumbnail(plantData.default_image) : plantData.default_image.thumbnail
                   }
                })
            
            })
        }
    },
    computed:{
        filteredList(){
            let filteredPlants = this.plants
            if (this.filter.common_name != ""){
                filteredPlants = filteredPlants.filter((plant) =>
                plant.common_name.toLowerCase().includes(this.filter.common_name.toLocaleLowerCase()))
            }
            if(this.filter.cycle != ""){
                filteredPlants = filteredPlants.filter((plant) =>
                plant.cycle.toLowerCase().includes(this.filter.cycle.toLocaleLowerCase()))
            }
            if(this.filter.watering != ""){
                filteredPlants = filteredPlants.filter((plant) => 
                plant.watering.toLowerCase().includes(this.filter.watering.toLocaleLowerCase()))
            }

            return filteredPlants
        },
        compareIndoorPlantArray(){
            this.plants.forEach(element => {
            this.indoorPlants.forEach(elements => {
                if(element.id === elements.id){
                this.element.indoor = "true";
                } else{
                    this.element.indoor = "false";
                }
            })
            });
            console.log("indoor plants boolean check")
            console.log(this.plants)
            return this.plants
        }

    },
}
</script>

<style>

</style>