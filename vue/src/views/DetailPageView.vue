<template>
  <div>
      <plant-detail />
  </div>
</template>

<script>

import PlantDetail from '../components/DetailPage.vue'
export default {
    name: 'plant-detail-view',

    components: {
        PlantDetail
    },

    created(){
        this.$store.commit('SET_ACTIVE_PLANT', this.$route.params.id);
    }

}
</script>

<style>

</style>