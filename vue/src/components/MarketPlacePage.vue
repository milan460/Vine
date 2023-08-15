<template>
  <div class="mainSeller">
    <div>
      
    </div>
    <div
      id="listingCard"
      v-for="listingItem in SellerListings"
      v-bind:key="listingItem.favoritesId"
    >
      <!-- {{listingItem.plantObj.common_name}} -->
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
          <img
            src="../assets/indoor-plants.png"
            v-if="checkIndoorPlants === true"
            :title="plant.indoor"
          />
          //indoor
          <img
            class="ratingStar"
            src="../assets/drop.png"
            v-bind:title="listingItem.plantObj.watering"
          />
          //watering
          <ul
            v-for="sunlight in listingItem.plantObj.sunlight"
            v-bind:key="sunlight"
          >
            <ol>
              <img
                class="ratingStar"
                src="../assets/sun.png"
                v-bind:title="listingItem.plantObj.sunlight"
              />
              //sunlight
            </ol>
          </ul>
        </b-card-text>

        <b-button
          href="#"
          @click="addToCart(listingItem.favoritesId)"
          variant="primary"
          >Add To Cart</b-button
        >
      </b-card> -->
      <!-- DIVIDING COMMENT -->
  <b-card
        :title="listingItem.plantObj.common_name"
        v-bind:img-src="listingItem.plantObj.default_image.medium_url"
        alt="Plant Image"
        img-left
        class="mb-3"
      >
        <b-card-text>
          Seller Name: 
          <strong>{{ listingItem.username }}</strong>
           Plant Description:
          <strong>{{ listingItem.description }}</strong>
          Price:
          <strong>${{listingItem.price}}</strong>
           Stock:
          <strong>{{listingItem.stockQuantity}}</strong>

        </b-card-text>
        <b-button
          href="#"
          @click="addToCart(listingItem.favoritesId)"
          variant="primary"
          >Add To Cart</b-button
        >
      </b-card>
    </div>
  </div>
</template>

<script>
import PlantData from "../services/PlantData.js";
import SellerService from "../services/SellerService.js";
export default {
  data() {
    return {
      SellerListings: [
        {
          username: "",
          favoritesId: "",
          plantId: "",
          description: "",
          price: "",
          stockQuantity: "",
          isAvailable: "",
          plantObj: {},
        },
      ],
    };
  },
  
  methods: {
    getAllListings() {
      SellerService.getAllSellerListings().then((response) => {
        if (response.status === 200) {
          this.SellerListings = response.data.map((listingItem) => {
            return {
              username: listingItem.username,
              favoritesId: listingItem.favoritesId,
              plantId: listingItem.plantId,
              description: listingItem.description,
              price: listingItem.price,
              stockQuantity: listingItem.stockQuantity,
              isAvailable: listingItem.available,
              plantObj: listingItem.plantObj,
            };
          });
          this.getPlantData();
          console.log("this is the seller listing");
          console.log(this.SellerListings);
        }
      });
    },
    getPlantData() {
      this.SellerListings.forEach((listingItem) => {
        PlantData.getPlantDetails(listingItem.plantId).then((response) => {
          if (response.status === 200) {
            listingItem.plantObj = response.data;
          }
        });
      });
    },

    addToCart(favoriteId) {
      console.log(this.$store.state.currentFavoriteId);
      console.log(favoriteId);
      const selectedListing = this.SellerListings.find(
        (listingItem) => listingItem.favoritesId === favoriteId
      );
      if (selectedListing.username === this.$store.state.user.username) {
        alert("This is your own listing. Please select a different listing");
      } else {
        console.log("this is the add to cart item");
        console.log(this.$store.state.user.username);
        this.$store.commit("ADD_TO_CART_ARRAY", selectedListing);
        console.log(this.$store.state.cartArray);
      }
    },
  },

  created() {
    this.getAllListings();
  },
};
</script>

<style>
</style>