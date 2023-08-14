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

deleteFromFavoritesWithPlantId(plantId){

    // const requestData = {}


    return axios.delete('favorites/' + plantId)
},

updateFavoritesUsername(favoriteId){
    const requestData = {favoriteId: favoriteId};
    
    const headers = {
        'Content-Type': 'application/json',
    };

    return axios.put('/updateFavorite', requestData, {headers})
},

updateFavoriteOwnedPlant(favoriteId){
    const requestData = {favoriteId: favoriteId};
    
    const headers = {
        'Content-Type': 'application/json',
    };

    return axios.put('/updateOwned', requestData, {headers})
}

}