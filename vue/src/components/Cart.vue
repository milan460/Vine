<template>
  <div>
    
    <b-sidebar id="sidebar-right" title="Cart" right shadow>
      <div class="cart-content">
        <h3 class="cart-title">Your Cart</h3>
        <b-card v-for="item in this.$store.state.cartArray" :key="item.favoriteId" class="cart-item">
        <img id="delete" src="../assets/trash-can.png" @click="deleteFromCart(item.favoritesId)" >
    
          <b-card-text>
            <p class="item-name">{{ item.plantObj.common_name }}</p>
            <img :src="item.plantObj.default_image.medium_url" alt="Plant image" class="item-image">
            <p class="item-price">${{ item.price }}</p>
            <p class="item-quantity">{{ item.stockQuantity }} in stock</p>
          </b-card-text>
        </b-card>
        <b-button @click="confirmPurchase()" variant="primary" class="checkout-button">Checkout</b-button>
      </div>
    </b-sidebar>
  </div>
</template>

<script>
import FavoriteService from '../services/FavoriteService';
import SellerService from '../services/SellerService';
export default {
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
            console.log("this is the favorites id")
            console.log(this.$store.state.cartArray)
            this.$store.state.cartArray.forEach(item => {
              SellerService.deleteListing(item.favoritesId)
              FavoriteService.updateFavoriteOwnedPlant(item.favoritesId, true)
              this.deleteFromCart(item.favoritesId)
            });
            //SellerService.deleteListing(this.$store.state.cartArray.favoritesId)
          SellerService
            //call the favoritesService and update an entry based on favorite object from the cartArray in the store
           
            FavoriteService
            //mutation clear the cart array create mutation in store
            
        }
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
