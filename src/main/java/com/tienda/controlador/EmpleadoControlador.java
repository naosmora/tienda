package com.tienda.controlador;

import com.tienda.modelo.Empleado;
import com.tienda.servicio.EmpleadoServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/empleados")
public class EmpleadoControlador {

    @Autowired
    private EmpleadoServicio empleadoServicio;

    // READ - Listar todos los empleados
    @GetMapping
    public String listarEmpleados(Model model) {
        List<Empleado> empleados = empleadoServicio.listarEmpleados();
        model.addAttribute("empleados", empleados);
        model.addAttribute("empleado", new Empleado());
        return "empleados";
    }

    // CREATE - Guardar nuevo empleado
    @PostMapping("/guardar")
    public String guardarEmpleado(@ModelAttribute Empleado empleado) {
        empleadoServicio.guardarEmpleado(empleado);
        return "redirect:/empleados";
    }

    // UPDATE - Mostrar formulario de edición
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Empleado empleado = empleadoServicio.obtenerEmpleadoPorId(id);
        model.addAttribute("empleado", empleado);
        return "editarEmpleado";
    }

    // UPDATE - Guardar cambios
    @PostMapping("/actualizar/{id}")
    public String actualizarEmpleado(@PathVariable Long id, 
                                      @ModelAttribute Empleado empleado) {
        empleadoServicio.actualizarEmpleado(id, empleado);
        return "redirect:/empleados";
    }

    // DELETE - Eliminar empleado
    @GetMapping("/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable Long id) {
        empleadoServicio.eliminarEmpleado(id);
        return "redirect:/empleados";
    }
}