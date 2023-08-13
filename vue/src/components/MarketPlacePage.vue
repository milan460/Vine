<template>
  <div class="mainSeller">
      <div id="listingCard" v-for="listingItem in SellerListings" v-bind:key="listingItem.plantSellerId">

        {{listingItem.plantObj.common_name}}
          <!-- <b-card
            :title="listingItem.plantObj.common_name"
            v-bind:img-src="listingItem.plantObj.default_image.medium_url"
            alt="Plant Image"
            img-top
            tag="article"
            style="max-width: 20rem"
            class="mb-2 flex1"
          >
            <b-card-text>
              <img src="../assets/indoor-plants.png" v-if="checkIndoorPlants === true" :title="plant.indoor"> //indoor
              <img
          class="ratingStar"
          src="../assets/drop.png"
          v-bind:title="favoriteItem.plantObj.watering"
        /> //watering
              <ul v-for="sunlight in favoriteItem.plantObj.sunlight" v-bind:key="sunlight">
                <ol>
                  <img
          class="ratingStar"
          src="../assets/sun.png"
          v-bind:title="favoriteItem.plantObj.sunlight"
        /> //sunlight
                </ol>

              </ul>
            </b-card-text>

            <b-button href="#" @click="removeFromfavoritesDatabase(favoriteItem.favoriteId)" variant="primary">delete From Favorites</b-button>
          </b-card>

      </div> -->
  </div>
</template>

<script>
import PlantData from '../services/PlantData.js'
import SellerService from '../services/SellerService.js'
export default {
  data(){
    return{
      SellerListings: [
        {
          username: '',
          plantSellerId: '',
          plantId: '',
          description: '',
          price: '',
          stockQuantity: '',
          plantObj: {}
        }
      ]
    }
  },
  methods:{
    getAllListings(){
      SellerService.getAllSellerListings().then( (response) => {
      if(response.status === 200){
        this.SellerListings = response.data.map( (listingItem) => {
          return {
            username: listingItem.username,
            plantSellerId: listingItem.plantSellerId,
            plantId: listingItem.plantId,
            description: listingItem.description,
            price: listingItem.price,
            stockQuantity: listingItem.stockQuantity,
            plantObj: listingItem.plantObj
          }
        })
        this.getPlantData()
        console.log(this.SellerListings)
    
    
      }
    })
    },
    getPlantData(){
      this.SellerListings.forEach(listingItem => {
        PlantData.getPlantDetails(listingItem.plantId).then(response =>{
          if(response.status === 200){
            listingItem.plantObj = response.data
          }
        })
      })
    },
  },

  created(){
    this.getAllListings()
  }
}
</script>

<style>

</style>