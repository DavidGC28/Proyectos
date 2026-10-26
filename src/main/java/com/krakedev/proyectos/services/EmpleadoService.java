package com.krakedev.proyectos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Empleado;
import com.krakedev.proyectos.repositories.EmpleadoRepository;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> listarEmpleados() {
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> obtenerPorId(int id) {
        return empleadoRepository.findById(id);
    }

    public Empleado guardarEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public void eliminarEmpleado(int id) {
        empleadoRepository.deleteById(id);
    }
}