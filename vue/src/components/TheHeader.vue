<template>
  <div>
      <h1>Plants R Us</h1>
  <b-card title="Card Title" no-body>
    <b-card-header header-tag="nav">
      <b-nav card-header pills>
        <b-nav-item id = "home" class = "active" active><router-link v-bind:to="{ name: 'home' }">Home</router-link></b-nav-item>
        <b-nav-item id = "home"><router-link v-bind:to=" { name: 'plant-search' } ">Search</router-link></b-nav-item>
        <b-nav-item id = "home"><router-link v-bind:to="{ name: 'login' }" v-if="$store.state.token == ''"
        >Login</router-link
      ><router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link></b-nav-item>
      </b-nav>
    </b-card-header>
  </b-card>
</div>
</template>

<script>
export default {
    name: 'the-header'

}
</script>

<style>
#home > .active {
  background-color: rgb(157, 238, 157);
}
h1{
    background-color: rgb(206, 245, 206);
}
</style>