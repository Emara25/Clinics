package com.example.CRSbackend.reservation;

import com.example.CRSbackend.appointment.Appointment;
import com.example.CRSbackend.appointment.AppointmentRepository;
import com.example.CRSbackend.clinicadministration.ClinicAdministration;
import com.example.CRSbackend.clinicadministration.ClinicAdministrationRepository;
import com.example.CRSbackend.patient.Patient;
import com.example.CRSbackend.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final AppointmentRepository appointmentRepository;
    private final ClinicAdministrationRepository clinicAdministrationRepository;
    private final PatientRepository patientRepository;
    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              AppointmentRepository appointmentRepository,
                              ClinicAdministrationRepository clinicAdministrationRepository,
                              PatientRepository patientRepository) {
        this.reservationRepository = reservationRepository;
        this.appointmentRepository = appointmentRepository;
        this.clinicAdministrationRepository = clinicAdministrationRepository;
        this.patientRepository = patientRepository;
    }
    public List<Reservation> getReservations(){
        return reservationRepository.findAll();
    }
    @Transactional
    public int addReservation(int clinicId, int patientId, int appId, Reservation reservation) {
        if(! clinicAdministrationRepository.existsById(clinicId)
                || !patientRepository.existsById(patientId)
                || !appointmentRepository.existsById(appId)){
            return -1;
        }
        for(Reservation reservation1: reservationRepository.findAllByAppointment(new Appointment(appId))){
            if(reservation1.getPatient().getPatientId() == patientId) return -1;
        }
        int maxPatients = appointmentRepository.findById(appId).get().getMaxPatients();
        int numRegPatients = appointmentRepository.findById(appId).get().getNumRegPatients();

        if(numRegPatients == maxPatients) return -1;

        int isConsultation;
        List<Reservation> reservationHistory = reservationRepository.findAllByPatientAndClinicAdministration(
                new Patient(patientId),
                new ClinicAdministration(clinicId)
        );
        if(reservationHistory.size() == 0){
            isConsultation = 0;
        }else {
            Reservation lastReservation = new Reservation();
            int lastID = 0;
            for(Reservation reservation2: reservationHistory){
                if(reservation2.getReservationId() > lastID) lastReservation = reservation2;
            }
            if(lastReservation.getIsConsultaion() == 0) isConsultation = 1;
            else isConsultation = 0;
        }

        Appointment app = appointmentRepository.getById(appId);
        app.setNumRegPatients(numRegPatients + 1);

        int patientNumber = numRegPatients + 1;
        ClinicAdministration clinicAdministration = new ClinicAdministration(clinicId);
        Patient patient = new Patient(patientId);
        Appointment appointment = new Appointment(appId);
        Reservation newReservation = new Reservation(
                Date.valueOf(LocalDate.now()),
                reservation.getAppointmentDate(),
                patientNumber,
                isConsultation,
                clinicAdministration,
                patient,
                appointment
        );
        this.reservationRepository.save(newReservation);
        return patientNumber;
    }
}
