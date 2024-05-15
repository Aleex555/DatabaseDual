# HostelHunter

## Descripción
HostelHunter es un proyecto diseñado para ayudar a los viajeros a encontrar y comparar hostales alrededor del mundo. Nuestra plataforma facilita la búsqueda de alojamientos que se ajusten a las necesidades y presupuestos de los usuarios, asegurando una experiencia de viaje óptima y accesible.

## Módulo: DBAPI

### Descripción del módulo DBAPI

El módulo `DBAPI` de HostelHunter es crucial para el funcionamiento integral del sistema. Esta API gestiona todas las interacciones con la base de datos, utilizando Java con Hibernate para el mapeo objeto-relacional y el servidor HTTP Grizzly para manejar las solicitudes.

### Funcionalidades principales

1. **Gestión de Alojamientos**:
   - **Agregar Alojamientos**: Permite a los administradores añadir nuevos alojamientos a la base de datos.
   - **Editar Alojamientos**: Los detalles existentes de los alojamientos pueden ser modificados.
   - **Eliminar Alojamientos**: Permite a los administradores remover alojamientos de la base de datos.
   - **Operaciones CRUD sobre la información de alojamientos**: Gestiona ubicación, precios, disponibilidad, y servicios de cada alojamiento.

2. **Manejo de Reservaciones**:
   - **Crear Reservas**: Los usuarios pueden hacer reservaciones en línea para los alojamientos disponibles.
   - **Visualización de Reservas**: Los usuarios y administradores pueden ver todas las reservas activas y pasadas.

3. **Gestión de Likes**:
   - Los usuarios pueden expresar su preferencia por un alojamiento específico a través de "likes", lo cual ayuda a otros usuarios a decidir y a los administradores a entender la popularidad de los alojamientos.

4. **Integración con otros Módulos**:
   - Comunicación eficiente con otros módulos del sistema para proporcionar una experiencia de usuario coherente y fluida.

### Tecnologías utilizadas

- **Hibernate**: Framework ORM utilizado para mapear las clases de Java a tablas de base de datos y gestionar las operaciones de base de datos de manera eficiente.
- **Grizzly**: Servidor HTTP que proporciona un framework NIO para facilitar el desarrollo de aplicaciones escalables.
- **Java**: Lenguaje de programación utilizado para el desarrollo de la API.

### Cómo empezar

#### Prerrequisitos

Asegúrate de tener instalado **Java JDK 8** o superior y **Maven** para la gestión de dependencias.

#### Configuración del proyecto

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/Aleex555/hostelHunterDBAPI


2. **Compilar el proyecto con Maven:**
   ```bash
   mvn clean install

3. **Ejecutar la aplicación:**
   ```bash
   sudo /bin/bash ./run.sh cat.iesesteveterradas.dbapi.AppMain