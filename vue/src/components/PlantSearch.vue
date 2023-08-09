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
            <input type="checkbox" id="indoorPlants" @change="checkIndoorPlants">

            <label for="outdoorPlants"> Outdoor Plants  </label>
            <input type="checkbox" id="outdoorPlants" @change="checkOutdoorPlants">

            <label for="ediblePlants"> Edible Plants</label>
            <input type="checkbox" id="ediblePlants" @change="checkEdiblePlants">

        </div>
    
        <div class="plant-card-container" id="indoorPlants">
      <div class="plant-card" v-for="plant in filteredList" v-bind:key="plant.id">
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
        incrementPage(){
            this.pagecounter++;
            if(!this.indoorFilterOn && !this.outdoorFilterOn){
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
            else if(this.indoorFilterOn && !this.outdoorFilterOn){
                plantData.getIndoorOrOutdoorPlants(this.pagecounter, 1).then(response => {
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

            else if(!this.indoorFilterOn && this.outdoorFilterOn){
                plantData.getIndoorOrOutdoorPlants(this.pagecounter, 0).then(response => {
                 this.outdoorPlants = response.data.data.map(plantData => {
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
            else if(this.edibleFilterOn && !this.indoorFilterOn && !this.indoorFilterOn){
                plantData.getEdiblePlants(this.pagecounter, 1).then(response => {
                 this.ediblePlants = response.data.data.map(plantData => {
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
        decrementPage(){
            if(this.pagecounter > 1){
                this.pagecounter--;
                 if(!this.indoorFilterOn && !this.outdoorFilterOn && !this.edibleFilterOn){
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
            else if(this.indoorFilterOn && !this.outdoorFilterOn && !this.edibleFilterOn){
                plantData.getIndoorOrOutdoorPlants(this.pagecounter, 1).then(response => {
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

            else if(!this.indoorFilterOn && this.outdoorFilterOn && !this.edibleFilterOn){
                plantData.getIndoorOrOutdoorPlants(this.pagecounter, 0).then(response => {
                 this.outdoorPlants = response.data.data.map(plantData => {
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
            else if(!this.indoorFilterOn && !this.indoorFilterOn && this.edibleFilterOn){
                plantData.getEdiblePlants(this.pagecounter, 1).then(response => {
                 this.ediblePlants = response.data.data.map(plantData => {
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
            }
        },
        checkIndoorPlants(){
            this.indoorFilterOn = !this.indoorFilterOn;
            plantData.getIndoorOrOutdoorPlants(this.pagecounter, 1).then(response => {
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

        checkOutdoorPlants(){
            this.outdoorFilterOn = !this.outdoorFilterOn;
            plantData.getIndoorOrOutdoorPlants(this.pagecounter, 0).then(response => {
                 this.outdoorPlants = response.data.data.map(plantData => {
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
        checkEdiblePlants(){
            this.edibleFilterOn = !this.edibleFilterOn;
            plantData.getEdiblePlants(this.pagecounter, 1).then(response => {
                 this.ediblePlants = response.data.data.map(plantData => {
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
            let filteredPlants
        
            if(!this.edibleFilterOn && !this.indoorFilterOn && this.outdoorFilterOn){
                filteredPlants = this.outdoorPlants
            }
            else if(!this.edibleFilterOn &&  !this.outdoorFilterOn && this.indoorFilterOn){
                filteredPlants = this.indoorPlants
            }
            else if(this.edibleFilterOn && !this.outdoorFilterOn && !this.indoorFilterOn){
                filteredPlants = this.ediblePlants
            } else{
                filteredPlants = this.plants
            }

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
                } 
                else{
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
 #filters {
    margin-bottom: 20px;
  }

  .plant-card-container {
    display: grid;
    grid-template-columns: repeat(3, 1fr); /* Three columns per row */
    gap: 20px;
    margin-top: 20px;
  }

  .plant-card {
    border: 1px solid #ccc;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    padding: 20px;
    box-sizing: border-box;
  }

  .plant-card img {
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
  }

  #page-arrows {
    margin-top: 20px;
  }
</style>