package com.example.CRSbackend.clinicadministration;

import com.example.CRSbackend.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClinicAdministrationRepository extends JpaRepository<ClinicAdministration, Integer> {
    Optional<ClinicAdministration> findByEmail(String email);

    List<ClinicAdministration> findALlByLocation(String location);

    List<ClinicAdministration> findAllBySpeciality(String speciality);
    Boolean existsByEmail(String email);

    List<ClinicAdministration> findAllByDrNameContaining(String infix);



}
