package io.github.alblm28.reserva_cabanas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fotos_cabania")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FotosCabania {
    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_foto")
    private Integer idFoto;
    //URL
    @Column(name = "url_imagen", nullable = false, length = 500)
    private String urlImagen;
    //ORDEN
    @Column(name = "orden", nullable = false)
    private Short orden;
    //relacion cabania
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cabania", nullable = false)
    private Cabania cabania;
}