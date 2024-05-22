package com.example.CRSbackend.clinicadministration;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;


import com.example.CRSbackend.patient.Patient;
import com.example.CRSbackend.patient.PatientRepository;
import com.example.CRSbackend.reservation.Reservation;
import com.example.CRSbackend.reservation.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClinicAdministrationService {
    private final ClinicAdministrationRepository clinicAdministrationRepository;
    private final ReservationRepository reservationRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = null;

    @Autowired
    public ClinicAdministrationService(ClinicAdministrationRepository clinicAdministrationRepository, ReservationRepository reservationRepository) {
        this.clinicAdministrationRepository = clinicAdministrationRepository;
        this.reservationRepository = reservationRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public List<ClinicAdministration> getClinics() {
        return clinicAdministrationRepository.findAll();
    }

    public ResponseEntity<ClinicAdministration> getClinic(int id) {
        Optional<ClinicAdministration> optionalClinicAdministration = clinicAdministrationRepository.findById(id);
        if (optionalClinicAdministration.isPresent()) {
            ClinicAdministration clinic = optionalClinicAdministration.get();
            return new ResponseEntity<>(clinic, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public Integer signUp(ClinicAdministration clinicAdministration) {
        if (this.clinicAdministrationRepository.existsByEmail(clinicAdministration.getEmail())) {
            return -1;
        }
        // encode password
        String encodedPassword = this.bCryptPasswordEncoder.encode(clinicAdministration.getPassword());
        clinicAdministration.setPassword(encodedPassword);
        clinicAdministration.setDrName(clinicAdministration.getDrFirstName() + " " + clinicAdministration.getDrLastName());
        this.clinicAdministrationRepository.save(clinicAdministration);
        return clinicAdministration.getClinicId();
    }
    public void setEncodedPassword(ClinicAdministration clinic){
        String encodedPassword = this.bCryptPasswordEncoder.encode(clinic.getPassword());
        clinic.setPassword(encodedPassword);
    }

    public int logIn(String email, String password) {
     /*   if(this.clinicAdministrationRepository.existsByEmail(clinicAdministration.getEmail())){
            String encodedPassword = this.clinicAdministrationRepository.findPatientByEmail(clinicAdministration.getEmail())
                                .get().getPassword();
            String rawPassword = clinicAdministration.getPassword();
            boolean isMatched = this.bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
            return isMatched;
        }
        return false;*/

        Optional<ClinicAdministration> optionalClinicAdministration = clinicAdministrationRepository.findByEmail(email);
        if (optionalClinicAdministration.isPresent()) {
            ClinicAdministration clinic = optionalClinicAdministration.get();
            if (bCryptPasswordEncoder.matches(password, clinic.getPassword())) {
                return clinic.getClinicId();
            }
        }
        return -1;
    }


    public boolean updateClinic(int id, ClinicAdministration clinicNew) {
        Optional<ClinicAdministration> optionalClinic = clinicAdministrationRepository.findById(id);
        if (optionalClinic.isPresent()) {
            ClinicAdministration clinicOld = optionalClinic.get();
            clinicOld.setClinicName(clinicNew.getClinicName());
            clinicOld.setDetails(clinicNew.getDetails());
            clinicOld.setDrFirstName(clinicNew.getDrFirstName());
            clinicOld.setDrLastName(clinicNew.getDrLastName());
            clinicOld.setLocation(clinicNew.getLocation());
            clinicOld.setEmail(clinicNew.getEmail());
            setEncodedPassword(clinicNew);
            clinicOld.setPassword(clinicNew.getPassword());
            clinicOld.setSpeciality(clinicNew.getSpeciality());
            clinicAdministrationRepository.save(clinicOld);
            return true ;
        }
        return false ;
    }

        public List<ClinicAdministration> ascSorting(String criteria) {
            return clinicAdministrationRepository.findAll(Sort.by(Sort.Direction.ASC, criteria));
        }

        public List<ClinicAdministration> descSorting(String criteria) {
            return clinicAdministrationRepository.findAll(Sort.by(Sort.Direction.DESC, criteria));
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

    public void deleteAccount(int id) {
        clinicAdministrationRepository.deleteById(id);
    }
    public List<Reservation> getReservations(Integer id) {
        return this.reservationRepository.findAllByClinicAdministration(new ClinicAdministration(id));
    }
}
