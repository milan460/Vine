<template>
  <div id="main">
    <h1 id="h1">Vine <img id="pic" src="../assets/logo.gif" /></h1>
    <b-card id="nav" title="Card Title" no-body>
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
              <b-button v-b-toggle.sidebar-right>Toggle Sidebar</b-button>
              <b-sidebar id="sidebar-right" title="Sidebar" right shadow>
                <div class="px-3 py-2">
                  <p>
                    Our Checkout Items
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