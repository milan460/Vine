<template>
  <div class="review-container">
<h1 id="title">{{reviewObj.title}} <img class ="ratingStar" src="../assets/star.png" v-bind:title="reviewObj.rating" v-for="n in reviewObj.rating" v-bind:key="n"></h1>
<p>{{reviewObj.reviewDetail}}</p>
<h6>{{reviewObj.username}}</h6>
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
.ratingStar {
    height: 40px;
    
}

#title {
font-size: 40px;

}

p{
    font-size: 20px;
}


</style>