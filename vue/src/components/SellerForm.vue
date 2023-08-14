<template>
  <form v-on:submit.prevent>
        <div>

            <label for="description">Description</label><br>
            <input type="text" id="description" name="description" v-model="listing.description"><br>

            <label for="price">Price</label><br>
            <input type="number" id="price" name="price" v-model="listing.price"><br>

            <label for="stockQuantity">Quantity</label><br>
            <input type="number" id="stockQuantity" name="stockQuantity" v-model="listing.stockQuantity"><br>

            <button>Is this available for sale?</button>

        </div>
        <div>
            <button type="submit" @click="submitReview(); setCurrentReviewId(); resetForm();">Submit</button>
            <button type="cancel" @click="resetForm();">Cancel</button>
        </div>
    </form>
</template>

<script>
export default {
    props:['favoriteList'],
    data(){
        return{
            showAddForm: false,
            listing:{
                username: this.$store.state.user.username,
                favorite_id: this.favoriteList.favorite_id,
                description: '',
                price: '',
                isAvailable: false,
                stockQuantity: '',

            }
        }
    },
methods:{
    /** 
     * this is where we would call the add-listing method. we are gonna pass in the whole listin object into this method.
     *  for data properties, we would map the favoritesId, description, price, is available(button), stock quantity.
     * 
     */
    addListing(){
    
    }
}
}
</script>

<style>

</style>