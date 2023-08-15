<template>
  <div id="portal">
     
    <form @submit.prevent="addListing" class="mt-3">
         <h1>Plant Information</h1>
      <div class="form-group">
        <label for="description">Description:</label>
        <textarea
          id="description"
          class="form-control"
          rows="4"
          v-model="listing.description"
        ></textarea>
      </div>

      <div class="form-group">
        <label for="price">Price:</label>
        <input
            type="number"
            class="form-control"
            placeholder="0.00"
            min="0"
            step="0.01"
            v-model="listing.price"
        />
      </div>


      <div class="form-group">
        <label for="stockQuantity">Stock Quantity:</label>
        <input
          type="number"
          class="form-control"
          placeholder="0"
          min="1"
          v-model="listing.stockQuantity"
        />
      </div>

      <button type="submit" class="btn btn-primary">Add Listing</button>
    </form>

    
  </div>
</template>

<script>
import SellerService from '../services/SellerService';
export default {
  data() {
    return {
      showAddForm: false,
      listing: {
        username: this.$store.state.user.username,
        favoritesId: this.$store.state.currentFavoriteId,
        description: "",
        price: "",
        isAvailable: true,
        stockQuantity: "",
      },
    };
  },
  computed:{
    //   filterStringToNumber(){
    //       let listing;
    //       if(this.listing.price === typeof String){
    //             listing = Number(this.listing.price)
    //       }
    //       return this.listingParse.price 
    //   }
  },
  methods: {
    /**
     * this is where we would call the add-listing method. we are gonna pass in the whole listin object into this method.
     *  for data properties, we would map the favoritesId, description, price, is available(button), stock quantity.
     *
     */
    addListing() {
        // this.listing.isAvailable = !this.listing.isAvailable
        // console.log(this.listing.isAvailable)
    //   let listingParse = {
    //       username: this.$store.state.user.username,
    //         favoritesId: (this.$store.state.currentFavoriteId),
    //         description: this.listing.description,
    //         price: Number(this.listing.price),
    //         isAvailable: true,
    //         stockQuantity: this.listing.stockQuantity,
    //   }
    //   console.log(listingParse)
    //   console.log('this is the listing object')
    if(this.listing.isAvailable === true){
      SellerService.addListing(this.listing).then( (response) => {
          if(response.status === 201){
              console.log('this has gone thru')
          }
      })
    }
    },

  },
};
</script>

<style scoped>
#portal {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.form-group {
  margin-bottom: 1rem;
}

label {
  margin-right: 0.5rem;
}

#portal form {
  max-width: 450px; /* Adjust this value as needed */
  width: 100%;
  padding: 20px;
  box-shadow: 0px 16px 48px 0px rgba(0, 0, 0, 0.176);
  border-radius: 20px;
}

#btn {
  margin-left: 23%;
  width: 7vw;
  margin-bottom: 5%;
  border-radius: 5%;
}
</style>