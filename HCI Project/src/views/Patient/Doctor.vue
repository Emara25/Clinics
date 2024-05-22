<template>
    <div class="headercontainer">
        <h1 class="header">{{ doctor.clinicName }}</h1>
    </div>

    <div class="infocontainer">
        <h2>Doctor's informaiton:</h2>
        <div class="info">
            <p>Doctor's name: {{ doctor.drFirstName + ' ' + doctor.drLastName }}<br />
                Doctor's speciality: {{ doctor.speciality }}<br />
                Clinic's address: {{ doctor.location }}</p>
        </div>
        <h2>More details:</h2>
        <div class="info">
            <p>{{ doctor.details }} </p>
        </div>
    </div>

    <div v-if="patientTurn!=null">
        <h1>Your turn: {{ patientTurn }}</h1>
    </div>

    <div class="schedulecontainer">
        <h1 style="">Dr.{{ doctor.drFirstName }}'s schedule</h1>
        <div class="schedule">
            <ul v-for="day in days">
                <li class="day">{{ day.name }}</li>
                <li v-for="hour in day.workingHours" :class="hour.satatus == 'valid' ? 'valid' : hour.satatus == 'not valid'? 'notvalid':'booked'"
                @click="showReservationModal(hour)">
                    From:{{ hour.startTime }}<br>
                    To:{{ hour.endTime }}<br>
                    {{ hour.satatus }}
                </li>
            </ul>
        </div>
    </div>
    <ReservationModal v-if="showModal" :clinicName="this.modalClinicName" :hour="this.modalHour" @close="showModal=false"
    @save="hour=>addReservation(hour)"/>
</template>

<script>
import ReservationModal from './ReservationModal.vue'
import axios from 'axios'
const workingHours = [{ startTime: '00:00', endTime: '02:00', satatus: 'not valid', appId: null, numRegPatients: null},
{ startTime: '02:00', endTime: '04:00', satatus: 'not valid', appId: null, numRegPatients: null },
{ startTime: '04:00', endTime: '06:00', satatus: 'not valid', appId: null, numRegPatients: null },
{ startTime: '06:00', endTime: '08:00', satatus: 'not valid', appId: null, numRegPatients: null },
{ startTime: '08:00', endTime: '10:00', satatus: 'not valid', appId: null, numRegPatients: null },
{ startTime: '10:00', endTime: '12:00', satatus: 'not valid', appId: null, numRegPatients: null },
{ startTime: '12:00', endTime: '14:00', satatus: 'not valid', appId: null, numRegPatients: null },
{ startTime: '14:00', endTime: '16:00', satatus: 'not valid', appId: null, numRegPatients: null },
{ startTime: '16:00', endTime: '18:00', satatus: 'not valid', appId: null, numRegPatients: null },
{ startTime: '18:00', endTime: '20:00', satatus: 'not valid', appId: null, numRegPatients: null },
{ startTime: '20:00', endTime: '22:00', satatus: 'not valid', appId: null, numRegPatients: null },
{ startTime: '22:00', endTime: '00:00', satatus: 'not valid', appId: null, numRegPatients: null }
]
export default {
    components: {ReservationModal},
    data() {
        return {
            doctor: {},
            days: [{ name: 'Sunday', workingHours: JSON.parse(JSON.stringify(workingHours)) },
            { name: 'Monday', workingHours: JSON.parse(JSON.stringify(workingHours)) },
            { name: 'Tuesday', workingHours: JSON.parse(JSON.stringify(workingHours)) },
            { name: 'Wednesday', workingHours: JSON.parse(JSON.stringify(workingHours)) },
            { name: 'Thursday', workingHours: JSON.parse(JSON.stringify(workingHours)) },
            { name: 'Friday', workingHours: JSON.parse(JSON.stringify(workingHours)) },
            { name: 'Saturnday', workingHours: JSON.parse(JSON.stringify(workingHours)) }],

            showModal: false,
            modalClinicName: null,
            modalHour: null,

            patientTurn: null,
            hasBooked: false
        }
    },
    mounted() {
        axios.get('http://localhost:8080/clinics/' + this.$route.params.doctor).then(res => {
            this.doctor = res.data
        })
        .then(()=>{
            axios.get('http://localhost:8080/appointments/clinicAppointments/' + this.$route.params.doctor)
            .then(res=>{
                this.mountAppointments(res.data)
            })
        })
        .then(()=>{
            axios.get(`http://localhost:8080/clinics/getReservations/${this.$route.params.doctor}`)
            .then(res=> {
                for (let reservation of res.data) {
                    if (reservation.patient.patientId==this.$route.params.id) {
                        this.patientTurn = reservation.patientNum
                        for (let day of this.days) {
                            for (let hour of day.workingHours){
                                if (hour.appId==reservation.appointment.appId) {
                                    hour.satatus='booked'
                                    this.hasBooked = true
                                }
                            }
                        }
                    }
                }
            })
        })
    },
    methods: {
        mountAppointments(appointments) {
            for (let appointment of appointments) {
                let index = Number(appointment.startTime.substring(0, 2))/2
                this.days[appointment.day-1].workingHours[index].satatus = appointment.numRegPatients<appointment.maxPatients? 'valid' : 'not valid'
                this.days[appointment.day-1].workingHours[index].appId = appointment.appId
                this.days[appointment.day-1].workingHours[index].numRegPatients = appointment.numRegPatients
            }
        },
        showReservationModal(hour) {
            if (this.hasBooked) return
            this.modalClinicName = this.doctor.clinicName
            this.modalHour = hour
            this.showModal = true
        },
        addReservation(hour) {
            let patientId = this.$route.params.id
            let clinicId = this.$route.params.doctor
            let appId = hour.appId
            axios.post(`http://localhost:8080/reservations/addReservation/${clinicId}/${patientId}/${appId}`,
            {patientNum: hour.numRegPatients})
            .then(res=> this.patientTurn = res.data)
            .then(()=>{
                this.showModal=false
                hour.satatus = 'booked'
            })
        }
    }
}
</script>

