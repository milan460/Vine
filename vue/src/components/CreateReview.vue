<template>
    <form v-on:submit.prevent="submitReview">
        <div>

            <label for="title">Title</label><br>
            <input type="text" id="title" name="title" v-model="review.title"><br>

            <label for="reviewDetail">Description</label><br>
            <input type="text" id="reviewDetail" name="reviewDetail" v-model="review.reviewDetail"><br>

            <label for="rating">Rating</label><br>
            <input type="number" id="rating" name="rating" placeholder="1" min="1" max="5" v-model="review.rating"><br>

        </div>
        <div>
            <input type="submit" value="Save">
            <input type="button" value="Cancel" @click="resetForm">
        </div>
    </form>

 
</template>

<script>
import ReviewService from '../services/ReviewService'
export default {

    props:['plantId', 'showTag'],
    data(){
        return {
            showNewReview: false,
            review: {
                username: this.$store.state.user.username,
                title: '',
                reviewDetail: '',
                rating: '',
                plantID: this.plantId,
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
            ReviewService.getRecentReviewId().then(response => {
                if(response.status === 200){
                    console.log('this has run')
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
           this.incrementReviewId
           ReviewService.addReview(this.review).then( response => {
               if(response.status === 200){
                   this.resetForm()
                   this.$store.commit('ADD_ONE_REVIEW', response.data)
               }
           })
           .catch(error => {
                alert(error.response.data.message)
            })

        },

    }
}
</script>

<style scoped>

</style>