<template>
  <div class="mainSeller">
    <div>
      <h1 id="current">Current Listings</h1>
    </div>
    <div
      id="listingCard"
      v-for="listingItem in filteredAvailablePlants"
      v-bind:key="listingItem.listingId"
    >
      <b-card
        id="card"
        :title="listingItem.plantObj.common_name"
        v-bind:img-src="checkThumbnail(listingItem.plantObj.default_image)"
        alt="Plant Image"
        img-top
        tag="article"
        style="max-width: 20rem"
        class="mb-2 flex1"
      >
        <b-card-text>
          <img id="user" src="../assets/user.png">
          {{ listingItem.username }}
          <br>
          <div id="description">
            <h5><strong>Description:</strong>
              {{ listingItem.description }}
            </h5>
            
          </div>
          <div id="price"><span >${{ listingItem.price }}</span><strong>Qty:</strong>
            {{ listingItem.stockQuantity }}</div>
        </b-card-text>
        <img
          id="delete"
          src="../assets/flower-basket.png"
          @click="addToCart(listingItem.listingId)"
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
          listingId: "",
          username: "",
          favoritesId: "",
          plantId: "",
          description: "",
          price: "",
          stockQuantity: 0,
          isAvailable: "",
          plantObj: {},
          default_image: "",
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
          console.log("this is the response")
          console.log(response.data)
          response.data.forEach(item => {
            console.log("this is the item stock quantity")
            console.log(item.stockQuantity)
            if (item.stockQuantity === 0){
              SellerService.deleteListing(item.favoritesId)
            }
          })
          }
          this.SellerListings = response.data.map((listingItem) => {
            return {
              listingId: listingItem.listingId,
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
    );
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
    checkSufficentQuantity(listingId) {

      if(listingId === undefined){
        return false;
      }
      const selectedListing = this.SellerListings.find(
        (listingItem) => listingItem.listingId === listingId
      );
      console.log(listingId)
      if (
        selectedListing.listingId == listingId &&
        selectedListing.stockQuantity > 0
      ) {
        return true;
        // console.log("it updated it");
        // console.log(item.stockQuantity);
      }

      return false;
    },

    addToCart(listingId) {
      const qtyRequest = 1;
      const selectedListing = this.SellerListings.find(
        (listingItem) => listingItem.listingId === listingId
      );
      let hasSufficentQuantity = this.checkSufficentQuantity(
        listingId
      );
      if (selectedListing.username === this.$store.state.user.username) {
        alert("This is your own listing. Please select a different listing");
        //hide this with the state token thing
      } else if (!hasSufficentQuantity) {
        alert("insufficient quantity");
      } else {
        selectedListing.stockQuantity--;
        // SellerService.insertBuyer(listingId, selectedListing.favoritesId, qtyRequest).then((response => {
        //   if (response.status === 200){
          SellerService.updateStock(listingId, qtyRequest);
          this.$store.commit("ADD_TO_CART_ARRAY", selectedListing);
          }
        
        

        
      },
    
    checkThumbnail(default_image) {
      if (default_image === null) {
        return "https://static.vecteezy.com/system/resources/previews/024/551/617/original/gardening-houseplant-error-404-flash-message-environmental-friendly-watering-plant-empty-state-ui-design-page-not-found-popup-cartoon-image-flat-illustration-concept-on-white-background-vector.jpg";
      }
      return default_image.medium_url;
    },
  },
  created() {
    this.getAllListings();
  },
};
</script>
<style scoped>
#listingCard {
  display: inline-flex;
  flex-wrap: wrap;
  justify-content: center;
  margin-left: 3%;
}
#card {
  /* height: 5vh; */
  box-shadow: 3px 3px 5px gray;
}
#card > img {
  height: 45vh;
}
#delete {
  height: 6vh;
  margin-left: 79%;
  margin-top: 3%;
}
#current {
  margin: 4%;
}
span{
  margin-right: 58%;
}
#price{
  margin-top: 10%;
}
#user{
  height: 3vh;
}
</style>