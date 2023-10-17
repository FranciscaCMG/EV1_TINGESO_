package cl.usach.tingeso.ev1.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="arancel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArancelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer ID;

    private String rutEstudiante;
    private Integer monto;
    private String tipoPago;
    private Integer cantidadCuotas;

}
