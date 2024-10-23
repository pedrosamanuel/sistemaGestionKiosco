Sistema de Gestión de Kioscos
Descripción
Este proyecto es un sistema de gestión de kioscos desarrollado en Java utilizando el framework Spring Boot. El sistema permite administrar productos, proveedores, ventas y precios de manera eficiente, con autenticación y autorización mediante Spring Security y JWT. Además, utiliza Spring Data JPA para la persistencia de datos.

Tecnologías utilizadas
Java 17: Lenguaje de programación principal del proyecto.
Spring Boot: Framework para la construcción del backend.
Spring Security: Proporciona autenticación y autorización robusta, implementando control de acceso basado en roles.
JWT (JSON Web Tokens): Para la autenticación de usuarios, asegurando la transmisión segura de credenciales.
Spring Data JPA: Manejo de la persistencia de datos de forma eficiente a través de ORM (Object-Relational Mapping).
PostgreSQL: Base de datos relacional utilizada en el entorno de producción.
Docker: Para la contenedorización y fácil despliegue de la aplicación.
Maven: Herramienta de gestión de dependencias y construcción del proyecto.
Características principales
Gestión de Productos: Permite agregar, editar, listar y eliminar productos. Cada producto puede asociarse con un o varios proveedores y contiene información de stock mínimo y actual.
Gestión de Ventas: Registra ventas, actualiza automáticamente el stock y calcula ganancias.
Gestión de Proveedores: Administra los proveedores y sus productos asociados.
Gestión de Precios: Calcula precios de productos con base en los costos de proveedores, permitiendo aplicar márgenes de ganancia.
Autenticación y Autorización: Implementación de roles de usuario, con JWT para la autenticación y control de acceso basado en roles.
Seguridad: Encriptación de contraseñas y protección de endpoints críticos.
