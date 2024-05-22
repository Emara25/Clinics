package com.example.CRSbackend.patient;

import com.example.CRSbackend.appointment.AppointmentRepository;
import com.example.CRSbackend.clinicadministration.ClinicAdministration;
import com.example.CRSbackend.clinicadministration.ClinicAdministrationRepository;
import com.example.CRSbackend.reservation.Reservation;
import com.example.CRSbackend.reservation.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final ReservationRepository reservationRepository;
    private final ClinicAdministrationRepository clinicAdministrationRepository;
    private final AppointmentRepository appointmentRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = null;
    @Autowired
    public PatientService(PatientRepository studentRepository,
                          ReservationRepository reservationRepository,
                          ClinicAdministrationRepository clinicAdministrationRepository,
                          AppointmentRepository appointmentRepository) {
        this.patientRepository = studentRepository;
        this.reservationRepository = reservationRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.clinicAdministrationRepository = clinicAdministrationRepository;
        this.appointmentRepository = appointmentRepository;
    }
    public List<Patient> getPatients(){
        return patientRepository.findAll();
    }
    public ResponseEntity<Patient> getPatient(int id) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            return new ResponseEntity<>(patient, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    public Integer signUp(Patient patient) {
        if(this.patientRepository.existsByEmail(patient.getEmail())){
            return -1;
        }
        // encode password
        String encodedPassword = this.bCryptPasswordEncoder.encode(patient.getPassword());
        patient.setPassword(encodedPassword);
        this.patientRepository.save(patient);
        return patient.getPatientId();
    }
    public int logIn(String email , String password){
        /*if(this.patientRepository.existsByEmail(patient.getEmail())){
//            if(this.patientRepository.findPatientByEmail(patient.getEmail())
//                    .get().getPassword().equals(patient.getPassword())){
//                return true;
//            }
            String encodedPassword = this.patientRepository.findPatientByEmail(patient.getEmail())
                    .get().getPassword();
            String rawPassword = patient.getPassword();
            boolean isMatched = this.bCryptPasswordEncoder.matches(rawPassword, encodedPassword);*/

        Optional<Patient> optionalPatient = patientRepository.findPatientByEmail(email);

        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            String patientPassword = patient.getPassword();
            if (bCryptPasswordEncoder.matches(password, patientPassword)) {
                return patient.getPatientId();
            }
        }
        return -1 ;
    }
    public void setEncodedPassword(Patient patient){
         String encodedPassword = this.bCryptPasswordEncoder.encode(patient.getPassword());
        patient.setPassword(encodedPassword);
    }

    public boolean updatePatient(int id , Patient patientNew) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            Patient storedPatient = optionalPatient.get();
            storedPatient.setBirthday(patientNew.getBirthday());
            storedPatient.setDetails(patientNew.getDetails());
            storedPatient.setEmail(patientNew.getEmail());
           setEncodedPassword(patientNew);
            storedPatient.setPassword(patientNew.getPassword());
            storedPatient.setGender(patientNew.getGender());
            storedPatient.setFirstName(patientNew.getFirstName());
            storedPatient.setLastName(patientNew.getLastName());
            storedPatient.setPhone(patientNew.getPhone());
            storedPatient.setLocation(patientNew.getLocation());
            patientRepository.save(storedPatient);
            return true;
        }
        return false ;
    }

    public void deleteAccount(int id){
        patientRepository.deleteById(id);
    }
    public List<Reservation> getReservations(int id) {
        return this.reservationRepository.findAllByPatient(new Patient(id));
    }

    public List<ClinicAdministration> search(String location , String infix , String speciality) {

        if (location != null) {
            return clinicAdministrationRepository.findALlByLocation(location);
        } else if (infix != null) {
            return clinicAdministrationRepository.findAllByDrNameContaining(infix);
        } else if (speciality != null) {
            return clinicAdministrationRepository.findAllBySpeciality(speciality);
        }
        else{
            return clinicAdministrationRepository.findAll();
        }
    }

    public List<ClinicAdministration> filter(List<ClinicAdministration> clinics,String speciality ,  String location) {
        if (speciality != null) {
            clinics =  clinics.stream()
                    .filter(clinic -> Objects.equals(clinic.getSpeciality(), speciality))
                    .collect(Collectors.toList());
        }
        if (location != null) {
            clinics = clinics.stream()
                    .filter(clinic -> Objects.equals(clinic.getLocation(), location))
                    .collect(Collectors.toList());
        }
        return clinics;
    }

    public List<ClinicAdministration> sort(List<ClinicAdministration> clinics, String criteria) throws IntrospectionException {

        PropertyDescriptor pd = new PropertyDescriptor(criteria, ClinicAdministration.class);
        Method getter = pd.getReadMethod();

        clinics.sort((clinic1, clinic2) ->{ try {
            Comparable value1 = (Comparable) getter.invoke(clinic1);
            Comparable value2 = (Comparable) getter.invoke(clinic2);
            return value1.compareTo(value2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
        }) ;

        return clinics;
    }

    @Transactional
    public boolean deleteReservation(int patientId, int reservationId) {
        if(! patientRepository.existsById(patientId)) return false;
        if (! reservationRepository.existsById(reservationId)) return false;
        if(reservationRepository.findById(reservationId).get().getPatient().getPatientId() != patientId)
            return false;
        int appId = reservationRepository.findById(reservationId).get().getAppointmentId();
        int numRegPatients = appointmentRepository.findById(appId).get().getNumRegPatients();
        appointmentRepository.findById(appId).get().setNumRegPatients(numRegPatients-1);
        reservationRepository.deleteById(reservationId);
        return true;
    }
}
