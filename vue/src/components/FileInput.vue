<template>
  <div id="app">
  <v-app id="inspire">
    <v-app-bar
      app
      color="white"
      flat>
      <v-toolbar-title>Convert to Base64</v-toolbar-title>
    </v-app-bar>
    <v-main class="grey lighten-3">
      <v-container>
        <v-row>
          <v-col cols="12" sm="12">
            <v-sheet
              class="pt-2"
              min-height="70vh"
              rounded="lg">
              <v-row class="ma-4">
                <v-col cols="12" sm="12">
                  <v-file-input
                    outlined
                    label="File"
                    v-model="image"/>
                </v-col>
                <v-col cols="12" sm="12">
                  <v-textarea
                    outlined
                              auto-grow disabled
                    label="Base64 value"
                    v-model="base64"/>
                </v-col>
              </v-row>
            </v-sheet>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</div>
</template>

<script>
export default {
  data() {
      return{
    image: null,
    base64: null
    }
  },
  watch: {
    image: function (newVal) {
      if(newVal) {
        this.createBase64Image(newVal);
      } else {
        this.base64 = null;
      }
    }
  },
  methods: {
    createBase64Image: function(FileObject) {
      const reader = new FileReader();
      reader.onload = (event) => {
        this.base64 = event.target.result;
      }
      reader.readAsDataURL(FileObject);
    }
  },

}
</script>

<style>

</style>