<template>
  <div id="main">
    <h1 id="h1">Vine <img id="pic" src="../assets/logo.gif" /></h1>
    <b-card id="nav" title="Card Title" no-body>
<<<<<<< HEAD
    <b-card-header header-tag="nav">
      
      <b-nav  card-header pills>
        
        <b-nav-item id = "home"><router-link class="link" v-bind:to="{ name: 'home' }">Home</router-link></b-nav-item>
        <b-nav-item id = "home"><router-link class="link" v-bind:to=" { name: 'plant-search' } ">Search</router-link></b-nav-item>
        <b-nav-item id = "home"><router-link class="link" v-bind:to=" { name: 'marketplace' }" >Plant Shop</router-link></b-nav-item>
        <b-nav-item id = "home"><router-link class="link" v-bind:to=" { name: 'favorites-page' }" v-if="$store.state.token != ''">Personal Garden</router-link></b-nav-item>
        <b-nav-item id = "home"><router-link class="link" v-bind:to="{ name: 'login' }" v-if="$store.state.token == ''">Login</router-link>
        <router-link class="link" v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link></b-nav-item>
        
      </b-nav>
    </b-card-header>
  </b-card>
</div>
=======
      <b-card-header header-tag="nav">
        <b-nav card-header pills>
          <b-nav-item id="home"
            ><router-link class="link" v-bind:to="{ name: 'home' }"
              >Home</router-link
            ></b-nav-item
          >
          <b-nav-item id="home"
            ><router-link class="link" v-bind:to="{ name: 'plant-search' }"
              >Search</router-link
            ></b-nav-item
          >
          <b-nav-item id="home"
            ><router-link
              class="link"
              v-bind:to="{ name: 'marketplace' }"
              >Plant Shop</router-link
            ></b-nav-item
          >
          <b-nav-item id="home"
            ><router-link
              class="link"
              v-bind:to="{ name: 'favorites-page' }"
              v-if="$store.state.token != ''"
              >Personal Garden</router-link
            ></b-nav-item
          >
          <b-nav-item id="home"
            ><router-link
              class="link"
              v-bind:to="{ name: 'login' }"
              v-if="$store.state.token == ''"
              >Login</router-link
            >
            <!-- THIS IS THE PLACEHOLDER/ style this up-->
            <div>
              <b-button v-b-toggle.sidebar-1>Toggle Sidebar</b-button>
              <b-sidebar id="sidebar-1" title="Sidebar" shadow>
                <div class="px-3 py-2">
                  <p>
                    Cras mattis consectetur purus sit amet fermentum. Cras justo
                    odio, dapibus ac facilisis in, egestas eget quam. Morbi leo
                    risus, porta ac consectetur ac, vestibulum at eros.
                  </p>
                  <b-img
                    src="https://picsum.photos/500/500/?image=54"
                    fluid
                    thumbnail
                  ></b-img>
                </div>
              </b-sidebar>
            </div>
            <router-link
              class="link"
              v-bind:to="{ name: 'logout' }"
              v-if="$store.state.token != ''"
              >Logout</router-link
            ></b-nav-item
          >
        </b-nav>
      </b-card-header>
    </b-card>
  </div>
>>>>>>> e6201ded961d2a96b27a3cf9fe2d4375ef91c873
</template>

<script>
export default {
  name: "the-header",
};
</script>

<style>
#main {
  background-color: rgb(206, 245, 206);
}

#pic {
  height: 13vh;
}

.active {
  background-color: rgb(157, 238, 157);
}

.link {
  text-decoration: none;
  color: black;
}
#nav {
  background-color: rgb(206, 245, 206);
  border: none;
}

#h1 {
  text-align: center;
  font-size: 60px;
  height: 10vh;
}
</style>