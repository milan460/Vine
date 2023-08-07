import axios from "axios";

const http = axios.create({
    baseURL: "https://perenual.com/api/"
});

const apiKey = 'sk-9krg64cea0dfc5fe81776';



export default{

    getPlantData(nPage){
        return http.get('species-list?page=' + nPage + '&key=' + apiKey);
    },
    getPlantDetails(nPlantId){
        return http.get('species/details/' + nPlantId + '?key=' + apiKey)
    }
}