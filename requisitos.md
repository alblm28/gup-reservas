\# Requisitos del Proyecto - Sistema de Reservas GUP



\*\*Versión:\*\* 1.0  

\*\*Fecha:\*\* Marzo 2026  

\*\*Estado:\*\* En desarrollo  



\---



\## Descripción del Proyecto



Este proyecto es un prototipo para la \*\*asociación GUP\*\*, que consiste en un sistema de reservas de cabañas para voluntarios que viajan a Senegal.



El sistema permite gestionar el catálogo de cabañas, realizar reservas con datos de múltiples huéspedes, procesar pagos y generar documentación para los viajeros.



La idea está sujeta a cambios, al haber puntos aún sin definir por la asociación.



\---



\## Actores del Sistema



| Actor | Descripción | ¿Puede reservar? |

|-------|-------------|------------------|

| \*\*Visitante\*\* | Usuario no registrado que navega por la web |  No |

| \*\*Voluntario\*\* | Usuario registrado con cuenta verificada |  Sí |

| \*\*Administrador\*\* | Personal de GUP con permisos de gestión |  No (solo gestiona) |



\---



\##  Funcionalidades



\### Módulo Público (Visitantes)



| ID | Funcionalidad | Prioridad | Estado |

|----|---------------|-----------|--------|

| F01 | Ver catálogo de cabañas | Alta | ✅ Implementado en diseño |

| F02 | Filtrar cabañas por fechas | Alta | ✅ Implementado en diseño |

| F03 | Filtrar por tipo y capacidad | Alta | ✅ Implementado en diseño |

| F04 | Ordenar cabañas (precio, capacidad) | Media | ✅ Implementado en diseño |

| F05 | Ver detalle de cabaña (fotos, descripción, características) | Alta | ✅ Implementado en diseño |

| F06 | Ver fechas disponibles | Alta | ✅ Implementado en diseño |

| F07 | Ver mapa de ubicación | Baja | ⏳ Futuro (no definido) |



\### Módulo de Usuario (Voluntarios)



| ID | Funcionalidad | Prioridad | Estado |

|----|---------------|-----------|--------|

| F08 | Registrarse (nombre, apellidos, email, teléfono, contraseña) | Alta | ✅ Implementado en diseño |

| F09 | Iniciar sesión | Alta | ✅ Implementado en diseño |

| F10 | Cerrar sesión | Alta | ✅ Implementado en diseño |

| F11 | Reservar cabaña | Alta | ✅ Implementado en diseño |

| F12 | Introducir datos de huéspedes (N personas) | Alta | ✅ Implementado en diseño |

| F13 | Pagar con Stripe API | Alta | ✅ Implementado en diseño |

| F14 | Descargar PDF de confirmación | Alta | ✅ Implementado en diseño |

| F15 | Recibir email de confirmación | Alta | ✅ Implementado en diseño |

| F16 | Ver mis reservas | Media | ✅ Implementado en diseño |

| F17 | Cancelar reserva | Media | ⏳ Por definir |



\### Módulo de Administrador



| ID | Funcionalidad | Prioridad | Estado |

|----|---------------|-----------|--------|

| F18 | Iniciar sesión como admin | Alta | ✅ Implementado en diseño |

| F19 | Ver calendario completo de reservas | Alta | ✅ Implementado en diseño |

| F20 | Gestionar cabañas (CRUD) | Alta | ✅ Implementado en diseño |

| F21 | Editar estado de cabañas | Alta | ✅ Implementado en diseño |

| F22 | Eliminar cabañas | Alta | ✅ Implementado en diseño |

| F23 | Añadir nuevas cabañas | Alta | ✅ Implementado en diseño |

| F24 | Ver datos de usuarios | Alta | ✅ Implementado en diseño |

| F25 | Ver datos de huéspedes | Alta | ✅ Implementado en diseño |

| F26 | Gestionar estado de reservas | Alta | ✅ Implementado en diseño |

