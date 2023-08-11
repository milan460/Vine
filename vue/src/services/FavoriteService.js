import axios from "axios";

export default{

AddtoFavorites(){
    return axios.post('/favorites-add/')
},

getFavoritesList(){
    return axios.get('/favorites');
},

deleteFromFavorites(nPlantId){
    return axios.delete('favorites/' + nPlantId)
}

}