<template>
  <div class="home">
    <h1>Home</h1>
    <p>You must be authenticated to see this</p>
    <TestComponent></TestComponent>
  </div>
</template>

<script>
import TestComponent from '../components/TestComponent.vue';
export default {
  name: "home",
components:{
  TestComponent
}
};
</script>

<style scoped>

</style>