| F27 | Verificar pagos | Media | ✅ Implementado en diseño |



\### Funcionalidades Futuras (No MVP)



| ID | Funcionalidad | Prioridad | Estado |

|----|---------------|-----------|--------|

| F28 | API de búsqueda de vuelos | Baja | ⏳ No implementado |

| F29 | Información sobre vacunas para menores | Baja | ⏳ No implementado |

| F30 | Autorización para menores de edad | Baja | ⏳ No implementado |

| F31 | Aceptación manual de reservas antes de confirmar | Baja | ⏳ No implementado |

| F32 | Modo offline (cache local) | Baja | ⏳ No implementado |

| F33 | Tokenización de pagos | Media | ⏳ No implementado |



\---



\## 📋 Reglas de Negocio



\### Usuarios

| Regla | Descripción |

|-------|-------------|

| RN01 | El email debe ser único (no puede haber cuentas duplicadas) |

| RN02 | El teléfono debe ser único si se proporciona |

| RN03 | El teléfono es \*\*opcional\*\* para usuarios españoles |

| RN04 | Para usuarios españoles: 2 apellidos obligatorios (si solo tiene 1, repetir el primero en apellido2) |

| RN05 | Para usuarios internacionales: apellido2 es opcional |

| RN06 | El NIF es obligatorio para españoles, pasaporte para internacionales |

| RN07 | El administrador NO puede realizar reservas (solo gestionar) |



\### Reservas

| Regla | Descripción |

|-------|-------------|

| RN08 | Solo mayores de 18 años pueden reservar |

| RN09 | Durante el proceso de pago, la cabaña se bloquea para otros usuarios |

| RN10 | Una reserva la realiza un único usuario |

| RN11 | Una reserva puede tener múltiples huéspedes |

| RN12 | El número de huéspedes no puede superar la capacidad máxima de la cabaña |

| RN13 | La fecha de fin debe ser posterior a la fecha de inicio |



\### Pagos

| Regla | Descripción |

|-------|-------------|

| RN14 | Una reserva puede generar múltiples pagos (reembolsos, pagos parciales, reintentos) |

| RN15 | Un pago solo pertenece a una reserva |

| RN16 | La fecha de pago es opcional al crear el registro (se completa cuando Stripe confirma) |



\---



\## 🗄️ Estados del Sistema



\### RESERVA.estado

| Estado | Descripción |

|--------|-------------|

| `bloqueada` | Durante el proceso de pago (temporal) |

| `confirmada` | Pago completado exitosamente |

| `cancelada` | Reserva cancelada por usuario o admin |

| `completada` | Estancia finalizada |



\### PAGO.estado

| Estado | Descripción |

|--------|-------------|

| `pendiente` | Esperando confirmación de Stripe |

| `completado` | Pago exitoso |

| `fallido` | Pago rechazado |

| `reembolsado` | Pago devuelto (total o parcial) |



\---



\## 🛠️ Stack Tecnológico



| Capa | Tecnología | Versión | Notas |

|------|------------|---------|-------|

| \*\*Base de Datos\*\* | PostgreSQL | 18 | Se elige por ser más avanzado que MySQL |

| \*\*Backend\*\* | Java + Spring Boot | 17+ / 3.x | Lenguaje que está aprendiendo el desarrollador |

| \*\*Frontend\*\* | HTML, CSS, JavaScript | - | React no descartado para el futuro |

| \*\*Pagos\*\* | Stripe API | - | Falta de definir completamente |

| \*\*PDF\*\* | Por definir | - | Para generación de confirmaciones |

| \*\*Email\*\* | Por definir | - | Para notificaciones |

| \*\*Deploy Backend\*\* | Render / Railway | - | Por definir |

| \*\*Deploy Frontend\*\* | Vercel | - | Por definir |



\### Alternativas Consideradas

| Tecnología | Alternativa | ¿Por qué no se eligió? |

|------------|-------------|----------------------|

