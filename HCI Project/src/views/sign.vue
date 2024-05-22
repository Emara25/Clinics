<template>
    <div class="container">
        <div :class="{ 'show-message': showMessage }" class="message">
            {{ messageText }}
        </div> 
        <div class ="sign">
            <button id="doctor" :class="{ active: activeButton === 'doctor' }"
            @click="setActiveButton('doctor')">Doctor</button>
            <button id="patient" :class="{ active: activeButton === 'patient' }"
            @click="setActiveButton('patient')">Patient</button>
            <form @submit.prevent="submitForm">
              <div id="email" :class="{ 'error-field': showError && !isValid}">
                    <input type="email" v-model="userData.email" placeholder="Enter your email" />
                </div>
                <div id="password" :class="{ 'error-field': showError && !isValid}">
                    <input type="password" v-model="userData.password" placeholder="Enter your password" />
                    </div>
                <button type="submit" id="submit">Sign in</button>
                <!-- add router link for signup page-->
                <div id="signup">Don't have an account?<RouterLink :to="activeButton+'/signup'">Sign up</RouterLink></div>
            </form>
        </div>
    </div>
</template>
<script>

import axios from 'axios';

export default {
    
    name: "signPage",
    data(){
        return{
            userData:{
                email: "",
                password: ""
                },
            activeButton: 'doctor',
            isValid: true,
            showError: false,
            id:-1,
            }
    },

    methods: {
        setActiveButton(buttonId) {
            this.activeButton = buttonId;
        },

        async submitForm() {
    await this.getId(this.activeButton);
    console.log(this.id);

    if (this.id === -1) {
        this.showError = true;
        this.isValid = false;
        this.showErrorMessage('Wrong email or password!');
    } else {
        this.goHome(this.activeButton, this.id);
    }
},

async getId(activeButton) {
    try {
        if(this.userData.email == "" || this.userData.password == ""){
            return; 
        }
        var v = this.cORp(activeButton);
        const response = await axios.get(`http://localhost:8080/${v}/logIn`, {
            params: {
                email: this.userData.email,
                password: this.userData.password,
            }
        });

        console.log('id loaded successfully:', response.data);
        this.id = response.data;
    } catch (error) {
        console.error('Error loading id:', error);
        this.showErrorMessage('Wrong email or password!');
    }
},

         cORp(activeButton){
             if(activeButton === 'doctor'){
                 return 'clinics';
             }else{
                 return 'patients';
             }
         },
         showErrorMessage(message) {
                this.messageText = message;
                this.showMessage = true;

            setTimeout(() => {
                this.showMessage = false;
                this.messageText = '';
            }, 5000); 
            },

        goHome(activeButton, id){
            console.log(`go to ${activeButton} homw page`+ id);
            this.$router.push('/'+activeButton +'/' + id + '/home/');
        }

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
    background-image: url('../assets/doctorImage.jpeg');
    background-repeat: no-repeat;
    background-size: 50% 100%;
    background-position: right top;
}
.sign{
    position: relative;
    max-width: 50%;
    height: 100%;
    box-sizing: border-box;
 
}

button{
    position: absolute;
    background-color: #7f56da;
    border-radius: 8px; 
    padding: 10px 15px; 
    color: #ffffff; 
    border: none; 
    cursor: pointer;
}
button:hover {
    background-color:hsla(259, 64%, 60%, 0.699); 
    }
#doctor{
    top: 30%; 
    left: 38%;
    transform: translate(-38%, -30%);
}
#patient{
    top: 30%; 
    left: 62%;
    transform: translate(-62%,-30%);
}
#signup{
    position: absolute;
    top: 80%; 
    left: 50%;
    transform: translate(-50%, -75%);
    font-size: 12px;
    color: gray;
}
a{
    color: #7f56da;
}
#email{

    border: 1px solid #dddfe2;
    color: #1d2129;
    font-size: 12px;
    height: 22px;
    line-height: 16px;
    
    vertical-align: middle;

    position: absolute;
    font-size: 14px;
    text-align: left;
    top: 42%; 
    left: 50%;
    transform: translate(-50%, -42%);
}
#password{

    border: 1px solid rgb(226, 221, 221);
    color: #1d2129;
    font-size: 12px;
    height: 22px;
    line-height: 16px;
    
    vertical-align: middle;

    font-size: 14px;
    text-align: left;
    position: absolute;
    top: 55%; 
    left: 50%;
    transform: translate(-50%,-55%);
}
#submit{
    font-size: 16px;
    text-align: center;
    top: 70%; 
    left: 52%;
    transform: translate(-52%,-70%);
    width: 42%;
}

input{     
    width: 100%; /* Adjust the width as needed */
    padding: 8px;
    border: 1px solid #dddfe2; 
    border-radius: 4px;
}
input[type="password"]:focus {
    border: 2px solid #7f56da; /* Change the border color and style as needed */
    outline: none; /* Remove the default focus outline */
    box-shadow: 0 0 5px rgba(127, 86, 218, 0.5); /* Optional: Add a box shadow for better visibility */
}
input[type="email"]:focus {
    border: 2px solid #7f56da; /* Change the border color and style as needed */
    outline: none; /* Remove the default focus outline */
    box-shadow: 0 0 5px rgba(127, 86, 218, 0.5); /* Optional: Add a box shadow for better visibility */
}
button.active {
    background-color: gray;
    /* Add any other styles you want for the active state */
}
.error-field {
  border: 5px solid red; /* Change the border color and style as needed */
    outline: none; /* Remove the default focus outline */
    box-shadow: 10px 10px 26px red ;
}
.show-message {
    position: fixed;
    bottom: 80%;
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