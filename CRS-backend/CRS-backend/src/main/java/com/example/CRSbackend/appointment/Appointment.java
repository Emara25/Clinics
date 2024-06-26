package com.example.CRSbackend.appointment;

import com.example.CRSbackend.reservation.Reservation;
import jakarta.persistence.*;

import java.sql.Time;
import java.util.List;

@Entity
@Table
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appId;

    @Column
    private int day;
    @Column
    private Time startTime;
    @Column
    private Time endTime;
    @Column
    private int maxPatients;
    @Column
    private int numRegPatients;
    @Column
    private int currentActiveNumber;
    @Column
    private Time expTimeCurrentPatient;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private List<Reservation> reservations;


    public Appointment() {
    }

    public Appointment(int appId) {
        this.appId = appId;
    }

    public Appointment(int appId, int day, Time startTime, Time endTime, int maxPatients, int numRegPatients, int currentActiveNumber, Time expTimeCurrentPatient) {
        this.appId = appId;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxPatients = maxPatients;
        this.numRegPatients = numRegPatients;
        this.currentActiveNumber = currentActiveNumber;
        this.expTimeCurrentPatient = expTimeCurrentPatient;
    }
    public Appointment(int day, Time startTime, Time endTime, int maxPatients) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxPatients = maxPatients;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public int getMaxPatients() {
        return maxPatients;
    }

    public void setMaxPatients(int maxPatients) {
        this.maxPatients = maxPatients;
    }

    public int getNumRegPatients() {
        return numRegPatients;
    }

    public void setNumRegPatients(int numRegPatients) {
        this.numRegPatients = numRegPatients;
    }

    public int getCurrentActiveNumber() {
        return currentActiveNumber;
    }

    public void setCurrentActiveNumber(int currentActiveNumber) {
        this.currentActiveNumber = currentActiveNumber;
    }

    public Time getExpTimeCurrentPatient() {
        return expTimeCurrentPatient;
    }

    public void setExpTimeCurrentPatient(Time expTimeCurrentPatient) {
        this.expTimeCurrentPatient = expTimeCurrentPatient;
    }
}
