package com.example.CRSbackend.appointment;

import com.example.CRSbackend.clinicadministration.ClinicAdministration;
import com.example.CRSbackend.clinicadministration.ClinicAdministrationRepository;
import com.example.CRSbackend.patient.Patient;
import com.example.CRSbackend.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointRepository;
    private final ClinicAdministrationRepository clinicAdminRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, ClinicAdministrationRepository clinicAdminRepository) {
        this.appointRepository = appointmentRepository;
        this.clinicAdminRepository = clinicAdminRepository;
    }
    public List<Appointment> getAppointments(){
        return appointRepository.findAll();
    }

    @Transactional
    public boolean addAppointment(int id, Appointment appointment){
//        appointRepository.save(appointment);
//        appointRepository.findById(1).get()
        ClinicAdministration clinicAdministration = clinicAdminRepository.findById(id).orElse(null);
        List<Appointment> appointments = clinicAdministration.getAppointments();
        appointments.add(appointment);
        clinicAdministration.setAppointments(appointments);
        return true;
    }

    @Transactional
    public boolean addAppointments(int id, List<Appointment> appointments) {
        ClinicAdministration clinicAdministration = clinicAdminRepository.findById(id).orElse(null);
        clinicAdministration.setAppointments(appointments);
        return true;
    }

    public List<Appointment> getClinicAppointments(int clinicId) {
        return clinicAdminRepository.findById(clinicId).get().getAppointments();
    }
}
