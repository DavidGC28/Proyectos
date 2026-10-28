package com.krakedev.proyectos.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krakedev.proyectos.entidades.Empleado;
import com.krakedev.proyectos.services.EmpleadoService;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<?> listarEmpleados() {
        try {
            List<Empleado> empleados = empleadoService.listarEmpleados();
            return ResponseEntity.ok(empleados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al listar empleados: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerEmpleadoPorId(@PathVariable int id) {
        try {
            Optional<Empleado> empleado = empleadoService.obtenerPorId(id);
            if (empleado.isPresent()) {
                return ResponseEntity.ok(empleado.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener el empleado: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> guardarEmpleado(@RequestBody Empleado empleado) {
        try {
            Empleado nuevoEmpleado = empleadoService.guardarEmpleado(empleado);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEmpleado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el empleado: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEmpleado(@PathVariable int id, @RequestBody Empleado empleadoDetalles) {
        try {
            Optional<Empleado> empleadoOpt = empleadoService.obtenerPorId(id);
            if (empleadoOpt.isPresent()) {
                Empleado empleado = empleadoOpt.get();
                empleado.setNombre(empleadoDetalles.getNombre());
                empleado.setCargo(empleadoDetalles.getCargo());
                Empleado actualizado = empleadoService.guardarEmpleado(empleado);
                return ResponseEntity.ok(actualizado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no encontrado para actualizar con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el empleado: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEmpleado(@PathVariable int id) {
        try {
            Optional<Empleado> empleado = empleadoService.obtenerPorId(id);
            if (empleado.isPresent()) {
                empleadoService.eliminarEmpleado(id);
                return ResponseEntity.ok("Empleado eliminado correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no encontrado para eliminar con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el empleado: " + e.getMessage());
        }
    }
}