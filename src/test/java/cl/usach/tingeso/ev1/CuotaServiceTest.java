package cl.usach.tingeso.ev1;

import cl.usach.tingeso.ev1.entities.CuotaEntity;
import cl.usach.tingeso.ev1.services.CuotaService;
import cl.usach.tingeso.ev1.repositories.CuotaRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
public class CuotaServiceTest {

    @Autowired
    CuotaService cuotaService;

    @Autowired
    CuotaRepository cuotaRepository;

    @Test
    void obtenerCuotaPorRutTest() {
        assertNotNull(cuotaService.obtenerCuotaPorRut("104679781"));
    }

    @Test
    void cuotasAtrasadaTest() {

        Integer cuotasAtrasadas = 0;
        List<CuotaEntity> cuotas = cuotaService.obtenerCuotaPorRut("104679781");

        for (CuotaEntity cuota : cuotas) {
            if (cuota.getEstado().equals("Atrasada")) {
                cuotasAtrasadas++;
            }
        }

        Integer cuotaFuncion = cuotaService.cuotasAtrasadas("104679781");

        assertEquals(cuotasAtrasadas, cuotaFuncion);

    }

    @Test
    void calculoInteresTest() {

        int interes = cuotaService.calculoInteres(1);

        assertEquals(interes, 3);

    }

    @Test
    void modificoMontoTest() {

        List<CuotaEntity> cuotas = cuotaService.obtenerCuotaPorRut("104679781");

        cuotaService.modificoMonto(cuotas, 3);

        for (CuotaEntity cuota : cuotas) {
            if (cuota.getEstado().equals("Atrasada")) {
                assertEquals(cuota.getValorCuota(), 227950);
            }
        }

    }

    @Test
    void revisarFechaTest() {

        List<CuotaEntity> cuotas = cuotaService.obtenerCuotaPorRut("104679781");

        String estado = cuotaService.revisarFecha("2020-06-05", "2020-05-05", cuotas, 3);
        String estado2 = cuotaService.revisarFecha("2020-04-05", "2020-05-05", cuotas, 6);

        assertEquals(estado, "Pagada");
        assertEquals(estado2, "Atrasada");

    }

    @Test
    void pagarCuotaTest() {

        cuotaService.pagarCuota(1);

        CuotaEntity cuota = cuotaRepository.findById(1).get();

        assertEquals(cuota.getEstado(), "Pagada");

    }

    // Falta guardar cuota

}
