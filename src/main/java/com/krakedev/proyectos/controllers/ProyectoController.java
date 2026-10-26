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

import com.krakedev.proyectos.entidades.Proyecto;
import com.krakedev.proyectos.services.ProyectoService;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @GetMapping
    public ResponseEntity<?> listarProyectos() {
        try {
            List<Proyecto> proyectos = proyectoService.listarProyectos();
            return ResponseEntity.ok(proyectos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al listar proyectos: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProyectoPorId(@PathVariable int id) {
        try {
            Optional<Proyecto> proyecto = proyectoService.obtenerPorId(id);
            if (proyecto.isPresent()) {
                return ResponseEntity.ok(proyecto.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener el proyecto: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> guardarProyecto(@RequestBody Proyecto proyecto) {
        try {
            Proyecto nuevoProyecto = proyectoService.guardarProyecto(proyecto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProyecto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el proyecto: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProyecto(@PathVariable int id, @RequestBody Proyecto proyectoDetalles) {
        try {
            Optional<Proyecto> proyectoOpt = proyectoService.obtenerPorId(id);
            if (proyectoOpt.isPresent()) {
                Proyecto proyecto = proyectoOpt.get();
                proyecto.setNombre(proyectoDetalles.getNombre());
                proyecto.setDescripcion(proyectoDetalles.getDescripcion());
                proyecto.setFechaInicio(proyectoDetalles.getFechaInicio());
                Proyecto actualizado = proyectoService.guardarProyecto(proyecto);
                return ResponseEntity.ok(actualizado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado para actualizar con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el proyecto: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProyecto(@PathVariable int id) {
        try {
            Optional<Proyecto> proyecto = proyectoService.obtenerPorId(id);
            if (proyecto.isPresent()) {
                proyectoService.eliminarProyecto(id);
                return ResponseEntity.ok("Proyecto eliminado correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado para eliminar con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el proyecto: " + e.getMessage());
        }
    }
}