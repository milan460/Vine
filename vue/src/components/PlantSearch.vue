<template>
  <div>
        <div id="filters">
            <label for="common name">common name  </label>
            <input type="text" id="common name" v-model="filter.common_name"/>

            <label for="cycle">cycle  </label>
            <input type="text" id="cycle" v-model="filter.cycle"/>

            <label for="watering">watering conditions  </label>
            <input type="text" name="watering conditions" id="watering" v-model="filter.watering"/>
        
            <label for="indoorPlants"> Indoor Plants  </label>
            <input type="checkbox" id="indoorPlants" v-on:change="checkIndoorPlants" v-model="compareIndoorPlantArray">

            <label for="alphabetically"> Sort Alphabetically  </label>
            <input type="checkbox" id="alphabetically" v-model="sortAlphabetically">
        </div>
    
      <div v-for="plant in filteredList" v-bind:key="plant.id">
        <router-link v-bind:to=" {name: 'plant-detail', params: {id: plant.id}} ">
        <img :src="plant.thumbnail" alt="Plant Image">
        </router-link>
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
            sortAlphabetically: false,
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
        },
    },
    computed:{
         sortedArray: function() {
            function compare(a, b) {
            if (a.name < b.name)
                return -1;
            if (a.name > b.name)
                return 1;
            return 0;
            }

            return this.filteredList.sort(compare);
            },
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

            // if (this.sortAlphabetically) {
            //     this.filteredPlants.sort((x, y) => {
            //         return y.common_name - x.common_name;
            //     });
            // }

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