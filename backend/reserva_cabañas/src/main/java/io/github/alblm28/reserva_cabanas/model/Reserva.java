package io.github.alblm28.reserva_cabanas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;
import java.math.BigDecimal;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Integer idReserva;
    //PRECIO
    @Column(name = "precio_final", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioFinal;
    //HUESPEDES
    @Column(name = "num_huespedes", nullable = false)
    private Short numHuespedes;
    //ESTADO
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "estado", nullable = false)
    private EstadoReserva estado;
    //CREACION
    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false)
    private ZonedDateTime fechaCreacion;
    //INICIO
    @Column(name = "fecha_inicio", nullable = false)
    private ZonedDateTime fechaInicio;
    //FIN
    @Column(name = "fecha_fin", nullable = false)
    private ZonedDateTime fechaFin;

   //relacion N 1 cabanias
    @ManyToOne(fetch = FetchType.LAZY)
    // Fetch.LAZYno carga la cabaña hasta que se necesite para mejor rendimiento
    @JoinColumn(name = "id_cabania", nullable = false)
    // dDefine la columna FOREIGN KEY
    private Cabania cabania;
    //relacion N 1 usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}