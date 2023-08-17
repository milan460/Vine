<template>
  <div class="container mt-5">
    <form v-on:submit.prevent>
      <div class="form-group">
        <label for="title">Title</label>
        <input type="text" class="form-control" id="title" name="title" v-model="transaction.title">
      </div>

      <div class="form-group">
        <label for="fromUsername">From Username</label>
        <input type="text" class="form-control" id="fromUsername" name="fromUsername" :value="transaction.fromUsername" disabled>
      </div>

      <div class="form-group">
        <label for="toUsername">To Username</label>
        <input type="text" class="form-control" id="toUsername" name="toUsername" :value="transaction.toUsername" disabled>
      </div>

      <div class="form-group">
        <label for="price">Price</label>
        <input type="text" class="form-control" id="price" name="price" :value="transaction.price" disabled>
      </div>

      <div class="form-group">
        <label for="plantId">Plant ID</label>
        <input type="text" class="form-control" id="plantId" name="plantId" :value="transaction.plantId" disabled>
      </div>

      <div class="form-group">
        <label for="cardInformation">Card Information</label>
        <input type="text" class="form-control" id="cardInformation" name="cardInformation" v-model="transaction.cardInformation">
      </div>

      <div class="d-flex justify-content-between">
        <button type="submit" class="btn btn-primary mr-2" @click="confirmPurchase">Submit</button>
        <button type="button" class="btn btn-secondary" @click="resetForm();">Cancel</button>
      </div>
    </form>
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
}
}
</script>
<style>

</style>