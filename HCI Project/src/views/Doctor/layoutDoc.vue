<template>
    <div>      
      <div class="content-container">
      
      <form @submit.prevent="confirmEdit" class="edit-form">

        <label for="clinicName" class="label">Clinic Name</label>
        <input v-model="accInfo.clinicName" type="text" id="clinicName" >

        <label for="firstName" class="label">First Name</label>
        <input v-model="accInfo.drFirstName" type="text" id="firstName" >
  
        <label for="lastName" class="label">Last Name</label>
        <input v-model="accInfo.drLastName" type="text" id="lastName">
  
        <label for="email" class="label">E-mail</label>
        <input v-model="accInfo.email" type="email" id="email">
  
        <label for="password" class="label">Password</label>
        <input v-model="accInfo.password" type="password" id="password">
  
        <label for="speciality" class="label">Speciality</label>
        <select v-model="accInfo.speciality" id="speciality">
          <option v-for="speciality in specialities" :key="speciality" :value="speciality">{{ speciality }}</option>
        </select>
  
        <label for="location" class="label">Location</label>
        <select v-model="accInfo.location" id="location">
          <option v-for="location in locations" :key="location" :value="location">{{ location }}</option>
        </select>
  
        <label for="details" class="label">Details</label>
        <input v-model="accInfo.details" type="text" id="details"><br>

        <button type="submit" class="confirm-button">Confirm</button><br>
        <button type="submit" class="confirm-button" @click="deleteAcc">Delete Account</button>

      </form>
      <img src="D:\CSE level 2\HCI\project\HCI Project\src\assets/editProfile.png" alt="Website Logo" class="logo">
      <router-link :to="'/doctor/'+this.$route.params.id+'/home'">
        <img src="D:\CSE level 2\HCI\project\HCI Project\src\assets/home-button.png" alt="Home" class="home-button">
      </router-link>
      
      <div v-if="successMessage" class="success-message">{{ successMessage }}</div>
      </div>

    
    
    </div>
  </template>
  
  <script>
  import axios from 'axios';


  export default {

    
    data() {
      return {

        accInfo: {
          clinicName:'',
          drFirstName: '',
          drLastName: '',
          email: '',
          password: '',
          speciality: '',
          location: '',
          details: ''
        },
        successMessage: '',
        id:'',
        specialities: [
        'Leather',
        'Teeth',
        'Psychological',
        'Children & Newborns',
        'Brain and nerves',
        'bones',
        'Obstetrics & Gynec',
        'Ear, nose and throat',
        'Heart and blood vessels',
        'surgery',
        'Blood diseases',
        'Tumors',
        'Internal Medicine',
        'Slimming and nutrition',
        'Gastroenterology and endoscopy',
        'Allergy and immunity',
        'ICSI and IVF',
        'Masculinity and infertility',
        'Chest and respiratory system',
        'Eyes',
        'liver',
        'kidneys',
        'Urology',
      ],
      locations: [
        'Alexandria',
        'Aswan',
        'Asyut',
        'Beheira',
        'Beni Suef',
        'Cairo',
        'Dakahlia',
        'Damietta',
        'Fayoum',
        'Gharbia',
        'Giza',
        'Helwan',
        'Ismailia',
        'Kafr El Sheikh',
        'Luxor',
        'Matruh',
        'Minya',
        'Monufia',
        'New Valley',
        'North Sinai',
        'Port Said',
        'Qalyubia',
        'Qena',
        'Red Sea',
        'Sharqia',
        'Sohag',
        'South Sinai',
        'Suez',
        'The 6th of October',
      ],
      };

      
    },
    
    mounted() {
      this.id = this.$route.params.id;
        axios.get(`http://localhost:8080/clinics/${this.id}`)
        .then(respnse => {this.accInfo = respnse.data})
        .catch(error => {
              console.error('Error loading account information:', error);
            });
    },

    methods: {
      confirmEdit() {

        console.log('Account information edited:', this.accInfo);
        axios.put(`http://localhost:8080/clinics/${this.id}`, this.accInfo)
         .then(response => {
           console.log('Account information updateded successfully:', response.data);
         })
         .catch(error => {
           console.error('Error updating account information:', error);
         });
         this.successMessage = 'Information saved successfully!';
         this.messageTimer();
      },
      messageTimer() {

      setTimeout(() => {
        this.successMessage = '';
      }, 3000);
    },
    deleteAcc() {
      axios.delete(`http://localhost:8080/clinics/${this.id}`)
         .then(response => {
        console.log('Account deleted successfully:', response.data);
        this.$router.push('/login');
      })
      .catch(error => {
        console.error('Error deleting account:', error);
      });
    },


       
    }
  };
  </script>
  
  <style>
  body{
    margin: 0;
    padding: 0;
  }
  
  .content-container {
  margin-top: 150px;
  display: flex;
  align-items:center;
  justify-content:space-around;
  flex-wrap: wrap;
}

.logo {
  max-width: 25%;
  margin: 10px; 
}
  
  
  .edit-form {
    padding: 10px;
    margin-bottom: 20px;
    width: 50%;
  }
  .edit-form input {
  width: 50%;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-sizing: border-box;
}
.edit-form select {
  width: 50%;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-sizing: border-box;
}
  
  .label {
    font-family: 'Roboto', sans-serif;
    display: flex;
    align-items: center;
  }
  .home-button {
  width: 50px;
  height: auto;
  cursor: pointer;
}

.confirm-button{
  width: 50%;
  padding: 10px;
  margin-bottom: 10px;
  border-radius: 5px;
  box-sizing: border-box;
  background-color: blueviolet;
  color:#fff;
  font-size: 16px;
  transition: background-color 0.3s;
}
.confirm-button:active {
  background-color: rgb(104, 0, 149); 
}

  .success-message {
    position: fixed;
    bottom: 20px;
    left: 50%;
    transform: translateX(-50%);
    background-color: #4CAF50;
    color: white;
    padding: 15px;
    border-radius: 5px;
    display: block;
}

</style>
