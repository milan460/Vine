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
            <input type="checkbox" id="indoorPlants" @click="checkIndoorPlants">

            <label for="alphabetically"> Sort Alphabetically  </label>
            <input type="checkbox" id="alphabetically" v-model="sortAlphabetically">
        </div>

        <!-- <div v-if="indoorPlants != null" v-for="plant in indoorPlants" v-bind:key="plant.id">
        <h2>{{indoorPlant.common_name}}</h2>
        <p>{{indoorPlant.cycle}}</p>
        <p>{{indoorPlant.watering}}</p>
        <ul v-for="sunlight in indoorPlant.sunlight" v-bind:key="sunlight">
            <ol>{{sunlight}}</ol>
        </ul> -->
    
        <!-- </div> -->
    
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
      <div id="page arrows">
          <button id="pageDown" @click="decrementPage()">
              Previous Page
          </button>
        <button id="pageUp" @click="incrementPage()">
            Next Page
        </button>
      </div>
  </div>
</template>

<script>
import plantData from '../services/PlantData.js'
export default {
    data(){
        return{
            plants: [],
            plantsCopy: [],
            indoorPlants: [],
            pagecounter: 1,
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
            plantData.getPlantData(this.pagecounter).then(response => {
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
                this.plantsCopy = this.plants
            })

    },
    methods:{
        checkThumbnail(default_image){
            if(default_image === null){
                return "https://perenual.com/storage/species_image/16_acer_griseum/thumbnail/5158906371_ed08a86876_b.jpg"
            }
            
            return default_image;
            
        },
        incrementPage(){
            this.pagecounter++;
            plantData.getPlantData(this.pagecounter).then(response => {
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
        decrementPage(){
            if(this.pagecounter > 1){
                this.pagecounter--;
                 plantData.getPlantData(this.pagecounter).then(response => {
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
            }
        },
        checkIndoorPlants(){
            plantData.getIndoorPlants(this.pagecounter).then(response => {
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
            
            {
        //     plantData.getPlantData(this.pagecounter).then(response => {
        //          this.plants = response.data.data.map(plantData => {
        //            return{
        //                 id: plantData.id,
        //                 common_name: plantData.common_name,
        //                 cycle: plantData.cycle,
        //                 watering: plantData.watering,
        //                 sunlight: plantData.sunlight,
        //                 thumbnail: plantData.default_image === null ? this.checkThumbnail(plantData.default_image) : plantData.default_image.thumbnail,
        //                 indoor: ''
        //            }
        //         })
        //         })
            }
        }
    },
    computed:{
        //  sortedArray: function() {
        //     function compare(a, b) {
        //     if (a.name < b.name)
        //         return -1;
        //     if (a.name > b.name)
        //         return 1;
        //     return 0;
        //     }

          
        //     },
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
            if (this.sortAlphabetically) {
                filteredPlants.sort((a,b) =>
                a.common_name.localeCompare(b.common_name, undefined, { sensitivity: 'base'}))
                
                }
               
                return filteredPlants
            },

            
        },
        watch: {
            sortAlphabetically(newValue) {
                if (newValue){
                    this.filteredList.sort((a,b) =>
                    a.common_name.localeCompare(b.common_name, undefined, {sensitivity: 'base'}));
                } else{
                    this.sortAlphabetically = false;
                    plantData.getPlantData(this.pagecounter).then(response => {
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
            }
            }},
        
}
</script>

<style>

</style>