| Backend | Node.js / Python | El desarrollador está aprendiendo Java |

| Base de Datos | MySQL | PostgreSQL es más avanzado para manejo de fechas |

| Frontend | React | Por ahora HTML/CSS/JS puro, React en el futuro |



\---



\## 📁 Estructura de la Base de Datos



\### Entidades Principales



| Entidad | Descripción | Atributos Clave |

|---------|-------------|-----------------|

| \*\*USUARIO\*\* | Usuarios registrados (voluntarios y admins) | id, rol, nombre, apellidos, email, tel, contraseña, fecha\_creacion |

| \*\*CABANIA\*\* | Cabañas disponibles para reserva | id, nombre, descripcion, precio\_noche, capacidad\_max, estado, caracteristicas |

| \*\*RESERVA\*\* | Reservas realizadas por usuarios | id, fechas, estado, precio\_final, nº\_huespedes |

| \*\*HUESPED\*\* | Datos de las personas que se alojan | id, nif/pasaporte, nombre, apellidos, fecha\_nacimiento, email, tel |

| \*\*PAGO\*\* | Transacciones de pago (Stripe) | id, id\_transaccion\_externa, fecha\_pago, cantidad, estado |

| \*\*FOTOS\_CABANIA\*\* | Imágenes de cada cabaña | id, url\_imagen, orden, id\_cabania (FK) |



\### Relaciones

\- \*\*USUARIO → RESERVA:\*\* 1:N (un usuario hace N reservas)

\- \*\*CABANIA → RESERVA:\*\* 1:N (una cabaña tiene N reservas)

\- \*\*RESERVA → HUESPED:\*\* N:M (una reserva tiene N huéspedes, un huésped puede estar en N reservas)

\- \*\*RESERVA → PAGO:\*\* 1:N (una reserva puede tener N pagos por reembolsos/reintentos)

\- \*\*CABANIA → FOTOS\_CABANIA:\*\* 1:N (una cabaña tiene N fotos)



\### Notas de Diseño

\- \*\*FOTOS\_CABANIA es entidad débil:\*\* Las fotos solo pertenecen a una cabaña y se borran si la cabaña se elimina

\- \*\*url\_imagen NO es unique:\*\* Puede haber fotos repetidas (ej: exterior) que se guardan con IDs diferentes

\- \*\*Cada entidad tiene un ID único:\*\* Número entero que se incrementa automáticamente



\---



\## 📊 Requisitos No Funcionales



| ID | Requisito | Descripción |

|----|-----------|-------------|

| RNF01 | Rendimiento | El tiempo de respuesta debe ser < 2 segundos |

| RNF02 | Disponibilidad | El sistema debe estar disponible 99% del tiempo |

| RNF03 | Seguridad | Las contraseñas deben estar encriptadas (BCrypt) |

| RNF04 | Privacidad | Los datos personales deben protegerse según GDPR |

| RNF05 | Escalabilidad | Debe soportar al menos 100 usuarios concurrentes |

| RNF06 | Offline | El PDF debe poder consultarse sin cobertura |

| RNF07 | Mantenibilidad | El código debe estar documentado y ser legible |



\---



\## 📈 Métricas y Seguimiento



| Métrica | Descripción | ¿Cómo se mide? |

|---------|-------------|----------------|

| Usuarios registrados | Total de voluntarios en la plataforma | Campo `fecha\_creacion` en USUARIO |

| Reservas por mes | Número de reservas completadas mensualmente | Consultas sobre RESERVA |

| Ocupación de cabañas | Porcentaje de días ocupados por cabaña | Cruce RESERVA + CABANIA |

| Ingresos generados | Total recaudado por reservas | Suma de PAGO.cantidad |



\---



\## ⚠️ Aspectos Pendientes de Definir



| Aspecto | Estado | Notas |

|---------|--------|-------|

| API de pagos (Stripe) | 🟡 Parcialmente definido | Falta definir flujo completo de tokenización |

