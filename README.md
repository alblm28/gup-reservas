\# 🏠 Sistema de Reservas GUP



\[!\[Estado](https://img.shields.io/badge/estado-en%20desarrollo-yellow)]()

\[!\[Java](https://img.shields.io/badge/Java-17+-orange)]()

\[!\[Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)]()

\[!\[PostgreSQL](https://img.shields.io/badge/PostgreSQL-18-blue)]()



Sistema de gestión de reservas de cabañas para voluntarios de la asociación GUP en Senegal.



\---



\## 📋 Tabla de Contenidos



\- \[Descripción](#-descripción)

\- \[Características](#-características)

\- \[Stack Tecnológico](#-stack-tecnológico)

\- \[Estructura del Proyecto](#-estructura-del-proyecto)

\- \[Estado del Proyecto](#-estado-del-proyecto)

\- \[Base de Datos](#-base-de-datos)

\- \[Instalación](#-instalación)

\- \[Uso](#-uso)

\- \[Documentación](#-documentación)

\- \[Licencia](#-licencia)

\- \[Contacto](#-contacto)



\---



\## 📖 Descripción



Este proyecto es un prototipo desarrollado para la \*\*asociación GUP\*\*, que facilita la gestión de reservas de cabañas para voluntarios que viajan a Senegal.



El sistema permite:

\- 📅 Consultar disponibilidad de cabañas por fechas

\- 🔍 Filtrar y ordenar cabañas según preferencias

\- 📝 Realizar reservas con datos de múltiples huéspedes

\- 💳 Procesar pagos mediante Stripe API

\- 📄 Generar PDF de confirmación para descargar

\- 🗓️ Panel de administración con calendario de reservas



\---



\## ✨ Características



\### Para Voluntarios

| Funcionalidad | Descripción |

|---------------|-------------|

| 🏠 Catálogo de cabañas | Ver todas las cabañas disponibles con fotos y características |

| 🔍 Filtros avanzados | Filtrar por fechas, capacidad, tipo de cama, amenities |

| 📅 Disponibilidad en tiempo real | Ver fechas libres/ocupadas de cada cabaña |

| 👥 Reserva múltiple | Reservar para varias personas con sus datos individuales |

| 💳 Pago seguro | Integración con Stripe para transacciones seguras |

| 📄 PDF descargable | Confirmación de reserva para imprimir o guardar offline |

| 📧 Notificaciones por email | Confirmación y recordatorios automáticos |



\### Para Administradores

| Funcionalidad | Descripción |

|---------------|-------------|

| 🗓️ Calendario completo | Ver todas las reservas en vista de calendario |

| 🏠 Gestión de cabañas | CRUD completo (crear, editar, eliminar, cambiar estado) |

| 👥 Ver usuarios y huéspedes | Acceder a datos de todos los usuarios registrados |

| 💰 Gestión de pagos | Verificar y reconciliar pagos con Stripe |

| 📊 Métricas y reportes | Estadísticas de ocupación y reservas |



\---



\## 🛠️ Stack Tecnológico



| Capa | Tecnología | Versión |

|------|------------|---------|

| \*\*Base de Datos\*\* | PostgreSQL | 18 |

| \*\*Backend\*\* | Java + Spring Boot | 17+ / 3.x |

| \*\*Frontend\*\* | HTML, CSS, JavaScript | - |

| \*\*ORM\*\* | Hibernate / JPA | - |

| \*\*Pagos\*\* | Stripe API | - |

| \*\*PDF\*\* | iText / Apache PDFBox | - |

| \*\*Email\*\* | Spring Mail / SendGrid | - |

| \*\*Deploy\*\* | Render / Railway / Vercel | - |



\### Herramientas de Desarrollo

\- IntelliJ IDEA / VS Code

\- pgAdmin 4 / DBeaver

\- Git + GitHub

\- Postman (testing API)



\---



\## 📁 Estructura del Proyecto



