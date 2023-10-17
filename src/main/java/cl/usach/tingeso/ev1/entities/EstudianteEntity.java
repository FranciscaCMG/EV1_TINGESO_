package cl.usach.tingeso.ev1.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "estudiante") // link con la base de datos
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteEntity {
    @Id
    @Column(name = "rut", nullable = false)
    private String rut;

    private String apellidos;
    private String nombres;
    private String fechanacimiento;
    private String tipoColegioP;
    private String nombreColegio;
    private Integer anioEgreso;
    private Integer examenesRendidos;
    private Integer promedio;
}
