<template>
  <div>
      <button @click="routeToCheckoutPage">Checkout</button>
      <b-sidebar id="sidebar-right" title="Sidebar" right shadow>
                <div class="px-3 py-2">
                  <p>
                    Our Checkout Items
                  </p>
                  <div v-for="item in this.$store.state.cartArray" v-bind:key="item.favoritesId">
                    {{item}}
                </div>
                </div>
              </b-sidebar>
  </div>
  
</template>

<script>
export default {
    // data(){
    //     return {
    //         item: []
    //     }
    // },
    methods:{
        routeToCheckoutPage(){
            this.$router.push({ name: 'checkout'})
        }
    }
}
</script>

<style>

</style>