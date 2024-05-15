
HostelHunter
Descripción
HostelHunter es un proyecto diseñado para ayudar a los viajeros a encontrar y comparar hostales alrededor del mundo. Nuestra plataforma facilita la búsqueda de alojamientos que se ajusten a las necesidades y presupuestos de los usuarios, asegurando una experiencia de viaje óptima y accesible.

Módulo: DBAPI
Descripción del módulo DBAPI
El módulo DBAPI de HostelHunter es crucial para el funcionamiento integral del sistema. Esta API gestiona todas las interacciones con la base de datos, utilizando Java con Hibernate para el mapeo objeto-relacional y el servidor HTTP Grizzly para manejar las solicitudes.

Funcionalidades principales
Gestión de Hostales: Operaciones CRUD sobre la información de hostales, como ubicación, precios, disponibilidad y servicios.

Búsqueda y Filtrado Avanzado: Soporte para búsquedas multi-criterio que permiten a los usuarios encontrar hostales que mejor se adapten a sus necesidades.

Manejo de Reservaciones: Gestión completa de las reservaciones de los usuarios.

Integración con otros Módulos: Comunicación eficiente con otros módulos del sistema para proporcionar una experiencia de usuario coherente y fluida.

Tecnologías utilizadas
Hibernate: Framework ORM utilizado para mapear las clases de Java a tablas de base de datos y gestionar las operaciones de base de datos de manera eficiente.
Grizzly: Servidor HTTP que proporciona un framework NIO para facilitar el desarrollo de aplicaciones escalables.
Java: Lenguaje de programación utilizado para el desarrollo de la API.
Cómo empezar
Prerrequisitos
Asegúrate de tener instalado Java JDK 8 o superior y Maven para la gestión de dependencias.

Configuración del proyecto
Clonar el repositorio:
git clone https://github.com/HostelHunter/DBAPI.git

Entrar al directorio del proyecto:
cd DBAPI

Compilar el proyecto con Maven:
mvn clean install

Ejecutar la aplicación:
java -jar target/dbapi.jar

Soporte
Para preguntas o problemas relacionados con la DBAPI, no dudes en abrir un issue en nuestro repositorio de GitHub.

¡Agradecemos tu interés en contribuir a HostelHunter y deseamos que tu experiencia desarrollando con nosotros sea excelente!