<style scoped>
.headercontainer {
    display: flex;
    align-items: center;
    justify-content: center;
}

.header {
    font-size: xx-large;
    color: white;
    border: 0;
    border-radius: 4px;
    background-color: #53beec;
    margin-top: 15px;
    padding: 10px;
}

.infocontainer {
    border-style: groove;
    border-color: #53beec;
    border-width: 2px;
}

.infocontainer h2 {
    padding-left: 10px;
}

.info {
    border: 2px #53beec;
    display: flex;
    justify-content: space-around;
    flex-direction: column;
    border-left-style: groove;
    border-top-style: hidden;
    border-bottom-style: hidden;
    margin: 15px;
}

.info p {
    padding-left: 10px;
    font-weight: bolder;
}

.schedulecontainer {
    margin-top: 20px;

}

.schedule {
    border: 2px groove #53beec;
    display: grid;
    grid-template-columns: repeat(7, 1fr);
    justify-content: center;
    align-items: center;
}

.schedule .day {
    background-color: grey;
    margin-bottom: 5px;
}

.schedule ul {
    list-style-type: none;
    padding: 15px;

}

.schedule li {
    border-radius: 4px;
    margin: 10px;
    text-align: center;
}

.schedule .valid {

    background-color: rgb(37, 245, 37);
}

.schedule .valid:hover {

    background-color: rgb(50, 200, 50);
}
.schedule .booked {
background-color: #53beec;
}

.schedule .booked:hover {
background-color: rgb#3f93b8;
}

.schedule .notvalid {
    background-color: rgb(182, 41, 41);
}

.schedule .notvalid:hover {
    background-color: rgb(142, 41, 41);
}
</style>