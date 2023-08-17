import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if(currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    activePlant: 0,
    currentFavoriteId: 0,
    currentReviewId: 0,
    plantsArray: [],
    cartArray: []
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user',JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },
    ADD_TO_CART_ARRAY(state, listingItem){
      state.cartArray.push(listingItem)
    },
    SET_ACTIVE_PLANT(state, nPlantId){
      state.activePlant = nPlantId;
    },
    SET_REVIEW_ID(state, reviewId){
      state.currentReviewId = reviewId
    },
    SET_FAVORITE_PLANT(state, favoriteId){
      state.currentFavoriteId = Number(favoriteId);
    },
    CLEAR_CART_ARRAY(state){
      state.cartArray = [];
    },
    DELETE_FROM_CART_ARRAY(state, favoriteId){
      console.log(favoriteId)
        const filterFunction = state.cartArray.filter((cartItem) => cartItem.favoritesId !== favoriteId)
        console.log(filterFunction)
        state.cartArray = filterFunction;
      }
    
    
  }
  ////////// USE THIS TO ROUTE /////
})
