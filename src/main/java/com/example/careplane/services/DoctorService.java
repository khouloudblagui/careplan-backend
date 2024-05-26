package com.example.careplane.services;


import com.example.careplane.dto.PatientDto;
import com.example.careplane.entity.Appointment;
import com.example.careplane.entity.Patient;
import com.example.careplane.repository.AppointmentRepository;
import com.example.careplane.repository.DoctorRepository;
import com.example.careplane.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository; // Supposons que vous avez un repository pour gérer les rendez-vous des patients

    @Autowired
    private PatientRepository patientRepository; // Supposons que vous avez un repository pour gérer les patients

    public List<Appointment> getAllAppointments(Long doctorId) {
        // Récupère tous les rendez-vous des patients gérés par le médecin

        return appointmentRepository.findByDoctorId(doctorId); // Ajoutez la logique pour récupérer les rendez-vous en fonction de l'ID du médecin
    }

    public void createPatientAccount(Long doctorId, PatientDto patientDTO) {
        // Crée un compte pour un patient
        Patient patient = new Patient();
        patient.setFirstname(patientDTO.getFirstname());
        patient.setLastname(patientDTO.getLastname());
        // Définissez d'autres propriétés

        patientRepository.save(patient);
    }

    // Méthodes pour gérer d'autres fonctionnalités spécifiques au médecin
}

