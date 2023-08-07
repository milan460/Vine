import axios from "axios";

const http = axios.create({
    baseURL: "https://perenual.com/api/"
});

const apiKey = 'sk-NPHb64cdc278bbc211769';



export default{

    getPlantData(nPage){
        return http.get('species-list?page=' + nPage + '&key=' + apiKey);
    },
    getPlantDetails(nPlantId){
        return http.get('species/details/' + nPlantId + '?key=' + apiKey)
    }
}