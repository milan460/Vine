import axios from "axios";

export default{

    getAllSellerListings(){
        return axios.get('/seller-listings')
    },

    addListing(listing){
        return axios.post('addListing', listing)
    }
}