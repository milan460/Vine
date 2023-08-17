<template>
  <div>
   <template>
  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="card">
          <div class="card-body">
            <div v-if="identificationResult">
              <h2 class="card-title">Plant Identification Results</h2>
              <div v-for="suggestion in identificationResult.result.classification.suggestions" :key="suggestion.id" class="result-item">
                <h3>Plant Name: {{ suggestion.name }}</h3>
                <p>Probability of plant: {{ suggestion.probability }}</p>
                <p>Simila</p>
                <img :src="suggestion.similar_images[0].url" alt="Plant Image" class="img-fluid" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
  </div>
</template>

<script>
export default {
  props: {
    identificationResult: Object,
  },
};
</script>