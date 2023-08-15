<template>
  <seller-portal></seller-portal>
</template>

<script>
import SellerPortal from '../components/SellerPortal.vue'
export default {
  components:{
      SellerPortal
  },

  created(){
    this.$store.commit('SET_FAVORITE_PLANT', this.$route.params.id)
  }

}
</script>

<style>

</style>