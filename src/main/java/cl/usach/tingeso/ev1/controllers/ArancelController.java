package cl.usach.tingeso.ev1.controllers;

import cl.usach.tingeso.ev1.entities.ArancelEntity;
import cl.usach.tingeso.ev1.entities.EstudianteEntity;
import cl.usach.tingeso.ev1.services.ArancelService;
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
@RequestMapping("/arancel")
public class ArancelController {

    @Autowired
    ArancelService arancelService;

    @GetMapping("/listar")
    public String listar(Model model) {
        List<ArancelEntity> aranceles= arancelService.obtenerArancel();
        model.addAttribute("aranceles", aranceles);
        return "mostrarArancel";
    }

    @GetMapping("/agregar-arancel")
    public String arancel(){
        return "ingresar-arancel"; }

    @PostMapping("/agregar-arancel")
    public String nuevoArancel(@RequestParam("rutEstudiante") String rutEstudiante,
                               @RequestParam("monto") Integer monto,
                               @RequestParam("tipoPago") String tipoPago,
                               @RequestParam("cantidadCuotas") Integer cantidadCuotas, RedirectAttributes redirectAttributes){

        System.out.println(rutEstudiante);

        if(!arancelService.existeRutEnBaseDeDatos(rutEstudiante)){
            redirectAttributes.addFlashAttribute("mensaje", "El usuario ingresado no existe, por favor ingrese un rut valido");

        }
        else{

            if (arancelService.existeArancel(rutEstudiante)){
                redirectAttributes.addFlashAttribute("mensaje", "El usuario ingresado ya tiene un arancel asociado, por favor ingrese un rut valido");
            }
            else{
                arancelService.guardarArancel(rutEstudiante, monto, tipoPago, cantidadCuotas);
                redirectAttributes.addFlashAttribute("mensaje", "El Arancel se ha creado correctamente! :)");
            }



        }

        return "redirect:/arancel/listar";

    }

}
