<template>
    <div class="container">
        <div :class="{ 'show-message': showMessage }" class="message">
            {{ messageText }}
        </div>
        <div class ="sign">
                <div id="title" class="formElement">
                   Sign up as a Patient
                </div>
            <form @submit.prevent="submitForm">
        <div class="formElement">
          <label for="firstName">First Name</label><br>
          <input type="text" v-model="patientData.firstName" id="firstName" placeholder="Enter your first name" />
        </div>

        <div class="formElement">
          <label for="lastName">Last Name</label><br>
          <input type="text" v-model="patientData.lastName" id="lastName" placeholder="Enter your last name" />
        </div>

        <div class="formElement">
          <label for="email">Email</label><br>
          <input type="email" v-model="patientData.email" id="email" placeholder="Enter your email" />
        </div>

        <div class="formElement">
          <label for="password">Password</label><br>
          <input type="password" v-model="patientData.password" id="password" placeholder="Enter your password" />
        </div>

        <div class="formElement">
          <label for="birthday">Birth Date</label><br>
          <input type="date" v-model="patientData.birthday" id="birthday"/>
        </div>

        <div class="formElement">
          <label for="gender">Gender</label><br>
            <select v-model="patientData.gender" id="gender">
                <option value="" disabled selected>Select a gender</option>
                <option v-for="option in genderOptions" :key="option.value" :value="option.value">
                    {{ option.label }}
                </option>
            </select>
        </div>

        <div class="formElement">
          <label for="details">Details</label><br>
          <input type="text" v-model="patientData.details" id="details" placeholder="Enter further details" />
        </div>

        <button type="submit" id="submit" class="formElement">Sign up</button>
      </form>
        </div>
    </div>
</template>
<script>

import axios from 'axios';
export default {
    
    name: "PatientSignUp",
    data() {
        return{

            showMessage: false,
            messageText: "",

            patientData:{
                firstName:'',
                lastName:'',
                email:'',
                password:'',
                birthday:'',
                gender:'',
                details:'',
            },
            genderOptions: [
                { value: 'male', label: 'male' },
                { value: 'female', label: 'female' },
                ],
            
        }
    },
        methods:{
            submitForm(){
                /*if (this.axiosPost()) {
                    this.goHome();
                }else{
                    this.showErrorMessage('This User already exists.');
                }*/
                axios.post('http://localhost:8080/patients/signUp', this.patientData)
                  .then(
                      response => {
                          
                          if(response.data == -1){
                            console.error('Error signing up:', error);
                            this.showErrorMessage('This User already exists.');
                          }else{
                          this.patientData.id = response.data;
                          console.log( this.patientData.id);
                          this.goHome();
                          return response.data;}
                       })
                  .catch(error => {
                      console.error('Error signing up:', error);
                      this.showErrorMessage('This User already exists.');
                      });
            },
            /*axiosPost(){
                return true;
                axios.post('http://localhost:8080/patients/signUp', this.patientData)
                  .then(
                      response => {
                          console.log( response.data);
                          return response.data;
                       })
                  .catch(error => {
                      console.error('Error signing up:', error);
                      });
            },*/
            goHome(){
                console.log(`go to patient homw page`);
                this.$router.push('/patient/' + this.patientData.id + '/home/');
            },
            showErrorMessage(message) {
                this.messageText = message;
                this.showMessage = true;

            setTimeout(() => {
                this.showMessage = false;
                this.messageText = '';
            }, 5000); 
            },
        }
}
</script>
<style scoped>
body, html {
            margin: 0;
            padding: 0;
            height: 100%;
        }
.container {
    margin: auto;
    width: 88vw;
    height: 88vh;
    background-image: url('D:\CSE level 2\HCI\project\HCI Project\src\assets/doctorPatientImage.jpeg');
    background-repeat: no-repeat;
    background-size: 50% 100%;
    background-position: right top;
}
.sign {
  max-width: 50%;
  height: 100%;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 70px;
}

.formElement {
    margin-bottom: 30px;
    text-align: left;
    color: #1d2129;
    font-size: 12px;
    height: 40px;
    line-height: 16px;
    vertical-align: middle;
    border-radius: 4px;
}
input{
    border-radius: 4px;
    border: 1px solid rgb(226, 221, 221);
    height: 30px;
}
#title {
  font-size: 22px;
  padding-right: 20px;
  margin-bottom: 20px;
}

input[type="password"]:focus {
    border: 2px solid #7f56da; 
    outline: none; 
    box-shadow: 0 0 5px rgba(127, 86, 218, 0.5);
}
input[type="email"]:focus {
    border: 2px solid #7f56da; 
    outline: none; 
    box-shadow: 0 0 5px rgba(127, 86, 218, 0.5);
}
input[type="text"]:focus {
    border: 2px solid #7f56da; 
    outline: none; 
    box-shadow: 0 0 5px rgba(127, 86, 218, 0.5);
}
select{
    color: #1d2129;
    width: 207px;
    border: 1px solid #dddfe2;
}
date{
    color: #1d2129;
    width: 207px;
    border: 1px solid #dddfe2
}

select:focus {
    border: 2px solid #7f56da; 
    outline: none; 
    box-shadow: 0 0 5px rgba(127, 86, 218, 0.5);
}
#submit{
    font-size: 16px;
    text-align: center;
    margin-top: 15px;
    width: 209px;
    color: #ffffff; 
}
button{
    background-color: #7f56da;
    border-radius: 8px; 
   padding: auto;
    
    border: none; 
    cursor: pointer;
}
button:hover {
    background-color:hsla(259, 64%, 60%, 0.699); 
    }

.show-message {
    position: fixed;
    bottom: 250px;
    left: 47%;
    transform: translateX(-50%);
    background-color: lightgray;
    color: rgb(255, 0, 0);
    border: 1px solid black;
    padding: 15px;
    border-radius: 5px;
    display: block;
  }

</style>