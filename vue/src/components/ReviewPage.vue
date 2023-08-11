<template>
  <div>
    <div class="element" v-for="review in reviewObj" :key="review.reviewId">
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
      <h6><img id="user" src="../assets/user.png"> {{ review.username }}</h6>
    </div>
  </div>
</template>

<script>
import ReviewService from "../services/ReviewService";

export default {
  name: "review-page",
  props: ["plantId"],
  data() {
    return {
      reviewObj: [],
    };
  },
  created() {
    this.fetchReviews();
  },
  computed: {
    currentReviewId() {
      return this.$store.state.currentReviewId;
    },
  },
  watch: {
    currentReviewId: {
      immediate: true,
      handler(newReviewId, oldReviewId) {
        if (newReviewId !== oldReviewId) {
          this.fetchReviews();
        }
      },
    },
  },
  methods: {
    fetchReviews() {
      ReviewService.listOfReview(this.plantId)
        .then((response) => {
          this.reviewObj = response.data.map((reviews) => {
            return {
              username: reviews.username,
              title: reviews.title,
              reviewDetail: reviews.reviewDetail,
              rating: reviews.rating,
              reviewId: reviews.reviewId,
              plantId: reviews.plantID,
            };
          });
        })
        .catch((error) => {
          alert(error.response.data.message);
        });
    },
  },
};
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
.element {
  width: 95%;
  height: 165px;
  background-color: rgba(255, 255, 255, 1);
  color: #000;
  border-radius: 10px;
  box-shadow: 0px 16px 48px 0px rgba(0, 0, 0, 0.176);
  margin-bottom: 1%;
}

#user{
  height: 3vh;
}
</style>