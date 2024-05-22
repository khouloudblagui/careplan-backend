package com.example.careplane.services;

import com.example.careplane.dto.AppointmentDto;
import com.example.careplane.dto.Response;
import com.example.careplane.entity.Appointment;
import com.example.careplane.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    @Transactional
    public Response addAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = Appointment.builder()
                .doctorId(appointmentDto.getDoctorId())
                .patientId(appointmentDto.getPatientId())
                .appointmentDate(LocalDate.parse(appointmentDto.getAppointmentDate()))
                .hour(LocalTime.parse(appointmentDto.getHour()))
                .status(appointmentDto.getStatus())
                .build();
        appointmentRepository.save(appointment);
        return new Response("Appointment added successfully", true);
    }

    @Transactional(readOnly = true)
    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    @Transactional(readOnly = true)
    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    @Transactional
    public Response updateAppointment(AppointmentDto appointmentDto) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointmentDto.getId());
        if (appointmentOptional.isPresent()) {
            Appointment appointment = appointmentOptional.get();
            appointment.setAppointmentDate(LocalDate.parse(appointmentDto.getAppointmentDate()));
            appointment.setHour(LocalTime.parse(appointmentDto.getHour()));
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
