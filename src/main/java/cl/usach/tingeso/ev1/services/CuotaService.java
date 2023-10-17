package cl.usach.tingeso.ev1.services;

import cl.usach.tingeso.ev1.entities.ArancelEntity;
import cl.usach.tingeso.ev1.repositories.CuotaRepository;
import cl.usach.tingeso.ev1.entities.CuotaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

@Service
public class CuotaService {

    @Autowired
    CuotaRepository cuotaRepository;

    @Autowired
    EstudianteService estudianteService;

    @Autowired
    ArancelService arancelService;

    public List<CuotaEntity> obtenerCuotaPorRut(String rutEstudiante) {
        return cuotaRepository.findByRutEstudiante(rutEstudiante);
    }

    public void guardarCuota(Integer idArancel, Integer nroCuota) {

        System.out.println("entre ");
        Integer valor;
        String rut = "";
        String fechaP = "";
        String estado = "No pagada";

        ArancelEntity arancel = arancelService.obtenerArancelPorId(idArancel);
        rut = arancel.getRutEstudiante();
        valor = arancel.getMonto() / arancel.getCantidadCuotas();
        List<CuotaEntity> cuotasExistentes = cuotaRepository.findByRutEstudianteAndNroCuota(rut, nroCuota);

        System.out.println(cuotasExistentes);
        if (cuotasExistentes.isEmpty()) {
            // Si no existen cuotas para el mismo rut y número de cuota, crear una nueva
            // cuota
            CuotaEntity cuotaEntity = new CuotaEntity();
            cuotaEntity.setRutEstudiante(rut);

            cuotaEntity.setNroCuota(nroCuota);
            cuotaEntity.setValorCuota(valor);
            cuotaEntity.setFechaVencimiento("2023-" + String.valueOf(nroCuota + 2) + "-05"); // Fecha inicio clases
            cuotaEntity.setFechaPago(fechaP);
            cuotaEntity.setEstado(estado);
            cuotaRepository.save(cuotaEntity);

        } else {
            System.out.println("Ya existe una cuota para el mismo rut y número de cuota.");
        }

    }

    public Integer cuotasAtrasadas(String rutEstudiante) {
        List<CuotaEntity> cuotas = cuotaRepository.findByRutEstudiante(rutEstudiante);
        Integer cuotasAtrasadas = 0;
        for (CuotaEntity cuota : cuotas) {
            if (cuota.getEstado().equals("Atrasada")) {
                cuotasAtrasadas++;
            }
        }
        return cuotasAtrasadas;
    }

    public Integer calculoInteres(Integer nroAtrasos) {
        Integer interes = 0;
        if (nroAtrasos == 1) {
            interes = 3;
        } else if (nroAtrasos == 2) {
            interes = 3;
        } else if (nroAtrasos == 3) {
            interes = 3;
        } else if (nroAtrasos > 3) {
            interes = 6;
        }
        return interes;
    }

    public void modificoMonto(List<CuotaEntity> listaCuotas, Integer interes) {
        for (CuotaEntity cuotaEntity : listaCuotas) {
            if (cuotaEntity.getEstado().equals("Atrasada")) {
                cuotaEntity.setValorCuota(cuotaEntity.getValorCuota() + (cuotaEntity.getValorCuota() * interes / 100));
            }
        }
    }

    public String revisarFecha(String fechaVencimiento, String fechaPago, List<CuotaEntity> listaCuotas,
            Integer interes) {

        String estado = "Pagada";

        String[] listVencimiento = fechaVencimiento.split("-");
        String[] listpago = fechaPago.split("-");

        if (Integer.parseInt(listVencimiento[0]) > Integer.parseInt(listpago[0])) {
            modificoMonto(listaCuotas, interes);
            return estado;

        } else if (Integer.parseInt(listVencimiento[0]) < Integer.parseInt(listpago[0])) {
            estado = "Atrasada";
            modificoMonto(listaCuotas, interes);
            return estado;

        } else {

            if (Integer.parseInt(listVencimiento[1]) > Integer.parseInt(listpago[1])) {
                modificoMonto(listaCuotas, interes);
                return estado;

            } else if (Integer.parseInt(listVencimiento[1]) < Integer.parseInt(listpago[1])) {
                estado = "Atrasada";
                modificoMonto(listaCuotas, interes);
                return estado;
            } else {
                if (Integer.parseInt(listVencimiento[2]) > Integer.parseInt(listpago[2])) {
                    modificoMonto(listaCuotas, interes);
                    return estado;

                } else {
                    estado = "Atrasada";
                    modificoMonto(listaCuotas, interes);
                    return estado;
                }
            }
        }
    }

    public void pagarCuota(Integer idCuota) {

        CuotaEntity cuota = cuotaRepository.findById(idCuota).get();

        String fechaVenci = cuota.getFechaVencimiento();
        Integer nroAtrasos = cuotasAtrasadas(cuota.getRutEstudiante());
        Integer interes = calculoInteres(nroAtrasos);

        Date fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        List<CuotaEntity> listaCuotas = obtenerCuotaPorRut(cuota.getRutEstudiante());

        if (cuota.getFechaPago().isEmpty()) {

            cuota.setFechaPago(formato.format(fecha));
            String estado = revisarFecha(fechaVenci, cuota.getFechaPago(), listaCuotas, interes);
            cuota.setEstado(estado);
        }
        cuotaRepository.save(cuota);

    }
}
