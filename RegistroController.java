package com.tienda.controller;

import com.tienda.domain.Usuario;
import com.tienda.service.RegistroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    private final RegistroService registroService;

    public RegistroController(RegistroService registroService) {
        this.registroService = registroService;
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "/registro/nuevo";
    }

    @PostMapping("/guardar")
    public String guardar(Usuario usuario, RedirectAttributes redirectAttributes) {
        try {
            registroService.registrarUsuario(usuario);
            redirectAttributes.addFlashAttribute("todoOk",
                    "Usuario registrado. Pendiente de activación.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error al registrar usuario: " + e.getMessage());
        }
        return "redirect:/registro/nuevo";
    }
}