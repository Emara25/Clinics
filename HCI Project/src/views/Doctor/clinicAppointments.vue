<!-- Appointments.vue -->

<template>
  <div class="main">
    <div class="title">
      {{ clinicName }}'s Appointements
      <button @click="this.$router.push('/')" id="signout">Sign Out</button>
      <button  @click="this.$router.push('/doctor/'+this.$route.params.id+'/edit')" id="profile">Edit profile</button>
    </div>
    <div v-for="(day, index) in Days" :key="index" class="box">

      <div v-if="day.length > 0" class="container" :class="{ 'current-day': currDay === weekdays[index] }">
        <h1>{{ weekdays[index] }}</h1>
        <div class="appointements" v-for="appointment in day" :key="appointment">

          <div class="subTitle">TIME -{{ appointment.startTime }}</div>

          <div class="appointementDetails" v-for="reservation in filteredAppointmentsReservations(appointment)"
            :key="reservation"
            :class="{ 'current-num': appointment.currActiveNumber === reservation.patientNum && currDay === weekdays[index] }">
            <div><strong>Patient:</strong> {{ reservation.patient.firstName }}{{ reservation.patient.lastName }}</div>
            <div><strong>Number:</strong> {{ reservation.patientNum }}</div>

            <!-- <desceptionModal :id="reservation.patient.patientId" ref="'modal' + reservation.patient.patientId"/> -->
          </div>
        </div>
      </div>
    </div>
  </div>
  <div>

  </div>
</template>

<script>
import axios from 'axios';

// import desceptionModal from './descriptionModal.vue'
export default {
  components: {
    // desceptionModal
  },
  data() {
    return {
      // selectedDate: new Date().toISOString().substr(0, 10), // Initial date
      writingStates: {},
      clinicName: null,
      Days: [[], [], [], [], [], [], []],
      appointMap: [],
      currDay: null,
      currActiveNumber: 3,
      weekdays: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
      appointments: null,
      reservations: null,
    }
  },
  created() {
    // Call your method immediately when the component is created
    axios.get(`http://localhost:8080/clinics/${this.$route.params.id}`)
      .then(res => this.clinicName = res.data.clinicName)
      .then(() => {
        axios.get(`http://localhost:8080/appointments/clinicAppointments/${this.$route.params.id}`)
          .then((res => this.appointments = res.data))
          .then(() => {
            axios.get(`http://localhost:8080/clinics/getReservations/${this.$route.params.id}`)
              .then(res => this.reservations = res.data)
              .then(() => {
                this.filterAppointemnts();
                this.getAppointments();
                this.getCurrentDayOfWeek();
                this.getReservation();
              })
          })
      })
  },
  methods: {
    openModal() {
      this.writing = true;
    },
    closeModal() {
      this.writing = false;
    },
    getAppointments() {
      // getappoitements from backend
    },
    getReservation() {
      // get resrvations from backend
    },
    // filter appointemnts to group them by day 
    //and sort them for in each day ascendingly 
    filterAppointemnts() {
      this.appointments.forEach(
        (appointment) => {
          this.Days[appointment.day - 1].push(appointment);
          this.Days[appointment.day - 1].sort((a, b) => {
            const minutesA = this.timeToMinutes(a.startTime);
            const minutesB = this.timeToMinutes(b.startTime);
            return minutesA - minutesB;
          });
        }
      );
      this.reservations.sort(
        (a, b) => {
          const minutesA = this.timeToMinutes(a.appointment.startTime);
          const minutesB = this.timeToMinutes(b.appointment.startTime);
          return minutesA - minutesB;
        }
      )
      this.reservations.sort(
        (a, b) => {
          if (a.day !== b.day) {
            return a.day - b.day;
          }
          // If days are equal, sort by startTime
          const timeA = this.timeToMinutes(a.appointment.startTime);
          const timeB = this.timeToMinutes(b.appointment.startTime);

          return timeA - timeB;
        }
      )
      return true;
    },
    filteredAppointmentsReservations(appointement) {
      // Use Array.filter to filter appointments based on the specified attribute and value
      return this.reservations.filter(reservation => JSON.stringify(reservation.appointment) === JSON.stringify(appointement));
    },
    timeToMinutes(time) {
      const [hours, minutes] = time.split(':').map(Number);
      return hours * 60 + minutes;
    },
    getCurrentDayOfWeek() {
      const currentDate = new Date();
      // Get the day of the week (0 = Sunday, 1 = Monday, ..., 6 = Saturday)
      const dayIndex = currentDate.getDay();
      // Return the corresponding day name
      this.currDay = this.weekdays[dayIndex];
      console.log(this.currDay);
    },
    signOut() {
      // router thing goes here
    },
    toProfile() {
      // router thing goes here
    }
  },
}

</script>
<style scoped>
.title {
  margin-bottom: 40px;
  padding: 20px;
  text-align: center;
  font-size: 45px;
  background-color: #53beec;
  color: black;
  height: 20%;
  position: relative;
}

button {
  position: absolute;
  top: 60%;
  padding: 5px 16px;
  border-radius: 10px;
}

#signout {
  right: 5%;
}

#profile {
  left: 5%;
}

.subTitle {
  margin-top: 10px;
  margin-left: 10px;
  font-size: x-large;
}

.main {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  width: 70%;

  justify-content: center;
  align-items: center;
  height: 100vh;
  overflow-y: auto;

  scrollbar-width: thin;
  /* Firefox */
  scrollbar-color: #888 #f0f0f0;
  /* Firefox */
}

/* WebKit (Chrome, Safari) scrollbar styling */
.main::-webkit-scrollbar {
  width: 5px;
}

.main::-webkit-scrollbar-thumb {
  background-color: #888;
  border-radius: 5px;
}

.main::-webkit-scrollbar-track {
  background-color: #f0f0f0;
}

.box {
  border: 1px solid #000000;
  box-shadow: 5px 5px 5px black;
  border-radius: 10px;
  margin-bottom: 20px;
  width: 80%;
  background-color: #53beec;
  margin-left: 10%;
  color: #000000;
}

.appointements {
  max-height: 150px;
  overflow-y: auto;
  margin-left: 5%;

  border-radius: 10px;
  margin-bottom: 10px;
  scrollbar-width: thin;
  /* Firefox */
  scrollbar-color: #888 #f0f0f0;
  /* Firefox */
}

/* WebKit (Chrome, Safari) scrollbar styling */
.appointements::-webkit-scrollbar {
  width: 10px;
}

.appointements::-webkit-scrollbar-thumb {
  background-color: #888;
  border-radius: 5px;
}

.appointements::-webkit-scrollbar-track {
  background-color: #f0f0f0;
}

.appointementDetails {
  margin-bottom: 20px;
  margin-left: 2%;
  margin-top: 2%;
  max-width: 50%;
  padding: 15px;
  border: 1px solid #000000;
  border-radius: 10px;
  background-color: #ccc;
}

.appointementDetails div {
  margin-bottom: 10px;
}

.appointementDetails strong {
  color: #333;
}</style>
