import axios from 'axios';

export default {

  // getReview(id) {
  //   return axios.get(`/review/${id}`)
  // },

  addReview(review) {
    return axios.post('/review', review)
  },

  deleteReview(reviewId) {
      return axios.put('/review/:id', reviewId)
  },

  listOfReview(id) {
      return axios.get(`/review/${id}`)
  }


}