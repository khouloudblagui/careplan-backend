package com.example.careplane.services;

import com.example.careplane.dto.AppointmentDto;
import com.example.careplane.dto.PatientDto;
import com.example.careplane.dto.Response;
import com.example.careplane.entity.Appointment;
import com.example.careplane.entity.Patient;
import com.example.careplane.repository.AppointmentRepository;
import com.example.careplane.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    @Transactional
    public Response addPatient(PatientDto patientDto) {
        Patient patient = PatientDto.toEntity(patientDto);
        patient.setRole("ROLE_PATIENT");
        patientRepository.save(patient);
        return new Response("Patient added successfully", true);
    }

    @Transactional(value = Transactional.TxType.SUPPORTS) // Correct readOnly usage for JTA
    public List<Appointment> getPatientAppointments(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    @Transactional
    public Response updateAppointment(AppointmentDto appointmentDto) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointmentDto.getId());
        if (appointmentOptional.isPresent()) {
            Appointment appointment = appointmentOptional.get();
            appointment.setAppointmentDate(LocalDate.parse(appointmentDto.getAppointmentDate()));
            appointment.setStatus(appointmentDto.getStatus());
            appointmentRepository.save(appointment);
            return new Response("Appointment updated successfully", true);
        } else {
            return new Response("Appointment not found", false);
        }
    }

    @Transactional
    public Response deleteAppointment(Long appointmentId) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointmentId);
        if (appointmentOptional.isPresent()) {
            appointmentRepository.deleteById(appointmentId);
            return new Response("Appointment deleted successfully", true);
        } else {
            return new Response("Appointment not found", false);
        }
    }
}
