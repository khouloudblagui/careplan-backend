package com.example.careplane.services;

import com.example.careplane.dto.DoctorDto;
import com.example.careplane.dto.Response;
import com.example.careplane.entity.Appointment;
import com.example.careplane.entity.Doctor;
import com.example.careplane.entity.Role;
import com.example.careplane.repository.AppointmentRepository;
import com.example.careplane.repository.DoctorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    @Transactional
    public Response addDoctor(DoctorDto doctorDto) {
        Doctor doctor = DoctorDto.toEntity(doctorDto);
        doctor.setRole(Role.valueOf("ROLE_DOCTOR"));
        doctorRepository.save(doctor);
        return new Response("Doctor added successfully", true);
    }

    @Transactional(readOnly = true)
    public List<Appointment> getDoctorAppointments(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    @Transactional
    public Response deleteDoctor(Long doctorId) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        if (doctorOptional.isPresent()) {
            doctorRepository.deleteById(doctorId);
            return new Response("Doctor deleted successfully", true);
        } else {
            return new Response("Doctor not found", false);
        }
    }
}
