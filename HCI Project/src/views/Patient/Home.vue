<template>
    <div class="upperbar">
        <h1 class="header">Clinic Reservations</h1>
        <div class="buttoncontainer">
            <button @click="this.$router.push('/')">Signout</button>
            <button @click="this.$router.push('/patient/'+this.$route.params.id+'/edit')">Edit profile</button>
        </div>
    </div>
    <div class="searchcontainer" v-if="showSearchBar">
        <input type="text" :placeholder="'Enter a '+searchBy" v-model="searchKey" @keypress="k=>{if (k.key=='Enter') search()}">
        <button @click="search()">Search</button>
    </div>
    <RouterView/>
</template>

<script>
import { RouterLink } from 'vue-router';

export default {
    data() {
        return {
            searchBy: 'name',
            searchKey: '',
            showSearchBar: true,
        };
    },
    methods: {
        search() {
            this.$router.push('/patient/' + this.$route.params.id + '/search/infix/' + this.searchKey);
        }
    }
    ,
    components: { RouterLink }
}
</script>

<style scoped>
    .upperbar{
        font-size: x-large;
        background-color: #53beec;
        margin-top: 0;
        margin-left: 0;
        margin-right: 0;
        position: relative;
        top: 0;
        right: 0;
        left: 0;
        display: flex;
        justify-content: space-between;
        align-self: center;
    }

    .upperbar .header {
        color: white; 
        margin-left: 10px;
    }

    .upperbar .buttoncontainer {
        display: flex;
        align-self: center;
        justify-content: space-between;
    }

    .upperbar .buttoncontainer button {
        background-color: white;
        border: 0;
        height: 30px;
        margin: 5px;
        border-radius: 4px;
    }

    .upperbar .buttoncontainer button:hover {
        background-color: wheat;
    }    

    .searchcontainer {
        display: flex;
        justify-content: center;
        align-self: center;
        margin: 50px;
    }

    .searchcontainer input {
        width: 800px;
        padding: 10px;
        font-size: larger;
        border-radius: 4px;
        border-style: groove;
    }

    .searchcontainer button {
        background-color: #53beec;
        border-radius: 4px;
        border-style: groove;
        margin-left: 10px;
        width: 100px;
    }

    .searchcontainer button:hover {
        background-color: rgb(18, 176, 176);
    }
</style>

