package cl.usach.tingeso.ev1;

import cl.usach.tingeso.ev1.services.EstudianteService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
public class EstudianteServiceTest {

    @Autowired
    EstudianteService estudianteService;

    @Test
    void obtenerEstudiantesTest() {
        assertNotNull(estudianteService.obtenerEstudiantes());
    }

    @Test
    void obtenerEstudiantePorRutTest() {
        assertNotNull(estudianteService.obtenerEstudiantePorRut("104679781"));
    }

    @Test
    void guardarEstudianteTest() {

        estudianteService.guardarEstudiante("10",
                "apellido", "nombre", "fecha",
                "Municipal", "Liceo", 2020);

        assertNotNull(estudianteService.obtenerEstudiantePorRut("10"));

        estudianteService.eliminarEstudiante("10");
    }

    @Test
    void promedioEstudianteTest() {

        estudianteService.promedioEstudiante("104679781");

        int promedio = estudianteService.obtenerEstudiantePorRut("104679781").getPromedio();
        int examenesRendidos = estudianteService.obtenerEstudiantePorRut("104679781").getExamenesRendidos();

        assertEquals(promedio, 460);
        assertEquals(examenesRendidos, 2);

    }

    // Aca se puede crear los estudiantes
    // en la misma funcion para no depender de la base de datos
    // o cargar script de prueba en la base de datos con los datos necesarios

    // Falta eliminar estudiante

}
