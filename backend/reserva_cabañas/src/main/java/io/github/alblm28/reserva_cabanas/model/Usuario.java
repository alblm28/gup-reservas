package io.github.alblm28.reserva_cabanas.model;

import jakarta.persistence.*;

// Lombok genera getters y setters automaticos
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// para manejar fechas con zona horaria TIMESTAMPTZ en Postgre
import java.time.ZonedDateTime;
import org.hibernate.annotations.CreationTimestamp;
@Entity
// hace q spring boot sepa q es una tabla

@Table(name = "usuario")
// nombre exacto tabla

@Data
// Lombok genera  getters, setters, toString, equals, hashCode

@NoArgsConstructor
// Lombok crea un constructor sin parámetros
// Requerido por JPA/Hibernate para poder crear objetos de esta clase

@AllArgsConstructor
// Lombok crea un constructor con todos los campos
// util para crear nuevos usuarios

public class Usuario {
    //ID
    @Id
   // marca como la PRIMARY KEY, cada tabla debe tener uno

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // GENERATED ALWAYS AS IDENTITY

    @Column(name = "id_usuario")
    // si no se pone name, JPA usari idUsuario
    private Integer idUsuario;
    //Integer puede ser null, int no

//ROL
    @Enumerated(EnumType.STRING)
    // guarda el enum como texto en la BD
    // sin esto, guardaría como 0,1

    @Column(name = "rol", nullable = false)
    // nullable = false es not null

    private RolUsuario rol;

    //NOMBRE
    @Column(name = "nombre", nullable = false, length = 30)

    private String nombre;

   //APELLIDO1
    @Column(name = "apellido1", nullable = false, length = 30)
    private String apellido1;

    //APELLIDO2
    @Column(name = "apellido2", nullable = false, length = 30)
    private String apellido2;
   //EMAIL
    @Column(name = "email", nullable = false, unique = true, length = 300)
    private String email;
    //TELEFONO
    @Column(name = "tel", unique = true, length = 15)
    private String tel;

    //CONTRASEÑA
    @Column(name = "contrasenia", nullable = false, length = 100)
    private String contrasenia;
    //RECORDAR HASHEAR, ahora texto plano

    //FECHA CREACION
    @CreationTimestamp
    // Hibernate asigna automáticamente CURRENT TIMESTAMP
    @Column(name = "fecha_creacion", nullable = false)
    private ZonedDateTime fechaCreacion;

}