package cl.usach.tingeso.ev1.controllers;

import cl.usach.tingeso.ev1.entities.CuotaEntity;
import cl.usach.tingeso.ev1.services.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/cuotas")
public class CuotasController {

    @Autowired
    CuotaService cuotaService;

    @GetMapping("/listar")
    public String listar(Model model,@RequestParam(name = "rutEstudiante") String rut) {
        List<CuotaEntity> cuotas = cuotaService.obtenerCuotaPorRut(rut);
        model.addAttribute("cuotas", cuotas);
        return "mostrarCuotas";
    }

    @PostMapping("/listar")
    public String listarPost(@RequestParam(name = "ID") Integer arancelId, @RequestParam( name = "cantidadCuotas") Integer nroCuota,RedirectAttributes redirectAttributes) {

        for (int i = 1; i <= nroCuota; i++) {
            cuotaService.guardarCuota(arancelId, i);
        }
        redirectAttributes.addFlashAttribute("mensaje", "¡ Cuota Generada Correctamente ! :)");
        return "redirect:/arancel/listar";
    }

    @PostMapping("/pagarCuota")
    public String pagarCuota(@RequestParam(name = "ID") Integer cuotaId, @RequestParam(name = "rutEstudiante") String rut, RedirectAttributes redirectAttributes) {
        System.out.println("ID: " + cuotaId);
        cuotaService.pagarCuota(cuotaId);
        redirectAttributes.addFlashAttribute("mensaje", "¡ Cuota Pagada Correctamente ! :)");
        return "redirect:/cuotas/listar?rutEstudiante="+ rut;
    }




















}
