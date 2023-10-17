package cl.usach.tingeso.ev1.services;

import cl.usach.tingeso.ev1.entities.CuotaEntity;
import cl.usach.tingeso.ev1.entities.ResultadosEntity;
import cl.usach.tingeso.ev1.repositories.CuotaRepository;
import cl.usach.tingeso.ev1.repositories.ResultadosRepository;

import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResultadosService {
    @Autowired
    ResultadosRepository resultadosRepository;

    @Autowired
    CuotaRepository cuotaRepository;

    private final Logger logg = LoggerFactory.getLogger(ResultadosService.class);

    public List<ResultadosEntity> obtenerResultados() {
        return resultadosRepository.findAll();
    }

    public List<ResultadosEntity> obtenerResultadosPorRut(String rut) {
        return resultadosRepository.findByRutPuntaje(rut);
    }

    public ResultadosEntity guardarDatosResultados(ResultadosEntity resultados) {
        return resultadosRepository.save(resultados);
    }

    @Generated
    public String guardarArchivo(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename != null) {
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");
                } catch (IOException e) {
                    logg.error("ERROR", e);
                }
            }
            return "Archivo guardado con exito";
        } else {
            return "No se pudo guardar el archivo";
        }
    }

    @Generated
    public void leerCsv(String direccion) {
        String texto = "";
        BufferedReader bf = null;

        try {
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while ((bfRead = bf.readLine()) != null) {
                if (count == 1) {
                    count = 0;
                } else {
                    guardarDataDB(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2]);
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
            System.out.println("Archivo leido exitosamente");
        } catch (Exception e) {
            System.err.println("No se encontro el archivo");
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    logg.error("ERROR", e);
                }
            }
        }

    }

    public void guardarDataDB(String rut, String fecha, String puntaje) {
        ResultadosEntity newData = new ResultadosEntity();
        newData.setRutPuntaje(rut);
        newData.setFechaExamen(fecha);
        newData.setPuntaje(puntaje);
        guardarDatosResultados(newData);
    }

    public Integer promedioPruebas(String rut) {

        int suma = 0;
        int promedio = 0;

        List<ResultadosEntity> listResultados = resultadosRepository.findByRutPuntaje(rut);

        for (ResultadosEntity resultado : listResultados) {
            suma = suma + Integer.parseInt(resultado.getPuntaje());
        }

        promedio = suma / listResultados.size();

        return promedio;

    }

    public void aplicarDscto(String rut) {

        List<CuotaEntity> listCuota = cuotaRepository.findByRutEstudiante(rut);

        int promedio = 0;

        for (CuotaEntity cuota : listCuota) {
            promedio = promedioPruebas(rut);

            if (promedio >= 950 && promedio <= 1000) {
                cuota.setValorCuota(cuota.getValorCuota() - (cuota.getValorCuota() * 10 / 100));
            } else if (promedio >= 900 && promedio < 950) {
                cuota.setValorCuota(cuota.getValorCuota() - (cuota.getValorCuota() * 5 / 100));
            } else if (promedio >= 850 && promedio < 900) {
                cuota.setValorCuota(cuota.getValorCuota() - (cuota.getValorCuota() * 2 / 100));
            } else {
                cuota.setValorCuota(cuota.getValorCuota());
            }
            cuotaRepository.save(cuota);
        }
    }

    public void seleccionaRut() {

        String rutEstudiante;

        List<ResultadosEntity> listResultados = resultadosRepository.findAll();
        List<String> listaRut = new ArrayList<>();

        for (ResultadosEntity resultado : listResultados) {
            rutEstudiante = resultado.getRutPuntaje();

            if (!listaRut.contains(rutEstudiante)) {
                listaRut.add(rutEstudiante);
            }
        }

        for (String rut : listaRut) {
            aplicarDscto(rut);
        }
    }
}
