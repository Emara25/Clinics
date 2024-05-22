<template>
    <h1 class="header">Specialities</h1>
    <div class="speccontainer">
        <div v-for="speciality in specialities" :key="speciality" class="spec" @click="searchby(speciality)">
            <h3>{{ speciality }}</h3>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        data() {
            return {
                specialities: ['Leather',
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
                                'Urology',]
            }
        },

        methods: {
            searchby(speciality) {
                this.$router.push('/patient/'+this.$route.params.id+'/search/speciality/'+speciality)
            }
        }
    }
</script>

<style scoped>
    .speccontainer {
        display: grid;
        grid-template-columns: repeat(6,1fr);
    }

    .spec {
        border: 1px solid #53beec;
        padding: 10px;
        margin: 15px;
        text-align: center;
        border-radius: 5px;
    }

    .spec:hover {
        background-color: #53beec;
    }

    .header{
        margin-left: 15px;
    }
</style>