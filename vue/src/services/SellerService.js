import axios from "axios";

export default{

    getAllSellerListings(){
        return axios.get('/seller-listings')
    },

    addListing(listing){
        return axios.post('/addListing', listing)
    },

    deleteListing(favoriteId){
        return axios.delete('/deleteListing/' + favoriteId)
    },

    updateStock(favoritesId, requestQty){
        const requestData = {favoriteId: favoritesId, requestQty: requestQty}
        const headers = {
            'Content-Type': 'application/json',
        };
        return axios.put('/updateStock/', requestData, {headers})
    }
}