package io.github.alblm28.reserva_cabanas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "huesped")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Huesped {
    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_huesped")
    private Integer idHuesped;
    //NIF
    @Column(name = "nif", nullable = false, unique = true, length = 20)
    private String nif;
    //NOMBRE
    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre;
   //APELLIDO1
    @Column(name = "apellido1", nullable = false, length = 30)
    private String apellido1;
    //APELLIDO2
    @Column(name = "apellido2", nullable = false, length = 30)
    private String apellido2;
   //NACIMIENTO
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;
    //TELEFONO
    @Column(name = "tel", unique = true, length = 15)
    private String tel;
    //EMAIL
    @Column(name = "email", unique = true, length = 300)
    private String email;
}