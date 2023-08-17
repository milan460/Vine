<template>
  <div>
    <input type="file" ref="fileInput" @change="handleFileChange" multiple />
  </div>
</template>

<script>
export default {
  methods: {
    async handleFileChange(event) {
      const files = event.target.files;
      const images = Array.from(files);

      if (images.length > 0) {
        const convertedImages = await Promise.all(images.map(this.convertImageToBase64));
        this.$emit("images-updated", convertedImages);
      }
    },
    
    convertImageToBase64(image) {
      return new Promise((resolve, reject) => {
        const reader = new FileReader();
        
        reader.onload = (event) => {
          resolve({
            base64: event.target.result,
          });
        };

        reader.onerror = (error) => {
          reject(error);
        };

        reader.readAsDataURL(image);
      });
    },
  },
};
</script>