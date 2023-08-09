<template>
  <div class="review-container">
<h1>{{reviewObj.title}}</h1>
<div class ="rating">
    <img class ="ratingStar" src="../assets/star.png" v-bind:title="reviewObj.rating" v-for="n in reviewObj.rating" v-bind:key="n">

</div>
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