package com.tienda.controller;

import com.tienda.domain.Constante;
import com.tienda.service.ConstanteService;
import java.util.Locale;
import java.util.Optional;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/constante")
public class ConstanteController {

    private final ConstanteService constanteService;
    private final MessageSource messageSource;

    public ConstanteController(ConstanteService constanteService,
            MessageSource messageSource) {
        this.constanteService = constanteService;
        this.messageSource = messageSource;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var constantes = constanteService.getConstantes();
        model.addAttribute("constantes", constantes);
        model.addAttribute("totalConstantes", constantes.size());
        model.addAttribute("constante", new Constante());
        return "/constante/listado";
    }

    @PostMapping("/guardar")
    public String guardar(Constante constante, RedirectAttributes redirectAttributes) {
        constanteService.save(constante);
        redirectAttributes.addFlashAttribute("todoOk",
                messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/constante/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idConstante,
            RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String detalle = "mensaje.eliminado";
        try {
            constanteService.delete(idConstante);
        } catch (Exception e) {
            titulo = "error";
            detalle = "error";
        }
        redirectAttributes.addFlashAttribute(titulo,
                messageSource.getMessage(detalle, null, Locale.getDefault()));
        return "redirect:/constante/listado";
    }

    @GetMapping("/modificar/{idConstante}")
    public String modificar(@PathVariable Integer idConstante,
            Model model, RedirectAttributes redirectAttributes) {
        Optional<Constante> constanteOpt = constanteService.getConstante(idConstante);
        if (constanteOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Constante no encontrada.");
            return "redirect:/constante/listado";
        }
        model.addAttribute("constante", constanteOpt.get());
        return "/constante/modifica";
    }
}