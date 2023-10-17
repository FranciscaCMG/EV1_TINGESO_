package cl.usach.tingeso.ev1.services;

import cl.usach.tingeso.ev1.entities.EstudianteEntity;
import cl.usach.tingeso.ev1.repositories.EstudianteRepository;
import cl.usach.tingeso.ev1.repositories.ResultadosRepository;
import cl.usach.tingeso.ev1.entities.ResultadosEntity;
import cl.usach.tingeso.ev1.entities.ArancelEntity;
import cl.usach.tingeso.ev1.services.ArancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService {
    @Autowired
    EstudianteRepository estudianteRepository;

    @Autowired
    ResultadosRepository resultadosRepository;

    public List<EstudianteEntity> obtenerEstudiantes() {
        return estudianteRepository.findAll();
    }

    public EstudianteEntity obtenerEstudiantePorRut(String rut) {
        return estudianteRepository.findByRut(rut);
    }

    public void eliminarEstudiante(String rut) {
        EstudianteEntity estudiante = estudianteRepository.findByRut(rut);
        estudianteRepository.delete(estudiante);
    }

    public void guardarEstudiante(String rut, String apellidos, String nombres, String fechanacimiento,
            String tipoColegioP, String nombreColegio, Integer anioEgreso) {
        EstudianteEntity estudiante = new EstudianteEntity();
        estudiante.setRut(rut);
        estudiante.setApellidos(apellidos);
        estudiante.setNombres(nombres);
        estudiante.setFechanacimiento(fechanacimiento);
        estudiante.setTipoColegioP(tipoColegioP);
        estudiante.setNombreColegio(nombreColegio);
        estudiante.setAnioEgreso(anioEgreso);
        estudianteRepository.save(estudiante);
    }

    public void promedioEstudiante(String rut) {

        Integer promedio = 0;
        Integer sumaPromedio = 0;
        Integer cantidadExamenes = 0;

        EstudianteEntity estudiante = estudianteRepository.findByRut(rut);

        List<ResultadosEntity> resultados = resultadosRepository.findByRutPuntaje(rut);

        for (ResultadosEntity resultado : resultados) {

            sumaPromedio += Integer.parseInt(resultado.getPuntaje());
            cantidadExamenes += 1;
        }

        promedio = sumaPromedio / cantidadExamenes;

        estudiante.setPromedio(promedio);
        estudiante.setExamenesRendidos(cantidadExamenes);
        estudianteRepository.save(estudiante);

    }

}
