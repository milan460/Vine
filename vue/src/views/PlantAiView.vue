<template>
  <div id="app">
    <IdentificationRequest :identification-result="identificationResult" />
    <ResultDisplay :identificationResult="identificationResult" />
  </div>
</template>

<script>
import IdentificationRequest from "../components/PlantIdentification.vue";
import ResultDisplay from "../components/ResultDisplay.vue";

export default {
  components: {
    IdentificationRequest,
    ResultDisplay,
  },
  data() {
    return {
      identificationResult: null,
    };
  },
};
</script>