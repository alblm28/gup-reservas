package io.github.alblm28.reserva_cabanas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "cabania")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cabania {
    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cabania")
    private Integer idCabania;

   //NOMBRE
    @Column(name = "nombre_cabania", nullable = false, length = 150)
    private String nombreCabania;

  //DESCRIPCION
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    //CAPACIDAD MAX
    @Column(name = "capacidad_max", nullable = false)
    private Short capacidadMax;

   //PRECIO NOCHE
    @Column(name = "precio_noche", nullable = false, precision = 10, scale = 2) //para los decimales
    private BigDecimal precioNoche;

   //ESTADO
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoCabania estado;

    //VENTILACION
    @Enumerated(EnumType.STRING)
    @Column(name = "ventilacion", nullable = false)
    private TipoVentilacion ventilacion;

    //CAMA
    @Enumerated(EnumType.STRING)
    @Column(name = "cama", nullable = false)
    private TipoCama cama;
    //ENCHUFES
    @Column(name = "enchufes")
    private Short enchufes;
    @Column(name = "num_banios", nullable = false)
    private Short numBanios;

    //FOTOS relacion debil
    @OneToMany(mappedBy = "cabania", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<FotosCabania> fotos;
}
