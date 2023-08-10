<template>
  <div>
    <div
      class="review-container"
      v-for="review in reviewObj"
      :key="review.reviewId"
    >
      <h1 id="title">
        {{ review.title }}
        <img
          class="ratingStar"
          src="../assets/leaf.png"
          v-bind:title="review.rating"
          v-for="n in review.rating"
          v-bind:key="n"
        />
      </h1>
      <p>{{ review.reviewDetail }}</p>
      <h6>{{ review.username }}</h6>
    </div>
  </div>
</template>

<script>
import ReviewService from "../services/ReviewService";

export default {
    name: 'review-page',
    props: ['plantId'],
    data(){
        return{
            reviewObj: [],
        }
    },
    created(){
        this.fetchReviews();
    },
    computed: {
    currentReviewId() {
      return this.$store.state.currentReviewId;
    },
  },
    watch:{
        currentReviewId: {
      immediate: true,
      handler(newReviewId, oldReviewId) {
        if (newReviewId !== oldReviewId) {
          this.fetchReviews();
        }
      },
    },
    },
    methods:{
        fetchReviews(){
            ReviewService.listOfReview(this.plantId).then(response => {
                this.reviewObj = response.data.map(reviews => {
                    return {
                        username: reviews.username,
                        title: reviews.title,
                        reviewDetail: reviews.reviewDetail,
                        rating: reviews.rating,
                        reviewId: reviews.reviewId,
                        plantId: reviews.plantID
                    
                    }
                })
            }).catch(error => {
            alert(error.response.data.message)

            })
        }

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

p {
  font-size: 20px;
}
</style>