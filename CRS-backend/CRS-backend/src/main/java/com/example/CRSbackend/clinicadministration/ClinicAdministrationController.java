package com.example.CRSbackend.clinicadministration;

import com.example.CRSbackend.patient.Patient;
import com.example.CRSbackend.patient.PatientService;
import com.example.CRSbackend.reservation.Reservation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(path = "clinics")
public class ClinicAdministrationController {
    private ClinicAdministrationService clinicAdministrationService;

    @Autowired
    public ClinicAdministrationController(ClinicAdministrationService clinicAdministrationService) {
        this.clinicAdministrationService = clinicAdministrationService;
    }

    @GetMapping
    public List<ClinicAdministration> getClinics() {
        return clinicAdministrationService.getClinics();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ClinicAdministration> getClinic(@PathVariable int id) {
        return clinicAdministrationService.getClinic(id);
    }

    @PostMapping("signUp")
    public Integer signUp(@RequestBody ClinicAdministration clinicAdministration) throws IOException {
        return clinicAdministrationService.signUp(clinicAdministration);
    }

    @GetMapping("logIn")
    public int logIn(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password) throws IOException {
        return clinicAdministrationService.logIn(email, password);
    }

    @PutMapping(path = "{id}")
    public boolean updateClinic(@PathVariable("id") int id, @RequestBody ClinicAdministration clinicNew) {
        return clinicAdministrationService.updateClinic(id, clinicNew);
    }


    @GetMapping(path = "/search")
    public List<ClinicAdministration> findAllByLocation(@RequestParam(value = "speciality", required = false) String speciality,
                                                        @RequestParam(value = "location", required = false)String location,
                                                        @RequestParam(value = "infix", required = false) String infix){
        return clinicAdministrationService.search(location, infix, speciality);
    }


    @GetMapping(path = "/filter")
    public List<ClinicAdministration> filterClinics(@RequestParam("clinics") String clinicsParam,
                                                    @RequestParam(value = "location", required = false) String location,
                                                    @RequestParam(value = "speciality", required = false) String speciality) throws JsonProcessingException {
        List<ClinicAdministration> clinics = new ObjectMapper().readValue(URLDecoder.decode(clinicsParam, StandardCharsets.UTF_8), new TypeReference<List<ClinicAdministration>>() {});
        return clinicAdministrationService.filter(clinics, speciality, location);
    }

    @GetMapping(path = "/sort")
    public List<ClinicAdministration> sortClinics(@RequestBody List<ClinicAdministration> clinics,
                                                  @RequestParam(value = "criteria") String criteria) throws IntrospectionException {
        return clinicAdministrationService.sort(clinics, criteria);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteAccount(@PathVariable int id) {
        clinicAdministrationService.deleteAccount(id);
    }

    @GetMapping("getReservations/{id}")
    public List<Reservation> getReservations(@PathVariable Integer id){
        return clinicAdministrationService.getReservations(id);
    }
}

