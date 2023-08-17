<template>
  <div class="container-fluid mt-5">
    <div class="row">
      <div class="col-md-3">
        <b-sidebar title="Cart" shadow>
          <div class="cart-content">
            <h3 class="cart-title">Your Cart</h3>
            <b-card v-for="item in $store.state.cartArray" :key="item.favoriteId" class="cart-item">
              <img id="delete" src="../assets/trash-can.png" @click="deleteFromCart(item.favoritesId)">
              <b-card-text>
                <p class="item-name">{{ item.plantObj.common_name }}</p>
                <img :src="checkThumbnail(item.plantObj.default_image)" alt="Plant image" class="item-image">
                <p class="item-price">${{ item.price }}</p>
              </b-card-text>
            </b-card>
            <p class="total-price">Total Price: ${{ totalPrice }}</p>
            <router-link to="/checkout">
              <b-button @click="routeToCheckoutPage()" variant="primary" class="checkout-button">Checkout</b-button>
            </router-link>
          </div>
        </b-sidebar>
      </div>
      <div class="col-md-9">
        <form v-on:submit.prevent>
          <div class="form-group">
            <label for="title">Email Address</label>
            <input type="text" class="form-control" id="title" name="title">
          </div>

          <div class="form-group">
            <label for="cardInformation">Card Information</label>
            <input type="text" class="form-control" id="cardInformation" name="cardInformation" v-model="transaction.cardInformation">
          </div>

          <div class="d-flex justify-content-between">
            <button type="submit" class="btn btn-primary mr-2" @click="submitReview(); setCurrentReviewId(); resetForm();">Submit</button>
            <button type="button" class="btn btn-secondary" @click="resetForm();">Cancel</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>



<script>
import FavoriteService from '../services/FavoriteService'
import SellerService from '../services/SellerService'

export default {
    
    data(){
        return{
            transaction: {
                title: "",
                fromUsername: this.$store.state.cartArray.username,
                toUsername: this.$store.state.user.username,
                price: this.$store.state.cartArray.price,
                plantId: this.$store.state.user.plantId,
                cardInformation: '',
            }
        }
    },
    methods:{
        confirmPurchase(){
             //call the sellersService and delete an entry based on favorite Id
            this.$store.state.cartArray.forEach(item => {
              SellerService.deleteListing(item.favoritesId)
              FavoriteService.updateFavoritesUsername(item.favoritesId)
              this.deleteFromCart(item.favoritesId)
              if(item.stockQuantity == 0){
                this.$router.push()
              }
        });
        
    },
    deleteFromCart(favoriteId){
        //mutation to delete from cart array in store
        console.log("this is the favorite ID of the listing i click on")
        console.log(favoriteId)
        this.$store.commit('DELETE_FROM_CART_ARRAY', favoriteId)
    },

},
computed:{
 totalPrice() {
      return this.$store.state.cartArray.reduce((total, item) => total + item.price, 0);
    
  },
}
}
</script>
<style>

</style>