| API de autorización previa | 🔴 No definido | Podría incluirse en el futuro |

| Mapa de ubicación de cabañas | 🔴 No definido | No se sabe si será útil |

| Búsqueda de vuelos (API externa) | 🔴 No definido | Funcionalidad futura opcional |

| Información de vacunas para menores | 🔴 No definido | Solo si se permiten menores en el futuro |

| Proveedor de envío de emails | 🔴 No definido | Pendiente de seleccionar |

| Librería de generación de PDF | 🔴 No definido | Pendiente de seleccionar (iText, PDFBox, etc.) |

| Política de cancelación | 🔴 No definido | ¿Reembolso total, parcial, ninguno? |

| Antelación mínima para reservar | 🔴 No definido | ¿Se puede reservar para mañana? ¿Con cuántos días de antelación? |



\---



\## 🚀 Fases del Proyecto



\### Fase 1 - Diseño ✅ COMPLETADA

\- \[x] Requisitos del negocio definidos

\- \[x] Diagrama Entidad-Relación (ER)

\- \[x] Diagrama de tablas lógicas

\- \[x] Diagrama de casos de uso

\- \[x] Documentación completa



\### Fase 2 - Base de Datos 🔄 EN PROGRESO

\- \[ ] Scripts SQL de creación de tablas

\- \[ ] Constraints y validaciones

\- \[ ] Índices de rendimiento

\- \[ ] Datos de prueba



\### Fase 3 - Backend ⏳ PENDIENTE

\- \[ ] Configuración Spring Boot

\- \[ ] Modelos de entidad (JPA)

\- \[ ] Repositorios (Spring Data)

\- \[ ] Servicios y lógica de negocio

\- \[ ] Controladores REST API

\- \[ ] Integración con Stripe

\- \[ ] Generación de PDF

\- \[ ] Envío de emails



\### Fase 4 - Frontend ⏳ PENDIENTE

\- \[ ] Maquetación HTML/CSS

\- \[ ] Interfaz de catálogo de cabañas

\- \[ ] Formulario de reserva

\- \[ ] Panel de usuario

\- \[ ] Panel de administrador



\### Fase 5 - Deploy ⏳ PENDIENTE

\- \[ ] Configuración de producción

\- \[ ] Deploy en Render/Railway

\- \[ ] Pruebas de integración

\- \[ ] Documentación de usuario



\---



\## 📝 Notas Adicionales



1\. \*\*Nacionalidad:\*\* El sistema está pensado principalmente para usuarios españoles, pero debe permitir usuarios internacionales con ajustes (apellido2 opcional, pasaporte en vez de NIF).



2\. \*\*Menores de edad:\*\* Por ahora se asume que solo mayores de 18 años pueden reservar. Si en el futuro se permiten menores, se deberá implementar sistema de autorización parental y información de vacunas.



3\. \*\*Conectividad:\*\* Dado que los voluntarios viajarán a zonas con posible falta de cobertura, el PDF descargable es una funcionalidad crítica para poder consultar la reserva offline.



4\. \*\*Administrador:\*\* El rol de administrador está separado del de voluntario. Un admin NO puede hacer reservas, solo gestionar el sistema.



5\. \*\*Pagos múltiples:\*\* La relación 1:N entre RESERVA y PAGO permite manejar reembolsos, pagos parciales (señal + resto) y reintentos si un pago falla.



\---



\## 📧 Contacto



| Rol | Información |

|-----|-------------|

| \*\*Proyecto\*\* | Sistema de Reservas GUP |

| \*\*Asociación\*\* | GUP - Voluntariado Senegal |

| \*\*Desarrollador\*\* | \[Alba Llano] |

| \*\*Email\*\* | \[alballanomanrique28@gmail.com] |

| \*\*GitHub\*\* | \[@alblm28](https://github.com/alblm28) |



\---



\*Documento creado en Marzo 2026 - Versión 1.0\*

