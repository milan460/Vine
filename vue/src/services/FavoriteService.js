import axios from "axios";

export default{


addToFavorites(nPlantId){

    const requestData = {plantId: nPlantId};

    const headers = {
        'Content-Type': 'application/json',
    };

    return axios.post('/favorites-add/', requestData, { headers });
},

getFavoritesList(){
    return axios.get('/favorites');
},

deleteFromFavoritesWithPlantId(favoriteId){

    // const requestData = {}


    return axios.delete('/deleteFavorites/' + favoriteId)
},

updateFavoritesUsername(favoriteId){
    const requestData = {favoriteId: favoriteId};
    
    const headers = {
        'Content-Type': 'application/json',
    };

    return axios.put('/updateFavorite', requestData, {headers})
},

updateFavoriteOwnedPlant(favoriteId, ownedPlant){
    const requestData = {favoriteId: favoriteId, ownedPlant: ownedPlant};
    
    const headers = {
        'Content-Type': 'application/json',
    };

    return axios.put('/updateOwned', requestData, {headers})
}

}