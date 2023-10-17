package cl.usach.tingeso.ev1.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="cuota")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuotaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer ID;

    private String rutEstudiante;
    private Integer nroCuota;
    private Integer valorCuota;
    private String fechaVencimiento;
    private String fechaPago;
    private String estado;
}
