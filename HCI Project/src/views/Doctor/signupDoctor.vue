<template>
    <div class="container">
        <div :class="{ 'show-message': showMessage }" class="message">
            {{ messageText }}
        </div>
        <div class="sign">
            <div id="title" class="formElement">
                Sign up as a doctor
            </div>
            <form @submit.prevent="submitForm">
                <div class="formElement">
                    <label for="drFirstName">First Name</label><br>
                    <input type="text" v-model="clinicData.drFirstName" id="drFirstName"
                        placeholder="Enter your first name" />
                </div>

                <div class="formElement">
                    <label for="drLastName">Last Name</label><br>
                    <input type="text" v-model="clinicData.drLastName" id="drLastName" placeholder="Enter your last name" />
                </div>

                <div class="formElement">
                    <label for="email">Email</label><br>
                    <input type="email" v-model="clinicData.email" id="email" placeholder="Enter your email" />
                </div>

                <div class="formElement">
                    <label for="password">Password</label><br>
                    <input type="password" v-model="clinicData.password" id="password" placeholder="Enter your password" />
                </div>

                <div class="formElement">
                    <label for="clinicName">Clinic Name</label><br>
                    <input type="text" v-model="clinicData.clinicName" id="clinicName"
                        placeholder="Enter your clinic name" />
                </div>

                <div class="formElement">
                    <label for="speciality">Speciality</label><br>
                    <select v-model="clinicData.speciality" id="speciality">
                        <option value="" disabled selected>Select a Speciality</option>
                        <option v-for="option in specialityOptions" :key="option.value" :value="option.value">
                            {{ option.label }}
                        </option>
                    </select>
                </div>

                <div class="formElement">
                    <label for="location">Location</label><br>
                    <select v-model="clinicData.location" id="location">
                        <option value="" disabled selected>Select a location</option>
                        <option v-for="option in locationOptions" :key="option.value" :value="option.value">
                            {{ option.label }}
                        </option>
                    </select>
                </div>

                <div class="formElement">
                    <label for="workingHours">Working hours</label><br>
                    <button type="button" id="workingHours" style="margin-top: 5px; padding: 7px;" @click="showModal()">Set
                        working hours</button>
                </div>

                <div class="formElement">
                    <label for="details">Details</label><br>
                    <input type="text" v-model="clinicData.details" id="details" placeholder="Enter further details" />
                </div>

                <button type="submit" id="submit" class="formElement">Sign up</button>

            </form>
        </div>
        <WorkhoursModal v-show="viewModal" @close="hideModal" @save="data=>appointments=data"/>
    </div>
