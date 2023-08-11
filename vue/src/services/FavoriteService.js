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

deleteFromFavorites(favoriteId){
    return axios.delete('favorites/' + favoriteId)
}

}