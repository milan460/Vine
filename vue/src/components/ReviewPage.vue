<template>
  <div class="review-container">
<h1>{{reviewObj.title}} {{reviewObj.rating}}</h1>
<h2>{{reviewObj.username}}</h2>
<p>{{reviewObj.reviewDetail}}</p>
  </div>
</template>

<script>
import ReviewService from '../services/ReviewService';

export default {
    name: 'review-page',
    props: ['plantId'],
    data(){
        return{
            reviewObj: {},
            currentReviewId: 0,
            
        }
    },
    created(){
        ReviewService.getReview(this.plantId).then(response => {
            this.reviewObj = response.data
        }).catch(error => {
           alert(error.response.data.message)

        })
    }
}
</script>

<style>
.review-container {
    border-style: solid;
    border-color: black;
    border-width: 2px;
}
</style>