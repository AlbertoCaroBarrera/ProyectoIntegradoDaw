[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/fE1utJVw)


# Proyecto Horarios de Profesores

## Tegnologias usadas

- Docker
- MySql
- Java
- Spring Boot
- JavaScripts
- Angular
- PrimeNg
- PrimeFlex

## Configuración del entorno

1. Clona este repositorio en tu máquina local.

2. Inicia los contenedores utilizando Docker:
   
- En este [enlace](https://hub.docker.com/r/alberto1994/mysql) podemos encontrar mi contenedor docker con mysql


3. Una vez iniciado el contenedor docker, disponemos de la BBDD en MySql. En mi caso usaré Dbeaver para ver los datos de la BBDD.

4. Una vez iniciada la BBDD accedemos a nuestro proyecto de spring-horarios.

Configuración del Backend (Spring Boot)
Asegúrate de tener Java Development Kit (JDK) y Eclipse IDE instalados en tu sistema.

5. Importa el proyecto backend en Eclipse:

Abre Eclipse IDE.
Selecciona "File" -> "Import" -> "Existing Maven Projects".
Navega hasta el directorio tu-proyecto/backend y selecciona el archivo pom.xml.
Haz clic en "Finish" para importar el proyecto.
Una vez importado el proyecto, asegúrate de que todas las dependencias se hayan descargado correctamente. Puedes hacer clic derecho en el proyecto y seleccionar "Maven" -> "Update Project" para forzar la actualización de las dependencias.

Localiza la clase principal de la aplicación Spring Boot, generalmente llamada Application.java o MainApplication.java, y ábrela.

Haz clic derecho en la clase principal y selecciona "Run As" -> "Java Application" para iniciar la aplicación backend.

La aplicación backend se ejecutará en http://localhost:8080

6. Para acceder al desarrollo de Angular
   Desde la terminal:

```
    cd Angular-Horarios
```
Podemos abrir el proyecto de Angular en VsCode con:
```
    code .
```

Recuerde instalar todas las dependencias con el siguiente comando:
```
    npm install
```

Para ejecutar el servidor de Angular ejecutamos:
```
    ng serve
```

# Resumen de Funcionalidades

Este proyecto es una aplicación full stack compuesta por un frontend en Angular y un backend en Spring Boot. A continuación se detallan las funcionalidades principales de ambos componentes.

## Base de Datos

- **Carga de Datos**: Los datos se cargan en la base de datos MySQL desde archivos XML ubicados en una carpeta específica.
- **MySQL en Docker**: La base de datos MySQL está disponible como una imagen Docker en [DockerHub](https://hub.docker.com/repository/docker/alberto1994/mysql/general).
-  **Tambien puedes ejecutar el Dockerfile de la carpeta Mysql y se instalará desde cero.**

## Backend - Spring Boot

- **Spring Security**: Integración y configuración de Spring Security para controlar el acceso a los recursos.
- **JWT**: Implementación de JSON Web Tokens (JWT) para proporcionar autenticación basada en token.
- **Sistema de Inicio de Sesión**: Permite a los usuarios autenticarse en la aplicación.
- **Sistema de Registro**: Permite a los usuarios registrarse en la aplicación.
- **Cifrado de Contraseñas**: Las contraseñas de los usuarios se cifran antes de almacenarse en la base de datos.
- **Operaciones CRUD**: Operaciones básicas de gestión de datos para todas las entidades de la aplicación.
- **Peticiones HTTP**: 
  - **GET**: Acceso sin autenticación.
  - **POST, DELETE, PUT**: Requieren autenticación de usuario con rol de ADMIN.

## Frontend - Angular

- **Angular 17**: Desarrollo del frontend utilizando Angular 17 con Standalone integrado.
- **PrimeNG y PrimeFlex**: Uso de estos frameworks para el diseño y estilos.
- **Menú de Navegación**: Menú con acceso a los Cruds y funcionalidades de la página.
- **Login**: Sistema de autenticación mediante login.
- **Registro**: HTML creado para el registro de usuarios, pendiente de integración.
- **CRUDs**: Gestión de todos los modelos.
- **Seguridad**: Gestión de autenticación mediante JWT almacenado en el Session Storage.
- **Validaciones y Mensajes**: Validaciones básicas y mensajes de error y confirmación al crear datos.

## Despliegue

- **Docker para Angular**:
  - **Dockerfile**: Configuración para construir y ejecutar la aplicación Angular.
  - **Comandos**:
    ```
    docker build -t angular-docker .
    docker run -p 4200:4200 angular-docker
    ```

## Pendiente
- **Testing: Pruebas Unitarias**

Para completar el apartado de pruebas, necesitamos añadir pruebas unitarias tanto para el backend (Spring) como para el frontend (Angular).

## Pruebas Unitarias para Spring (Backend)

Las pruebas unitarias para Spring nos permiten verificar de manera aislada el comportamiento de las clases y métodos
individuales en nuestro backend. Aquí están los pasos a seguir:

**Configurar JUnit y Mockito:**

Asegurarnos de que las dependencias de JUnit y Mockito están correctamente configuradas en nuestro archivo pom.xml.
Escribir Pruebas Unitarias:
Crear clases de prueba para cada clase de servicio, controlador y
repositorio.
Utilizar Mockito para crear objetos simulados (mocks) de las
dependencias.
Verificar el comportamiento de los métodos utilizando aserciones
de JUnit.
Ejecutar y Validar Pruebas.


## Pruebas Unitarias para Angular (Frontend)
Las pruebas unitarias para Angular nos permiten asegurar que los componentes, servicios y otros elementos del frontend funcionan
de manera aislada. Aquí están los pasos a seguir:

**Configurar Jasmine y Karma:**

Asegurarnos de que las dependencias de Jasmine y Karma están correctamente configuradas en nuestro archivo angular.json.
Escribir Pruebas Unitarias:
Crear archivos de prueba (.spec.ts) para cada componente, servicio y pipe.
Ejecutar las pruebas con el comando ng test.
Revisar los resultados en el navegador o en la consola y asegurar que todas las pruebas pasen correctamente.
