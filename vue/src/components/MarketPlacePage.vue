<template>
  <div class="mainSeller">
    <div>
      <h1>Current Listings</h1>
    </div>
    <div
      id="listingCard"
      v-for="listingItem in filteredAvailablePlants"
      v-bind:key="listingItem.favoritesId"
    >
      <b-card
      id="card"
     :title="listingItem.plantObj.common_name"
        v-bind:img-src="listingItem.plantObj.default_image.medium_url"
        alt="Plant Image"
        img-top
            tag="article"
            style="max-width: 20rem"
            class="mb-2 flex1"
      >
        <b-card-text>
          
          
          <h5>Description:</h5>

          {{ listingItem.description }}
          <br>
          <strong>${{ listingItem.price }}</strong>
          <br>
          Qty:
          <strong>{{ listingItem.stockQuantity }}</strong>
          <br>
          Sold By:
          <strong>{{ listingItem.username }}</strong>
        </b-card-text>

       
        <img
          id="delete"
          src="../assets/flower-basket.png"
          @click="addToCart(listingItem.favoritesId)"
        />
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
  computed: {
    filteredAvailablePlants() {
      return this.SellerListings.filter(
        (sellerItem) => sellerItem.isAvailable === true
      );
    },
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

<style scoped>

#listingCard{
  display: inline-flex;
  flex-wrap: wrap;
  justify-content: center;
  margin-left: 3%;
  
}
#card{
  /* height: vh; */
  box-shadow: 3px 3px 5px gray;
}
#delete {
  height: 6vh;
  margin-left: 79%;
  margin-top: 3%;
}

</style>