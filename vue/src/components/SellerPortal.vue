<template>
  <div id="portal">
     
    <form @submit.prevent="addListing" class="mt-3">
         <h1>Plant Information</h1>
      <div class="form-group">
        <label for="description">Description:</label>
        <textarea
          id="description"
          placeholder="(1000 character limit)"
          class="form-control"
          maxlength="1000"
          required
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
            required
            min="0"
            max="922337203685"
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
          required
          max="1000000"
          min="1"
          v-model="listing.stockQuantity"
        />
      </div>
      <button type="submit" @click="addListing" class="btn btn-success">Add Listing</button>
      
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
        Available: true,
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
    
    if(this.listing.Available === true){
      this.$router.push( {name: 'favorites-page'} )
      
      SellerService.addListing(this.listing).then( (response) => {
          if(response.status === 200){
              console.log('this has gone thru')
              alert("listing was added successfully!")
              this.$router.push( {name: 'favorites-page'} )
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
  background-image: url(../assets/testBG.jpg);
  background-position: bottom;
  background-size: contain;
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
.mt-3{
  background-color: rgb(78, 189, 105);
  opacity: 0.96;
}

#btn {
  margin-left: 23%;
  width: 7vw;
  margin-bottom: 5%;
  border-radius: 5%;
}
</style>