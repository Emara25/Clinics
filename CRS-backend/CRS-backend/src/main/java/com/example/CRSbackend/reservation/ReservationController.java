package com.example.CRSbackend.reservation;

import com.example.CRSbackend.patient.Patient;
import com.example.CRSbackend.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "reservations")
public class ReservationController {
    private ReservationService reservationService;
    @Autowired
    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }
    @GetMapping
    public List<Reservation> getReservations(){
        return reservationService.getReservations();
    }
    @PostMapping("addReservation/{clinicId}/{patientId}/{appId}") // appointmentDate
    public int addReservation(
            @PathVariable int clinicId,
            @PathVariable int patientId,
            @PathVariable int appId,
            @RequestBody Reservation reservation
    ){
        return this.reservationService.addReservation(clinicId, patientId, appId, reservation);
    }
}
