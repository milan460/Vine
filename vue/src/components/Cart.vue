<template>
  <div>
    
    <b-sidebar id="sidebar-right" title="Cart" right shadow>
      <div class="cart-content">
        <h3 class="cart-title">Your Cart</h3>
        <b-card v-for="item in this.$store.state.cartArray" :key="item.favoriteId" class="cart-item">
        <img id="delete" src="../assets/trash-can.png" @click="deleteFromCart(item.favoritesId)" >
    
          <b-card-text>
            <p class="item-name">{{ item.plantObj.common_name }}</p>
            <img :src="checkThumbnail(item.plantObj.default_image)" alt="Plant image" class="item-image">
            <p class="item-price">${{ item.price }}</p>
          </b-card-text>
        </b-card>
        <p class="total-price">Total Price: ${{ totalPrice }}</p>
         
        <b-button @click="confirmPurchase" variant="primary" class="checkout-button">Checkout</b-button>
        
      </div>
    </b-sidebar>
  </div>
</template>



<script>
import FavoriteService from '../services/FavoriteService';
import SellerService from '../services/SellerService';
export default {
  
computed:{
 totalPrice() {
      return this.$store.state.cartArray.reduce((total, item) => total + item.price, 0);
    
  },
  
},
  methods: {
    routeToCheckoutPage() {
      this.$router.push({ name: 'checkout' });
    },
    deleteFromCart(favoriteId){
        //mutation to delete from cart array in store
        console.log("this is the favorite ID of the listing i click on")
        console.log(favoriteId)
        this.$store.commit('DELETE_FROM_CART_ARRAY', favoriteId)
    },
    confirmPurchase(){
            //call the sellersService and delete an entry based on favorite Id
            this.$store.state.cartArray.forEach(item => {
             let deleteDuplicates =  this.deleteDuplicatesFromCart(item.favoritesId)
             if(deleteDuplicates){
              SellerService.deleteListing(item.favoritesId)
              FavoriteService.updateFavoritesUsername(item.favoritesId)
              this.deleteFromCart(item.favoritesId)
              if(item.stockQuantity == 0){
                window.location.reload();
              }}
            });
            //SellerService.deleteListing(this.$store.state.cartArray.favoritesId)
          SellerService
            //call the favoritesService and update an entry based on favorite object from the cartArray in the store
           
            FavoriteService
            //mutation clear the cart array create mutation in store
            
        },

        deleteDuplicatesFromCart(favoriteId) {
        
      const indexes = [];
      this.$store.state.cartArray.forEach((item, index) => {
        if (item.favoritesId === favoriteId) {
          indexes.push(index);
        }
      });

      // Keep the first occurrence and remove the others
      if (indexes.length >= 1) {
        // Remove items from the cartArray in reverse order to maintain indexes
        for (let i = indexes.length - 1; i > 0; i--) {
          this.$store.commit('DELETE_FROM_CART_ARRAY', this.$store.state.cartArray[indexes[i]].favoritesId);
        }
        return true;
      }
      else{
        return false;
      }
    },


            checkThumbnail(default_image) {
      if (default_image === null) {
        return "https://static.vecteezy.com/system/resources/previews/024/551/617/original/gardening-houseplant-error-404-flash-message-environmental-friendly-watering-plant-empty-state-ui-design-page-not-found-popup-cartoon-image-flat-illustration-concept-on-white-background-vector.jpg";
      }
      return default_image.medium_url;
    },
  },
};
</script>

<style>
.cart-content {
  padding: 20px;
}

.cart-title {
  font-size: 20px;
  margin-bottom: 10px;
}

.cart-item {
  margin-bottom: 15px;
  border: none;
}
#delete{
  height: 2vh;
}

.item-name {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 5px;
}

.item-image {
  width: 100%;
  max-height: 150px;
  object-fit: cover;
  margin-bottom: 10px;
}

.item-price {
  font-size: 16px;
  margin-bottom: 5px;
}

.item-quantity {
  font-size: 14px;
  color: #888;
}
.checkout-button{
   margin-top: auto;
  align-self: flex-end;
}
</style>
