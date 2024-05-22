package com.example.CRSbackend.patient;

import com.example.CRSbackend.clinicadministration.ClinicAdministration;
import com.example.CRSbackend.reservation.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "patients")
public class PatientController {
    private PatientService patientService;
    @Autowired
    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }
    @PostMapping("signUp")
    public Integer signUp(@RequestBody Patient patient) throws IOException {
        return patientService.signUp(patient);
    }
    @GetMapping
    public List<Patient> getPatients(){
        return patientService.getPatients();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable int id) {
        return patientService.getPatient(id);
    }
    @GetMapping("logIn")
    public int logIn(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password) throws IOException {
        return patientService.logIn(email, password);
    }
    @PutMapping("{id}")
    public boolean updatePatient(@PathVariable int id
            , @RequestBody Patient newPatient){
        return patientService.updatePatient(id, newPatient);
    }
    @GetMapping("getReservations/{id}")
    public List<Reservation> getReservations(@PathVariable int id){
        return patientService.getReservations(id);
    }
    @DeleteMapping(path = "/{id}")
    public void deleteAccount(@PathVariable int id) {
        patientService.deleteAccount(id);
    }
    @DeleteMapping(path = "deleteReservation/{patientId}/{reservationId}")
    public boolean deleteReservation(@PathVariable int patientId, @PathVariable int reservationId){
        return patientService.deleteReservation(patientId, reservationId);
    }
}
