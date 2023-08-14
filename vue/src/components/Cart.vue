<template>
  <div>
      <button @click="routeToCheckoutPage">Checkout</button>
  </div>
  
</template>

<script>
export default {
    methods:{
        routeToCheckoutPage(){
            this.$router.push({ name: 'checkout'})
        }
    }
}
</script>

<style>

</style>