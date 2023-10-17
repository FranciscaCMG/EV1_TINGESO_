package cl.usach.tingeso.ev1.controllers;

import cl.usach.tingeso.ev1.entities.EstudianteEntity;
import cl.usach.tingeso.ev1.services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/estudiante")
public class EstudianteController {
    @Autowired
    EstudianteService estudianteService;

    @GetMapping("/listar")
    public String listar(Model model) {
        List<EstudianteEntity> estudiantes= estudianteService.obtenerEstudiantes();
        model.addAttribute("estudiantes", estudiantes);
        return "index";
    }

    @GetMapping("/agregar-estudiante")
    public String estudiante(){
        return "ingresar-estudiante"; }

    @PostMapping("/agregar-estudiante")
    public String nuevoEstudiante(@RequestParam("rut") String rut,
                                 @RequestParam("apellidos") String apellidos,
                                 @RequestParam("nombres") String nombres,
                                 @RequestParam("fechanacimiento") String fechanacimiento,
                                  @RequestParam("tipoColegioP") String tipoColegioP,
                                  @RequestParam("nombreColegio") String nombreColegio,
                                  @RequestParam("anioEgreso") Integer anioEgreso){
        estudianteService.guardarEstudiante(rut, apellidos, nombres, fechanacimiento, tipoColegioP, nombreColegio, anioEgreso);
        return "redirect:/estudiante/listar";
    }
}
