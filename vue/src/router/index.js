import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import Register from '../views/Register.vue'
import store from '../store/index'
import DetailPage from '../views/DetailPageView.vue'
import SearchPage from '../views/SearchPage.vue'
import FavoritesPage from '../views/FavoritesPageView.vue'
import Test from '../views/TestCSS.vue'
import MarketPlaceView from '../views/MarketPlaceView.vue'
Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/plant/:id",
      name: "plant-detail",
      component: DetailPage,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/search",
      name: "plant-search",
      component: SearchPage,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/favorites-page",
      name: "favorites-page",
      component: FavoritesPage,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/test",
      name: "test",
      component: Test,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/marketplace",
      name: "marketplace",
      component: MarketPlaceView,
      meta: {
        requiresAuth: true
      }
    }
    
    
  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