</template>
<script>
import axios from 'axios';
import WorkhoursModal from './WorkhoursModal.vue';
export default {
    components: { WorkhoursModal },
    name: "signupDoctor",
    data() {
        return {
            showMessage: false,
            messageText: "",
            viewModal: false,
            appointments: null,

            clinicData: {
                clinicName: '',
                drFirstName: '',
                drLastName: '',
                email: '',
                password: '',
                speciality: '',
                location: '',
                details: '',
            },
            specialityOptions: [
                { value: 'Leather', label: 'Leather' },
                { value: 'Teeth', label: 'Teeth' },
                { value: 'Psychological', label: 'Psychological' },
                { value: 'Children & Newborns', label: 'Children & Newborns' },
                { value: 'Brain and nerves', label: 'Brain and nerves' },
                { value: 'bones', label: 'Bones' },
                { value: 'Obstetrics & Gynec', label: 'Obstetrics & Gynec' },
                { value: 'Ear, nose and throat', label: 'Ear, nose and throat' },
                { value: 'Heart and blood vessels', label: 'Heart and blood vessels' },
                { value: 'surgery', label: 'Surgery' },
                { value: 'Blood diseases', label: 'Blood diseases' },
                { value: 'Tumors', label: 'Tumors' },
                { value: 'Internal Medicine', label: 'Internal Medicine' },
                { value: 'Slimming and nutrition', label: 'Slimming and nutrition' },
                { value: 'Gastroenterology and endoscopy', label: 'Gastroenterology and endoscopy' },
                { value: 'Allergy and immunity', label: 'Allergy and immunity' },
                { value: 'ICSI and IVF', label: 'ICSI and IVF' },
                { value: 'Masculinity and infertility', label: 'Masculinity and infertility' },
                { value: 'Chest and respiratory system', label: 'Chest and respiratory system' },
                { value: 'Eyes', label: 'Eyes' },
                { value: 'liver', label: 'Liver' },
                { value: 'kidneys', label: 'Kidneys' },
                { value: 'Urology', label: 'Urology' },
            ],
            locationOptions: [
                { value: 'Alexandria', label: 'Alexandria' },
                { value: 'Aswan', label: 'Aswan' },
                { value: 'Asyut', label: 'Asyut' },
                { value: 'Beheira', label: 'Beheira' },
                { value: 'Beni Suef', label: 'Beni Suef' },
                { value: 'Cairo', label: 'Cairo' },
                { value: 'Dakahlia', label: 'Dakahlia' },
                { value: 'Damietta', label: 'Damietta' },
                { value: 'Fayoum', label: 'Fayoum' },
                { value: 'Gharbia', label: 'Gharbia' },
                { value: 'Giza', label: 'Giza' },
                { value: 'Helwan', label: 'Helwan' },
                { value: 'Ismailia', label: 'Ismailia' },
                { value: 'Kafr El Sheikh', label: 'Kafr El Sheikh' },
                { value: 'Luxor', label: 'Luxor' },
                { value: 'Matruh', label: 'Matruh' },
                { value: 'Minya', label: 'Minya' },
                { value: 'Monufia', label: 'Monufia' },
                { value: 'New Valley', label: 'New Valley' },
                { value: 'North Sinai', label: 'North Sinai' },
                { value: 'Port Said', label: 'Port Said' },
                { value: 'Qalyubia', label: 'Qalyubia' },
                { value: 'Qena', label: 'Qena' },
                { value: 'Red Sea', label: 'Red Sea' },
                { value: 'Sharqia', label: 'Sharqia' },
                { value: 'Sohag', label: 'Sohag' },
                { value: 'South Sinai', label: 'South Sinai' },
                { value: 'Suez', label: 'Suez' },
                { value: 'The 6th of October', label: 'The 6th of October' },
            ],
        }
    },
    methods: {
        submitForm() {
            if (this.appointments==null || this.appointments.length==0) {
                this.showErrorMessage('You must choose working hours.')
                return
            }
            axios.post('http://localhost:8080/clinics/signUp', this.clinicData)
                .then(
                    response => {
                        console.log(response.data);
                        if (response.data == -1) {
                            console.error('Error signing up:', error);
                            this.showErrorMessage('This Clinic already exists.');
                        } else {
                            this.clinicData.id = response.data;
                            console.log(this.clinicData.id);
                            this.goHome();
                            return response.data;
                        }
                    })
                    .then((id)=>{
                        axios.post('http://localhost:8080/appointments/addAppointments/'+id, this.appointments)
                    })
                .catch(error => {
                    console.error('Error signing up:', error);
                    this.showErrorMessage('This Clinic already exists.');
                });
        },
        /*axiosPost(){
            axios.post('http://localhost:8080/clinics/signUp', this.userData)
            .then(
                  response => {
                      console.log( response.data);
                      return response.data;
                   })
              .catch(error => {
                  console.error('Error signing up:', error);
                  });
        },*/
        goHome() {
            console.log(`go to doctor homw page`);
            this.$router.push('/doctor/' + this.clinicData.id + '/home/');
        },

        showErrorMessage(message) {
            this.messageText = message;
            this.showMessage = true;

            setTimeout(() => {
                this.showMessage = false;
                this.messageText = '';
            }, 5000);
        },
        showModal() {
            this.viewModal = true;
        },
        hideModal() {
            this.viewModal = false;
        }
    }
}
</script>
<style scoped>
body,
html {
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

input {
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

select {
    color: #1d2129;
    width: 207px;
    border: 1px solid #dddfe2;
}

select:focus {
    border: 2px solid #7f56da;
    outline: none;
    box-shadow: 0 0 5px rgba(127, 86, 218, 0.5);
}

#submit {
    font-size: 16px;
    text-align: center;
    margin-top: 15px;
    width: 209px;
    color: #ffffff;
}

button {
    background-color: #7f56da;
    border-radius: 8px;
    padding: auto;

    border: none;
    cursor: pointer;
}

button:hover {
    background-color: hsla(259, 64%, 60%, 0.699);
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
}</style>