<template>
  <div id="app">
    <the-header />
    

    <router-view />
  </div>
</template>


<script>
import TheHeader from "../src/components/TheHeader.vue";

export default {
  components: {
    TheHeader,

  },
  method: {
    pillButton() {
      document.getElementsByClassName("active").onclick = function jsFunc() {
        document.getElementsByClassName("active").style.backgroundColor =
          "Purple";
      };
    },
  },
};
</script>

<style >

a:link {
  color: black;
  text-decoration: none;
  
}
.loading {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
