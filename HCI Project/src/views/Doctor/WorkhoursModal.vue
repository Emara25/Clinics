<template>
    <div class="modal-container">
        <div class="modal-content">
            <h2>Choose the clinic's availabe working hours</h2>
            <div class="schedulecontainer">
                <div class="schedule">
                    <ul v-for="day in days">
                        <li class="day">{{ day.name }}</li>
                        <li v-for="hour in day.workingHours" :class="hour.satatus == 'valid' ? 'valid' : 'notvalid'" @click="toggleStatus(hour)">
                            From:{{ hour.startTime }}<br>
                            To:{{ hour.endTime }}<br>
                            {{ hour.satatus }}
                        </li>
                    </ul>
                </div>
                <label for="max" style="padding: 5px; font-weight: bold;">Enter the maximum number of patients per time slot range:</label>
                <input type="number" id="max" v-model="maxPatients">
                <p style="color: red;" v-if="notAllowedMax">Max number has to be greater than 1</p>
            </div>
            <button @click="closeModal">cancle</button>
            <button @click="save">Save</button>
        </div>
    </div>
</template>

<script>
const workingHours = [{ startTime: '00:00', endTime: '02:00', satatus: 'not valid' },
            { startTime: '02:00', endTime: '04:00', satatus: 'not valid' },
            { startTime: '04:00', endTime: '06:00', satatus: 'not valid' },
            { startTime: '06:00', endTime: '08:00', satatus: 'not valid' },
            { startTime: '08:00', endTime: '10:00', satatus: 'not valid' },
            { startTime: '10:00', endTime: '12:00', satatus: 'not valid' },
            { startTime: '12:00', endTime: '14:00', satatus: 'not valid' },
            { startTime: '14:00', endTime: '16:00', satatus: 'not valid' },
            { startTime: '16:00', endTime: '18:00', satatus: 'not valid' },
            { startTime: '18:00', endTime: '20:00', satatus: 'not valid' },
            { startTime: '20:00', endTime: '22:00', satatus: 'not valid' },
            { startTime: '22:00', endTime: '00:00', satatus: 'not valid' }
            ]
export default {
    data() {
        return {
            days: [{name: 'Sunday', workingHours: JSON.parse(JSON.stringify(workingHours))}, 
            {name: 'Monday', workingHours: JSON.parse(JSON.stringify(workingHours))}, 
            {name: 'Tuesday', workingHours: JSON.parse(JSON.stringify(workingHours))},
            {name: 'Wednesday', workingHours: JSON.parse(JSON.stringify(workingHours))},
            {name: 'Thursday', workingHours: JSON.parse(JSON.stringify(workingHours))}, 
            {name: 'Friday', workingHours: JSON.parse(JSON.stringify(workingHours))}, 
            {name: 'Saturnday', workingHours: JSON.parse(JSON.stringify(workingHours))}],

            maxPatients: 5,
            notAllowedMax: false
        }
    },
    methods: {
        closeModal() {
            this.$emit('close')
        },
        toggleStatus(hour) {
            hour.satatus = hour.satatus=='valid'? 'not valid' : 'valid'
        },
        save() {
            if (this.maxPatients<1) {
                this.notAllowedMax = true;
                return
            }
            this.notAllowedMax = false;
            let hours = []
            for (let i=0; i<7;i++) {
                let day = this.days[i]
                for (let hour of day.workingHours) {
                    if (hour.satatus=='valid') {
                        let appointmentsHour = {day: i+1, startTime: hour.startTime+":00", endTime: hour.endTime+":00", 
                            maxPatients: this.maxPatients, numRegPatients: 0, currentActiveNumber:0}
                        hours.push(appointmentsHour)
                    }
                }
            }
            this.$emit('save', hours)
            this.closeModal()
        }
    }
}
</script>

<style scoped>
.modal-container {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
}

.modal-content {
    background: white;
    padding: 20px;
    border-radius: 8px;
    text-align: center;
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
    max-height: 70vh;
    overflow: scroll;
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
    width: 100px;
}

.schedule li:hover {
    cursor: pointer;
}

.schedule .valid {

    background-color: rgb(37, 245, 37);
}

.schedule .valid:hover {
    background-color: rgb(50, 200, 50);
}

.schedule .notvalid {
    background-color: white;
}

.schedule .notvalid:hover {
    background-color: rgb(50, 200, 50);
}
.modal-content button {
    float: right;
    margin: 5px;
    padding: 7px;
    background-color: #7f56da;
    border-radius: 8px;
    border: none; 
    cursor: pointer;
}

.modal-container button:hover {
    background-color:hsla(259, 64%, 60%, 0.699); 
}
</style>