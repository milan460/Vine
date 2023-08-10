<template>
    <form v-on:submit.prevent>
        <div>

            <label for="title">Title</label><br>
            <input type="text" id="title" name="title" v-model="review.title"><br>

            <label for="reviewDetail">Description</label><br>
            <input type="text" id="reviewDetail" name="reviewDetail" v-model="review.reviewDetail"><br>

            <label for="rating">Rating</label><br>
            <input type="number" id="rating" name="rating" v-model="review.rating"><br>

        </div>
        <div>
            <button type="submit" @click="submitReview">Submit</button>
        </div>
    </form>
</template>

<script>
import ReviewService from '../services/ReviewService'
export default {
    props:['plantId'],
    data(){
        return {
            review: {
                username: this.$store.state.user.username,
                reviewId: 6,
                title: '',
                reviewDetail: '',
                rating: '',
                plantID: this.plantId
            }
        }
    },
    methods:{
       submitReview(){
           ReviewService.addReview(this.review).then( response => {
               if(response.status === 201){
                   console.log("review request has been sent")
               }
           })
           .catch(error => {
                alert(error.response.data.message)
            })
        }
    }
}
</script>

<style>

</style>