package cl.usach.tingeso.ev1.controllers;

import cl.usach.tingeso.ev1.services.ResultadosService;
import cl.usach.tingeso.ev1.entities.ResultadosEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/resultados")
public class ResultadosController {

        @Autowired
        ResultadosService resultadosService;

        @GetMapping("/fileUpload")
        public String main() {
            return "resultadosFileUpload";
        }

        @PostMapping("/fileUpload")
        public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
            resultadosService.guardarArchivo(file);
            redirectAttributes.addFlashAttribute("mensaje", "¡Archivo de Resultados cargado correctamente!");
            resultadosService.leerCsv("Resultados.csv");
            return "redirect:/resultados/fileUpload";
        }

        @GetMapping("/fileInformation")
        public String listar(Model model) {
            List<ResultadosEntity> datas = resultadosService.obtenerResultados();
            model.addAttribute("datas", datas);
            return "resultadosFileInformation";
        }

        @GetMapping("/dsctoPuntaje")
        public String dsctoPuntaje() {
            resultadosService.seleccionaRut();
            return "redirect:/arancel/listar";
        }



}
