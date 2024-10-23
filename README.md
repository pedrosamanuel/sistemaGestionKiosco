<h1>Sistema de Gestión de Kioscos</h1>

<h2>Descripción</h2>
<p>
    Este proyecto es un <strong>sistema de gestión de kioscos</strong> desarrollado en Java utilizando el framework Spring Boot. 
    El sistema permite administrar productos, proveedores, ventas y precios de manera eficiente, con autenticación y autorización 
    mediante Spring Security y JWT. Además, utiliza Spring Data JPA para la persistencia de datos.
</p>

<h2>Tecnologías utilizadas</h2>
<ul>
    <li><strong>Java 17</strong>: Lenguaje de programación principal del proyecto.</li>
    <li><strong>Spring Boot</strong>: Framework para la construcción del backend.</li>
    <li><strong>Spring Security</strong>: Proporciona autenticación y autorización robusta, implementando control de acceso basado en roles.</li>
    <li><strong>JWT (JSON Web Tokens)</strong>: Para la autenticación de usuarios, asegurando la transmisión segura de credenciales.</li>
    <li><strong>Spring Data JPA</strong>: Manejo de la persistencia de datos de forma eficiente a través de ORM (Object-Relational Mapping).</li>
    <li><strong>PostgreSQL</strong>: Base de datos relacional utilizada en el entorno de producción.</li>
    <li><strong>Docker</strong>: Para la contenedorización y fácil despliegue de la aplicación.</li>
    <li><strong>Maven</strong>: Herramienta de gestión de dependencias y construcción del proyecto.</li>
</ul>

<h2>Características principales</h2>
<ul>
    <li><strong>Gestión de Productos</strong>: Permite agregar, editar, listar y eliminar productos. Cada producto puede asociarse con uno o varios proveedores y contiene información de stock mínimo y actual.</li>
    <li><strong>Gestión de Ventas</strong>: Registra ventas, actualiza automáticamente el stock y calcula ganancias.</li>
    <li><strong>Gestión de Proveedores</strong>: Administra los proveedores y sus productos asociados.</li>
    <li><strong>Gestión de Precios</strong>: Calcula precios de productos con base en los costos de proveedores, permitiendo aplicar márgenes de ganancia.</li>
    <li><strong>Autenticación y Autorización</strong>: Implementación de roles de usuario, con JWT para la autenticación y control de acceso basado en roles.</li>
    <li><strong>Seguridad</strong>: Encriptación de contraseñas y protección de endpoints críticos.</li>
</ul>

