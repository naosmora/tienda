package com.tienda.servicio;

import com.tienda.modelo.Empleado;
import com.tienda.repositorio.EmpleadoRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoServicio {

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    // READ - Listar todos
    public List<Empleado> listarEmpleados() {
        return empleadoRepositorio.findAll();
    }

    // READ - Obtener por ID
    public Empleado obtenerEmpleadoPorId(Long id) {
        return empleadoRepositorio.findById(id).orElse(null);
    }

    // CREATE - Guardar nuevo
    public Empleado guardarEmpleado(Empleado empleado) {
        return empleadoRepositorio.save(empleado);
    }

    // UPDATE - Actualizar existente
    public Empleado actualizarEmpleado(Long id, Empleado empleadoActualizado) {
        Empleado empleado = empleadoRepositorio.findById(id).orElse(null);
        if (empleado != null) {
            empleado.setNombre(empleadoActualizado.getNombre());
            return empleadoRepositorio.save(empleado);
        }
        return null;
    }

    // DELETE - Eliminar por ID
    public void eliminarEmpleado(Long id) {
        empleadoRepositorio.deleteById(id);
    }
}