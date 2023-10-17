package cl.usach.tingeso.ev1;

import cl.usach.tingeso.ev1.entities.CuotaEntity;
import cl.usach.tingeso.ev1.repositories.CuotaRepository;
import cl.usach.tingeso.ev1.entities.ResultadosEntity;
import cl.usach.tingeso.ev1.services.ResultadosService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
public class ResultadoServiceTest {

    @Autowired
    ResultadosService resultadosService;

    @Autowired
    CuotaRepository cuotaRepository;

    @Test
    void obtenerResultadosTest() {
        assertNotNull(resultadosService.obtenerResultados());
    }

    @Test
    void obtenerResultadosPorRutTest() {
        assertNotNull(resultadosService.obtenerResultadosPorRut("104679781"));
    }
    
    @Test
    void promedioPruebasTest() {

        Integer resultado = resultadosService.promedioPruebas("104679781");

        assertEquals(resultado, 460);
    }

    @Test
    void aplicarDsctoTest() {

        resultadosService.aplicarDscto("104679781");

        List<CuotaEntity> listCuota = cuotaRepository.findByRutEstudiante("104679781");

        for (CuotaEntity cuota : listCuota) {
            Integer resultado = cuota.getValorCuota();
            assertEquals(resultado, 414);
        }

    }

    // Falta guardar Resultados faltan hartas
}
