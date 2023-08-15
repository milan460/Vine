<template>
  <div>
    
    <b-sidebar id="sidebar-right" title="Cart" right shadow>
      <div class="cart-content">
        <h3 class="cart-title">Your Cart</h3>
        <b-card v-for="item in this.$store.state.cartArray" :key="item.favoriteId" class="cart-item">
          <b-card-text>
            <p class="item-name">{{ item.plantObj.common_name }}</p>
            <img :src="item.plantObj.default_image.medium_url" alt="Plant image" class="item-image">
            <p class="item-price">${{ item.price }}</p>
            <p class="item-quantity">{{ item.stockQuantity }} in stock</p>
          </b-card-text>
        </b-card>
        <b-button @click="routeToCheckoutPage" variant="primary" class="checkout-button">Checkout</b-button>
      </div>
    </b-sidebar>
  </div>
</template>

<script>
export default {
  methods: {
    routeToCheckoutPage() {
      this.$router.push({ name: 'checkout' });
    },
  },
};
</script>

<style>
.cart-content {
  padding: 20px;
}

.cart-title {
  font-size: 20px;
  margin-bottom: 10px;
}

.cart-item {
  margin-bottom: 15px;
  border: none;
}

.item-name {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 5px;
}

.item-image {
  width: 100%;
  max-height: 150px;
  object-fit: cover;
  margin-bottom: 10px;
}

.item-price {
  font-size: 16px;
  margin-bottom: 5px;
}

.item-quantity {
  font-size: 14px;
  color: #888;
}
.checkout-button{
   margin-top: auto;
  align-self: flex-end;
}
</style>
