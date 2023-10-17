package cl.usach.tingeso.ev1;

import cl.usach.tingeso.ev1.entities.EstudianteEntity;
import cl.usach.tingeso.ev1.services.ArancelService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
public class ArancelServiceTest {

    @Autowired
    ArancelService arancelService;

    @Test
    void obtenerArancelTest() {
        assertNotNull(arancelService.obtenerArancel());
    }

    @Test
    void obtenerArancelPorIdTest() {
        assertNotNull(arancelService.obtenerArancelPorId(1));
    }

    @Test
    void descuentoTipoPagoTest() {

        EstudianteEntity estudiante = new EstudianteEntity();
        estudiante.setRut("104679781");
        estudiante.setApellidos("Rodriguez Cordero");
        estudiante.setNombres("Viviana Loreto");
        estudiante.setFechanacimiento("1998-09-01");
        estudiante.setTipoColegioP("Municipal");
        estudiante.setNombreColegio("Liceo A-131");
        estudiante.setAnioEgreso(2019);
        estudiante.setExamenesRendidos(4);
        estudiante.setPromedio(6);

        assertEquals((arancelService.descuentoTipoPago("Contado", estudiante)), 50); // REVISAR SALIDA
    }

    @Test
    void modificarMontoTotalTest() {
        assertEquals((arancelService.modificarMontoTotal(30, 250000)), 175000); // REVISAR SALIDA
    }

    @Test
    void existeRutEnBaseDeDatosTest() {

        boolean resultado = arancelService.existeRutEnBaseDeDatos("104679781");
        boolean resultadoF = arancelService.existeRutEnBaseDeDatos("203294934"); // RUT QUE NO EXISTE EN LA BASE DE
                                                                                 // DATOS
        assertTrue(resultado);
        assertFalse(resultadoF);
    }

    @Test
    void existeArancelTest() {

        boolean resultado = arancelService.existeArancel("104679781");
        boolean resultadoF = arancelService.existeArancel("203294934"); // RUT QUE NO EXISTE EN LA BASE DE DATOS

        assertTrue(resultado);
        assertFalse(resultadoF);
    }

    /*
     * FALTA
     * - Metodo guardar arancer
     */

}
