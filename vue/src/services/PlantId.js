import axios from "axios";


const apiKey = "Mc1IKHgmm7ME5fFnrsFvwKDKpwpR104BjDZGH06sJvA4MimNuw";
const apiUrl = "https://plant.id/api/v3/identification";
export default{


identifyPlant(requestBody) {
  const config = {
    headers: {
      "Content-Type": "application/json",
      "Api-Key": apiKey,
    },
  };
  console.log("Request Body:", requestBody); // Log the request body
  console.log("Request Headers:", config.headers); // Log the request headers

  try {
    const response = axios.post(apiUrl, requestBody, config);
    return response.data;
  } catch (error) {
    console.error("Identification error:", error);
    throw error;
  }
}
}


// import axios from 'axios';
// import * as fs from 'fs';

// const files = ['../img/photo1.jpg', '../img/photo2.jpg', '../img/photo3.jpg'];

// const base64files = files.map(file => fs.readFileSync(file, 'base64'));

// const data = {
//     api_key: "-- ask for one: https://web.plant.id/api-access-request/ --",
//     images: base64files,
//     /* modifiers docs: https://github.com/flowerchecker/Plant-id-API/wiki/Modifiers */
//     modifiers: ["crops_fast", "similar_images"],
//     plant_language: "en",
//     /* plant details docs: https://github.com/flowerchecker/Plant-id-API/wiki/Plant-details */
//     plant_details: ["common_names",
//         "url",
//         "name_authority",
//         "wiki_description",
//         "taxonomy",
//         "synonyms"],
// };

// axios.post('https://api.plant.id/v2/identify', data).then(res => {
//     console.log('Success:', res.data);
// }).catch(error => {
//     console.error('Error: ', error)
// })