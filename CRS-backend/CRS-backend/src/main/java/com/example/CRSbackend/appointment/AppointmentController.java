package com.example.CRSbackend.appointment;

import com.example.CRSbackend.patient.Patient;
import com.example.CRSbackend.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "appointments")
public class AppointmentController {
    private AppointmentService appointmentService;
    @Autowired
    public AppointmentController(AppointmentService appointmentService){
        this.appointmentService = appointmentService;
    }
    @GetMapping
    public List<Appointment> getAppointments(){
        return appointmentService.getAppointments();
    }
    @GetMapping(path = "clinicAppointments/{clinicId}")
    public List<Appointment> getClinicAppointments(@PathVariable int clinicId){
        return appointmentService.getClinicAppointments(clinicId);
    }
    @PostMapping("addAppointment/{id}") // day, startTime, endTime, maxPatients
    public boolean addAppointment(@PathVariable int id, @RequestBody Appointment appointment) throws IOException {
        return this.appointmentService.addAppointment(id, appointment);
    }
    @PostMapping("addAppointments/{id}") // day, startTime, endTime, maxPatients
    public boolean addAppointments(@PathVariable int id, @RequestBody List<Appointment> appointments) throws IOException {
        return this.appointmentService.addAppointments(id, appointments);
    }
}
