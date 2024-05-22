<template>
    <div class="filterbar">
      <h2 class="header">Filter by:</h2>
      <select v-model="filteringCriterion" class="select">
        <option value="location">location</option>
        <option value="speciality">speciality</option>
      </select>
      <select v-if="filteringCriterion === 'location'" v-model="selectedLocation" class="select">
        <option v-for="location in locations" :key="location" :value="location">{{ location }}</option>
      </select>
      <select v-if="filteringCriterion === 'speciality'" v-model="selectedSpeciality" class="select">
        <option v-for="speciality in specialities" :key="speciality" :value="speciality">{{ speciality }}</option>
      </select>
      <button @click="filter">Filter</button>
    </div>
    <div class="doctorcontainer">
        <div v-for="doctor in resultDoctors" :key="doctor.doctorId" class="doctor" @click="this.$router.push('/patient/'+this.$route.params.id+'/'+doctor.clinicId)">
            <h3>Name: {{ doctor.drFirstName+' '+doctor.drLastName }}</h3>
            <h5>Speciality: {{ doctor.speciality }}</h5>
            <h5>Location: {{ doctor.location }}</h5>
        </div>
    </div>
    <div :class="{ 'show-message': showMessage }" class="message">
            {{ messageText }}
    </div>
    <div class="searchcontainer" v-if=false>
        <input type="text" :placeholder="'Enter a '+searchBy" v-model="searchKey" @keypress="k=>{if (k.key=='Enter') search()}">
        <button @click="search()">Search</button>
    </div>
</template>

<script>
    import axios from 'axios'
    export default {
        data() {
            return{
                resultDoctors: null,
                filteringCriterion: null,
                showSearchBar: false,
                selectedLocation: null,
                selectedSpeciality: null,
                locations:[
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
      ],specialities:[
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
      ]
            }
        },
        mounted() {
            let keyType = this.$route.params.keytype
            console.log(keyType+"<--keytype"+ this.$route.params.key)
            const params = new URLSearchParams([[keyType, this.$route.params.key]])
            axios.get('http://localhost:8080/clinics/search', {params})
            .then(res=>{
                console.log(res.data+" "+ res.data.length);
                this.resultDoctors = res.data;
                if(res.data.length == 0)
                    this.showErrorMessage('No result!');
            })
            .catch(e=> {
                console.log(e)
            })
        },
        methods: {
            filter() {
                
                console.log(this.resultDoctors+"            "+this.selectedLocation);
                const clinicsParam = encodeURIComponent(JSON.stringify(this.resultDoctors));
                axios.get('http://localhost:8080/clinics/filter',{
                params: {
                    clinics: clinicsParam,
                    location: this.selectedLocation, 
                    speciality: this.selectedSpeciality,
                },}).then(res=>{
                console.log(res.data+" "+ res.data.length);
                this.resultDoctors = res.data;
                if(res.data.length == 0)
                    this.showErrorMessage('No result!');
            })
            .catch(e=> {
                console.log(e)
            })
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
    .doctorcontainer {
        display: flex;
        flex-direction: column;
    }

    .doctor {
        border: 1px solid #53beec;
        padding: 10px;
        margin: 15px;
    }

    .doctor:hover {
        background-color: wheat;
    }

    .filterbar {
    font-family: 'Roboto', sans-serif;
    display: flex;
    align-items: center;
  }

  .header {
    margin: 15px;
  }

  .select {
    width: 250px;
    height: 30px;
    font-size: x-large;
    border-radius: 4px;
  }

  .filterbar button {
    width: 100px;
    height: 30px;
    margin: 15px;
    border-radius: 4px;
    background-color: #53beec;
  }

  .filterbar button:hover {
    background-color: #3d95bb;
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