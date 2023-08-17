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
          <b-nav-item id="image"
            ><router-link class="link" v-bind:to="{ name: 'plantImage' }"
              >Plant Identifier</router-link
            ></b-nav-item
          >
          <b-nav-item id="home"
            ><router-link class="link" v-bind:to="{ name: 'plant-search' }"
              >Search</router-link
            ></b-nav-item
          >
          <b-nav-item id="home"
            ><router-link class="link" v-bind:to="{ name: 'marketplace' }"
             v-if="$store.state.token != ''"
              >Plant Shop</router-link
            ></b-nav-item
          >
          <b-nav-item id="home"
            ><router-link
              class="link"
              v-bind:to="{ name: 'favorites-page' }"
              v-if="$store.state.token != ''"
              >Favorites</router-link
            ></b-nav-item
          >
          <b-nav-item id="home"
            ><router-link
              class="link"
              v-bind:to="{ name: 'login' }"
              v-if="$store.state.token == ''"
              >Login</router-link
            >
            <router-link
              class="link"
              v-bind:to="{ name: 'logout' }"
              v-if="$store.state.token != ''"
              >Logout</router-link
            >
          </b-nav-item>
          <b-nav-item id="cart">
            <img
              v-if="$store.state.token != ''"
              id="basket"
              src="../assets/flower-basket.png"
              v-b-toggle.sidebar-right
            />
            <cart></cart>
            
          </b-nav-item>
        </b-nav>
      </b-card-header>
    </b-card>
  </div>
</template>

<script>
import Cart from "../components/Cart.vue";
export default {
  name: "the-header",
  components: {
    Cart,
  },
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
.link:hover {
  background-color: rgb(152, 226, 152);
  border: rgb(152, 226, 152) 10px solid;
  border-radius: 4px;
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

#basket {
  height: 7vh;
}
#cart {
  margin-left: 50%;
}
</style>