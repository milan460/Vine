import axios from "axios";


const http = axios.create({
    baseURL: "https://perenual.com/api/"
});

const apiKey = 'sk-cWXs64d07319aced21784';




export default{

    // getPlantData(nPage){
    //     return http.get('species-list?page=' + nPage + '&key=' + apiKey);
    // },
    getPlantDetails(nPlantId){
        return http.get('species/details/' + nPlantId + '?key=' + apiKey);
    },
   getIndoorOrOutdoorPlants(nPage, numParam){
         return http.get('species-list?page=' + nPage + '&key=' + apiKey + '&indoor=' + numParam)
    },
    getEdiblePlants(nPage, nParam){
        return http.get('species-list?page=' + nPage + '&key=' + apiKey + '&edible=' + nParam)
    },
    getPlantCareData(nPlantId){
        return http.get('species-care-guide-list?key=' + apiKey + '&species_id=' + nPlantId)
    },
    getPlantSearch(plantName, page){
        return http.get('species-list?key=' + apiKey, {params: {page: page, search: plantName}})
    }
}