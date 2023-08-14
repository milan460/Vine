<template>
  <form v-on:submit.prevent>
        <div>

            <label for="cardInformation">Title</label><br>
            <input type="text" id="title" name="title" v-model="review.title"><br>

            

        </div>
        <div>
            <button type="submit" @click="submitReview(); setCurrentReviewId(); resetForm();">Submit</button>
            <button type="cancel" @click="resetForm();">Cancel</button>
        </div>
    </form>
</template>

<script>
import FavoriteService from '../services/FavoriteService'
import SellerService from '../services/SellerService'

export default {
    
    data(){
        return{
            transaction: {
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
            SellerService.deleteListing(this.$store.state.cartArray.favoriteId)
            FavoriteService
            //call the sellersService and delete an entry based on favorite Id
            //call the favoritesService and update an entry based on favorite object from the cartArray in the store
            //call the receiptService and post the entry with the transaction object from the data properties above
        }
        
    }
}
</script>
<style>

</style>