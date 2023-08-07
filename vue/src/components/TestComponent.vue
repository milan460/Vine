<template>
  <div>
      <plantData></plantData>
  </div>
</template>

<script>
import plantData from '../services/PlantData.js'
export default {
    created(){
            plantData.getPlantDetails(1).then(response => {
                console.log(response.data)
            })
    },
 components:{
     plantData
 },
}
</script>

<style>

</style>