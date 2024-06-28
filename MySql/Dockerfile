# Usamos la imagen oficial de MySQL
# Usamos la imagen oficial de MySQL
FROM mysql:8.0

# Establecemos las variables de entorno necesarias para la configuración de MySQL
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=horarios

# Copiamos el script de inicialización al contenedor
COPY ./init.sql /docker-entrypoint-initdb.d/

# Exponemos el puerto 3306 (puerto por defecto de MySQL)
EXPOSE 3306


# docker build -t mysql-horarios .
# docker run -d -p 3306:3306 --name mysql-horarios mysql-horarios
