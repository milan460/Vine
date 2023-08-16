<template>
  <div>
    <FileInput @images-updated="updateImages" />
    <button @click="findPlant(images)">Identify Plant</button>
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
      const requestBody = {
      images: images,
      latitude: 49.207,
      longitude: 16.608,
      similar_images: true,
    };
      const response =  PlantId.identifyPlant(requestBody); // Pass apiKey and images to identifyPlant function
         console.log("Image Data:", this.requestBody);
        this.identificationResult = response.data;
        console.log("this is the response for the identification")
        console.log(response.data)
      } catch (error) {
        console.error("Identification error:", error);
        // Handle error
      }
    },
  },
};
</script>