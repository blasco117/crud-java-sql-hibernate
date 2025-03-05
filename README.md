CRUD en java con MySQL y Hibernate

crud en java hecho con hibernate, mysql y swing para la interfaz grafica, un sistema de gestion simple de usuarios con todas las funciones de listar, eliminar, guardar, etc.

CONFIGURAR ANTES DE USAR:

-en SQL crear una base de datos y la tabla con los siguientes datos de la tabla Usuarios:

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    contraseña VARCHAR(255) NOT NULL,
    correo VARCHAR(255) UNIQUE NOT NULL,
    direccion TEXT,
    telefono VARCHAR(20)
);

-Configurar el archivo hibernate.cfg.xml:

agregar al lado de la url de configuracion el nombre la base de datos creada y tambien agregar el nombre de usuario y contraseña.
