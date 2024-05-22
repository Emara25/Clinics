<template>
    <div class="modal-container">
        <div class="modal-content">
            <h1>Are you sure you want to make a reservation at <strong style="color: #53beec;">{{ clinicName }}</strong>
                 at <strong style="color: #53beec;">{{ hour.startTime }}</strong>?</h1>
            <button @click="save">Yes</button>
            <button @click="cancle">Cancle</button>
        </div>
    </div>
</template>

<script>
export default {
    props: {
        clinicName: null,
        hour: null
    },
    methods: {
        cancle() {
            this.$emit('close')
        },
        save() {
            this.$emit('save', this.hour)
        }
    }
}
</script>

<style>
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
</style>