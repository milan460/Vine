<template>
  <div>
    <h1>Identify a Plant</h1>
    <FileInput @images-updated="updateImages" />
    <button id="btn" @click="findPlant(images)">Identify Plant</button>
    <ResultDisplay :identificationResult="identificationResult" />
  </div>
</template>

<script>
import PlantId from "../services/PlantId.js"; // Replace with correct import path
import FileInput from "../components/FileInput.vue";
import ResultDisplay from "../components/ResultDisplay.vue";

export default {
  components: {
    FileInput,
    ResultDisplay,
  },
  data() {
    return {
      apiKey: "Mc1IKHgmm7ME5fFnrsFvwKDKpwpR104BjDZGH06sJvA4MimNuw", // Replace with your actual API key
      images: [],
      identificationResult: null,
    };
  },
  methods: {
    updateImages(images) {
      console.log("these are the images")
      console.log(images)
      this.images = images;
    },
     findPlant(images) {
      try {
        const base64Array = images.map(item => item.base64)
        console.log("this is the base 64 array")
        console.log(base64Array)
      const requestBody = {
      images: base64Array,
      latitude: 49.207,
      longitude: 16.608,
      similar_images: true,
    };
      PlantId.identifyPlant(requestBody).then((response) => {
          console.log("this is the response data for the identification results")
          console.log (response)
          this.identificationResult = response;
          console.log(this.identificationResult)
      })
      } catch (error) {
        console.error("Identification error:", error);
        // Handle error
      }
    },
  },
};
</script>
<style scope>



#btn{
  background-color: rgb(152, 226, 152);
  border: rgb(152, 226, 152) 10px solid;
  border-radius: 4px ; 

}
#btn:hover{
  background-color: rgb(152, 226, 152);
  border: rgb(152, 226, 152) 10px solid;
  border-radius: 4px ; 

}







</style>