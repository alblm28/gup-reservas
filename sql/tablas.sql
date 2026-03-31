CREATE TYPE rol_usuario AS ENUM ('voluntario', 'admin');
CREATE TYPE estado_reserva AS ENUM ('bloqueada', 'confirmada', 'cancelada', 'completada');
CREATE TYPE estado_pago AS ENUM ('pendiente', 'completado', 'fallido','cancelado', 'reembolsado');
CREATE TYPE estado_cabania AS ENUM ('disponible', 'mantenimiento', 'inactiva');
CREATE TYPE tipo_ventilacion AS ENUM ('ventilador', 'aire_acondicionado', 'natural');
CREATE TYPE tipo_cama AS ENUM ('individual', 'doble', 'litera');

CREATE TABLE usuario (
    id_usuario INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    -- AUTO_INCREMENT en Mysql, similar a SERIAL
    rol rol_usuario NOT NULL DEFAULT 'voluntario',
    nombre VARCHAR(30) NOT NULL,
    apellido1 VARCHAR(30) NOT NULL,
    apellido2 VARCHAR(30) NOT NULL,
    -- Se asume que todos los usuarios tienen dos apellidos, si uno no tiene segundo apellido,
	-- se repite el primero, podria cambiarse a futuro
    email VARCHAR(300) NOT NULL UNIQUE,
    tel VARCHAR(15) UNIQUE NULL, 
    contrasenia VARCHAR(100)NOT NULL,
    -- recordar hashear 
    fecha_creacion TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
    --fromato con franja horaria
);

CREATE TABLE cabania (
    id_cabania INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre_cabania VARCHAR(150) NOT NULL,
    descripcion TEXT,
    capacidad_max SMALLINT NOT NULL CHECK (capacidad_max > 0 ),
	--mejor que int para números pequeños
	-- podria poner límite superior para acotar mejor
    precio_noche NUMERIC(10, 2)  NOT NULL CHECK (precio_noche >= 0),
    -- numeric da mas precisión que float
 	estado estado_cabania NOT NULL DEFAULT 'disponible',
    ventilacion tipo_ventilacion NOT NULL DEFAULT 'natural', 
    cama tipo_cama NOT NULL DEFAULT 'individual',
    enchufes SMALLINT CHECK (enchufes >= 0),
    num_banios SMALLINT NOT NULL CHECK (num_banios >= 0)
);

CREATE TABLE fotos_cabania (
    id_foto INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    url_imagen VARCHAR(500) NOT NULL, 
	-- no única, dos cabañas pueden tener el mismo URL por ejemplo de un exterior
    orden SMALLINT NOT NULL DEFAULT 0 CHECK (orden >= 0),
    -- determina el orden en que se presentan las fotos, TODO hacer que incremente en JAVA
    id_cabania INT NOT NULL,
	
    CONSTRAINT fk_foto_cabania
        FOREIGN KEY (id_cabania) REFERENCES cabania (id_cabania)
        ON DELETE CASCADE
        -- CASCADE para que fotos se eliminen si su cabaña de referncia lo hace (entidad DÉBIL)
);

CREATE TABLE huesped (
    id_huesped INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nif VARCHAR(20) NOT NULL UNIQUE,
    -- es nif suponiendo que funcionará solo en España, podría cambiarse a pasaporte o similar 
	-- en caso de querer expandir el proyecto
	-- también podría añadir un atributo nacionalidad
    nombre VARCHAR(30) NOT NULL,
    apellido1 VARCHAR(30) NOT NULL,
    apellido2 VARCHAR(30)NOT NULL,
    fecha_nacimiento DATE NOT NULL CHECK (fecha_nacimiento < CURRENT_DATE AND fecha_nacimiento > '1920-01-01'),
    tel VARCHAR(15) UNIQUE,
    email VARCHAR(300) UNIQUE
);

CREATE TABLE reserva (
    id_reserva INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    precio_final NUMERIC(10, 2) NOT NULL CHECK (precio_final >= 0),
    num_huespedes SMALLINT NOT NULL CHECK (num_huespedes > 0),
    estado estado_reserva  NOT NULL DEFAULT 'bloqueada',
    -- bloqueada es el estado de reserva temporal mientras se introducen datos y pago.
    fecha_creacion TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_inicio TIMESTAMPTZ NOT NULL,
    fecha_fin TIMESTAMPTZ NOT NULL,
 	-- inicio y fin también con TIMESTAMPTZ para que alguien pueda irse a las 9 y 
	 -- llegar otro a las 2, sin problemas de solapamientos
	 
    CONSTRAINT chk_fechas CHECK (fecha_fin > fecha_inicio),
	
    id_cabania INT NOT NULL,
    id_usuario INT NOT NULL,
	
    CONSTRAINT fk_reserva_cabania
        FOREIGN KEY (id_cabania)
        REFERENCES cabania (id_cabania)
        ON DELETE RESTRICT,
        -- no se puede eliminar una cabaña que tenga reservas.
		
    CONSTRAINT fk_reserva_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuario (id_usuario)
        ON DELETE RESTRICT
        -- no se puede eliminar un usuario con reservas.
);

CREATE TABLE incluye ( --tabla de relacion entre huesped y reserva
    id_reserva INT NOT NULL,
    id_huesped INT NOT NULL,
    PRIMARY KEY (id_reserva, id_huesped),
	
    CONSTRAINT fk_incluye_reserva
        FOREIGN KEY (id_reserva)
        REFERENCES reserva (id_reserva)
        ON DELETE CASCADE,
        -- si se elimina la reserva, se elimina su incluye.
 
    CONSTRAINT fk_incluye_huesped
        FOREIGN KEY (id_huesped)
        REFERENCES huesped (id_huesped)
        ON DELETE RESTRICT
        -- no se puede eliminar un huésped si tiene reservas activas.
);

CREATE TABLE pago (
    id_pago INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_transaccion_externa VARCHAR(255) UNIQUE,
    -- Opcional porque es null hasta que el pago se procesa 
	-- es el id ID devuelto por la pasarela externa.
    fecha_pago TIMESTAMPTZ,
	-- lo tenía con current, corregido para hacerse cuando el estado es completado
    cantidad NUMERIC(10, 2) NOT NULL CHECK (cantidad >= 0),
    estado estado_pago NOT NULL DEFAULT 'pendiente',
    id_reserva INT NOT NULL,

    CONSTRAINT fk_pago_reserva
        FOREIGN KEY (id_reserva)
        REFERENCES reserva (id_reserva)
        ON DELETE RESTRICT
);