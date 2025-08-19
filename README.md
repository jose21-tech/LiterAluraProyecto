README.md
📚 Literalura
Descripción del Proyecto
Una API RESTful en Java y Spring Boot para buscar y gestionar información de libros, utilizando la API externa Gutendex. El proyecto permite realizar búsquedas y almacenar los datos de los libros y autores en una base de datos PostgreSQL.

Funcionalidades Clave
El proyecto ofrece las siguientes funcionalidades, accesibles a través de un menú en la consola:

Buscar Libro por Título: Busca un libro en la API Gutendex y lo registra en la base de datos si no existe.

Listar Libros Registrados: Muestra todos los libros que han sido guardados en la base de datos.

Listar Autores Registrados: Muestra una lista de todos los autores de los libros guardados.

Listar Autores Vivos en un Año Determinado: Permite filtrar a los autores que estaban vivos en un año específico.

Listar Libros por Idioma: Filtra y muestra los libros según un código de idioma (ej. ES, EN, FR, PT).

Manejo de Errores
La aplicación maneja los errores para garantizar una experiencia de usuario fluida:

Libro no Encontrado: Muestra un mensaje claro si la búsqueda no arroja resultados.

Prevención de Duplicados: Evita que se guarde un libro que ya existe en la base de datos.

Tecnologías Utilizadas
Java 17

Spring Boot

Spring Data JPA

PostgreSQL

Maven

API Gutendex

Configuración y Ejecución
Requisitos
Asegúrate de tener instalado Java 17 y PostgreSQL en tu sistema.

Properties

spring.datasource.url=jdbc:postgresql://localhost:5432/tu_base_de_datos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
Cómo Ejecutar el Proyecto
Clona este repositorio.

Abre el proyecto en tu IDE (como IntelliJ IDEA o Eclipse).

Configura tus credenciales de PostgreSQL como se indica arriba.

Ejecuta la clase principal LiteraluraApplication.

Interactúa con el menú de la consola para usar las funcionalidades del conversor.
