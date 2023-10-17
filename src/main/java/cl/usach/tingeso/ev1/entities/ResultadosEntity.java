package cl.usach.tingeso.ev1.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="resultados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultadosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer ID;

    private String rutPuntaje;
    private String fechaExamen;
    private String puntaje;
}
