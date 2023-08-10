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
                title: '',
                reviewDetail: '',
                rating: '',
                plantID: this.plantId
            }
        }
    },
    methods:{
       submitReview(){
           console.log("this is the review Id before")
           console.log(this.$store.state.currentReviewId)
           this.incrementReviewId
           ReviewService.addReview(this.review).then( response => {
               if(response.status === 201){
                   console.log("review request has been sent")
                   this.setCurrentReviewId()
               }
           })
           .catch(error => {
                alert(error.response.data.message)
            })
            console.log("this is the review Id after")
            console.log(this.$store.state.currentReviewId)
        },

        setCurrentReviewId(){
            ReviewService.getRecentReviewId().then(response => {
                if(response.status === 200){
                    this.$store.commit('SET_REVIEW_ID', response.data)
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