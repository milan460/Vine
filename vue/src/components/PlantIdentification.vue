<template>
  <div id="page">
    
      <img id="pic" src="../assets/idplant.jpg">
    
    <FileInput id="choose" @images-updated="updateImages" />
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
<style scoped>
#btn{
  background-color: rgb(152, 226, 152);
  border: rgb(152, 226, 152) 3px solid;
  border-radius: 3px; 
  margin-left: 47%;
  margin-top: 1%;
  

}
#btn:hover{
 background-color:rgb(169, 252, 169);
  border-radius: 2px;

}
#choose{
margin-left: 47%;


}
#page{
  /* background-image: url(../assets/background.jpg); */
  background-blend-mode: lighten;
  background-size: contain;
}
h1{
  margin-left: 43%;
  font-weight: bolder;
}
#pic{
  height: 30vh;
  margin-left: 35%;

 

}








</style>