<template>
    <form v-on:submit.prevent>
        <div>

            <label for="title">Title</label><br>
            <input type="text" id="title" name="title" v-model="review.title"><br>

            <label for="reviewDetail">Description</label><br>
            <input type="text" id="reviewDetail" name="reviewDetail" v-model="review.reviewDetail"><br>

            <label for="rating">Rating</label><br>
            <input type="number" id="rating" name="rating" placeholder="1" min="1" max="5" v-model="review.rating"><br>

        </div>
        <div>
            <button type="submit" @click="submitReview(); setCurrentReviewId(); resetForm();">Submit</button>
            <button type="cancel" @click="resetForm();">Cancel</button>
        </div>
    </form>
</template>

<script>
import ReviewService from '../services/ReviewService'
export default {

    props:['plantId', 'showTag'],
    data(){
        return {
            showAddForm: false,
            review: {
                username: this.$store.state.user.username,
                title: '',
                reviewDetail: '',
                rating: '',
                plantID: this.plantId
            }
        }
    },
    computed:{
        reviewId(){
            return this.$store.state.currentReviewId
        },
       
    },
    methods:{
        setCurrentReviewId(){
            console.log('this has run')
            ReviewService.getRecentReviewId().then(response => {
                if(response.status === 200){
                    this.$store.commit('SET_REVIEW_ID', response.data)
                }
            })
            .catch(error => {
                alert(error.response.data.message)
            })
        },
        resetForm(){
            this.review.title = '',
            this.review.reviewDetail = '',
            this.review.rating = '',
            this.$emit('form-submitted')
        },
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

    }
}
</script>

<style>

</style>