<template>
  <form ref="form" @submit.prevent="sendEmail">
    <label>Address</label>
    <input type="text" name="user_name">
    <label>Email</label>
    <input type="email" name="user_email">
    <label>Message</label>
    <textarea name="message"></textarea>
    
    <input type="submit" value="Send">
  </form>
</template>

<script>
// import emailjs from '@emailjs/browser';
// export default {
//      sendEmail() {
//       emailjs.sendForm('service_nwv2xuv', 'YOUR_TEMPLATE_ID', this.$refs.form, 'U3sYtL-JdrVSbr_CX')
//         .then((result) => {
//             console.log('SUCCESS!', result.text);
//         }, (error) => {
//             console.log('FAILED...', error.text);
//         });
//     }
//   }
</script>

<style>

</style>